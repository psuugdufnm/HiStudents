package org.birdback.histudents.activity;


import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.birdback.histudents.R;
import org.birdback.histudents.adapter.OnRecyclerViewListener;
import org.birdback.histudents.adapter.SeachBluetoothAdapter;
import org.birdback.histudents.core.CoreBaseActivity;
import org.birdback.histudents.entity.PrintBean;
import org.birdback.histudents.utils.Session;
import org.birdback.histudents.utils.TextUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Set;

import static android.bluetooth.BluetoothClass.Device.AUDIO_VIDEO_WEARABLE_HEADSET;
import static android.bluetooth.BluetoothClass.Device.COMPUTER_DESKTOP;
import static android.bluetooth.BluetoothClass.Device.COMPUTER_LAPTOP;
import static android.bluetooth.BluetoothClass.Device.PHONE_SMART;


/**
 * 查找蓝牙打印机
 * Created by meixin.song on 2018/4/9.
 */

public class SeachPrinterActivity extends CoreBaseActivity implements OnRecyclerViewListener {
    private TextView tvTip1,tvTip2;
    private RecyclerView recyclerView;
    private BluetoothAdapter mAdapter;
    private ArrayList<BluetoothDevice> deviceList = new ArrayList<>();
    private SeachBluetoothAdapter listAdapter;
    private int REQUEST_PERMISSION_ACCESS_LOCATION = 102;

    public static void start(Context mContext) {
        mContext.startActivity(new Intent(mContext,SeachPrinterActivity.class));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_seach_printer;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        tvTip1 = findViewById(R.id.tv_tip1);
        tvTip2 = findViewById(R.id.tv_tip2);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        listAdapter = new SeachBluetoothAdapter(deviceList);
        recyclerView.setAdapter(listAdapter);

        initBlueTooth();
    }

    @Override
    public void initListener() {
        listAdapter.setOnRecyclerViewListener(this);
    }

    @Override
    public void onItemClick(View itemView, int position) {
        BluetoothDevice device = deviceList.get(position);
        mAdapter.cancelDiscovery();//停止扫描蓝牙
        BluetoothDevice btDev = mAdapter.getRemoteDevice(device.getAddress());
        try {
            //通过反射来配对对应的蓝牙
            boolean bond = createBond(btDev.getClass(), btDev);
            if (!bond && device.getBondState() == BluetoothDevice.BOND_BONDED){
                //已绑定的设备
                Session.setBluetoothAddress(device.getAddress());
                finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

    private void initBlueTooth() {
        //获取本地蓝牙适配器
        mAdapter = BluetoothAdapter.getDefaultAdapter();

        if (mAdapter == null){
            TextUtils.makeText("您的手机不支持蓝牙，请更换手机");
            return;
        }
        requestPermission();

        //注册广播
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothDevice.ACTION_FOUND);
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        intentFilter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        registerReceiver(mReceiver, intentFilter);
        //已配对的设备
        Set<BluetoothDevice> pairedDevices = mAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                int deviceClass = device.getBluetoothClass().getDeviceClass();
                if (deviceClass != PHONE_SMART
                        && deviceClass != AUDIO_VIDEO_WEARABLE_HEADSET
                        && deviceClass != COMPUTER_DESKTOP
                        && deviceClass != COMPUTER_LAPTOP){


                    if (!deviceList.contains(device)){

                        deviceList.add(device);
                    }
                }
            }
        }

    }



    private BroadcastReceiver mReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction();

            if (action.equals(BluetoothDevice.ACTION_FOUND)) {

                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                int deviceClass = device.getBluetoothClass().getDeviceClass();

                /**
                 * 防止设备过多，过滤掉手机耳机电脑等设备类型
                 */
                if (deviceClass != PHONE_SMART
                        && deviceClass != AUDIO_VIDEO_WEARABLE_HEADSET
                        && deviceClass != COMPUTER_DESKTOP
                        && deviceClass != COMPUTER_LAPTOP){

                    if (!deviceList.contains(device)){

                        deviceList.add(device);
                    }
                }
                listAdapter.notifyDataSetChanged();
                tvTip2.setText(String.format(getResources().getString(R.string.string_fond_bluetooth_tip),String.valueOf(deviceList.size())));

            } else if (action.equals(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)) {
                tvTip1.setText("查找完毕");
                if (deviceList.size()<= 0){
                    tvTip2.setText("未发现蓝牙设备，请检查蓝牙打印机是否已开启，或者重新启动蓝牙打印机");
                }

            }else if(action.equals(BluetoothDevice.ACTION_BOND_STATE_CHANGED)){
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                switch (device.getBondState()) {
                    case BluetoothDevice.BOND_BONDING://正在配对
                        //TextUtils.makeText("正在配对......");
                        break;
                    case BluetoothDevice.BOND_BONDED://配对结束
                        TextUtils.makeText("完成配对");
                        Session.setBluetoothAddress(device.getAddress());
                        SeachPrinterActivity.this.finish();
                        break;
                    case BluetoothDevice.BOND_NONE://取消配对/未配对
                        TextUtils.makeText("配对失败");
                    default:
                        break;
                }
            }
        }
    };

    /**
     * 检查设备是否符合条件
     * @param context
     */
    public void checkBleDevice(Context context) {

        if (mAdapter.isEnabled()) {
            if (mAdapter.isDiscovering()) {
                mAdapter.cancelDiscovery();
            }
            mAdapter.startDiscovery();//开始搜索蓝牙设备
        }else{
            //蓝牙未开启
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            enableBtIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(enableBtIntent);
        }
    }

    /**
     * 通过反射 蓝牙配对
     * @return 蓝牙连接状态
     */
    public boolean createBond(Class btClass, BluetoothDevice btDevice) throws Exception {
        Method createBondMethod = btClass.getMethod("createBond");//获取蓝牙的连接方法
        Boolean returnValue = (Boolean) createBondMethod.invoke(btDevice);
        return returnValue;
    }


    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            int checkAccessFinePermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
            if (checkAccessFinePermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_PERMISSION_ACCESS_LOCATION);
                Log.e(getPackageName(), "没有权限，请求权限");
                return;
            }
            Log.e(getPackageName(), "已有定位权限");
            checkBleDevice(this);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_PERMISSION_ACCESS_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.e(getPackageName(), "开启权限permission granted!");
                checkBleDevice(this);
            } else {
                Log.e(getPackageName(), "没有定位权限，请先开启!");
            }
        }
    }



}
