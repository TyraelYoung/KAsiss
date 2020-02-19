package com.shuitianyun.kassistant.presenter;

import android.os.AsyncTask;

import com.csmall.android.BrowserHelper;
import com.csmall.android.ToastUtil;
import com.csmall.log.LogHelper;
import com.csmall.share.MobApi;
import com.csmall.share.ShareData;
import com.shuitianyun.kassistant.activity.LocalSongDetailActivity;
import com.shuitianyun.kassistant.config.UrlStatic;
import com.shuitianyun.kassistant.json.LocalSong;

import wang.tyrael.os.baidu.BosBiz;

/**
 * Created by wangchao on 2017/4/7.
 */

public class LocalSongDetailPresenter {
    private static final java.lang.String TAG = "LocalSongDetailPresenter";
    private final LocalSongDetailActivity localSongDetailActivity;

    private LocalSong localSong;
    private String uploadUrl;


    public LocalSongDetailPresenter(LocalSongDetailActivity localSongDetailActivity, LocalSong localSong) {
        this.localSongDetailActivity = localSongDetailActivity;
        this.localSong = localSong;
    }

    public void saveAs(){
        if(uploadUrl == null){
            ToastUtil.show("需要先生成中转地址");
            uploadFile();
            return;
        }
        BrowserHelper.openBrowser(localSongDetailActivity, uploadUrl);
    }

    public void share(ShareData shareData){
        if(uploadUrl == null){
            ToastUtil.show("需要先生成中转地址");
            uploadFile();
            return;
        }
//       MobApi.getInstance().test(localSongDetailActivity);
        shareData.imageUrl = UrlStatic.APP_LOGO;
        shareData.url = uploadUrl;
        shareData.title = "K歌中转文件";
        shareData.content = "可在浏览器等中下载";
        shareData.activity = localSongDetailActivity;
        MobApi.getInstance().share(shareData);
    }

    public void uploadFile(){
        final String fullPath = localSong.fullPath;
        new AsyncTask<Void, Void, String>(){

            @Override
            protected void onPreExecute() {
                localSongDetailActivity.showProgress();
            }

            @Override
            protected String doInBackground(Void... params) {
                return new BosBiz().putObjectWithRandomName(fullPath, "kassistant/localSong");
            }

            @Override
            protected void onPostExecute(String s) {
                LogHelper.d(TAG, "onPostExecute:" + s);
                if(s == null){
                    ToastUtil.show("文件上传失败：是否是网络问题？");
                    localSongDetailActivity.hideProgress();
                }else{
                    uploadUrl = s;
                    localSongDetailActivity.onUploadSuccess(s);
                }
            }
        }.execute();
    }
}
