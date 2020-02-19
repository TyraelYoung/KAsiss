package com.shuitianyun.kassistant.activity.web;

import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by wangchao on 2017/4/18.
 */

public class MyWebViewClient extends WebViewClient {
    private final BaseWebActivity baseWebActivity;

    public MyWebViewClient(BaseWebActivity baseWebActivity) {
        this.baseWebActivity = baseWebActivity;
    }

    //level 21
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        String url = request.getUrl().toString();
        view.loadUrl(url);
        return true;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        baseWebActivity.onPageStart();
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        baseWebActivity.onPageFinish();
    }


}
