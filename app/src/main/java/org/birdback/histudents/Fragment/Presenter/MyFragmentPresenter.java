package org.birdback.histudents.Fragment.Presenter;

import org.birdback.histudents.Fragment.MyFragment;
import org.birdback.histudents.Fragment.contract.MyContract;
import org.birdback.histudents.activity.LoginActivity;
import org.birdback.histudents.net.Callback.OnFailureCallBack;
import org.birdback.histudents.net.Callback.OnSuccessCallBack;
import org.birdback.histudents.net.HttpServer;
import org.birdback.histudents.service.RequestParams;
import org.birdback.histudents.utils.Session;
import org.birdback.histudents.utils.TextUtils;

/**
 * Created by Administrator on 2018/4/8.
 */

public class MyFragmentPresenter extends MyContract.Presenter {
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
    public void getList() {
        mMode.getList();
    }

    @Override
    public void logout() {
        mMode.logout();
    }

    @Override
    public void logoutSuccess() {
        mView.logoutSuccess();
    }
}
