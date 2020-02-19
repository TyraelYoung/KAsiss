package com.shuitianyun.kassistant.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.csmall.android.ToastUtil;
import com.csmall.share.ShareData;
import com.shuitianyun.kassistant.R;
import com.shuitianyun.kassistant.dialog.ProgressDialogBuilder;
import com.shuitianyun.kassistant.presenter.RemoteSongPresenter;

public class RemoteSongFragment extends Fragment implements RemoteSongPresenter.RemoteSongViewInterface, View.OnClickListener {

    private RemoteSongPresenter mRemoteSongPresenter;
//    private Button btnPasteOriginalUrl;
    private EditText etOriginalSongUrl;
    private Button btnGetUrl;
    private TextView tvRealSongUrl;
    private Button btnCopy;
    private Button btnSaveAs;
    private ProgressDialog mProgressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_remote_song, container, false);
        mRemoteSongPresenter = new RemoteSongPresenter();
        mRemoteSongPresenter.addViewListener(this);
        initView(v);
        return v;
    }

    private void initView(View v) {
        v.findViewById(R.id.btnToQuanMin).setOnClickListener(this);
        v.findViewById(R.id.btnPaste).setOnClickListener(this);
        etOriginalSongUrl = (EditText) v.findViewById(R.id.etOriginalSongUrl);
        btnGetUrl = (Button) v.findViewById(R.id.btnGetUrl);
        tvRealSongUrl = (TextView) v.findViewById(R.id.tvRealSongUrl);
        v.findViewById(R.id.btnShare).setOnClickListener(this);
        btnCopy = (Button) v.findViewById(R.id.btnCopy);
        btnSaveAs = (Button) v.findViewById(R.id.btnSaveAs);

        btnGetUrl.setOnClickListener(this);
        btnCopy.setOnClickListener(this);
        btnSaveAs.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnToQuanMin:
                mRemoteSongPresenter.toQuanMin(getActivity());
                break;
            case R.id.btnPaste:
                mRemoteSongPresenter.pasteOrginalUrl();
                break;
            case R.id.btnGetUrl:
                //获取下载地址
                mRemoteSongPresenter.getDownloadUrl();
                break;
            case R.id.btnShare:
                ShareData shareData = new ShareData();
                shareData.activity = getActivity();
                mRemoteSongPresenter.share(shareData);
                break;
            case R.id.btnCopy:
                //复制获取的下载地址
                mRemoteSongPresenter.copyDownloadUrl();
                break;
            case R.id.btnSaveAs:
                //通过浏览器打开下载地址
                mRemoteSongPresenter.saveByBrowser(getActivity());
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public String getOginialUrl() {
        return etOriginalSongUrl.getText().toString().trim();
    }

    @Override
    public String getRealUrl() {
        return tvRealSongUrl.getText().toString().trim();
    }

    @Override
    public void setOriginalSongUrl(final String url) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                etOriginalSongUrl.setText(url);
            }
        });

    }

    @Override
    public void showDialog(String msg) {
        if (mProgressDialog == null) {
            mProgressDialog = ProgressDialogBuilder.build(getActivity());
        }
        mProgressDialog.setMessage(msg);
        mProgressDialog.show();
    }

    @Override
    public void dismissDialog() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mProgressDialog != null) {
                    mProgressDialog.dismiss();
                }
            }
        });

    }

    @Override
    public void showToast(String msg) {
        ToastUtil.show(msg);
    }

    @Override
    public void setRealSongUrl(final String url) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvRealSongUrl.setText(url);
            }
        });

    }
}
