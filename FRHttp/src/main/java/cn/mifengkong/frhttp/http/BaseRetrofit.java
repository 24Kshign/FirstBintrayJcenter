package cn.mifengkong.frhttp.http;

import android.accounts.NetworkErrorException;
import android.text.TextUtils;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.mifengkong.frtools.app.FRServletAddress;
import com.mifengkong.frtools.util.FRDebugMode;
import com.mifengkong.frtools.util.FRString;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import cn.mifengkong.frhttp.cookie.ReadCookiesInterceptor;
import cn.mifengkong.frhttp.cookie.SaveCookiesInterceptor;
import cn.mifengkong.frhttp.http.cloudapi.CloudInterceptorUtil;
import cn.mifengkong.frhttp.http.cloudapi.FRCloudAPIUtil;
import cn.mifengkong.frhttp.http.convert.CustomGsonConverterFactory;
import cn.mifengkong.frhttp.http.convert.ToStringConverterFactory;
import cn.mifengkong.frhttp.http.interceptor.BasicParamsInterceptor;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

/**
 *
 */

public abstract class BaseRetrofit {

    protected Retrofit mRetrofit;
    private static final int DEFAULT_TIME = 20;    //默认超时时间

    public BaseRetrofit() {
        super();
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        builder.readTimeout(DEFAULT_TIME, TimeUnit.SECONDS);
        builder.connectTimeout(DEFAULT_TIME, TimeUnit.SECONDS);
        boolean isKamao = !TextUtils.isEmpty(useOtherBaseUrl());
        //设置拦截器
        builder.addInterceptor(new BasicParamsInterceptor.Builder().addParamsMap(getCommonMap()).build());
        builder.addInterceptor(new ReadCookiesInterceptor());
        builder.addInterceptor(new SaveCookiesInterceptor(loginUrl()));
        if (null != FRCloudAPIUtil.getCloudAPIBean() && !FRString.isEmpty(FRCloudAPIUtil.getCloudAPIBean().getBaseUrl(isKamao))) {
            builder.addInterceptor(CloudInterceptorUtil.getCloudInterceptor(isKamao));
        }
        //是否不打印LOG
        if (FRDebugMode.isShowLog())
            builder.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
        OkHttpClient okHttpClient = builder.build();
        mRetrofit = new Retrofit.Builder()
                .baseUrl(getBaseUrl(isKamao))
                .client(okHttpClient)
                .addConverterFactory(new ToStringConverterFactory())
                .addConverterFactory(CustomGsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    private String getBaseUrl(boolean isKamao) {
        //使用阿里云网关
        if (FRCloudAPIUtil.isUserCloudAPiHost()) {
            if (FRString.isEmpty(FRCloudAPIUtil.getCloudAPIBean().getBaseUrl(isKamao))) {
                return FRString.isEmpty(useOtherBaseUrl()) ? FRServletAddress.getInstance().getServletAddress()
                        : FRServletAddress.getInstance().getCashAdvanceServletAddress();
            }
            return FRCloudAPIUtil.getCloudAPIBean().getBaseUrl(isKamao);
        } else {
            return FRString.isEmpty(useOtherBaseUrl()) ? FRServletAddress.getInstance().getServletAddress()
                    : FRServletAddress.getInstance().getCashAdvanceServletAddress();
        }
    }

    /**
     * 当用户登录的时候，我们这里需要将服务器传的cookie给保存起来，其他的请求接口就不用
     *
     * @return
     */
    protected String loginUrl() {
        return "!@#$%^&*";
    }

    protected abstract String useOtherBaseUrl();

    protected abstract Map<String, String> getCommonMap();

    protected <T> void toSubscribe(Observable<T> observable, Observer<T> observer) {
        observable.subscribeOn(Schedulers.io())    // 指定subscribe()发生在IO线程
                .unsubscribeOn(Schedulers.io())    // 指定取消subscribe()发生在IO线程
                .observeOn(AndroidSchedulers.mainThread())  // 指定Subscriber的回调发生在io线程
                .timeout(DEFAULT_TIME, TimeUnit.SECONDS)    //重连间隔时间
                .retryWhen(new Function<Observable<Throwable>, ObservableSource<?>>() {
                    private int mRetryCount;

                    @Override
                    public ObservableSource<?> apply(@NonNull Observable<Throwable> throwableObservable) throws Exception {
                        return throwableObservable.flatMap(new Function<Throwable, ObservableSource<?>>() {
                            @Override
                            public ObservableSource<?> apply(@NonNull Throwable throwable) throws Exception {
                                if ((throwable instanceof NetworkErrorException
                                        || throwable instanceof ConnectException
                                        || throwable instanceof SocketTimeoutException
                                        || throwable instanceof TimeoutException) && mRetryCount < 3) {
                                    mRetryCount++;
                                    return Observable.timer(2000, TimeUnit.MILLISECONDS);
                                }
                                return Observable.error(throwable);
                            }
                        });
                    }
                })
                .subscribe(observer);   //订阅
    }

    protected static <T> T getPresent(Class<T> cls) {
        T instance = null;
        try {
            instance = cls.newInstance();
            if (instance == null) {
                return null;
            }
            return instance;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}