package org.birdback.histudents.activity.presenter;

import org.birdback.histudents.activity.contract.LoginContract;
import org.birdback.histudents.utils.VerifyUtil;

/**
 *
 * Created by meixin.song on 2018/4/15.
 */

public class LoginPresenter extends LoginContract.Presenter {

    @Override
    public void requestLogin(String name, String pwd) {

        if (VerifyUtil.isEmpty(name)) {
            mView.showMessage("请输入用户名");
            return;
        }

        if (VerifyUtil.isEmpty(pwd)) {
            mView.showMessage("请输入密码");
            return;
        }

        if (mMode!=null){
            mMode.requestLogin(name,pwd);
        }
    }

    @Override
    public void loginSuccess() {
        mView.loginSuccess();
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void showMessage(String msg) {

    }


}
