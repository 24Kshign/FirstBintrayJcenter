package cn.mifengkong.frhttp.http.cloudapi;

/**
 * Created by jiangyongxing on 2017/9/27.
 * 描述：
 */

public class FRCloudAPIUtil {

    private static boolean mUserCloudAPiHost = false;
    private static CloudAPIBean mCloudAPIBean;

    public static boolean isUserCloudAPiHost() {
        return mUserCloudAPiHost;
    }

    public static void setUserCloudAPiHost(boolean userCloudAPiHost) {
        mUserCloudAPiHost = userCloudAPiHost;
    }

    public static void setCloudAPIBean(CloudAPIBean cloudAPIBean) {
        mCloudAPIBean = cloudAPIBean;
    }

    public static CloudAPIBean getCloudAPIBean() {
        return mCloudAPIBean;
    }

    public interface CloudAPIBean {

        String getBaseUrl(boolean isKamao);

        String getAPPKEY(boolean isKamao);

        String getSECRET(boolean isKamao);
    }
}