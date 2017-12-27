package com.mifengkong.frtools.controller;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;

import com.mifengkong.frtools.inputmethod.FRInputMethodManager;
import com.mifengkong.frtools.inputmethod.FRView;
import com.mifengkong.frtools.statusbar.FRBlackStatusBarOnWhiteUtil;

import org.greenrobot.eventbus.EventBus;

/**
 *
 */
public class FRActivityController {

    public FRActivityController(Activity activity) {
        mActivity = activity;
    }

    private Activity mActivity;
    private boolean mAutoHideSoftInput = false;
    private boolean isUseEventBus;

    public void onCreate(@Nullable Bundle savedInstanceState) {
        if (isUseEventBus) {
            EventBus.getDefault().register(mActivity);
        }
    }

    public void onDestroy() {
        if (isUseEventBus) {
            EventBus.getDefault().unregister(mActivity);
        }
    }

    public boolean isActivityFinish() {
        return mActivity.isFinishing();
    }

    public boolean dispatchTouchEvent(MotionEvent event) {
        if (mAutoHideSoftInput) {
            FRInputMethodManager.autoHideSoftInput(mActivity, event);
        }
        return false;
    }

    public void setAutoHideSoftInput(boolean autoHide) {
        mAutoHideSoftInput = autoHide;
    }

    public final View layoutInflate(int layoutResource) {
        return FRView.inflateLayout(mActivity, layoutResource);
    }

    public void setUseEventBus(boolean useEventBus) {
        isUseEventBus = useEventBus;
    }

    public void setBlackStatusBarOnWhite(boolean useBlackAndWhite){
        if (useBlackAndWhite)
            FRBlackStatusBarOnWhiteUtil.setStatusBarColorAndFontColor(mActivity);
    }
}