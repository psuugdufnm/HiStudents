package org.birdback.histudents.activity.model;

import android.support.v4.app.FragmentActivity;

import org.birdback.histudents.activity.LoginActivity;
import org.birdback.histudents.activity.contract.GoodsSettingContract;
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

public class GoodsManagerModel implements GoodsSettingContract.Model {


    private FragmentActivity mActivity;
    private GoodsSettingContract.Presenter mPresenter;

    @Override
    public void onStart(FragmentActivity activity, GoodsSettingContract.Presenter presenter) {
        this.mActivity = activity;
        this.mPresenter = presenter;
    }
}
