package org.birdback.histudents.Fragment.contract;

import org.birdback.histudents.core.CoreBaseContract;
import org.birdback.histudents.entity.MyMenuEntity;


/**
 * Created by meixin.song on 2018/4/9.
 */

public interface MyContract {

    interface View extends CoreBaseContract.CoreBaseView {

        void getListSuccess(MyMenuEntity entity);
    }

    interface Model extends CoreBaseContract.CoreBaseModel<Presenter> {

        void getList();
    }

    abstract class Presenter extends CoreBaseContract.CoreBasePresenter<View,Model> {

        public abstract void getList();
    }



}
