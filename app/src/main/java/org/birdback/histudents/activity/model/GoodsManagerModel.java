package org.birdback.histudents.activity.model;

import android.support.v4.app.FragmentActivity;

import org.birdback.histudents.activity.contract.GoodsSettingContract;

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
