package com.mifengkong.frtools.util;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;

import static com.mifengkong.frtools.util.FRToast.getResources;

/**
 * 单位换算的封装
 */
public class FRDisplayMetrics {

    public FRDisplayMetrics() {

    }

    /**
     * dp转px
     */
    public static int dp2px(Context context,float dp) {
        return (int) (dp * context.getResources().getDisplayMetrics().density + 0.5f);
    }

    /**
     * px转dp
     */
    public static int px2dp(Context context, int px) {
        return (int) (px / context.getResources().getDisplayMetrics().density + 0.5f);
    }

    // px转sp
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值
     */
    public static int sp2px(Context context, float sp) {
        return (int) (sp * context.getResources().getDisplayMetrics().scaledDensity + 0.5f);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////

    private static int sScreenWidth = 0;
    private static int sScreenHeight = 0;

    public static int getScreenWidth(Activity activity) {
        if (sScreenWidth == 0) {
            initScreenSize(activity);
        }
        return sScreenWidth;
    }

    public static int getScreenHeight(Activity activity) {
        if (sScreenHeight == 0) {
            initScreenSize(activity);
        }
        return sScreenHeight;
    }

    private static void initScreenSize(Activity activity) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        sScreenWidth = displayMetrics.widthPixels;
        sScreenHeight = displayMetrics.heightPixels;
    }

    /**
     * 获得状态栏的高度
     *
     * @param context
     * @return
     */
    public static int getStatusHeight(Context context) {

        int statusHeight = -1;
        try {
            Class clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    /**
     * 将整形转成16进制的颜色值
     *
     * @param color_int
     * @return
     */
    public static String hexColor(int color_int) {
        return String.format("#%06X", (0xFFFFFF & getResources().getColor(color_int)));
    }

    public static void setWidgetScaleAnim(int start, int end, final View view){
        ValueAnimator mAnimator = ValueAnimator.ofInt(start, end);
        mAnimator.setInterpolator(new DecelerateInterpolator());
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator arg0) {
                int value = (int) arg0.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                layoutParams.height = value;
                view.setLayoutParams(layoutParams);
            }
        });
        mAnimator.setDuration(300);
        mAnimator.start();
    }

    /**
     * 对view设置margin
     * @param v
     * @param l
     * @param t
     * @param r
     * @param b
     */
    public static void setMargins(View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }
}
