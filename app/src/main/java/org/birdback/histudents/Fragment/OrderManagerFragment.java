package org.birdback.histudents.Fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import org.birdback.histudents.Fragment.Model.OrderManagerModel;
import org.birdback.histudents.Fragment.Presenter.OrderManagerPresenter;
import org.birdback.histudents.Fragment.contract.OrderManagerContract;
import org.birdback.histudents.R;
import org.birdback.histudents.activity.PrinterManagerActivity;
import org.birdback.histudents.activity.SeachPrinterActivity;
import org.birdback.histudents.adapter.OnRecyclerViewListener;
import org.birdback.histudents.adapter.OrderListAdapter;
import org.birdback.histudents.core.CoreBaseFragment;
import org.birdback.histudents.entity.OrderListEntity;
import org.birdback.histudents.utils.Session;
import org.birdback.histudents.utils.TextUtils;
import org.birdback.histudents.utils.VerifyUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * 订单管理
 * Created by meixin.song on 2018/4/8.
 */

public class OrderManagerFragment extends CoreBaseFragment<OrderManagerPresenter,OrderManagerModel> implements View.OnClickListener,OrderManagerContract.View, SwipeRefreshLayout.OnRefreshListener, OnRecyclerViewListener {

    private ImageView mIvPrint; //打印机
    private Context mContext;
    private SwipeRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private List<OrderListEntity.GrabListBean> mDatas = new ArrayList<>();
    private OrderListAdapter adapter;

    private String[] perms = {Manifest.permission.CALL_PHONE};
    private final int PERMS_REQUEST_CODE = 200;
    private String phone;
    private View mEmptyView;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_order_manager;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        mIvPrint = view.findViewById(R.id.iv_print);
        mRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mEmptyView = view.findViewById(R.id.ll_empty_view);
        mContext = getActivity();

    }

    @Override
    public void initListener() {
        mIvPrint.setOnClickListener(this);
        mRefreshLayout.setOnRefreshListener(this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        adapter = new OrderListAdapter(mDatas);
        mRecyclerView.setAdapter(adapter);
        adapter.setOnRecyclerViewListener(this);

        mRefreshLayout.setRefreshing(true);
        mPresenter.requestList();

    }

    @Override
    public void onRequestPermissionsResult(int permsRequestCode, String[] permissions, int[] grantResults) {
        switch (permsRequestCode) {
            case PERMS_REQUEST_CODE:
                boolean storageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                if (storageAccepted) {
                    callPhone(phone);
                } else {
                    Log.i("MainActivity", "没有权限操作这个请求");
                }
                break;

        }
    }

    public void callPhone(String phone){

        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + phone);
        intent.setData(data);
        startActivity(intent);
    }
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_print:
                if(!VerifyUtil.isEmpty(Session.getBluetoothAddress())){
                    //已有配对蓝牙打印机
                    PrinterManagerActivity.start(mContext);
                }else {
                    //无配对打印机
                    SeachPrinterActivity.start(mContext);
                }
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
        mPresenter.requestList();

    }

    @Override
    public void requestListSuccess(OrderListEntity entity) {
        if (mRefreshLayout.isRefreshing()){
            mRefreshLayout.setRefreshing(false);
        }
        if (entity.getGrab_list().size() > 0){
            mEmptyView.setVisibility(View.GONE);
            mDatas.clear();
            mDatas.addAll(entity.getGrab_list());
            adapter.notifyDataSetChanged();
        }else {
            mEmptyView.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void submitSuccess() {
        //TODO
    }

    @Override
    public void submitFailure() {

    }


    @Override
    public void onItemClick(View itemView, int position) {
        if (itemView.getId() == R.id.tv_phone) {
            phone = mDatas.get(position).getAddr_phone();
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
                requestPermissions(perms,PERMS_REQUEST_CODE);//请求权限
            } else {
                callPhone(phone);
            }
        }

        if (itemView.getId() == R.id.btn_jiedan) {
            String orderNo = mDatas.get(position).getOrder_no();
            if (!VerifyUtil.isEmpty(orderNo)) {
                mPresenter.requestSubmit(orderNo);
            }else {
                TextUtils.makeText("订单异常，请刷新");
            }
        }
    }
}
