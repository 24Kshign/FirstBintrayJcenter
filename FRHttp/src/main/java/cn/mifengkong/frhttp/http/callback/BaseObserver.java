package cn.mifengkong.frhttp.http.callback;

import com.mifengkong.frtools.util.FRLog;

import cn.mifengkong.frhttp.http.progress.ProgressDialogHandler;
import cn.mifengkong.frhttp.util.Platform;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import static cn.mifengkong.frhttp.http.HttpFunction.DATA_AND_MESSAGE_NULL;

/**
 *
 */

abstract class BaseObserver<T> implements Observer<T> {

    protected abstract void onBaseError(Throwable t);

    protected abstract void onBaseNext(T data);

    protected abstract boolean isNeedProgressDialog();

    protected abstract String getTitleMsg();

    protected abstract void onBaseNextButDataAndMessageIsEmpty();

    private ProgressDialogHandler mProgressDialogHandler;
    private BaseImpl mBaseImpl;
    private Platform mPlatform;

    BaseObserver(BaseImpl baseImpl) {
        mBaseImpl = baseImpl;
        if (null != baseImpl && null != baseImpl.getActivity()) {
            if (null == mProgressDialogHandler) {
                mProgressDialogHandler = new ProgressDialogHandler(baseImpl.getActivity());
            }
        }
        mPlatform = Platform.get();
    }

    private void showProgressDialog() {
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.SHOW_PROGRESS_DIALOG, getTitleMsg()).sendToTarget();
        }
    }

    private void dismissProgressDialog() {
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.DISMISS_PROGRESS_DIALOG).sendToTarget();
            mProgressDialogHandler = null;
        }
    }

    @Override
    public void onSubscribe(Disposable d) {
        //显示进度条
        if (isNeedProgressDialog()) {
            showProgressDialog();
        }
        if (null != mBaseImpl) {
            mBaseImpl.addRxDestroy(d);
        }
    }

    @Override
    public void onNext(final T value) {
        //成功
        FRLog.debug("http is onNext");
        if (null != mBaseImpl && null != mBaseImpl.getActivity() && mBaseImpl.getActivity().isFinishing()) {
            return;
        }
        if (DATA_AND_MESSAGE_NULL.equals(value)) {
            onBaseNextButDataAndMessageIsEmpty();
            return;
        }
        mPlatform.execute(new Runnable() {
            @Override
            public void run() {
                onBaseNext(value);
            }
        });
    }

    @Override
    public void onError(final Throwable e) {
        //关闭进度条
        FRLog.error("http is onError");
        if (isNeedProgressDialog()) {
            dismissProgressDialog();
        }
        mPlatform.execute(new Runnable() {
            @Override
            public void run() {
                onBaseError(e);
            }
        });
    }

    @Override
    public void onComplete() {
        //关闭进度条
        if (isNeedProgressDialog()) {
            dismissProgressDialog();
        }
    }
}