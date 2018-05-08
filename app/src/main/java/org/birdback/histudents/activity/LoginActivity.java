package org.birdback.histudents.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import org.birdback.histudents.MainActivity;
import org.birdback.histudents.R;
import org.birdback.histudents.activity.contract.LoginContract;
import org.birdback.histudents.activity.model.LoginModel;
import org.birdback.histudents.activity.presenter.LoginPresenter;
import org.birdback.histudents.core.CoreBaseActivity;
import org.birdback.histudents.utils.TextUtils;

/**
 * 登录
 * Created by meixin.song on 2018/4/15.
 */

public class LoginActivity extends CoreBaseActivity<LoginPresenter,LoginModel> implements LoginContract.View, View.OnClickListener {


    private EditText mEtName;
    private EditText mEtPwd;
    private Button mBtnLogin;
    private ImageView ivShowPwd;
    private boolean isChecked;

    public static void start(Context context) {
        context.startActivity(new Intent(context,LoginActivity.class));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                mPresenter.requestLogin(mEtName.getText().toString(),mEtPwd.getText().toString());
                break;
            case R.id.iv_show_pwd:
                if (isChecked){
                    ivShowPwd.setImageResource(R.mipmap.icon_biyan);
                    mEtPwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    isChecked = false;
                }else {
                    ivShowPwd.setImageResource(R.mipmap.icon_zhengyan);
                    mEtPwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    isChecked = true;
                }
                break;
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initListener() {
        mBtnLogin.setOnClickListener(this);
        ivShowPwd.setOnClickListener(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mEtName = findViewById(R.id.et_name);
        mEtPwd = findViewById(R.id.et_pwd);
        mBtnLogin = findViewById(R.id.btn_login);
        ivShowPwd = findViewById(R.id.iv_show_pwd);
    }

    @Override
    public void showMessage(String msg) {
        TextUtils.makeText(msg);
    }

    /**
     * 登录成功
     */
    @Override
    public void loginSuccess() {
        MainActivity.start(LoginActivity.this);
        LoginActivity.this.finish();
    }
}
