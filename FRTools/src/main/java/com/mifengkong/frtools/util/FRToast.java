package com.mifengkong.frtools.util;

import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.mifengkong.frtools.app.FRApplication;

import java.lang.reflect.Field;
import java.lang.reflect.Method;


/**
 * 对Toast封装
 */
public final class FRToast {

    private static final int WHAT_HIDE_TOAST = 100;
    private static final int TOAST_DISAPPLY_TIME = 1000;


    private FRToast() {

    }

    private static Toast mToast = null;

    private static HideHandler myHandler = new HideHandler();


    /**
     * 获取资源
     */
    public static Resources getResources() {
        return FRApplication.getInstance().getResources();
    }

    /**
     * 获取文字
     */
    public static String getString(int resId) {
        return getResources().getString(resId);
    }

    public static long getMainThreadId() {
        return FRApplication.getMainThreadId();
    }

    /**
     * 获取主线程的handler
     */
    public static Handler getHandler() {
        return FRApplication.getMainThreadHandler();
    }

    /**
     * 在主线程执行runnable
     */
    public static boolean post(Runnable runnable) {
        return getHandler().post(runnable);
    }

    /**
     * 判断当前的线程是不是在主线程
     *
     * @return
     */
    public static boolean isRunInMainThread() {
        return android.os.Process.myTid() == getMainThreadId();
    }

    /**
     * 对toast的简易封装。线程安全，可以在非UI线程调用。
     */
    public static void showToastSafe(final int resId) {
        showToastSafe(getString(resId));
    }

    /**
     * 对toast的简易封装。线程安全，可以在非UI线程调用。
     */
    public static void showToastSafe(final String str) {
        if (isRunInMainThread()) {
            showToast(str);
        } else {
            post(new Runnable() {
                @Override
                public void run() {
                    showToast(str);
                }
            });
        }
        myHandler.sendEmptyMessageDelayed(WHAT_HIDE_TOAST, TOAST_DISAPPLY_TIME);

    }

    private static void showToast(String str) {
        Context context = FRApplication.getInstance();
        if (context != null) {
            showToast(context, str);
        }
    }

    public static void showToast(Context context, String text) {
        if (mToast == null) {
            mToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(text);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }

        mToast.show();
    }


    static class HideHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == WHAT_HIDE_TOAST) {
                try {
                    Class<Toast> toastClass = Toast.class;
                    Field mTN = toastClass.getDeclaredField("mTN");
                    mTN.setAccessible(true);
                    Object o = mTN.get(mToast);
                    Class<?> aClass = o.getClass();
                    Method hide = aClass.getDeclaredMethod("hide");
                    hide.setAccessible(true);
                    hide.invoke(o);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
