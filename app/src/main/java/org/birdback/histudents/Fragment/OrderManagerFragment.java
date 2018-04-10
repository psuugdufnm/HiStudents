package org.birdback.histudents.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ImageView;

import org.birdback.histudents.Fragment.Model.MyFragmentModel;
import org.birdback.histudents.Fragment.Model.OrderManagerModel;
import org.birdback.histudents.Fragment.Presenter.MyFragmentPresenter;
import org.birdback.histudents.Fragment.Presenter.OrderManagerPresenter;
import org.birdback.histudents.Fragment.contract.OrderManagerContract;
import org.birdback.histudents.R;
import org.birdback.histudents.activity.PrinterManagerActivity;
import org.birdback.histudents.core.CoreBaseFragment;

/**
 * Created by meixin.song on 2018/4/8.
 */

public class OrderManagerFragment extends CoreBaseFragment<OrderManagerPresenter,OrderManagerModel> implements View.OnClickListener,OrderManagerContract.View, SwipeRefreshLayout.OnRefreshListener {

    private ImageView mIvPrint; //打印机
    private Context mContext;
    private SwipeRefreshLayout mRefreshLayout;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_order_manager;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        mIvPrint = view.findViewById(R.id.iv_print);
        mRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        mContext = getActivity();
    }

    @Override
    public void initListener() {
        mIvPrint.setOnClickListener(this);
        mRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_print:
                PrinterManagerActivity.start(mContext);
                break;
            default:
                break;
        }
    }

    @Override
    public void showMessage(String msg) {

    }

    @Override
    public void onRefresh() {
        if (mRefreshLayout.isRefreshing()){
            mRefreshLayout.setRefreshing(false);
        }

    }
}
