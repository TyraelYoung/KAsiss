package com.shuitianyun.kassistant.biz;

import android.text.TextUtils;

import com.csmall.log.LogHelper;
import com.csmall.net.ordinary.RequestData;
import com.csmall.net.ordinary.RequestListener;
import com.csmall.net.ordinary.ResponseData;
import com.google.gson.Gson;
import com.shuitianyun.kassistant.biz.net.KHttpSender;
import com.shuitianyun.kassistant.json.DwnModel;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lzs on 2017-04-07.
 */

public class RemoteSongBiz {

    private static final String TAG = "RemoteSongBiz";
    //http://jump.sinaapp.com/
    private static final String DWZ_URL = "http://jump.sinaapp.com/api.php";

    private RemoteSongBizInterface mRemoteSongBizInterface;

    public void addRemoteSongBizListener(RemoteSongBizInterface remoteSongBizInterface) {
        mRemoteSongBizInterface = remoteSongBizInterface;
    }

    //获取网页HTML文本，通过script里的playurl截取出下载地址
    public void getDownloadUrl(final String shareUrl) {
        RequestData data = new RequestData();
        data.url = shareUrl;
        data.listener = new RequestListener(){
            @Override
            public void onError(int code, String msg) {
                LogHelper.e(TAG, msg);
                mRemoteSongBizInterface.onGetDownloadUrlFailure("获取下载地址发生错误");
            }

            @Override
            public void onSuccess(ResponseData responseData) {
                LogHelper.d(TAG, responseData.body);
                String htmlContent = responseData.body;
                int songUrlStart = htmlContent.indexOf("playurl\":\"");
                int songUrlEnd = htmlContent.indexOf("\",\"playurl_video");
                if (songUrlStart == -1) {
                    onError(0, "该URL的HTML不包含playurl");
                    return;
                }
                String url = htmlContent.substring(songUrlStart + 10, songUrlEnd);
                LogHelper.d(TAG, "songUrl:" + url);
                if (TextUtils.isEmpty(url)) {
                    //playurl为歌曲下载地址，playurl_video为视频下载地址
                    //如果playurl为空，可能会是视频文件
                    int videoUrlStart = htmlContent.indexOf("playurl_video\":\"");
                    int videoUrlEnd = htmlContent.indexOf("\",\"poi_id");
                    if (videoUrlStart == -1) {
                        onError(0, "该URL的HTML不包含playurl和playurl_video");
                        return;
                    }
                    url = htmlContent.substring(videoUrlStart + 16, videoUrlEnd);
                }
                if (!TextUtils.isEmpty(url)) {
                    createShortUrl(url);
                } else {
                    onError(0, "歌曲地址和视频地址均不存在");
                }
            }
        };
        KHttpSender.send(data);
    }

    //原地址太长，调用新浪SAE分布式云计算服务短网址接口生成短址 http://jump.sinaapp.com/
    private void createShortUrl(final String longUrl) {
        Map<String, String> map = new HashMap<>();
        map.put("url_long", longUrl);
        RequestData data = new RequestData();
        data.url = DWZ_URL;
        data.method = "POST";
        data.params = map;
        data.listener = new RequestListener() {
            @Override
            public void onError(int code, String msg) {
                //短址生成失败就直接显示原始地址
                mRemoteSongBizInterface.onGetDownloadUrlSuccess(longUrl);
                LogHelper.e(TAG, msg);
            }

            @Override
            public void onSuccess(ResponseData responseData) {
                LogHelper.i(TAG, responseData.body);
                String content = responseData.body;
                Gson gson = new Gson();
                try {
                    DwnModel dwnModel = gson.fromJson(content, DwnModel.class);
                    if(dwnModel == null){
                        onError(0, "解析失败");
                        return;
                    }
                    if (dwnModel.result == 1) {
                        LogHelper.d(TAG, "shortUrl:" + dwnModel.url_short);
                        mRemoteSongBizInterface.onGetDownloadUrlSuccess(dwnModel.url_short);
                    } else if (dwnModel.result == 0) {
                        onError(0, dwnModel.error);
                    } else {
                        onError(0, "未知错误");
                    }
                } catch (IllegalStateException e){
                    onError(0,"解析失败");
                }
            }
        };
        KHttpSender.send(data);
    }

    public interface RemoteSongBizInterface {
        void onGetDownloadUrlSuccess(String url);
        void onGetDownloadUrlFailure(String msg);
    }



}
