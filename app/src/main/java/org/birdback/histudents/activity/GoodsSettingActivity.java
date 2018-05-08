package org.birdback.histudents.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import org.birdback.histudents.R;
import org.birdback.histudents.activity.contract.GoodsSettingContract;
import org.birdback.histudents.activity.model.GoodsManagerModel;
import org.birdback.histudents.activity.presenter.GoodsManagerPresenter;
import org.birdback.histudents.core.CoreBaseActivity;

public class GoodsSettingActivity extends CoreBaseActivity<GoodsManagerPresenter,GoodsManagerModel> implements GoodsSettingContract.View {


    public static void start(Context context) {
        context.startActivity(new Intent(context,GoodsSettingActivity.class));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    public void initListener() {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    public void showMessage(String msg) {

    }
}
