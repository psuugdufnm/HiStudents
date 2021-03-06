package org.birdback.histudents.activity.model;

import android.support.v4.app.FragmentActivity;

import org.birdback.histudents.activity.LoginActivity;
import org.birdback.histudents.activity.contract.LoginContract;
import org.birdback.histudents.entity.UidEntity;
import org.birdback.histudents.net.Callback.OnFailureCallBack;
import org.birdback.histudents.net.Callback.OnSuccessCallBack;
import org.birdback.histudents.net.HttpServer;
import org.birdback.histudents.service.RequestParams;
import org.birdback.histudents.utils.Session;

/**
 * Created by meixin.song on 2018/4/15.
 */

public class LoginModel implements LoginContract.Model {


    private FragmentActivity mActivity;
    private LoginContract.Presenter mPresenter;

    @Override
    public void onStart(FragmentActivity activity, LoginContract.Presenter presenter) {
        this.mActivity = activity;
        this.mPresenter = presenter;
    }

    @Override
    public void requestLogin(String name, String pwd) {
        HttpServer.getDataFromServer(mPresenter,
                RequestParams.getInstance().login(name,pwd), new OnSuccessCallBack<UidEntity>() {
            @Override
            public void onSuccess(UidEntity entity) {
                Session.setUid(entity.getUid());
                mPresenter.loginSuccess();
            }
        }, new OnFailureCallBack() {
            @Override
            public void onFailure(int code, String msg) {
                mPresenter.mView.showMessage(msg);
                if (code == -1) {
                    Session.logout();
                    LoginActivity.start(mActivity);
                }
            }
        });
    }
}
