package org.birdback.histudents.activity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.birdback.histudents.R;
import org.birdback.histudents.activity.contract.PrinterManagerContract;
import org.birdback.histudents.activity.model.PrinterManagerModel;
import org.birdback.histudents.activity.presenter.PrinterManagerPresenter;
import org.birdback.histudents.core.CoreBaseActivity;
import org.birdback.histudents.entity.PrintBean;
import org.birdback.histudents.utils.PrintUtils;
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

    private static final String FINISH = "finish";
    private BluetoothSocket mmSocket;
    private OutputStream outputStream;

    private UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");


    private TextView mTvPrintTest,mTvPrintName,mTvConnectStatus;
    private PrintBean mPrintBean;
    private String mPrintStatus;

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
            /*这种方式打开蓝牙会弹出提示
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);*/
            //这种方式打开蓝牙不会弹出提示，强制打开。关闭蓝牙用disable();
            mBluetoothAdapter.enable();
            return;
        }

        //获取到的集合是已配对的设备 02:30:C8:92:E2:3A
        //TODO 暂时还没有判断打印机是否开启~~~~~~~~~·
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {

            for (BluetoothDevice device : pairedDevices) {
                BluetoothClass bluetoothClass = device.getBluetoothClass();
                int deviceClass = bluetoothClass.getDeviceClass();
                int majorDeviceClass = bluetoothClass.getMajorDeviceClass();
                Log.d("BluetoothDevice" ,
                        "name = " + device.getName() + "----deviceClass = " + deviceClass +
                                "----majorDeviceClass = " + majorDeviceClass);

                /**
                 * name = MINISO D39F----deviceClass = 1028 ----majorDeviceClass = 1024
                 * name = FK-POS201-U----deviceClass = 7936 ----majorDeviceClass = 7936
                 */
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
                send(outputStream);
                mPrintStatus = FINISH;
            } catch (Exception connectException) {
                connectException.printStackTrace();
            }
        }
    }

    /**
     * 发送数据
     */
    public synchronized void send(OutputStream outputStream) {
        PrintUtils.setOutputStream(outputStream);
        PrintUtils.selectCommand(PrintUtils.RESET);
        PrintUtils.selectCommand(PrintUtils.LINE_SPACING_DEFAULT);
        PrintUtils.selectCommand(PrintUtils.ALIGN_CENTER);
        PrintUtils.printText("美食餐厅\n\n");
        PrintUtils.selectCommand(PrintUtils.DOUBLE_HEIGHT_WIDTH);
        PrintUtils.printText("桌号：1号桌\n\n");
        PrintUtils.selectCommand(PrintUtils.NORMAL);
        PrintUtils.selectCommand(PrintUtils.ALIGN_LEFT);
        PrintUtils.printText(PrintUtils.printTwoData("订单编号", "201507161515\n"));
        PrintUtils.printText(PrintUtils.printTwoData("点菜时间", "2016-02-16 10:46\n"));
        PrintUtils.printText(PrintUtils.printTwoData("上菜时间", "2016-02-16 11:46\n"));
        PrintUtils.printText(PrintUtils.printTwoData("人数：2人", "收银员：张三\n"));

        PrintUtils.printText("--------------------------------\n");
        PrintUtils.selectCommand(PrintUtils.BOLD);
        PrintUtils.printText(PrintUtils.printThreeData("项目", "数量", "金额\n"));
        PrintUtils.printText("--------------------------------\n");
        PrintUtils.selectCommand(PrintUtils.BOLD_CANCEL);
        PrintUtils.printText(PrintUtils.printThreeData("面", "1", "0.00\n"));
        PrintUtils.printText(PrintUtils.printThreeData("米饭", "1", "6.00\n"));
        PrintUtils.printText(PrintUtils.printThreeData("铁板烧", "1", "26.00\n"));
        PrintUtils.printText(PrintUtils.printThreeData("一个测试", "1", "226.00\n"));
        PrintUtils.printText(PrintUtils.printThreeData("牛肉面啊啊", "1", "2226.00\n"));
        PrintUtils.printText(PrintUtils.printThreeData("牛肉面啊啊啊牛肉面啊啊啊", "888", "98886.00\n"));

        PrintUtils.printText("--------------------------------\n");
        PrintUtils.printText(PrintUtils.printTwoData("合计", "53.50\n"));
        PrintUtils.printText(PrintUtils.printTwoData("抹零", "3.50\n"));
        PrintUtils.printText("--------------------------------\n");
        PrintUtils.printText(PrintUtils.printTwoData("应收", "50.00\n"));
        PrintUtils.printText("--------------------------------\n");

        PrintUtils.selectCommand(PrintUtils.ALIGN_LEFT);
        PrintUtils.printText("备注：不要辣、不要香菜");
        PrintUtils.printText("\n\n\n\n\n");
    }


    @Override
    public void showMessage(String msg) {

    }
}
