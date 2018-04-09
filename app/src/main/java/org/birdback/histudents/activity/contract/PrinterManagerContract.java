package org.birdback.histudents.activity.contract;

import org.birdback.histudents.core.CoreBaseContract;

/**
 * Created by meixin.song on 2018/4/9.
 */

public interface PrinterManagerContract {

    interface View extends CoreBaseContract.CoreBaseView {

    }



    abstract class Presenter extends CoreBaseContract.CoreBasePresenter<View,Model> {

    }



    interface Model extends CoreBaseContract.CoreBaseModel<Presenter> {

    }


}
