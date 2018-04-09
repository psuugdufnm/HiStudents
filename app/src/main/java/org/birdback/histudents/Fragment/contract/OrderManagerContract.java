package org.birdback.histudents.Fragment.contract;

import org.birdback.histudents.core.CoreBaseContract;

/**
 * Created by Administrator on 2018/4/9.
 */

public interface OrderManagerContract {

    interface View extends CoreBaseContract.CoreBaseView {

    }

    interface Model extends CoreBaseContract.CoreBaseModel<Presenter> {

    }

    abstract class Presenter extends CoreBaseContract.CoreBasePresenter<View,Model> {

    }



}
