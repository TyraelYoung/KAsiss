package com.shuitianyun.kassistant.presenter;

import android.Manifest;
import android.app.Activity;
import android.os.AsyncTask;

import com.csmall.android.PermissionManager;
import com.csmall.android.ToastUtil;
import com.shuitianyun.kassistant.biz.quanmin.QuanminBiz;
import com.shuitianyun.kassistant.fragment.LocalSongFragment;
import com.shuitianyun.kassistant.fragment.PermissionFragment;
import com.shuitianyun.kassistant.json.LocalSong;

import java.util.List;


/**
 * 6.0以上要先获取权限
 * Created by wangchao on 2017/4/5.
 */

public class LocalSongPresenter {
    private final LocalSongFragment localSongFragment;
    private PermissionFragment permissionFragment;

    public LocalSongPresenter(LocalSongFragment localSongFragment) {
        this.localSongFragment = localSongFragment;
    }

    public void loadMain(){
        Activity activity = localSongFragment.getActivity();
        if(PermissionManager.checkPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE)){
            loadLocalSong();
        }else{
            //显示自定义以权限对话框
            permissionFragment = PermissionFragment.showDialog(localSongFragment.getFragmentManager(), "读取歌曲文件权限",
                    "需要您批准本应用获取存储权限，才能读取到存储上的" +
                            "歌曲文件。");
            //发起系统请求
//            requestPermission();
        }

    }

    private void loadLocalSong(){
        new AsyncTask<Integer, Integer, List<LocalSong>>(){
            @Override
            protected List<LocalSong> doInBackground(Integer... params) {
                QuanminBiz quanminBiz = new QuanminBiz();
                return quanminBiz.getLocalSongFromStorage();
            }

            @Override
            protected void onPostExecute(List<LocalSong> localSongs) {
                localSongFragment.updateLocalSong(localSongs);
            }
        }.execute();
    }

    public void requestPermission(){
//        Activity activity = localSongFragment.getActivity();
//        PermissionManager.requestPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE, 0);
        localSongFragment.requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
    }

    /**
     * 获取到权限了。
     * 更新界面
     */
    public void onSystemPermit(){
//        permissionFragment.dismiss();
        loadLocalSong();
    }

    public void onSystemRefuse(){
        ToastUtil.show("无法获取读取存储权限，无法读取文件");
        localSongFragment.getActivity().finish();
    }

    public void onPermitRefuse(){
        localSongFragment.getActivity().finish();
    }

//    public void uploadFile(final String fullPath){
//        new AsyncTask<Integer, Integer, String>(){
//
//            @Override
//            protected void onPreExecute() {
//                localSongFragment.showProgress();
//            }
//
//            @Override
//            protected String doInBackground(Integer... params) {
//                return new BosBiz().putObject(fullPath, "kassistant/localSong");
//            }
//
//            @Override
//            protected void onPostExecute(String s) {
//                ToastUtil.show("文件上传成功：" + s);
//                localSongFragment.hideProgress();
//            }
//        }.execute();
//    }
}
