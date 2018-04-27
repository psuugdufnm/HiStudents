package org.birdback.histudents.activity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


import org.birdback.histudents.R;
import org.birdback.histudents.activity.contract.PrinterManagerContract;
import org.birdback.histudents.activity.model.PrinterManagerModel;
import org.birdback.histudents.activity.presenter.PrinterManagerPresenter;
import org.birdback.histudents.core.CoreBaseActivity;
import org.birdback.histudents.entity.OrderListEntity;
import org.birdback.histudents.entity.PrintBean;
import org.birdback.histudents.utils.PrintUtils;
import org.birdback.histudents.utils.Session;
import org.birdback.histudents.utils.TextUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Set;
import java.util.concurrent.ExecutorService;
/**
 * 打印机管理
 * Created by meixin.song on 2018/4/9.
 */

public class PrinterManagerActivity extends CoreBaseActivity<PrinterManagerPresenter,PrinterManagerModel> implements PrinterManagerContract.View, View.OnClickListener {

    private static final String CONNSTATUS1 = "已连接";
    private static final String CONNSTATUS2 = "失去连接";
    private BluetoothSocket mmSocket;
    private OutputStream outputStream;

    private TextView mTvPrintTest,mTvPrintName,mTvConnectStatus,mTvPrintChange;
    private PrintBean mPrintBean;
    private BluetoothAdapter mBluetoothAdapter;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            closeProgressDialog();
            switch (msg.what){
                case 1:
                    mTvConnectStatus.setTextColor(getResources().getColor(R.color.txt_color_red));
                    mTvConnectStatus.setText(CONNSTATUS2);
                    break;
                case 2:
                    mTvConnectStatus.setTextColor(getResources().getColor(R.color.txt_color_red));
                    mTvConnectStatus.setText(CONNSTATUS2);
                    break;
                case 3:
                    mTvConnectStatus.setText(CONNSTATUS1);
                    mTvConnectStatus.setTextColor(getResources().getColor(R.color.txt_color_green));
                    break;
                case 4:
                    //什么都不做，只是为了
                    closeProgressDialog();
                    break;
            }
        }
    };


    public static void start(Context mContext) {
        mContext.startActivity(new Intent(mContext,PrinterManagerActivity.class));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_printer_manager;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mTvPrintTest = findViewById(R.id.tv_print_test);
        mTvPrintChange = findViewById(R.id.tv_print_change);
        mTvPrintName = findViewById(R.id.tv_print_name);
        mTvConnectStatus = findViewById(R.id.tv_connect_status);
    }

    @Override
    public void initListener() {
        mTvPrintTest.setOnClickListener(this);
        mTvPrintChange.setOnClickListener(this);

        initBuleTooth();
        initInfo();

        showProgressDialog();

        connThread(mExecutorService);
    }

    /**
     * 连接打印机
     * @param executorService
     */
    private void connThread(ExecutorService executorService) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    mmSocket.connect();
                    mHandler.sendEmptyMessage(3);
                } catch (Exception connectException) {
                    Log.d("", CONNSTATUS2);
                    mHandler.sendEmptyMessage(2);
                    connectException.printStackTrace();
                }
            }
        });
    }

    /**
     * 发送数据
     * @param executorService
     */
    private void sendThread(ExecutorService executorService) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    //连接socket
                    mmSocket.connect();
                    //连接成功获取输出流
                    outputStream = mmSocket.getOutputStream();
                    PrintUtils.send(outputStream);
                    mHandler.sendEmptyMessage(4);
                } catch (Exception connectException) {
                    mHandler.sendEmptyMessage(4);
                    TextUtils.makeText("打印失败，请检查蓝牙是否断开");
                    connectException.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_print_test:
                if (CONNSTATUS1.equals(mTvConnectStatus.getText().toString())){
                    showProgressDialog();
                    sendThread(mExecutorService);
                }else {
                    TextUtils.makeText("请连接蓝牙打印机");
                }
                break;
            case R.id.tv_print_change:
                //更换打印机
                SeachPrinterActivity.start(PrinterManagerActivity.this);
                finish();
                break;
        }
    }


    private void initInfo() {

        if (mPrintBean != null) {
            mTvPrintName.setText(mPrintBean.getName());

        }else {
            mTvPrintName.setText("暂无打印机");
            mTvConnectStatus.setText(CONNSTATUS2);
            mTvConnectStatus.setTextColor(getResources().getColor(R.color.txt_color_red));
        }

    }

    private void initBuleTooth() {
        mBluetoothAdapter = PrintUtils.getBluetoothAdapter();
        if (mBluetoothAdapter == null) {
            TextUtils.makeText("设备不支持蓝牙");
            return;
        }
        if (!mBluetoothAdapter.isEnabled()) {
            mBluetoothAdapter.enable();
            return;
        }

        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                if (Session.getBluetoothAddress().equals(device.getAddress())){
                    mPrintBean = new PrintBean(device);
                }
            }
        }
        if (mPrintBean != null) {
            try {
                mmSocket = mPrintBean.getDevice().createRfcommSocketToServiceRecord(PrintUtils.uuid);
            } catch (IOException e) {
                mHandler.sendEmptyMessage(1);
                e.printStackTrace();
            }
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }

    @Override
    public void showMessage(String msg) {

    }
}
