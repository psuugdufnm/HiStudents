package org.birdback.histudents.base;

import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

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
        mApplication.registerReceiver(mReceiver,makeFilter());
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
