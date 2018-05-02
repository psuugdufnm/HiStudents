package org.birdback.histudents.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import org.birdback.histudents.R;
import org.birdback.histudents.core.CoreBaseActivity;

public class SettingActivity extends CoreBaseActivity {


    public static void start(Context context) {
        context.startActivity(new Intent(context,SettingActivity.class));
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

}
