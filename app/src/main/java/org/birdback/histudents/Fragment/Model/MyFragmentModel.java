package org.birdback.histudents.Fragment.Model;

import android.support.v4.app.FragmentActivity;

import org.birdback.histudents.Fragment.MyFragment;
import org.birdback.histudents.Fragment.contract.MyContract;
import org.birdback.histudents.activity.LoginActivity;
import org.birdback.histudents.core.CoreBaseContract;
import org.birdback.histudents.entity.MyMenuEntity;
import org.birdback.histudents.net.Callback.OnFailureCallBack;
import org.birdback.histudents.net.Callback.OnSuccessCallBack;
import org.birdback.histudents.net.HttpServer;
import org.birdback.histudents.service.RequestParams;
import org.birdback.histudents.utils.Session;
import org.birdback.histudents.utils.TextUtils;

import java.util.List;

/**
 * Created by Administrator on 2018/4/8.
 */

public class MyFragmentModel implements MyContract.Model {
    MyContract.Presenter mPresenter;
    FragmentActivity activity;
    @Override
    public void onStart(FragmentActivity activity, MyContract.Presenter presenter) {
        this.mPresenter = presenter;
        this.activity = activity;
    }

    @Override
    public void getList() {
        HttpServer.getDataFromServer(RequestParams.getInstance().getList(), new OnSuccessCallBack<MyMenuEntity>() {
            @Override
            public void onSuccess(MyMenuEntity entity) {
                mPresenter.mView.getListSuccess(entity);
            }
        }, new OnFailureCallBack() {
            @Override
            public void onFailure(int code, String msg) {
                if (code == -1) {
                    Session.logout();
                    LoginActivity.start(activity);
                }
            }
        });
    }

    @Override
    public void logout() {
        HttpServer.getDataFromServer(RequestParams.getInstance().logout(Session.getDeviceToken()),
                new OnSuccessCallBack() {
                    @Override
                    public void onSuccess(Object entity) {
                        mPresenter.logoutSuccess();
                    }
                }, new OnFailureCallBack() {
                    @Override
                    public void onFailure(int code, String msg) {
                        mPresenter.mView.showMessage(msg);
                    }
                });
    }
}
