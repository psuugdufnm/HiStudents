package org.birdback.histudents.Fragment;

import android.os.Bundle;
import android.view.View;

import org.birdback.histudents.Fragment.Model.OrderManagerModel;
import org.birdback.histudents.Fragment.Model.OrderSearchModel;
import org.birdback.histudents.Fragment.Presenter.OrderSearchPresenter;
import org.birdback.histudents.Fragment.contract.OrderSearchContract;
import org.birdback.histudents.R;
import org.birdback.histudents.core.CoreBaseFragment;

/**
 * Created by Administrator on 2018/4/8.
 */

public class OrderSearchFragment extends CoreBaseFragment<OrderSearchPresenter,OrderSearchModel> implements OrderSearchContract.View {
    @Override
    public int getLayoutId() {
        return R.layout.fragment_order_search;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {

    }

    @Override
    public void initListener() {
    }

    @Override
    public void showMessage(String msg) {

    }
}
