package com.mifengkong.frtools.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;

import com.mifengkong.frtools.app.FRApplication;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.NetworkInterface;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * Created by jiangyongxing on 2017/12/12.
 * 描述：
 */

public class FRDeviceUtil {

    private static final char[] HEX_CHAR = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    private static final String marshmallowMacAddress = "02:00:00:00:00:00";
    private static final String fileAddressMac = "/sys/class/net/wlan0/address";

    /**
     * 获取手机型号
     *
     * @return
     */
    public static String getPhoneModels() {
        return Build.MODEL;
    }

    /**
     * 获取手机品牌
     *
     * @return
     */
    public static String getPhoneBrand() {
        return Build.BRAND;
    }


    /**
     * 获取手机的IMEI
     *
     * @return
     */
    public static String getPhoneInfo() {
        TelephonyManager tm = null;
        try {
            Context context = FRApplication.getInstance().getApplicationContext();
            tm = (TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            return tm.getDeviceId();
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 获取应用签名
     *
     * @param context
     * @return
     */
    public static String getSignatureDigest(Context context) {
        PackageInfo packageInfo = getPackageInfo(context);
        if (packageInfo == null)
            return "not found";
        int length = packageInfo.signatures.length;
        if (length <= 0) {
            return "";
        } else {
            Signature signature = packageInfo.signatures[0];
            MessageDigest md5 = null;

            try {
                md5 = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException var5) {
                var5.printStackTrace();
            }

            byte[] digest = md5.digest(signature.toByteArray());
            return toHexString(digest).toLowerCase();
        }
    }

    public static PackageInfo getPackageInfo(Context context) {
        PackageInfo packageInfo = null;
        PackageManager packageManager = null;
        try {
            packageManager = context.getPackageManager();
            packageInfo = packageManager.getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
            return packageInfo;
        } catch (PackageManager.NameNotFoundException var8) {
            var8.printStackTrace();
            return null;
        }
    }

    private static String toHexString(byte[] rawByteArray) {
        char[] chars = new char[rawByteArray.length * 2];

        for (int i = 0; i < rawByteArray.length; ++i) {
            byte b = rawByteArray[i];
            chars[i * 2] = HEX_CHAR[b >>> 4 & 15];
            chars[i * 2 + 1] = HEX_CHAR[b & 15];
        }

        return new String(chars);
    }


    /**
     * 获取Android_id作为唯一标示
     *
     * @param context
     * @return
     */
    public static String getDeviceId(Context context) {
        return FREncryptUtil.string2MD5(Settings.System.getString(context.getContentResolver(), Settings.System.ANDROID_ID));
    }


    /**
     * 获得SD卡总大小
     *
     * @return
     */
    public static String getSDTotalSize(Context context) {
        File path = Environment.getExternalStorageDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();//文件系统上块的大小（以字节为单位）
        long totalBlocks = stat.getBlockCount();//文件系统上的块的总数。
        return Formatter.formatFileSize(context, blockSize * totalBlocks);
    }

    /**
     * 获取蓝牙mac
     *
     * @return
     */
    public static String getBluetoothAddress(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), "bluetooth_address");
    }


    /**
     * 获取mac
     *
     * @return
     */
    public static String getAdresseMAC(Context context) {
        WifiManager wifiMan = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInf = wifiMan.getConnectionInfo();//在主module中有添加过权限   这里就不需要添加了

        if (wifiInf != null && marshmallowMacAddress.equals(wifiInf.getMacAddress())) {
            String result = null;
            try {
                result = getAdressMacByInterface();
                if (result != null) {
                    return result;
                } else {
                    result = getAddressMacByFile(wifiMan);
                    return result;
                }
            } catch (IOException e) {
            } catch (Exception e) {
            }
        } else {
            if (wifiInf != null && wifiInf.getMacAddress() != null) {
                return wifiInf.getMacAddress();
            } else {
                return "";
            }
        }
        return marshmallowMacAddress;
    }


    private static String getAdressMacByInterface() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (nif.getName().equalsIgnoreCase("wlan0")) {
                    byte[] macBytes = nif.getHardwareAddress();
                    if (macBytes == null) {
                        return "";
                    }

                    StringBuilder res1 = new StringBuilder();
                    for (byte b : macBytes) {
                        res1.append(String.format("%02X:", b));
                    }

                    if (res1.length() > 0) {
                        res1.deleteCharAt(res1.length() - 1);
                    }
                    return res1.toString();
                }
            }

        } catch (Exception e) {
        }
        return null;
    }

    private static String getAddressMacByFile(WifiManager wifiMan) throws Exception {
        String ret;
        int wifiState = wifiMan.getWifiState();

        wifiMan.setWifiEnabled(true);
        File fl = new File(fileAddressMac);
        FileInputStream fin = new FileInputStream(fl);
        ret = crunchifyGetStringFromStream(fin);
        fin.close();

        boolean enabled = WifiManager.WIFI_STATE_ENABLED == wifiState;
        wifiMan.setWifiEnabled(enabled);
        return ret;
    }

    private static String crunchifyGetStringFromStream(InputStream crunchifyStream) throws IOException {
        if (crunchifyStream != null) {
            Writer crunchifyWriter = new StringWriter();

            char[] crunchifyBuffer = new char[2048];
            try {
                Reader crunchifyReader = new BufferedReader(new InputStreamReader(crunchifyStream, "UTF-8"));
                int counter;
                while ((counter = crunchifyReader.read(crunchifyBuffer)) != -1) {
                    crunchifyWriter.write(crunchifyBuffer, 0, counter);
                }
            } finally {
                crunchifyStream.close();
            }
            return crunchifyWriter.toString();
        } else {
            return "No Contents";
        }
    }

    /**
     * 获取语言编码
     *
     * @return
     */
    public static String getLanguage() {
        Locale locale = Locale.getDefault();
        return locale.getLanguage();
    }

    /**
     * 获取国家编码
     *
     * @return
     */
    public static String getCountry() {
        Locale locale = Locale.getDefault();
        return locale.getCountry();
    }


}
