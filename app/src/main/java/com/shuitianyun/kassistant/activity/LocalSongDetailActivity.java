package com.shuitianyun.kassistant.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.csmall.android.ToastUtil;
import com.csmall.share.ShareData;
import com.shuitianyun.kassistant.R;
import com.shuitianyun.kassistant.biz.LocalSongBiz;
import com.shuitianyun.kassistant.dialog.ProgressDialogBuilder;
import com.shuitianyun.kassistant.json.LocalSong;
import com.shuitianyun.kassistant.presenter.LocalSongDetailPresenter;

public class LocalSongDetailActivity extends BaseActivity {
    public static final String BUNDLE_SONG_ID = "BUNDLE_SONG_ID";

    /**
     * 跳转到对应activity
     * @param from
     * @param songId
     */
    public static void to(Activity from, String songId){
        Intent intent = new Intent(from, LocalSongDetailActivity.class);
        intent.putExtra(BUNDLE_SONG_ID, songId);
        from.startActivity(intent);
    }

    TextView tvTitle;
    TextView tvTime;
    TextView tvFullPath;
    TextView tvUrl;
    Button bnUpload;
    Button bnShare;
    Button bnSaveAs;

    LocalSong localSong;

    ProgressDialog progressDialog;

    LocalSongDetailPresenter localSongPresenter;
    LocalSongBiz localSongBiz = LocalSongBiz.getInstance();

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
           switch (v.getId()){
               case R.id.bn_save_as:
                   localSongPresenter.saveAs();
                   break;
               case R.id.bn_share:
                   ShareData shareData = new ShareData();
                   localSongPresenter.share(shareData);
                   break;
               case R.id.bn_upload:
                   localSongPresenter.uploadFile();
                   break;
           }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String songId = getIntent().getStringExtra(BUNDLE_SONG_ID);
        localSong = localSongBiz.get(songId);
        if(localSong == null){
            ToastUtil.show("找不到歌曲的信息");
            return;
        }

        setContentView(R.layout.activity_local_song_detail);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvTime = (TextView) findViewById(R.id.tvTime);
        tvFullPath = (TextView) findViewById(R.id.tv_full_path);
        tvUrl = (TextView) findViewById(R.id.tv_url);
        bnUpload = (Button) findViewById(R.id.bn_upload);
        bnShare = (Button) findViewById(R.id.bn_share);
        bnSaveAs = (Button) findViewById(R.id.bn_save_as);

        bnUpload.setOnClickListener(clickListener);
        bnShare.setOnClickListener(clickListener);
        bnSaveAs.setOnClickListener(clickListener);

        localSongPresenter = new LocalSongDetailPresenter(LocalSongDetailActivity.this, localSong);


        tvTitle.setText(localSong.fileName);
        tvTime.setText(localSong.displayDisplayCreateAt());
        tvFullPath.setText(localSong.fullPath);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        hideProgress();
    }

    public void onUploadSuccess(String s){
        ToastUtil.show("文件上传成功：" + s);
        hideProgress();
        bnUpload.setEnabled(false);
        tvUrl.setText(s);
    }

    public void showProgress(){
//        clpb.setVisibility(View.VISIBLE);
        if(progressDialog == null){
            progressDialog = ProgressDialogBuilder.build(this, "正在生成链接");
        }
        progressDialog.show();
    }

    public void hideProgress(){
        if(progressDialog != null){
            progressDialog.dismiss();
        }

//        clpb.setVisibility(View.GONE);
    }
}
