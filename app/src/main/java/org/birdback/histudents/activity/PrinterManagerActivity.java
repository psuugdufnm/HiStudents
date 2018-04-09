package org.birdback.histudents.activity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.birdback.histudents.R;
import org.birdback.histudents.activity.contract.PrinterManagerContract;
import org.birdback.histudents.activity.model.PrinterManagerModel;
import org.birdback.histudents.activity.presenter.PrinterManagerPresenter;
import org.birdback.histudents.core.CoreBaseActivity;
import org.birdback.histudents.entity.PrintBean;
import org.birdback.histudents.utils.TextUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

/**
 * 打印机管理
 * Created by meixin.song on 2018/4/9.
 */

public class PrinterManagerActivity extends CoreBaseActivity<PrinterManagerPresenter,PrinterManagerModel> implements PrinterManagerContract.View, View.OnClickListener {

    private static int REQUEST_ENABLE_BT = 10;
    public static final int PRINT_TYPE = 1664;
    private BluetoothSocket mmSocket;
    private OutputStream outputStream;

    private UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");


    private TextView mTvPrintTest,mTvPrintName,mTvConnectStatus;
    private PrintBean mPrintBean;

    public static void start(Context mContext) {
        mContext.startActivity(new Intent(mContext,PrinterManagerActivity.class));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_printer_manager;
    }

    @Override
    public void initListener() {
        mTvPrintTest.setOnClickListener(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mTvPrintTest = findViewById(R.id.tv_print_test);
        mTvPrintName = findViewById(R.id.tv_print_name);
        mTvConnectStatus = findViewById(R.id.tv_connect_status);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_print_test:
                new ConnectThread(mPrintBean.getDevice()).start();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initBuleTooth();

        initInfo();
    }

    private void initInfo() {
        if (mPrintBean != null) {
            mTvPrintName.setText(mPrintBean.getName());
            mTvConnectStatus.setText("已连接");
            mTvConnectStatus.setTextColor(getResources().getColor(R.color.txt_color_green));
        }else {
            mTvPrintName.setText("暂无打印机");
            mTvConnectStatus.setText("未连接");
            mTvConnectStatus.setTextColor(getResources().getColor(R.color.txt_color_red));
        }

    }

    private void initBuleTooth() {
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            TextUtils.makeText("设备不支持蓝牙");
            return;
        }

        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            return;
        }

        //获取到的集合是已配对的设备 02:30:C8:92:E2:3A
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {

            for (BluetoothDevice device : pairedDevices) {

                mPrintBean = new PrintBean(device);

            }

        }
    }
    private class ConnectThread extends Thread {
        public ConnectThread(BluetoothDevice device) {
            try {
                mmSocket = device.createRfcommSocketToServiceRecord(uuid);
            } catch (IOException e) {
                TextUtils.makeText("连接失败");
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            try {
                //连接socket
                mmSocket.connect();
                //连接成功获取输出流
                outputStream = mmSocket.getOutputStream();

                send("#02" + "      顾客联 \n" +
                        "同学快跑 | 谷田稻香-浦江家乐福店"+ "\n" +
                        "下单时间：2018-04-08 18:11"+ "\n" +
                        "订单编号：8080505446856962"+ "\n" +
                        "缺货处理：如遇缺货，其他商品继续配送"+ "\n" +
                        "----------------------"+ "\n" +
                        "商品名称  数量  单价  金额"+ "\n" +
                        "----------------------"+ "\n" +
                        "红烧鲫鱼  x2  55  110"+ "\n" +
                        "中华火腿炒年糕+清爽可口  x1  55  55"+ "\n" +
                        "糖醋里脊  x1  9  9"+ "\n" +
                        "------其他费用-------"+ "\n" +
                        "餐盒费 x3  0  0"+ "\n" +
                        "应付       199"+ "\n" +
                        "优惠       -30"+ "\n" +
                        "实付       199"+ "\n" +
                        "----------------------"+ "\n" +
                        "地址：陈行公路2388号浦江科技广场9号楼 808"+ "\n" +
                        "收餐人：小小鸭"+ "\n" +
                        "手机：13115155555"+ "\n" +
                        "********完******\n\n\n"

                );
            } catch (Exception connectException) {
                TextUtils.makeText("连接失败");
            }
        }
    }

    /**
     * 发送数据
     */
    public void send(String text) {
        try {
            byte[] data = text.getBytes("gbk");
            outputStream.write(data, 0, data.length);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            TextUtils.makeText("send 败");
        }
    }


    @Override
    public void showMessage(String msg) {

    }
}
