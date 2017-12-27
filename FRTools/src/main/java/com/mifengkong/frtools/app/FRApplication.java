package com.mifengkong.frtools.app;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.mifengkong.frtools.resource.FRResource;

/**
 * 全局唯一的Application实例，只要继承MyApplication就可以了，MyApplication会把this存到单实例引用
 */
public abstract class FRApplication extends MultiDexApplication {

    public boolean restartApp = false;

    private static FRApplication sInstance;

    /**
     * 主线程ID
     */
    public static int mMainThreadId = -1;
    /**
     * 主线程ID
     */
    public static Thread mMainThread;
    /**
     * 主线程Handler
     */
    public static Handler mMainThreadHandler;
    /**
     * 主线程Looper
     */
    public static Looper mMainLooper;


    /**
     * 获取主线程ID
     */
    public static int getMainThreadId() {
        return mMainThreadId;
    }

    /**
     * 获取主线程
     */
    public static Thread getMainThread() {
        return mMainThread;
    }

    /**
     * 获取主线程的handler
     */
    public static Handler getMainThreadHandler() {
        return mMainThreadHandler;
    }

    /**
     * 获取主线程的looper
     */
    public static Looper getMainThreadLooper() {
        return mMainLooper;
    }

    public static FRApplication getInstance() {
        return sInstance;
    }

    protected FRApplication() {
        sInstance = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // queryAndLoadNewPatch不可放在attachBaseContext 中，否则无网络权限，建议放在后面任意时刻，如onCreate中
//        SophixManager.getInstance().queryAndLoadNewPatch();
        mMainThreadId = android.os.Process.myTid();
        mMainThread = Thread.currentThread();
        mMainThreadHandler = new Handler();
        mMainLooper = getMainLooper();

        FRResource.setFontScaleToDefault(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }


}
