package org.birdback.histudents.service;

import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;


import java.util.Random;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import com.umeng.message.UTrack;
import com.umeng.message.entity.UMessage;

import org.birdback.histudents.MainActivity;
import org.birdback.histudents.R;
import org.birdback.histudents.broadcastrecevier.NotificationBroadcast;
import org.birdback.histudents.event.MessageEvent;
import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

public class MyNotificationService extends Service {
    private static final String TAG = UmengNotificationService.class.getName();
    public static UMessage oldMessage = null;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent == null) {
            return super.onStartCommand(intent, flags, startId);
        }
        String message = intent.getStringExtra("UmengMsg");
        //通知fragment刷新数据
        EventBus.getDefault().post(new MessageEvent(message));

        try {
            UMessage msg = new UMessage(new JSONObject(message));
            if (oldMessage != null) {
                UTrack.getInstance(getApplicationContext()).setClearPrevMessage(true);
                UTrack.getInstance(getApplicationContext()).trackMsgDismissed(oldMessage);
            }
            showNotification(msg);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void showNotification(UMessage msg) {
        int id = new Random(System.nanoTime()).nextInt();
        oldMessage = msg;
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancelAll();
        Notification.Builder mBuilder = new Notification.Builder(this);
        mBuilder.setContentTitle(msg.title)
                .setContentText(msg.text)
                .setTicker(msg.ticker)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.icon_dingdan)
                .setAutoCancel(true);
        Notification notification = mBuilder.getNotification();
        PendingIntent clickPendingIntent = getClickPendingIntent(this, msg);
        PendingIntent dismissPendingIntent = getDismissPendingIntent(this, msg);
        notification.deleteIntent = dismissPendingIntent;
        notification.contentIntent = clickPendingIntent;
        notification.sound = Uri.parse("android.resource://" + getPackageName() + "/" +R.raw.umeng_push_notification_default_sound);
        manager.notify(id, notification);
    }

    public PendingIntent getClickPendingIntent(Context context, UMessage msg) {
        Intent clickIntent = new Intent(context, MainActivity.class);

        clickIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        clickIntent.putExtra(NotificationBroadcast.EXTRA_KEY_MSG, msg.getRaw().toString());
        clickIntent.putExtra(NotificationBroadcast.EXTRA_KEY_ACTION, NotificationBroadcast.ACTION_CLICK);


        PendingIntent clickPendingIntent = PendingIntent.getActivity(context, 0,
                clickIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        return clickPendingIntent;
    }

    public PendingIntent getDismissPendingIntent(Context context, UMessage msg) {
        Intent deleteIntent = new Intent();
        deleteIntent.setClass(context, NotificationBroadcast.class);
        deleteIntent.putExtra(NotificationBroadcast.EXTRA_KEY_MSG,
                msg.getRaw().toString());
        deleteIntent.putExtra(
                NotificationBroadcast.EXTRA_KEY_ACTION,
                NotificationBroadcast.ACTION_DISMISS);
        PendingIntent deletePendingIntent = PendingIntent.getBroadcast(context,
                (int) (System.currentTimeMillis() + 1),
                deleteIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        return deletePendingIntent;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

