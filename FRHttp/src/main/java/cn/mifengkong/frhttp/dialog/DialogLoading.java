package cn.mifengkong.frhttp.dialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.widget.ProgressBar;
import android.widget.TextView;

import cn.mifengkong.frhttp.R;

/**
 * Created by jiangyongxing on 2017/5/24.
 * 描述：
 */

public class DialogLoading extends ProgressDialog {

    private ProgressBar mProgressBar;
    private TextView mLoadingTv;

    public DialogLoading(@NonNull Context context) {
        super(context);
        init(context, null);
    }

    public DialogLoading(@NonNull Context context, String message) {
        super(context, R.style.dialog_loading);
        init(context, message);

    }

    private void init(@NonNull Context context, String message) {
        this.mLoadingTip = message;
        this.mContext = context;
        setCanceledOnTouchOutside(false);
    }

    private Context mContext;
    private String mLoadingTip;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    private void initData() {
        if (!TextUtils.isEmpty(mLoadingTip))
            mLoadingTv.setText(mLoadingTip);
        mLoadingTv.setTextColor(Color.rgb(255, 255, 255));
    }

    public void setMessage(String mLoadingTip) {
        this.mLoadingTip = mLoadingTip;
        if (mLoadingTv != null)
            mLoadingTv.setText(mLoadingTip);
    }

    public void setContent(String str) {
        mLoadingTv.setText(str);
    }

    private void initView() {
        //XML中设置无效，在代码中设置背景透明
        this.getWindow().setBackgroundDrawable(new ColorDrawable());
        setContentView(R.layout.progress_dialog);
        mProgressBar = (ProgressBar) findViewById(R.id.progressbar);
        mLoadingTv = (TextView) findViewById(R.id.message);
    }

}
