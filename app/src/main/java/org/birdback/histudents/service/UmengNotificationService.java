package org.birdback.histudents.service;


import android.content.Context;
import android.content.Intent;

import com.umeng.message.UmengMessageService;

import org.android.agoo.common.AgooConstants;


public class UmengNotificationService extends UmengMessageService {
    @Override
    public void onMessage(Context context, Intent intent) {

        String msg = intent.getStringExtra(AgooConstants.MESSAGE_BODY);
        Intent intent1 = new Intent();
        intent1.setClass(context, MyNotificationService.class);
        intent1.putExtra("UmengMsg", msg);
        context.startService(intent1);


    }
}
