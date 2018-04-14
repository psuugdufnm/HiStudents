package org.birdback.histudents.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ImageView;


import org.birdback.histudents.Fragment.Model.OrderManagerModel;
import org.birdback.histudents.Fragment.Presenter.OrderManagerPresenter;
import org.birdback.histudents.Fragment.contract.OrderManagerContract;
import org.birdback.histudents.R;
import org.birdback.histudents.activity.PrinterManagerActivity;
import org.birdback.histudents.core.CoreBaseFragment;
import org.birdback.histudents.net.Callback.OnFailureCallBack;
import org.birdback.histudents.net.Callback.OnSuccessCallBack;
import org.birdback.histudents.net.HttpServer;
import org.birdback.histudents.service.RequestParams;
import org.birdback.histudents.utils.TextUtils;


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

        view.findViewById(R.id.button_text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HttpServer.getDataFromServer(RequestParams.getInstance().getTest(), new OnSuccessCallBack<Object>() {
                    @Override
                    public void onSuccess(Object entity) {
                        TextUtils.makeText(entity.toString());

                    }
                }, new OnFailureCallBack() {
                    @Override
                    public void onFailure(int code, String msg) {

                    }
                });

            }
        });
    }

    @Override
    public void initListener() {
        mIvPrint.setOnClickListener(this);
        mRefreshLayout.setOnRefreshListener(this);

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
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
