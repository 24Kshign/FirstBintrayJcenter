package com.mifengkong.frtools.systemserver;

import android.accounts.AccountManager;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.AppOpsManager;
import android.app.DownloadManager;
import android.app.KeyguardManager;
import android.app.NotificationManager;
import android.app.SearchManager;
import android.app.UiModeManager;
import android.app.admin.DevicePolicyManager;
import android.app.job.JobScheduler;
import android.app.usage.NetworkStatsManager;
import android.app.usage.UsageStatsManager;
import android.appwidget.AppWidgetManager;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.RestrictionsManager;
import android.content.pm.LauncherApps;
import android.hardware.ConsumerIrManager;
import android.hardware.SensorManager;
import android.hardware.camera2.CameraManager;
import android.hardware.display.DisplayManager;
import android.hardware.fingerprint.FingerprintManager;
import android.hardware.input.InputManager;
import android.hardware.usb.UsbManager;
import android.location.LocationManager;
import android.media.AudioManager;
import android.media.MediaRouter;
import android.media.midi.MidiManager;
import android.media.projection.MediaProjectionManager;
import android.media.session.MediaSessionManager;
import android.media.tv.TvInputManager;
import android.net.ConnectivityManager;
import android.net.nsd.NsdManager;
import android.net.wifi.p2p.WifiP2pManager;
import android.nfc.NfcManager;
import android.os.BatteryManager;
import android.os.DropBoxManager;
import android.os.PowerManager;
import android.os.UserManager;
import android.os.Vibrator;
import android.os.storage.StorageManager;
import android.print.PrintManager;
import android.service.wallpaper.WallpaperService;
import android.telecom.TelecomManager;
import android.telephony.CarrierConfigManager;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.ClipboardManager;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.CaptioningManager;
import android.view.inputmethod.InputMethodManager;
import android.view.textservice.TextServicesManager;

import com.mifengkong.frtools.app.FRApplication;


/**
 * 系统服务
 */
public final class FRSystemService {

    private FRSystemService() {
    }

    public static InputMethodManager getInputMethodManager(Context context) {
        if (context == null) {
            context = FRApplication.getInstance();
        }
        return (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    public static InputMethodManager getInputMethodManager() {
        return getInputMethodManager(null);
    }

    public static AlarmManager getAlarmManager() {
        return (AlarmManager) FRApplication.getInstance().getSystemService(Context.ALARM_SERVICE);
    }

    public static PowerManager getPowerManager() {
        return (PowerManager) FRApplication.getInstance().getSystemService(Context.POWER_SERVICE);
    }

    public static WindowManager getWindowManager() {
        return (WindowManager) FRApplication.getInstance().getSystemService(Context.WINDOW_SERVICE);
    }

    public static LayoutInflater getLayoutInflater() {
        return (LayoutInflater) FRApplication.getInstance().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public static AccountManager getAccountManager() {
        return (AccountManager) FRApplication.getInstance().getSystemService(Context.ACCOUNT_SERVICE);
    }

    public static ActivityManager getActivityManager() {
        return (ActivityManager) FRApplication.getInstance().getSystemService(Context.ACTIVITY_SERVICE);
    }

    public static NotificationManager getNotificationManager() {
        return (NotificationManager) FRApplication.getInstance().getSystemService(Context.NOTIFICATION_SERVICE);
    }

    public static AccessibilityManager getAccessibilityManager() {
        return (AccessibilityManager) FRApplication.getInstance().getSystemService(Context.ACCESSIBILITY_SERVICE);
    }

    public static CaptioningManager getCaptioningManager() {
        return (CaptioningManager) FRApplication.getInstance().getSystemService(Context.CAPTIONING_SERVICE);
    }

    public static KeyguardManager getKeyguardManager() {
        return (KeyguardManager) FRApplication.getInstance().getSystemService(Context.KEYGUARD_SERVICE);
    }

    public static LocationManager getLocationManager() {
        return (LocationManager) FRApplication.getInstance().getSystemService(Context.LOCATION_SERVICE);
    }

    public static SearchManager getSearchManager() {
        return (SearchManager) FRApplication.getInstance().getSystemService(Context.SEARCH_SERVICE);
    }

    public static SensorManager getSensorManager() {
        return (SensorManager) FRApplication.getInstance().getSystemService(Context.SENSOR_SERVICE);
    }

    public static StorageManager getStorageManager() {
        return (StorageManager) FRApplication.getInstance().getSystemService(Context.STORAGE_SERVICE);
    }

    public static WallpaperService getWallpaperService() {
        return (WallpaperService) FRApplication.getInstance().getSystemService(Context.WALLPAPER_SERVICE);
    }

    public static Vibrator getVibrator() {
        return (Vibrator) FRApplication.getInstance().getSystemService(Context.VIBRATOR_SERVICE);
    }

    public static ConnectivityManager getConnectivityManager() {
        return (ConnectivityManager) FRApplication.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    public static NetworkStatsManager getNetworkStatsManager() {
        return (NetworkStatsManager) FRApplication.getInstance().getSystemService(Context.NETWORK_STATS_SERVICE);
    }

    public static WifiP2pManager getWifiP2pManager() {
        return (WifiP2pManager) FRApplication.getInstance().getSystemService(Context.WIFI_P2P_SERVICE);
    }

    public static NsdManager getNsdManager() {
        return (NsdManager) FRApplication.getInstance().getSystemService(Context.NSD_SERVICE);
    }

    public static AudioManager getAudioManager() {
        return (AudioManager) FRApplication.getInstance().getSystemService(Context.AUDIO_SERVICE);
    }

    public static FingerprintManager getFingerprintManager() {
        return (FingerprintManager) FRApplication.getInstance().getSystemService(Context.FINGERPRINT_SERVICE);
    }

    public static MediaRouter getMediaRouter() {
        return (MediaRouter) FRApplication.getInstance().getSystemService(Context.MEDIA_ROUTER_SERVICE);
    }

    public static MediaSessionManager getMediaSessionManager() {
        return (MediaSessionManager) FRApplication.getInstance().getSystemService(Context.MEDIA_SESSION_SERVICE);
    }

    public static TelephonyManager getTelephonyManager() {
        return (TelephonyManager) FRApplication.getInstance().getSystemService(Context.TELEPHONY_SERVICE);
    }

    public static SubscriptionManager getSubscriptionManager() {
        return (SubscriptionManager) FRApplication.getInstance().getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE);
    }

    public static TelecomManager getTelecomManager() {
        return (TelecomManager) FRApplication.getInstance().getSystemService(Context.TELECOM_SERVICE);
    }

    public static CarrierConfigManager getCarrierConfigManager() {
        return (CarrierConfigManager) FRApplication.getInstance().getSystemService(Context.CARRIER_CONFIG_SERVICE);
    }

    public static ClipboardManager getClipboardManager() {
        return (ClipboardManager) FRApplication.getInstance().getSystemService(Context.CLIPBOARD_SERVICE);
    }

    public static TextServicesManager getTextServicesManager() {
        return (TextServicesManager) FRApplication.getInstance().getSystemService(Context.TEXT_SERVICES_MANAGER_SERVICE);
    }

    public static AppWidgetManager getAppWidgetManager() {
        return (AppWidgetManager) FRApplication.getInstance().getSystemService(Context.APPWIDGET_SERVICE);
    }

    public static DropBoxManager getDropBoxManager() {
        return (DropBoxManager) FRApplication.getInstance().getSystemService(Context.DROPBOX_SERVICE);
    }

    public static DevicePolicyManager getDevicePolicyManager() {
        return (DevicePolicyManager) FRApplication.getInstance().getSystemService(Context.DEVICE_POLICY_SERVICE);
    }

    public static UiModeManager getUiModeManager() {
        return (UiModeManager) FRApplication.getInstance().getSystemService(Context.UI_MODE_SERVICE);
    }

    public static DownloadManager getDownloadManager() {
        return (DownloadManager) FRApplication.getInstance().getSystemService(Context.DOWNLOAD_SERVICE);
    }

    public static BatteryManager getBatteryManager() {
        return (BatteryManager) FRApplication.getInstance().getSystemService(Context.BATTERY_SERVICE);
    }

    public static NfcManager getNfcManager() {
        return (NfcManager) FRApplication.getInstance().getSystemService(Context.NFC_SERVICE);
    }

    public static BluetoothManager getBluetoothManager() {
        return (BluetoothManager) FRApplication.getInstance().getSystemService(Context.BLUETOOTH_SERVICE);
    }

    public static UsbManager getUsbManager() {
        return (UsbManager) FRApplication.getInstance().getSystemService(Context.USB_SERVICE);
    }

    public static InputManager getInputManager() {
        return (InputManager) FRApplication.getInstance().getSystemService(Context.INPUT_SERVICE);
    }
    public static DisplayManager getDisplayManager() {
        return (DisplayManager) FRApplication.getInstance().getSystemService(Context.DISPLAY_SERVICE);
    }

    public static UserManager getUserManager() {
        return (UserManager) FRApplication.getInstance().getSystemService(Context.USER_SERVICE);
    }

    public static LauncherApps getLauncherApps() {
        return (LauncherApps) FRApplication.getInstance().getSystemService(Context.LAUNCHER_APPS_SERVICE);
    }

    public static RestrictionsManager getRestrictionsManager() {
        return (RestrictionsManager) FRApplication.getInstance().getSystemService(Context.RESTRICTIONS_SERVICE);
    }

    public static AppOpsManager getAppOpsManager() {
        return (AppOpsManager) FRApplication.getInstance().getSystemService(Context.APP_OPS_SERVICE);
    }

    public static CameraManager getCameraManager() {
        return (CameraManager) FRApplication.getInstance().getSystemService(Context.CAMERA_SERVICE);
    }

    public static PrintManager getPrintManager() {
        return (PrintManager) FRApplication.getInstance().getSystemService(Context.PRINT_SERVICE);
    }

    public static ConsumerIrManager getConsumerIrManager() {
        return (ConsumerIrManager) FRApplication.getInstance().getSystemService(Context.CONSUMER_IR_SERVICE);
    }

    public static TvInputManager getTvInputManager() {
        return (TvInputManager) FRApplication.getInstance().getSystemService(Context.TV_INPUT_SERVICE);
    }

    public static UsageStatsManager getUsageStatsManager() {
        return (UsageStatsManager) FRApplication.getInstance().getSystemService(Context.USAGE_STATS_SERVICE);
    }

    public static JobScheduler getJobScheduler() {
        return (JobScheduler) FRApplication.getInstance().getSystemService(Context.JOB_SCHEDULER_SERVICE);
    }

    public static MediaProjectionManager getMediaProjectionManager() {
        return (MediaProjectionManager) FRApplication.getInstance().getSystemService(Context.MEDIA_PROJECTION_SERVICE);
    }

    public static MidiManager getMidiManager() {
        return (MidiManager) FRApplication.getInstance().getSystemService(Context.MIDI_SERVICE);
    }

}
