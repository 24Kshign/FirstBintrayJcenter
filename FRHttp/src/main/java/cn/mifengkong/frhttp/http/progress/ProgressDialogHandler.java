package cn.mifengkong.frhttp.http.progress;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;

import com.mifengkong.frtools.util.FRString;

import cn.mifengkong.frhttp.dialog.DialogLoading;


/**
 * 显示dialog的handler
 */
public class ProgressDialogHandler extends Handler {

    public static final int SHOW_PROGRESS_DIALOG = 1;
    public static final int DISMISS_PROGRESS_DIALOG = 2;

    private DialogLoading pd;

    private Activity context;

    public ProgressDialogHandler(Activity context) {
        super(context.getMainLooper());
        this.context = context;
    }

    private void initProgressDialog(String title) {
        if (null == context) {
            throw new IllegalStateException("activity should not be null");
        }
        if (pd == null && !context.isFinishing()) {
            pd = new DialogLoading(context);
            if (FRString.isEmpty(title)) {
                title = "加载中,请稍候....";
            }
            pd.setMessage(title);
            pd.setCancelable(true);
            pd.setCanceledOnTouchOutside(false);
            if (null != pd && !pd.isShowing()) {
                pd.show();
            }
        }
    }

    private void dismiss() {
        if (null != context && null != pd && pd.isShowing()) {
            pd.dismiss();
            pd = null;
        }
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case SHOW_PROGRESS_DIALOG:
                String title = (String) msg.obj;
                initProgressDialog(title);
                break;
            case DISMISS_PROGRESS_DIALOG:
                dismiss();
                break;
        }
    }
}