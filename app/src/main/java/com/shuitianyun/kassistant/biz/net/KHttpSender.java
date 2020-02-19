package com.shuitianyun.kassistant.biz.net;

import com.csmall.net.okhttpapi.OkSender;
import com.csmall.net.ordinary.RequestData;

/**
 * Created by wangchao on 2017/4/8.
 */

public class KHttpSender{
    private static final OkSender okSender = new OkSender();

    public static void send(final RequestData data) {
        okSender.send(data);
    }
}
