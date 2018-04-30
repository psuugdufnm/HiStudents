package org.birdback.histudents.Fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;

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
import org.birdback.histudents.event.UmengNotifyEntity;
import org.birdback.histudents.utils.Session;
import org.birdback.histudents.utils.TextUtils;
import org.birdback.histudents.utils.VerifyUtil;
import org.birdback.histudents.view.HiDialog;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    TextUtils.makeText("蓝牙打印机连接异常，请检查");
                    PrinterManagerActivity.start(getActivity());
                    break;

            }
        }
    };
    private String shopName;
    private Switch switchTab;
    private String autoOrderNo;
    private HiDialog.Builder builder;

    @Override
    public void  handlerSend(int what,String message){
        if (VerifyUtil.isEmpty(message)){
            mHandler.sendEmptyMessage(what);
        }
    }

    @Override
    public void judanSuccess() {
        //拒单成功刷新页面
        requestData();
    }

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
        switchTab = view.findViewById(R.id.switch_tab);
        mContext = getActivity();

        //注册事件
        EventBus.getDefault().register(this);

    }

    @Override
    public void initListener() {
        mIvPrint.setOnClickListener(this);
        mRefreshLayout.setOnRefreshListener(this);

        boolean autoGet = Session.getAutoGet();
        switchTab.setChecked(autoGet);

        switchTab.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Session.setAutoGet(b);
            }
        });

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        adapter = new OrderListAdapter(mDatas);
        mRecyclerView.setAdapter(adapter);
        adapter.setOnRecyclerViewListener(this);

        requestData();

        initPrinterView();

    }

    /**
     * 请求数据
     */
    private void requestData() {
        mRefreshLayout.setRefreshing(true);
        mPresenter.requestList();
    }

    private void initPrinterView() {
        if(VerifyUtil.isEmpty(Session.getBluetoothAddress())){
            SeachPrinterActivity.start(getActivity());
        }
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
        requestData();
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
        closeProgressDialog();
        TextUtils.makeText(msg);
    }

    @Override
    public void onRefresh() {
        requestData();
    }

    @Override
    public void requestListSuccess(OrderListEntity entity) {
        if (mRefreshLayout.isRefreshing()){
            mRefreshLayout.setRefreshing(false);
        }
        shopName = entity.getStore_name();
        List<OrderListEntity.GrabListBean> grab_list = entity.getGrab_list();
        if (grab_list.size() > 0){
            mEmptyView.setVisibility(View.GONE);
            mDatas.clear();
            mDatas.addAll(entity.getGrab_list());
            adapter.notifyDataSetChanged();


            if (switchTab.isChecked() && !VerifyUtil.isEmpty(autoOrderNo)){

                for (int i = 0; i < grab_list.size(); i++) {
                    OrderListEntity.GrabListBean grabListBean = grab_list.get(i);
                    if(autoOrderNo.equals(grabListBean.getOrder_no())){

                        mPresenter.requestSubmit(grabListBean.getOrder_no(),mExecutorService,shopName,grabListBean);
                    }

                }
            }


        }else {
            mEmptyView.setVisibility(View.VISIBLE);
        }



    }

    @Override
    public void submitSuccess() {
        requestData();
    }

    @Override
    public void submitFailure(int code, String msg) {
        TextUtils.makeText(msg);
    }


    @Override
    public void onItemClick(View itemView, final int position) {
        if (itemView.getId() == R.id.tv_phone) {
            phone = mDatas.get(position).getAddr_phone();
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
                requestPermissions(perms,PERMS_REQUEST_CODE);//请求权限
            } else {
                callPhone(phone);
            }
        }

        if (itemView.getId() == R.id.btn_jiedan) {
            OrderListEntity.GrabListBean grabListBean = mDatas.get(position);
            String orderNo = grabListBean.getOrder_no();
            if (!VerifyUtil.isEmpty(orderNo)) {
                mPresenter.requestSubmit(orderNo,mExecutorService,shopName,grabListBean);
            }else {
                TextUtils.makeText("订单异常，请刷新");
            }
        }

        if (itemView.getId() == R.id.btn_judan) {
            if (builder == null){
                builder = new HiDialog.Builder(getActivity())
                        .setContent("拒单后，钱款原路退回到顾客支付账户，确定要拒绝此订单吗？")
                        .setRightBtnText("确定")
                        .setRightCallBack(new HiDialog.RightClickCallBack() {
                            @Override
                            public void dialogRightBtnClick() {
                                OrderListEntity.GrabListBean grabListBean = mDatas.get(position);
                                String orderNo = grabListBean.getOrder_no();
                                if (!VerifyUtil.isEmpty(orderNo)) {
                                    mRefreshLayout.setRefreshing(true);
                                    mPresenter.requestJudan(orderNo);
                                } else {
                                    TextUtils.makeText("订单异常，请刷新");
                                }
                            }
                        }).setLeftBtnText("取消");
            }
            builder.build();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);

        EventBus.getDefault().unregister(this);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMoonEvent(UmengNotifyEntity entity){
        //收到通知，刷新页面

        requestData();
        autoOrderNo = entity.getExtra().getOrder_no();
    }

}
