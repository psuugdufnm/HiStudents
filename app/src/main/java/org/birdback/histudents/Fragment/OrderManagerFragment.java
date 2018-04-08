package org.birdback.histudents.Fragment;

import android.os.Bundle;
import android.view.View;

import org.birdback.histudents.Fragment.Model.MyFragmentModel;
import org.birdback.histudents.Fragment.Model.OrderManagerModel;
import org.birdback.histudents.Fragment.Presenter.MyFragmentPresenter;
import org.birdback.histudents.Fragment.Presenter.OrderManagerPresenter;
import org.birdback.histudents.R;
import org.birdback.histudents.core.CoreBaseFragment;

/**
 * Created by Administrator on 2018/4/8.
 */

public class OrderManagerFragment extends CoreBaseFragment<OrderManagerPresenter,OrderManagerModel> {
    @Override
    public int getLayoutId() {
        return R.layout.fragment_order_manager;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {

    }
}
