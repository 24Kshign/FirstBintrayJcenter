package com.mifengkong.frtools.handler;

import android.os.Looper;

public final class FRMainLooperHandler extends FRHandler {

    private static final class SingletonHolder {
        private static final FRMainLooperHandler INSTANCE = new FRMainLooperHandler();
    }

    public static FRMainLooperHandler getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private FRMainLooperHandler() {
        super(Looper.getMainLooper());
    }
}
