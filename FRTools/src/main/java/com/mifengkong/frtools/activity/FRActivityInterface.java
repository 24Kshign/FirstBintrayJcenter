package com.mifengkong.frtools.activity;

import android.app.Activity;
import android.view.View;

/**
 *
 */
public interface FRActivityInterface {
    void setAutoHideSoftInput(boolean autoHide);
    View layoutInflate(int layoutResource);
    Activity thisActivity();
}
