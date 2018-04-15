package org.birdback.histudents.activity.contract;

import org.birdback.histudents.core.CoreBaseContract;

/**
 * Created by meixin.song on 2018/4/9.
 */

public interface LoginContract {

    interface View extends CoreBaseContract.CoreBaseView {

        void loginSuccess();
    }



    abstract class Presenter extends CoreBaseContract.CoreBasePresenter<View,Model> {
        public abstract void requestLogin(String name,String pwd);

        public abstract void loginSuccess();
    }



    interface Model extends CoreBaseContract.CoreBaseModel<Presenter> {

        void requestLogin(String name, String pwd);
    }


}
