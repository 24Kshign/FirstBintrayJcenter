package com.mifengkong.frtools.webview;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

/**
 * Created by jiangyongxing on 2017/9/13.
 * 描述：
 */

public class FRCookieUtil {

    /**
     * 同步一下cookie
     */
    // 设置cookie
    public static void syncCookie(Context context, String url, String cookies, String cookieName) {
        CookieSyncManager.createInstance(context);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.removeSessionCookie();//移除
        if (!TextUtils.isEmpty(cookieManager.getCookie(url)) && cookieManager.getCookie(url).contains(cookieName))
            return;
        cookieManager.setCookie(url, cookies);//cookies是在HttpClient中获得的cookie
        if (Build.VERSION.SDK_INT < 21) {
            CookieSyncManager.getInstance().sync();
        } else {
            CookieManager.getInstance().flush();
        }

    }

    /**
     * 停止同步cookie
     */
    public static void stopSyncCookie() {
        CookieSyncManager.getInstance().stopSync();
    }
}
