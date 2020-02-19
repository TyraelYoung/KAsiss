package com.shuitianyun.kassistant.activity;

/*
 *   意见反馈1
 */

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.csmall.android.ToastUtil;
import com.csmall.mail.MailData;
import com.shuitianyun.kassistant.R;
import com.shuitianyun.kassistant.dialog.ProgressDialogBuilder;
import com.shuitianyun.kassistant.presenter.FeedbackPresenter;

public class FeedbackActivity extends BaseActivity {
    private EditText edit_feedback_content, edit_feedback_link;
    private RadioGroup rg;
    private ProgressDialog progressDialog;

    private String mFeedbackType = "default";

    private FeedbackPresenter feedbackPresenter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_feedback, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_send_feedback) {
            //发送
            sendFeed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        feedbackPresenter = new FeedbackPresenter(FeedbackActivity.this);
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        hideProgress();
    }

    private void initView() {
        edit_feedback_content = (EditText) findViewById(R.id.edit_feedback_content);
        edit_feedback_link = (EditText) findViewById(R.id.edit_feedback_link);
        rg = (RadioGroup) findViewById(R.id.rg_feedback);

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_feedback_function:
                        mFeedbackType = "function";
                        break;
                    case R.id.rb_feedback_tech:
                        mFeedbackType = "tech";
                        break;
                    case R.id.rb_feedback_default:
                        mFeedbackType = "default";
                        break;
                }
            }
        });
    }

    private void sendFeed() {
        if(TextUtils.isEmpty(edit_feedback_content.getText().toString().trim())){
            ToastUtil.show("请输入反馈。");
            return;
        }
        email();

        //两种方式
//        switch (mFeedbackType) {
//            case R.id.rb_feedback_function:
//                if(!LoginManager.getInstance().checkLogin()){
//                //后台要求登录。
//                    //业务上应该不需要
//                    ToastUtil.show("业务问题需要登陆后才能反馈。");
//                    return;
//                }
//                break;
//            case R.id.rb_feedback_tech:
//            case R.id.rb_feedback_default:
//                //主要是为了获取用户日志
//                emailWithLog();
//                break;
//        }
    }

    private void email() {
        MailData mailData = new MailData();
        mailData.mailTitle = MailData.TITLE_FEEDBACK;
        mailData.mailContent =
                " " + mFeedbackType
                + "\n" + edit_feedback_content.getText().toString().trim()
                + "\n联系方式：" + edit_feedback_link.getText().toString().trim();
        mailData.mailTitle += mailData.mailContent;
        feedbackPresenter.send(mailData);
    }

    public void onSendFinish(boolean success){
        hideProgress();
        if(success){
            ToastUtil.show("发送成功");
            this.finish();
        }else{
            ToastUtil.show("发送失败");
        }
    }

    public void showProgress(){
        if(progressDialog == null){
            progressDialog = ProgressDialogBuilder.build(this, "发送中");
        }
        progressDialog.show();
    }

    public void hideProgress(){
        if(progressDialog != null){
            progressDialog.dismiss();
        }
    }
}
