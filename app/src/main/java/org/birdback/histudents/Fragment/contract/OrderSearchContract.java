package org.birdback.histudents.Fragment.contract;

import org.birdback.histudents.core.CoreBaseContract;

/**
 * Created by meixin.song on 2018/4/9.
 */

public interface OrderSearchContract {

    interface View extends CoreBaseContract.CoreBaseView {

    }

    abstract class Presenter extends CoreBaseContract.CoreBasePresenter<View,Model> {

    }

    interface Model extends CoreBaseContract.CoreBaseModel<Presenter> {

    }
}
