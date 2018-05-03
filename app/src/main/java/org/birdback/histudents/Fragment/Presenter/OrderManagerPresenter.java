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
import java.util.IllegalFormatCodePointException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;

/**
 * Created by Administrator on 2018/4/8.
 */

public class OrderManagerPresenter extends OrderManagerContract.Presenter {

    private PrintBean mPrintBean;
    private BluetoothSocket mmSocket;
    private OrderListEntity.GrabListBean mGrabListBean;
    private String shopName;

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
    public void requestSubmit(String orderNo,ExecutorService mExecutorService,String name,OrderListEntity.GrabListBean grabListBean) {
        mGrabListBean = grabListBean;
        shopName = name;
        if (mGrabListBean != null && !Session.getClosePrint()){

            initBuleTooth(orderNo,mExecutorService);

        }else {
            //不需要打印订单，直接调用接单接口
            if (mMode != null) {
                mMode.requestSubmit(orderNo);
            }
        }


    }

    @Override
    public void submitSuccess() {
        mView.submitSuccess();
    }

    @Override
    public void submitFailure(int code, String msg) {
        mView.submitFailure(code,msg);
    }

    @Override
    public void requestJudan(String orderNo) {
        if (mMode!= null){
            mMode.requestJudan(orderNo);
        }
    }

    @Override
    public void judanSuccess() {
        mView.judanSuccess();
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
                showProgressDialog();
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
                    PrintUtils.send(outputStream,shopName,mGrabListBean);
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
