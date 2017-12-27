package com.mifengkong.frtools.permission;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.mifengkong.frtools.statusbar.MIUITypeUtils;
import com.mifengkong.frtools.util.FRToast;

/**
 * Created by jiangyongxing on 2017/6/28.
 * 描述：
 */

public class GoToPermissionSetting {

    private static final String TAG = "jiang";

    public static final int REQUEST_CODE_PERMISSION_SETTING = 11;
    /**
     * Build.MANUFACTURER
     */
    private static final String MANUFACTURER_HUAWEI = "HUAWEI";//华为
    private static final String MANUFACTURER_MEIZU = "Meizu";//魅族
    private static final String MANUFACTURER_XIAOMI = "Xiaomi";//小米
    private static final String MANUFACTURER_SONY = "Sony";//索尼
    private static final String MANUFACTURER_OPPO = "OPPO";
    private static final String MANUFACTURER_LG = "LG";
    private static final String MANUFACTURER_LGE = "LGE";
    private static final String MANUFACTURER_VIVO = "vivo";
    private static final String MANUFACTURER_SAMSUNG = "samsung";//三星
    private static final String MANUFACTURER_LETV = "Letv";//乐视
    private static final String MANUFACTURER_LEMOBILE = "LeMobile";//乐视
    private static final String MANUFACTURER_ZTE = "ZTE";//中兴
    private static final String MANUFACTURER_YULONG = "YuLong";//酷派
    private static final String MANUFACTURER_LENOVO = "LENOVO";//联想

    /**
     * 此函数可以自己定义
     *
     * @param activity
     */
    public static void GoToSetting(Activity activity) {
        try {
//            FRLog.e("jiang",Build.MANUFACTURER);
            switch (Build.MANUFACTURER) {
                case MANUFACTURER_HUAWEI:
                    Huawei(activity);
                    break;
                case MANUFACTURER_MEIZU:
                    Meizu(activity);
                    break;
                case MANUFACTURER_XIAOMI:
                    Xiaomi(activity);
                    break;
                case MANUFACTURER_SONY:
                    Sony(activity);
                    break;
                case MANUFACTURER_OPPO:
                    OPPO(activity);
                    break;
                case MANUFACTURER_LG:
                case MANUFACTURER_LGE:
                    LG(activity);
                    break;
                case MANUFACTURER_LETV:
                case MANUFACTURER_LEMOBILE:
                    Letv(activity);
                    break;
                case MANUFACTURER_VIVO:
                    VIVO(activity);
                    break;
                case MANUFACTURER_SAMSUNG:
                    SAMSUNG(activity);
                    break;
                default:
                    ApplicationInfo(activity);
                    break;
            }


        } catch (Exception e) {
            FRToast.showToastSafe("暂不支持您的手机型号，请到设置中打开");
        }
    }

    private static void SAMSUNG(Activity activity) {
        ApplicationInfo(activity);
    }

    /**
     * Vivo X9测试成功
     *
     * @param activity
     */
    private static void VIVO(Activity activity) {
        Intent intent = new Intent();
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("packageName", activity.getPackageName());
        ComponentName comp = new ComponentName("com.iqoo.secure", "com.iqoo.secure.safeguard.PurviewTabActivity");
        intent.setComponent(comp);
        activity.startActivityForResult(intent, REQUEST_CODE_PERMISSION_SETTING);
    }

    /**
     * 华为荣耀NEM-TL00c测试成功
     *
     * @param activity
     */
    public static void Huawei(Activity activity) {
        Intent intent = new Intent();
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("packageName", activity.getPackageName());
        ComponentName comp = new ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity");
        intent.setComponent(comp);
        activity.startActivityForResult(intent, REQUEST_CODE_PERMISSION_SETTING);
    }

    /**
     * MX4测试可用
     *
     * @param activity
     */
    public static void Meizu(Activity activity) {
        Intent intent = new Intent("com.meizu.safe.security.SHOW_APPSEC");
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.putExtra("packageName", activity.getPackageName());
        activity.startActivityForResult(intent, REQUEST_CODE_PERMISSION_SETTING);
    }

    /**
     * 红米NOTE，小米4测试可用
     *
     * @param activity
     */
    public static void Xiaomi(Activity activity) {
        MIUITypeUtils.applyMiuiPermission(activity);
    }

    public static void Sony(Activity activity) {
        Intent intent = new Intent();
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("packageName", activity.getPackageName());
        ComponentName comp = new ComponentName("com.sonymobile.cta", "com.sonymobile.cta.SomcCTAMainActivity");
        intent.setComponent(comp);
        activity.startActivityForResult(intent, REQUEST_CODE_PERMISSION_SETTING);
    }

    /**
     * 打开的是手机管家里面应用权限管理界面
     * oppoR9 测试成功
     *
     * @param activity
     */
    public static void OPPO(Activity activity) {
        Intent intent = new Intent();
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("packageName", activity.getPackageName());
        ComponentName comp = new ComponentName("com.coloros.safecenter", "com.coloros.safecenter.permission.singlepage.PermissionSinglePageActivity");
        intent.setComponent(comp);
        activity.startActivityForResult(intent, REQUEST_CODE_PERMISSION_SETTING);
    }

    public static void LG(Activity activity) {
        ApplicationInfo(activity);
    }

    /**
     * 乐视X620测试可用
     *
     * @param activity
     */
    public static void Letv(Activity activity) {
        Intent intent = new Intent();
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("packageName", activity.getPackageName());
        ComponentName comp = new ComponentName("com.letv.android.letvsafe", "com.letv.android.letvsafe.PermissionAndApps");
        intent.setComponent(comp);
        activity.startActivityForResult(intent, REQUEST_CODE_PERMISSION_SETTING);
    }

    /**
     * 只能打开到自带安全软件
     *
     * @param activity
     */
    public static void _360(Activity activity) {
        Intent intent = new Intent("android.intent.action.MAIN");
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("packageName", activity.getPackageName());
        ComponentName comp = new ComponentName("com.qihoo360.mobilesafe", "com.qihoo360.mobilesafe.ui.index.AppEnterActivity");
        intent.setComponent(comp);
        activity.startActivityForResult(intent, REQUEST_CODE_PERMISSION_SETTING);
    }

    /**
     * 应用信息界面
     *
     * @param activity
     */
    public static void ApplicationInfo(Activity activity) {
        Intent localIntent = new Intent();
//        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", activity.getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", activity.getPackageName());
        }
        activity.startActivityForResult(localIntent, REQUEST_CODE_PERMISSION_SETTING);
    }

    /**
     * 系统设置界面
     *
     * @param activity
     */
    public static void SystemConfig(Activity activity) {
        Intent intent = new Intent(Settings.ACTION_SETTINGS);
        activity.startActivityForResult(intent, REQUEST_CODE_PERMISSION_SETTING);
    }

    public static void showSnackBar(final Activity activity, View v) {
        Snackbar snackbar = Snackbar.make(v, "缺少相机或者读取存储卡权限", Snackbar.LENGTH_SHORT);
        snackbar.setActionTextColor(Color.parseColor("#23bd9f"));
        snackbar.setAction("去授权", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoToPermissionSetting.GoToSetting(activity);
            }
        });
        snackbar.show();
    }

}
