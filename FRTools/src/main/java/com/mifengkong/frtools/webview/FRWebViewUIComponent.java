package com.mifengkong.frtools.webview;

import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.mifengkong.frtools.util.FRClass;


/**
 * Created by jack on 17/3/1
 */

public abstract class FRWebViewUIComponent {

    private WebView mView;

    protected static <VIEW_COMPONENT extends FRWebViewUIComponent> VIEW_COMPONENT build(Class<VIEW_COMPONENT> cls, WebView view) {
        VIEW_COMPONENT viewComponent = FRClass.newInstance(cls);
        if (viewComponent != null) {
            viewComponent.initialize(view);
        }
        return viewComponent;
    }

    void initialize(WebView view) {
        mView = view;
        onCreate();
    }

    public WebView getWebView() {
        return mView;
    }

    protected void onCreate() {

    }

    public final void setWebChromeClient(WebChromeClient client) {
        mView.setWebChromeClient(client);
    }

    public final void setDownloadListener(DownloadListener listener) {
        mView.setDownloadListener(listener);
    }


    public final void loadUrl(String url) {
        mView.loadUrl(url);
    }

    public void reLoad() {
        mView.reload();
    }

    public void loadData(String data, String mimeType, String encoding) {
        mView.loadData(data, mimeType, encoding);
    }

    public void loadData(String data) {
        mView.loadData(data, "text/html", "UTF-8");
    }

    public boolean canGoBack() {
        return mView.canGoBack();
    }

    public void goBack() {
        mView.goBack();
    }

    public boolean canGoForward() {
        return mView.canGoForward();
    }

    public void goForward() {
        mView.goForward();
    }
}