package org.birdback.histudents.base;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;

import com.umeng.message.UmengMessageService;
import com.umeng.message.entity.UMessage;

import org.android.agoo.common.AgooConstants;
import org.birdback.histudents.MainActivity;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;


/**
 * 对消息自定义处理
 */
class MyPushIntentService extends UmengMessageService {
    @Override
    public void onMessage(Context context, Intent intent) {

        String message = intent.getStringExtra(AgooConstants.MESSAGE_BODY);
        UMessage msg = null;

        try {
            msg = new UMessage(new JSONObject(message));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Map<String, String> extra = msg.extra;

        Intent intentAct = new Intent();
        intentAct.setClass(context, MainActivity.class);
        intentAct.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        //展示通知
        showNotifications(context, msg, intentAct);
    }

    /**
     * 自定义通知布局
     *
     * @param context 上下文
     * @param msg     消息体
     * @param intent  intent
     */
    public void showNotifications(Context context, UMessage msg, Intent intent) {
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentTitle(msg.title)
                .setContentText(msg.text)
                .setTicker(msg.ticker)
                .setWhen(System.currentTimeMillis())

                .setColor(Color.parseColor("#41b5ea"))
                .setDefaults(Notification.DEFAULT_ALL)
                .setAutoCancel(true);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);
        mNotificationManager.notify(100, builder.build());
    }
}
