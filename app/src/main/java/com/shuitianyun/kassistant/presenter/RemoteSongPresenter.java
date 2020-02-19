package com.shuitianyun.kassistant.presenter;

import android.app.Activity;
import android.text.TextUtils;

import com.csmall.android.BrowserHelper;
import com.csmall.android.CopyPasteHelper;
import com.csmall.android.StartActiviyHelper;
import com.csmall.android.ToastUtil;
import com.csmall.log.LogHelper;
import com.csmall.net.UrlCheck;
import com.csmall.share.MobApi;
import com.csmall.share.ShareData;
import com.shuitianyun.kassistant.biz.RemoteSongBiz;
import com.shuitianyun.kassistant.config.UrlStatic;

/**
 * Created by lzs on 2017-04-07.
 */

public class RemoteSongPresenter implements RemoteSongBiz.RemoteSongBizInterface {

    private static final java.lang.String TAG = "RemoteSongPresenter";
    private RemoteSongViewInterface mRemoteSongViewInterface;
    private RemoteSongBiz mRemoteSongBiz;

    public RemoteSongPresenter() {
        mRemoteSongBiz = new RemoteSongBiz();
        mRemoteSongBiz.addRemoteSongBizListener(this);
    }

    public RemoteSongPresenter addViewListener(RemoteSongViewInterface remoteSongViewInterface) {
        mRemoteSongViewInterface = remoteSongViewInterface;
        return this;
    }

    public void pasteOrginalUrl(){
        String url = CopyPasteHelper.pasteText();
        if(url == null){
            mRemoteSongViewInterface.showToast("剪贴板中没有文本信息哦。请先去复制一个地址来。");
            return;
        }
        mRemoteSongViewInterface.setOriginalSongUrl(url);
    }

    public void getDownloadUrl() {
        String shareUrl = mRemoteSongViewInterface.getOginialUrl();
        if (TextUtils.isEmpty(shareUrl) || !(shareUrl.contains("kg") && shareUrl.contains("qq"))) {
            mRemoteSongViewInterface.showToast("请输入正确的歌曲分享链接");
            return;
        }
        if (!UrlCheck.isUrl(shareUrl)){
            mRemoteSongViewInterface.showToast("请输入正确的歌曲分享链接");
            return;
        }
        if (shareUrl.contains("album")){
            mRemoteSongViewInterface.showToast("您输入的是专辑地址，请输入单曲地址。");
            return;
        }
        mRemoteSongViewInterface.showDialog("正在获取地址");
        mRemoteSongBiz.getDownloadUrl(shareUrl);
    }

    public void copyDownloadUrl() {
        String text = mRemoteSongViewInterface.getRealUrl();
        if (TextUtils.isEmpty(text)) {
            ToastUtil.show("尚未生成下载地址，复制失败");
            return;
        }
        CopyPasteHelper.copyText(text);
        mRemoteSongViewInterface.showToast("已复制下载地址到剪贴板");
    }

    public void saveByBrowser(Activity context) {
        String text = mRemoteSongViewInterface.getRealUrl();
        if (TextUtils.isEmpty(text)) {
            ToastUtil.show("尚未生成下载地址，无法打开");
            return;
        }
        BrowserHelper.openBrowser(context, text);
    }

    @Override
    public void onGetDownloadUrlSuccess(String url) {
        LogHelper.d(TAG, "onGetDownloadUrlSuccess:" + url);
        mRemoteSongViewInterface.setRealSongUrl(url);
        mRemoteSongViewInterface.dismissDialog();
    }

    @Override
    public void onGetDownloadUrlFailure(String msg) {
        mRemoteSongViewInterface.showToast(msg);
        mRemoteSongViewInterface.dismissDialog();
    }

    public void toQuanMin(Activity activity) {
        mRemoteSongViewInterface.showToast("正在打开全民K歌");
        StartActiviyHelper.openOtherApp(activity,"com.tencent.karaoke");
    }

    public void share(ShareData shareData) {
        String text = mRemoteSongViewInterface.getRealUrl();
        if (TextUtils.isEmpty(text)) {
            ToastUtil.show("尚未生成下载地址，无法分享");
            return;
        }
        shareData.imageUrl = UrlStatic.APP_LOGO;
        shareData.url = text;
        shareData.title = "K歌中转文件";
        shareData.content = "可在浏览器等中下载";
        MobApi.getInstance().share(shareData);
    }

    public interface RemoteSongViewInterface {
        String getOginialUrl();
        String getRealUrl();
        void setOriginalSongUrl(String url);
        void showDialog(String msg);
        void dismissDialog();
        void showToast(String msg);
        void setRealSongUrl(String url);
    }

}
