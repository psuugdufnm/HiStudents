package org.birdback.histudents.base;

import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;

import org.birdback.histudents.service.UmengNotificationService;
import org.birdback.histudents.utils.DeviceUtil;
import org.birdback.histudents.utils.LogUtil;
import org.birdback.histudents.utils.Session;
import org.birdback.histudents.utils.SharedPreUtil;
import org.birdback.histudents.utils.TextUtils;


/**
 *
 * Created by meixin.song on 2018/4/6.
 */

public class BaseApplication extends Application {
    private static Context mApplication;
    private static String TAG = "BaseApplication";

    public static Context getApplication() {
        return mApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        SharedPreUtil.initialize(this);
        DeviceUtil.initChannel();
        initUMeng();

        openBlueTooth();
        mApplication.registerReceiver(mReceiver,makeFilter());
    }
    private void initUMeng() {
        UMConfigure.setLogEnabled(true);//log开关
        UMConfigure.init(mApplication,
                "5accc807f43e483284000225",
                DeviceUtil.getChannel()
                ,UMConfigure.DEVICE_TYPE_PHONE
                , "3585897f047c73f3b91f43a1af189c22");
        PushAgent mPushAgent = PushAgent.getInstance(this);
        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {
            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回device token
                LogUtil.d("device token ===== " + deviceToken);
                Session.setDeviceToken(deviceToken);
            }
            @Override
            public void onFailure(String s, String s1) {
            }
        });
        mPushAgent.setPushIntentServiceClass(UmengNotificationService.class);
    }

    @Override
    public void onTerminate() {
        // 程序终止的时候执行
        Log.d(TAG, "onTerminate");
        mApplication.unregisterReceiver(mReceiver);
        super.onTerminate();
    }
    @Override
    public void onLowMemory() {
        // 低内存的时候执行
        Log.d(TAG, "onLowMemory");
        super.onLowMemory();
    }
    @Override
    public void onTrimMemory(int level) {
        // 程序在内存清理的时候执行
        Log.d(TAG, "onTrimMemory");
        super.onTrimMemory(level);
    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e(TAG, "onReceive---------------");
            switch (intent.getAction()) {
                case BluetoothAdapter.ACTION_STATE_CHANGED:
                    int blueState = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, 0);
                    switch (blueState) {
                        //开启蓝牙执行前两个
                        case BluetoothAdapter.STATE_TURNING_ON:  //开启中
                            Log.e(TAG,"onReceive---------STATE_TURNING_ON");
                            break;
                        case BluetoothAdapter.STATE_ON://开启成功
                            Log.e(TAG,"onReceive---------STATE_ON");
                            break;

                        //关闭蓝牙执行后两个
                        case BluetoothAdapter.STATE_TURNING_OFF: //关闭中
                            Log.e(TAG,"onReceive---------STATE_TURNING_OFF");
                            break;
                        case BluetoothAdapter.STATE_OFF://关闭成功
                            Log.e(TAG,"onReceive---------STATE_OFF");
                            openBlueTooth();
                            break;
                    }
                    break;
            }
        }
    };

    private void openBlueTooth() {
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            TextUtils.makeText("设备不支持蓝牙");
            return;
        }
        if (!mBluetoothAdapter.isEnabled()) {
            //打开蓝牙
            mBluetoothAdapter.enable();
        }
    }

    private IntentFilter makeFilter() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        return filter;
    }

}
