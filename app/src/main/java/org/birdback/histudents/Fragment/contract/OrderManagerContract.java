package org.birdback.histudents.Fragment.contract;

import org.birdback.histudents.core.CoreBaseContract;
import org.birdback.histudents.entity.OrderListEntity;

import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * Created by Administrator on 2018/4/9.
 */

public interface OrderManagerContract {

    interface View extends CoreBaseContract.CoreBaseView {

        void requestListSuccess(OrderListEntity entity);

        void submitSuccess();

        void submitFailure(int code, String msg);
        void handlerSend(int what,String message);

        void judanSuccess();
    }

    interface Model extends CoreBaseContract.CoreBaseModel<Presenter> {

        void requestList();

        void requestSubmit(String orderNo);

        void requestJudan(String orderNo);
    }

    abstract class Presenter extends CoreBaseContract.CoreBasePresenter<View,Model> {

        public abstract void requestList();

        public abstract void requestListSuccess(OrderListEntity entity);

        public abstract void requestSubmit(String orderNo,ExecutorService mExecutorService,String name,OrderListEntity.GrabListBean grabListBean);

        public abstract void submitSuccess();

        public abstract void submitFailure(int code, String msg);

        public abstract void requestJudan(String orderNo);

        public abstract void judanSuccess();
    }



}
