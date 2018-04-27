package org.birdback.histudents.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Looper;
import android.support.v4.app.NotificationCompat;

import com.umeng.message.UmengMessageService;
import com.umeng.message.entity.UMessage;

import org.android.agoo.common.AgooConstants;
import org.birdback.histudents.MainActivity;
import org.birdback.histudents.R;
import org.birdback.histudents.utils.LogUtil;
import org.json.JSONException;
import org.json.JSONObject;


public class UmengNotificationService extends UmengMessageService {
    @Override
    public void onMessage(Context context, Intent intent) {
        LogUtil.d("UmengNotificationService Looper.getMainLooper().getThread() = " +Looper.getMainLooper().getThread());
        LogUtil.d("UmengNotificationService Thread.currentThread() = " +Thread.currentThread());

        String msg = intent.getStringExtra(AgooConstants.MESSAGE_BODY);
       /* Intent intent1 = new Intent();
        //intent1.setClass(context, MainActivity.class);
        //intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent1.putExtra("UmengMsg", msg);
        //context.startService(intent1);*/

        try {
            UMessage message = new UMessage(new JSONObject(msg));
            showNotifications(context, message);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    /**
     * 自定义通知布局
     *
     * @param context 上下文
     * @param msg     消息体
     */
    public void showNotifications(Context context, UMessage msg) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentTitle(msg.title)
                .setContentText(msg.text)
                .setTicker(msg.ticker)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.icon_blue_tooth)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.mipmap.icon_phone))
                .setColor(Color.parseColor("#41b5ea"))
                .setDefaults(Notification.DEFAULT_ALL)
                .setAutoCancel(true)
                .setContentIntent(resultPendingIntent);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);
        mNotificationManager.notify(100, builder.build());
    }
}
