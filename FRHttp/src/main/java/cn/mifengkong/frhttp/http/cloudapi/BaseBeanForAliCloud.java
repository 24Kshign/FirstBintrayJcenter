package cn.mifengkong.frhttp.http.cloudapi;

/**
 * Created by jiangyongxing on 2017/9/27.
 * 描述：
 */

public class BaseBeanForAliCloud implements FRCloudAPIUtil.CloudAPIBean {

    @Override
    public String getBaseUrl(boolean isKamao) {
        return isKamao ? "http://kamao-beta.mifengkong.cn"
                : "http://xinsudai-beta.mifengkong.cn";
    }

    @Override
    public String getAPPKEY(boolean isKamao) {
        return isKamao ? "24623686" : "24626500";
    }

    @Override
    public String getSECRET(boolean isKamao) {
        return isKamao ? "7e951a1892e04f80c290d8dc1414c4db" : "1f127ab74dfceb74a2f142d1219aaed1";
    }
}