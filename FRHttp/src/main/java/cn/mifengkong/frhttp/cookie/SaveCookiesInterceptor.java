package cn.mifengkong.frhttp.cookie;


import com.mifengkong.frtools.util.FRSharedPreferences;

import java.io.IOException;
import java.util.HashSet;

import cn.mifengkong.frhttp.util.CookieConstant;
import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by jiangyongxing on 2017/9/12.
 * 描述：
 */

public class SaveCookiesInterceptor implements Interceptor {

    private String loginUrl = "!@#$%^&*";

    public SaveCookiesInterceptor(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    public SaveCookiesInterceptor() {
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Response originalResponse = chain.proceed(chain.request());
        if (!originalResponse.headers("Set-Cookie").isEmpty() && chain.request().url().toString().contains(loginUrl)) {
            HashSet<String> cookies = new HashSet<>();
            cookies.addAll(originalResponse.headers("Set-Cookie"));

            FRSharedPreferences.setStringSet(CookieConstant.SP_NAME_COOKIES, CookieConstant.PREF_COOKIES, cookies);
        }
        return originalResponse;
    }
}
