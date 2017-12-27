package cn.mifengkong.frhttp.http;

import com.mifengkong.frtools.util.FRString;

/**
 * 异常处理的一个类
 */
public class ApiException extends RuntimeException {

    private static int mErrorCode;
    private static String mErrorMessage;

    public ApiException(int errorCode, String errorMessage) {
        super(errorMessage);
        mErrorCode = errorCode;
        mErrorMessage = errorMessage;
    }

    public static boolean accountOnLineOnOtherDevice() {
        if (FRString.isEmpty(mErrorMessage)) {
            return false;
        }
        return mErrorMessage.equals("您的账号在另外的设备上登录，所以需要重新登录哟");
    }

    public static boolean isUserInfoFalse() {
        if (FRString.isEmpty(mErrorMessage)) {
            return false;
        }
        if (mErrorMessage.equals("信息不匹配，请重新输入。")) {
            return true;
        } else if (mErrorMessage.equals("请添加信用卡。")) {
            return true;
        } else if (mErrorMessage.equals("请添加储蓄卡。")) {
            return true;
        }
        return false;
    }
}