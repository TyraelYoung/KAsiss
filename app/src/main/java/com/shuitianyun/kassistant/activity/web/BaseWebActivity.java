package com.shuitianyun.kassistant.activity.web;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.csmall.android.view.WebProgressBar;
import com.csmall.log.LogHelper;
import com.shuitianyun.kassistant.R;
import com.shuitianyun.kassistant.activity.BaseActivity;

public class BaseWebActivity extends BaseActivity {
    private static final String TAG = "BaseWebActivity";
    public static String BUNDLE_KEY_URL = "BUNDLE_KEY_URL";

    private WebView wv;
    private WebProgressBar wpb;

    private String url;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_baseweb, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_web_finish) {
            this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        url = getIntent().getStringExtra(BUNDLE_KEY_URL);
        if(url == null){
            LogHelper.w(TAG, "url == null");
            return;
        }
        setContentView(R.layout.activity_web);
        wv = (WebView) findViewById(R.id.wv);
        wpb = (WebProgressBar) findViewById(R.id.web_wpb);
        wv.setWebViewClient(new MyWebViewClient(this));
        WebSettings webSettings = wv.getSettings();
        webSettings.setJavaScriptEnabled(true);

        //这个太不准了
//        wv.setWebChromeClient(new WebChromeClient(){
//            @Override
//            public void onProgressChanged(WebView view, int newProgress) {
//                LogHelper.d(TAG, "newProgress:" + newProgress);
//                ToastUtil.show("newProgress:" + newProgress);
//            }
//        });
        wv.loadUrl(url);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && wv.canGoBack()) {

//            wv.loadUrl("javascript:soundOff()");
            wv.goBack();// 返回前一个页面

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void onPageStart(){
        wpb.onStart();
    }

    public void onPageFinish(){
        wpb.onFinish();
    }
}
