package com.mifengkong.frtools.handler;

import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;

import com.mifengkong.frtools.util.FRLog;


public class FRHandler extends Handler {

    public FRHandler() {
        super();
    }

    public FRHandler(Callback callback) {
        super(callback);
    }

    public FRHandler(Looper looper) {
        super(looper);
    }

    public FRHandler(Looper looper, Callback callback) {
        super(looper, callback);
    }

    public final boolean postDelayed(Runnable runnable, Object token, long delayMillis){
        FRLog.verbose("postDelayed");
        if (delayMillis < 0) {
            delayMillis = 0;
        }
        try {
            return postAtTime(runnable, token, SystemClock.uptimeMillis() + delayMillis);
        } catch (Exception e) {
            FRLog.error("postAtTime error", e);
            return false;
        }
    }

    public final boolean post(Runnable runnable, Object token){
        return postDelayed(runnable, token, 0);
    }
}
