package com.shuitianyun.kassistant.presenter;

import com.csmall.log.LogHelper;
import com.csmall.net.ordinary.RequestData;
import com.csmall.net.ordinary.RequestListener;
import com.csmall.net.ordinary.ResponseData;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.shuitianyun.kassistant.activity.DrawerActivity;
import com.shuitianyun.kassistant.biz.net.KHttpSender;
import com.shuitianyun.kassistant.config.KUrl;
import com.shuitianyun.kassistant.json.BulletinResponse;

/**
 * Created by wangchao on 2017/4/1.
 */

public class MainPresenter {
    private static final String TAG = "MainPresenter";
    private final DrawerActivity drawerActivity;

    public MainPresenter(DrawerActivity drawerActivity) {
        this.drawerActivity = drawerActivity;
    }

    public void loadMain(){
        loadBulletin();
    }

    public void loadBulletin(){
        RequestData requestData = new RequestData();
        requestData.url = KUrl.bulletin();
        requestData.listener = new RequestListener(){
            @Override
            public void onError(int code, String msg) {

            }

            @Override
            public void onSuccess(ResponseData responseData) {
                try{
                    BulletinResponse br = new Gson().fromJson(responseData.body, BulletinResponse.class);
                    if(br.code == 0){
                        drawerActivity.updateBulletin(br.data.bulletin);
                    }else{
                        LogHelper.w(TAG, br.message);
                        onError(0, br.message);
                    }
                }catch (JsonSyntaxException jse){
                    LogHelper.w(TAG, "", jse);
                    onError(0, "后台出错");
                }
            }
        };
        KHttpSender.send(requestData);
    }
}
