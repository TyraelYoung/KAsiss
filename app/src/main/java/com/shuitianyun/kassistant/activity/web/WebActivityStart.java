package com.shuitianyun.kassistant.activity.web;

import android.app.Activity;
import android.content.Intent;

import com.shuitianyun.kassistant.config.UrlStatic;

/**
 * Created by wangchao on 2017/4/18.
 */

public class WebActivityStart {
    public static void startBbs(Activity from){
        Intent intent = new Intent(from, BaseWebActivity.class);
        intent.putExtra(BaseWebActivity.BUNDLE_KEY_URL, UrlStatic.BBS);
        from.startActivity(intent);
    }
}
