package cn.mifengkong.frhttp.http.interceptor;

import android.content.Context;
import android.content.SharedPreferences;

import com.mifengkong.frtools.util.FRLog;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 作者：孟家敏 on 16/12/8 15:51
 * <p>
 * 邮箱：androidformjm@sina.com
 */
public class AddCookiesInterceptor implements Interceptor {
    private Context context;
    private String lang;

    public AddCookiesInterceptor(Context context, String lang) {
        super();
        this.context = context;
        this.lang = lang;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        if (chain == null)
            FRLog.debug("addCookie------->" + "Addchain == null");
        final Request.Builder builder = chain.request().newBuilder();
        SharedPreferences sharedPreferences = context.getSharedPreferences("cookie", Context.MODE_PRIVATE);
        Observable.just(sharedPreferences.getString("cookie", ""))
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String cookie) throws Exception {
                        if (cookie.contains("lang=ch")) {
                            cookie = cookie.replace("lang=ch", "lang=" + lang);
                        }
                        if (cookie.contains("lang=en")) {
                            cookie = cookie.replace("lang=en", "lang=" + lang);
                        }
                        //添加cookie
                        FRLog.debug("addCookie------->" + cookie);
                        builder.addHeader("cookie", cookie);
                    }
                });
        return chain.proceed(builder.build());
    }
}