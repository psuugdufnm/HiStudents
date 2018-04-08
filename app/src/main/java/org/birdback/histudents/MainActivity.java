package org.birdback.histudents;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import org.birdback.histudents.Fragment.MyFragment;
import org.birdback.histudents.Fragment.OrderManagerFragment;
import org.birdback.histudents.Fragment.OrderSearchFragment;
import org.birdback.histudents.core.CoreBaseFragment;
import org.birdback.histudents.utils.TextUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static int REQUEST_ENABLE_BT = 10;
    private ArrayList<String> mArray = new ArrayList<>();;
    private BluetoothSocket mmSocket;
    private OutputStream outputStream;

    private UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    private RadioButton radio0,radio1,radio2;
    private FrameLayout mFrameLayout;
    private FragmentManager mFragmentManager;
    private CoreBaseFragment mCacheFragment;
    private OrderManagerFragment managerFragment;
    private MyFragment myFragment;
    private OrderSearchFragment searchFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        initListener();

        //initBuleTooth();

        switchFragment(0);

    }

    private void initListener() {
        radio0.setOnClickListener(this);
        radio1.setOnClickListener(this);
        radio2.setOnClickListener(this);
    }

    private void initView() {
        radio0 = findViewById(R.id.radio_0);
        radio1 = findViewById(R.id.radio_1);
        radio2 = findViewById(R.id.radio_2);
        mFrameLayout = findViewById(R.id.container);
        mFragmentManager = getSupportFragmentManager();
    }

    private void switchFragment(int index) {

        FragmentTransaction transaction = mFragmentManager.beginTransaction();

        if (mCacheFragment != null) {
            transaction.hide(mCacheFragment);
        }
        switch (index) {
            case 0:
                if (managerFragment == null) {
                    managerFragment = new OrderManagerFragment();
                    transaction.add(R.id.container, managerFragment, OrderManagerFragment.class.getSimpleName());
                } else {
                    transaction.show(managerFragment);
                }
                mCacheFragment = managerFragment;
                break;
            case 1:
                if (searchFragment == null) {
                    searchFragment = new OrderSearchFragment();
                    transaction.add(R.id.container, searchFragment, MyFragment.class.getSimpleName());
                } else {
                    transaction.show(searchFragment);
                }
                mCacheFragment = searchFragment;
                break;
            case 2:
                if (myFragment == null) {
                    myFragment = new MyFragment();
                    transaction.add(R.id.container, myFragment, MyFragment.class.getSimpleName());
                } else {
                    transaction.show(myFragment);
                }
                mCacheFragment = myFragment;
                break;
            default:
                break;
        }
        transaction.commit();
    }

    private void initBuleTooth() {
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            TextUtils.makeText("设备不支持蓝牙");
            return;
        }

        if (!mBluetoothAdapter.isEnabled()) {
            TextUtils.makeText("蓝牙未启用");
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            return;
        }

        //获取到的集合是已配对的设备 02:30:C8:92:E2:3A
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {

            for (BluetoothDevice device : pairedDevices) {
                new ConnectThread(device).start();
                mArray.add(device.getName() + "\n" + device.getAddress());

            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.radio_0:
                switchFragment(0);
                break;
            case R.id.radio_1:
                switchFragment(1);
                break;
            case R.id.radio_2:
                switchFragment(2);
                break;
            default:
                break;
        }
    }


    private class ConnectThread extends Thread {
        public ConnectThread(BluetoothDevice device) {
            try {
                mmSocket = device.createRfcommSocketToServiceRecord(uuid);
            } catch (IOException e) {
                TextUtils.makeText("mmSocket连接失败");
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

                send("3年Android开发经验，擅长Android,Java。目前就职于某大" +
                        "型金融公司，负责产品开发、维护与性能优化，善于钻研技术，学习能力" +
                        "强，对工作热爱细心，工作效率高，思维开阔，定位问题迅速。喜欢运动、旅游。/n");
            } catch (Exception connectException) {
                Log.e("test", "连接失败");
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
}
