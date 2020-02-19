package com.shuitianyun.kassistant.dialog;

import android.app.Activity;
import android.app.ProgressDialog;

/**
 * Created by wangchao on 2017/4/11.
 */

public class ProgressDialogBuilder {
    public static ProgressDialog build(Activity activity){
        ProgressDialog progressDialog = new ProgressDialog(activity);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(false);
        return progressDialog;
    }

    public static ProgressDialog build(Activity activity, String msg){
        ProgressDialog progressDialog = new ProgressDialog(activity);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(msg);
        return progressDialog;
    }
}
