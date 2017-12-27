package cn.mifengkong.frhttp.http.interceptor;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * 作者：孟家敏 on 16/12/8 15:50
 * <p>
 * 邮箱：androidformjm@sina.com
 */
public class ReceivedCookiesInterceptor implements Interceptor {
    private Context context;
    SharedPreferences sharedPreferences;

    public ReceivedCookiesInterceptor(Context context) {
        super();
        this.context = context;
        sharedPreferences = context.getSharedPreferences("cookie", Context.MODE_PRIVATE);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        if (chain == null)
            Log.d("http", "Receivedchain == null");
        Response originalResponse = chain.proceed(chain.request());
        Log.d("http", "originalResponse" + originalResponse.toString());
        if (!originalResponse.headers("set-cookie").isEmpty()) {
            final StringBuffer cookieBuffer = new StringBuffer();
//            Observable.just(originalResponse.headers("set-cookie"))
//                    .map(new Function<String, Object>(){
//                        @Override
//                        public Object apply(@NonNull String s) throws Exception {
//                            String[] cookieArray = s.split(";");
//                            return cookieArray[0];
//                        }
//                    }).subscribe(new Consumer<Object>() {
//                        @Override
//                        public void accept(@NonNull Object cookie) throws Exception {
//                            cookieBuffer.append(cookie).append(";");
//                        }
//                    });
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("cookie", cookieBuffer.toString());
            Log.d("http", "ReceivedCookiesInterceptor" + cookieBuffer.toString());
            editor.commit();
        }

        return originalResponse;
    }
}