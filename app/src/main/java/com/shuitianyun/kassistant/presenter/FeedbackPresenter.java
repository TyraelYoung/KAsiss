package com.shuitianyun.kassistant.presenter;

import android.os.AsyncTask;

import com.csmall.mail.MailData;
import com.csmall.mail.MailSender;
import com.shuitianyun.kassistant.activity.FeedbackActivity;

/**
 * Created by wangchao on 2017/4/7.
 */

public class FeedbackPresenter {
    private final FeedbackActivity feedbackActivity;

    public FeedbackPresenter(FeedbackActivity feedbackActivity) {
        this.feedbackActivity = feedbackActivity;
    }

    public void send(final MailData mailData){
        new AsyncTask<Void, Void, Boolean>(){
            @Override
            protected void onPreExecute() {
                feedbackActivity.showProgress();
            }

            @Override
            protected Boolean doInBackground(Void... params) {
                return MailSender.sendMail(mailData);
            }

            @Override
            protected void onPostExecute(Boolean b) {
                feedbackActivity.onSendFinish(b);
            }
        }.execute();

    }
}
