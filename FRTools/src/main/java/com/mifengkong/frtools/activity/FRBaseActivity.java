package com.mifengkong.frtools.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.mifengkong.frtools.controller.FRActivityController;
import com.mifengkong.frtools.util.FRDisplayMetrics;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


/**
 * Activity基类的封装
 */
public abstract class FRBaseActivity extends FragmentActivity implements FRActivityInterface {

    /**
     * 当前沉浸模式，默认为布局沉浸式
     */
    private String immersionType = TYPE_LAYOUT;
    /**
     * 仅仅改变状态栏颜色的沉浸模式
     */
    public static final String TYPE_LAYOUT = "type_layout";
    /**
     * 将原布局背景扩散至状态栏的沉浸模式
     */
    public static final String TYPE_BACKGROUND = "type_background";

    private final FRActivityController mActivityController = new FRActivityController(this);
    private View mStatusBar;

    @Override
    public boolean dispatchTouchEvent(@NonNull MotionEvent event) {
        if (mActivityController.dispatchTouchEvent(event)) {
            return true;
        }
        return super.dispatchTouchEvent(event);
    }

    protected abstract int layoutRes();

    protected boolean diffStatusBar() {
        return false;
    }

    protected boolean useEventBus() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            if (0 != layoutRes()) {
                if (diffStatusBar()) {
                    setView(layoutRes(), TYPE_BACKGROUND);
                } else {
                    setContentView(layoutRes());
                }
                mActivityController.setBlackStatusBarOnWhite(useBlackStatusBarOnWhite());
                mActivityController.setUseEventBus(useEventBus());
                mActivityController.setAutoHideSoftInput(true);
                mActivityController.onCreate(savedInstanceState);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 是否采用白底黑字状态栏的效果
     *
     * @return
     */
    protected boolean useBlackStatusBarOnWhite() {
        return false;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEventMainThread(BaseActivityEvent messageEvent) {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mActivityController.onDestroy();
    }

    @Override
    public Activity thisActivity() {
        return this;
    }

    /**
     * 设置点击edittext外部隐藏键盘
     *
     * @param autoHide
     */
    @Override
    public void setAutoHideSoftInput(boolean autoHide) {
        mActivityController.setAutoHideSoftInput(autoHide);
    }

    @Override
    public View layoutInflate(int layoutResource) {
        return mActivityController.layoutInflate(layoutResource);
    }

    /**
     * 子类设置布局时应调用该方法
     *
     * @param resId 布局id
     */
    public void setView(int resId) {
        View contentView = View.inflate(this, resId, null);
        setView(contentView);
    }

    /**
     * @param resId 布局Id
     * @param type  浸入模式:TYPE_BACKGROUND或者TYPE_LAYOUT
     */
    public void setView(int resId, String type) {
        immersionType = type;
        setView(resId);
    }

    /**
     * 对4.4以下设备适配沉浸式状态栏
     */
    private void setView(View contentView) {
        //去掉状态栏布局
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //当API>=21时，状态栏会自动增加一块半透明色块，这段代码将其设为透明色
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getWindow();
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                        | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);
            }
            if (immersionType.equals(TYPE_LAYOUT)) {
                LinearLayout ll_content = new LinearLayout(this);
                ll_content.setBackgroundColor(Color.TRANSPARENT);
                ll_content.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
                ll_content.setOrientation(LinearLayout.VERTICAL);
                addStatusBar(ll_content);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                ll_content.addView(contentView, lp);
                setContentView(ll_content);
            } else if (immersionType.equals(TYPE_BACKGROUND)) {
                contentView.setPadding(0, FRDisplayMetrics.getStatusHeight(this), 0, 0);
                setContentView(contentView);
            }

        } else {
            setContentView(contentView);
        }
    }

    /**
     * 将状态栏添加至布局中
     *
     * @param viewGroup
     */
    private void addStatusBar(ViewGroup viewGroup) {
        mStatusBar = new View(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                FRDisplayMetrics.getStatusHeight(this));
        mStatusBar.setLayoutParams(lp);
        viewGroup.addView(mStatusBar);
    }
}