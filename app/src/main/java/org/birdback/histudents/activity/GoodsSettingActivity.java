package org.birdback.histudents.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.birdback.histudents.R;
import org.birdback.histudents.activity.contract.GoodsSettingContract;
import org.birdback.histudents.activity.model.GoodsManagerModel;
import org.birdback.histudents.activity.presenter.GoodsManagerPresenter;
import org.birdback.histudents.adapter.GoodsLeftAdapter;
import org.birdback.histudents.adapter.GoodsRightAdapter;
import org.birdback.histudents.core.CoreBaseActivity;

import java.util.ArrayList;
import java.util.List;

public class GoodsSettingActivity extends CoreBaseActivity<GoodsManagerPresenter,GoodsManagerModel> implements GoodsSettingContract.View {


    private RecyclerView mRecyclerLeft;
    private RecyclerView mRecyclerRight;
    private List<String> mLeftData = new ArrayList<>();
    private List<String> mRightData = new ArrayList<>();

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
        mRecyclerLeft = findViewById(R.id.recycler_left);
        mRecyclerRight = findViewById(R.id.recycler_right);

        mRecyclerLeft.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerRight.setLayoutManager(new LinearLayoutManager(this));

        for (int i = 0; i < 30; i++) {
            mLeftData.add("网红自制沙拉调料汁" + i);
        }
         mRecyclerLeft.setAdapter(new GoodsLeftAdapter(mLeftData));

        mRecyclerRight.setAdapter(new GoodsRightAdapter(mRightData));

    }

    @Override
    public void showMessage(String msg) {

    }
}
