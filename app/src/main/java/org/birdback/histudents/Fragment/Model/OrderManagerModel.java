package org.birdback.histudents.Fragment.Model;

import android.support.v4.app.FragmentActivity;

import org.birdback.histudents.Fragment.contract.OrderManagerContract;
import org.birdback.histudents.activity.LoginActivity;
import org.birdback.histudents.entity.OrderListEntity;
import org.birdback.histudents.net.Callback.OnFailureCallBack;
import org.birdback.histudents.net.Callback.OnSuccessCallBack;
import org.birdback.histudents.net.HttpServer;
import org.birdback.histudents.service.RequestParams;
import org.birdback.histudents.utils.Session;


/**
 * Created by Administrator on 2018/4/8.
 */

public class OrderManagerModel implements OrderManagerContract.Model {
    private OrderManagerContract.Presenter presenter;
    FragmentActivity activity;

    @Override
    public void onStart(FragmentActivity activity, OrderManagerContract.Presenter presenter) {
        this.presenter = presenter;
        this.activity = activity;
    }

    @Override
    public void requestList() {
        HttpServer.getDataFromServer(RequestParams.getInstance().requestOrderList(), new OnSuccessCallBack<OrderListEntity>() {
            @Override
            public void onSuccess(OrderListEntity entity) {
                presenter.requestListSuccess(entity);
            }
        }, new OnFailureCallBack() {
            @Override
            public void onFailure(int code, String msg) {
                presenter.mView.showMessage(msg);
                if (code == -1) {
                    Session.logout();
                    LoginActivity.start(activity);
                }
            }
        });
    }

    @Override
    public void requestSubmit(String orderNo) {
        HttpServer.getDataFromServer(RequestParams.getInstance().requestSubmit(orderNo), new OnSuccessCallBack() {
            @Override
            public void onSuccess(Object entity) {
                presenter.submitSuccess();
            }
        }, new OnFailureCallBack() {
            @Override
            public void onFailure(int code, String msg) {
                presenter.submitFailure(code, msg);
                if (code == -1) {
                    Session.logout();
                    LoginActivity.start(activity);
                }
            }
        });
    }

    @Override
    public void requestJudan(String orderNo) {
        HttpServer.getDataFromServer(RequestParams.getInstance().requestJudan(orderNo),
                new OnSuccessCallBack() {
            @Override
            public void onSuccess(Object entity) {
                presenter.judanSuccess();
            }
        }, new OnFailureCallBack() {
            @Override
            public void onFailure(int code, String msg) {
                presenter.mView.showMessage(msg);
                if (code == -1) {
                    Session.logout();
                    LoginActivity.start(activity);
                }
            }
        });
    }
}
