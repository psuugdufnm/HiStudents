package org.birdback.histudents.Fragment.contract;

import org.birdback.histudents.core.CoreBaseContract;
import org.birdback.histudents.entity.OrderListEntity;

import java.util.List;

/**
 * Created by Administrator on 2018/4/9.
 */

public interface OrderManagerContract {

    interface View extends CoreBaseContract.CoreBaseView {

        void requestListSuccess(OrderListEntity entity);
    }

    interface Model extends CoreBaseContract.CoreBaseModel<Presenter> {

        void requestList();
    }

    abstract class Presenter extends CoreBaseContract.CoreBasePresenter<View,Model> {

        public abstract void requestList();

        public abstract void requestListSuccess(OrderListEntity entity);
    }



}
