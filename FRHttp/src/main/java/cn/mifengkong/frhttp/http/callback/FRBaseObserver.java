package cn.mifengkong.frhttp.http.callback;

import android.accounts.NetworkErrorException;
import android.net.ParseException;

import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.mifengkong.frtools.util.FRLog;
import com.mifengkong.frtools.util.FRString;
import com.mifengkong.frtools.util.FRToast;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;

import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

import cn.mifengkong.frhttp.http.ApiException;
import cn.mifengkong.frhttp.http.event.CompulsoryDownLineEvent;

/**
 *
 */

public abstract class FRBaseObserver<T> extends BaseObserver<T> {

    private boolean isNeedProgress;
    private String titleMsg;

    public FRBaseObserver(BaseImpl baseImpl) {
        this(baseImpl, null);
    }

    public FRBaseObserver() {
        this(null, null);
    }

    public FRBaseObserver(BaseImpl baseImpl, String titleMsg) {
        super(baseImpl);
        this.titleMsg = titleMsg;
        if (!FRString.isEmpty(titleMsg)) {
            this.isNeedProgress = true;
        } else {
            this.isNeedProgress = false;
        }
    }

    @Override
    protected boolean isNeedProgressDialog() {
        return isNeedProgress;
    }

    @Override
    protected String getTitleMsg() {
        return titleMsg;
    }

    @Override
    protected void onBaseError(Throwable t) {
        handingExceptions(t, true);
    }

    protected void handingExceptions(Throwable t, boolean toastServerAbnormalInformation) {
        StringBuilder sb = new StringBuilder();
        sb.append("请求失败：");
        if (t instanceof NetworkErrorException
                || t instanceof UnknownHostException
                || t instanceof ConnectException) {
            sb.append("网络异常");
        } else if (t instanceof SocketTimeoutException
                || t instanceof InterruptedIOException
                || t instanceof TimeoutException) {
            sb.append("请求超时");
        } else if (t instanceof JsonSyntaxException) {
            sb.append("服务端数据解析失败");
        } else if (t instanceof JsonParseException
                || t instanceof JSONException
                || t instanceof ParseException) {   //  解析错误
            sb.append("解析错误");
        } else if (t instanceof ApiException) {
            sb.append(FRString.valueOf(t.getMessage()));
            if (ApiException.accountOnLineOnOtherDevice()) {
                EventBus.getDefault().post(new CompulsoryDownLineEvent());
            } else if (ApiException.isUserInfoFalse()) {
                return;
            }
        } else {
            if (toastServerAbnormalInformation)
                FRToast.showToastSafe(t.getMessage());
            return;
        }
        FRLog.error(sb.toString());
        if (toastServerAbnormalInformation)
            FRToast.showToastSafe(sb.toString());
    }

    @Override
    protected void onBaseNextButDataAndMessageIsEmpty() {

    }
}
