package com.mifengkong.frtools.inputmethod;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.IBinder;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.mifengkong.frtools.systemserver.FRSystemService;
import com.mifengkong.frtools.util.FRObject;


/**
 * 显示键盘的工具类
 */
public class FRInputMethodManager {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // show

    public static void showInputMethod(Context context, View view) {
        if (FRObject.isNullObject(view)) {
            return;
        }
        InputMethodManager inputMethodManager = FRSystemService.getInputMethodManager(context);
        if (FRObject.isNullObject(inputMethodManager)) {
            return;
        }
        inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
    }

    public static void showInputMethod(View view) {
        if (FRObject.isNullObject(view)) {
            return;
        }
        showInputMethod(view.getContext(), view);
    }

    public static void showInputMethod(Activity activity) {
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // hide

    public static void hideSoftInput(Context context, View view) {
        if (FRObject.isNullObject(view)) {
            return;
        }
        IBinder token = view.getWindowToken();
        if (FRObject.isNullObject(token)) {
            return;
        }
        InputMethodManager inputMethodManager = FRSystemService.getInputMethodManager(context);
        if (FRObject.isNullObject(inputMethodManager)) {
            return;
        }
        inputMethodManager.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public static void hideSoftInput(View view) {
        if (FRObject.isNullObject(view)) {
            return;
        }
        hideSoftInput(view.getContext(), view);
    }

    public static void hideSoftInput(Activity activity) {
        hideSoftInput(activity, activity.getCurrentFocus());
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // toggle

    public static void toggleSoftInput(Context context) {
        InputMethodManager inputMethodManager = FRSystemService.getInputMethodManager(context);
        if (FRObject.isNullObject(inputMethodManager)) {
            return;
        }
        inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    //isShowing
    public static boolean isSoftShowing(Activity activity) {
        //获取当前屏幕内容的高度
        int screenHeight = activity.getWindow().getDecorView().getHeight();
        //获取View可见区域的bottom
        Rect rect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        return screenHeight - rect.bottom != 0;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////

    public static void autoHideSoftInput(Activity activity, MotionEvent event) {
        if (event.getAction() != MotionEvent.ACTION_DOWN) {
            return;
        }

        View focusView = activity.getCurrentFocus();
        if (focusView == null) {
            return;
        }
        if (!(focusView instanceof EditText)) {
            return;
        }

        float x = event.getX();
        float y = event.getY();

        int[] location = {0, 0};
        focusView.getLocationInWindow(location);
        int left = location[0];
        int top = location[1];
        int bottom = top + focusView.getHeight();
        int right = left + focusView.getWidth();

        if (left <= x && x < right && top <= y && y < bottom) {
            // 点击事件在EditText的区域里
            return;
        }
        FRInputMethodManager.hideSoftInput(activity, focusView);
    }
}
