package org.birdback.histudents.Fragment.Presenter;

import org.birdback.histudents.Fragment.contract.OrderManagerContract;
import org.birdback.histudents.entity.OrderListEntity;

import java.util.List;

/**
 * Created by Administrator on 2018/4/8.
 */

public class OrderManagerPresenter extends OrderManagerContract.Presenter {
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
    public void requestSubmit(String orderNo) {
        if (mMode != null) {
            mMode.requestSubmit(orderNo);
        }
    }

    @Override
    public void submitSuccess() {
        mView.submitSuccess();
    }

    @Override
    public void submitFailure() {
        mView.submitFailure();
    }
}
