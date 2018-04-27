package org.birdback.histudents.Fragment.Presenter;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import org.birdback.histudents.Fragment.contract.OrderManagerContract;
import org.birdback.histudents.entity.OrderListEntity;
import org.birdback.histudents.entity.PrintBean;
import org.birdback.histudents.utils.PrintUtils;
import org.birdback.histudents.utils.Session;
import org.birdback.histudents.utils.TextUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;

/**
 * Created by Administrator on 2018/4/8.
 */

public class OrderManagerPresenter extends OrderManagerContract.Presenter {

    private PrintBean mPrintBean;
    private BluetoothSocket mmSocket;

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void showMessage(String msg) {

    }

    @Override
    public void requestList() {
        if (mMode!= null){
            mMode.requestList();
        }
    }

    @Override
    public void requestListSuccess(OrderListEntity entity) {
        mView.requestListSuccess(entity);
    }

    @Override
    public void requestSubmit(String orderNo,ExecutorService mExecutorService) {
        initBuleTooth(orderNo,mExecutorService);
    }

    @Override
    public void submitSuccess() {
        mView.submitSuccess();
    }

    @Override
    public void submitFailure(int code, String msg) {
        mView.submitFailure(code,msg);
    }

    private void initBuleTooth(String orderNo,ExecutorService mExecutorService) {
        BluetoothAdapter bluetoothAdapter = PrintUtils.getBluetoothAdapter();

        if (!bluetoothAdapter.isEnabled()) {
            bluetoothAdapter.enable();
            return;
        }
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
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

                sendThread(orderNo,mExecutorService);
            } catch (IOException e) {
                mView.handlerSend(1,null);
                e.printStackTrace();
            }
        }else {
            mView.handlerSend(1,null);
        }

    }

    /**
     * 发送数据
     * @param executorService
     */
    private void sendThread(final String orderNo, ExecutorService executorService) {
        executorService.execute(new Runnable() {
            private OutputStream outputStream;

            @Override
            public void run() {
                try {
                    //连接socket
                    mmSocket.connect();
                    //连接成功获取输出流
                    outputStream = mmSocket.getOutputStream();
                    PrintUtils.send(outputStream,"",new OrderListEntity.GrabListBean());
                    closeProgressDialog();

                    if (mMode != null) {
                        mMode.requestSubmit(orderNo);
                    }
                } catch (Exception connectException) {
                    closeProgressDialog();
                    mView.handlerSend(1,null);
                    connectException.printStackTrace();
                }
            }
        });
    }
}
