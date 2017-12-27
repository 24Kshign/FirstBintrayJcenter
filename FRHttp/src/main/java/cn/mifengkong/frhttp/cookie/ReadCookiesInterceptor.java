package cn.mifengkong.frhttp.cookie;

import com.mifengkong.frtools.util.FRSharedPreferences;

import java.io.IOException;
import java.util.HashSet;

import cn.mifengkong.frhttp.util.CookieConstant;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by jiangyongxing on 2017/9/12.
 * 描述：
 */

public class ReadCookiesInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request.Builder builder = chain.request().newBuilder();
        HashSet<String> preferences = (HashSet<String>) FRSharedPreferences.getStringSet(CookieConstant.SP_NAME_COOKIES,CookieConstant.PREF_COOKIES,new HashSet<String>());
        for (String cookie : preferences) {
            builder.addHeader("Cookie", cookie);
        }
        return chain.proceed(builder.build());
    }
}
