package org.birdback.histudents.Fragment;


import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.birdback.histudents.Fragment.Model.MyFragmentModel;
import org.birdback.histudents.Fragment.Presenter.MyFragmentPresenter;
import org.birdback.histudents.Fragment.contract.MyContract;
import org.birdback.histudents.R;
import org.birdback.histudents.activity.GoodsSettingActivity;
import org.birdback.histudents.activity.LoginActivity;
import org.birdback.histudents.adapter.GridAdapter;
import org.birdback.histudents.adapter.OnRecyclerViewListener;
import org.birdback.histudents.core.CoreBaseFragment;
import org.birdback.histudents.entity.MyMenuEntity;
import org.birdback.histudents.net.RequestUrl;
import org.birdback.histudents.utils.Session;
import org.birdback.histudents.utils.VerifyUtil;
import org.birdback.histudents.view.HiDialog;
import org.birdback.histudents.web.WebActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 店铺管理 on 2018/4/8.
 */

public class MyFragment extends CoreBaseFragment<MyFragmentPresenter, MyFragmentModel> implements MyContract.View, SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    private RecyclerView recyclerView;
    private List<MyMenuEntity.MenuBean> mData = new ArrayList<>();
    private GridAdapter gridAdapter;
    private TextView tvDayMoney, tvDayBrowseNum, tvDayPayNum;
    private TextView tvWaitReceipt, tvWaitSend, tvWaitCome;
    private TextView tvMonthOrderNum, tvMonthTurnover, tvMonthViewNum;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView tvLogout;
    private TextView tvGoodsManager;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_my_new;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        recyclerView = view.findViewById(R.id.recycler_view);

        tvGoodsManager = view.findViewById(R.id.tv_goods_manager);

        tvDayMoney = view.findViewById(R.id.tv_today_money);
        tvDayPayNum = view.findViewById(R.id.tv_today_order);
        tvDayBrowseNum = view.findViewById(R.id.tv_today_view);

        tvWaitReceipt = view.findViewById(R.id.tv_wait_receipt);
        tvWaitSend = view.findViewById(R.id.tv_wait_send);
        tvWaitCome = view.findViewById(R.id.tv_wait_come);

        tvMonthOrderNum = view.findViewById(R.id.tv_month_order);
        tvMonthTurnover = view.findViewById(R.id.tv_month_money);
        tvMonthViewNum = view.findViewById(R.id.tv_month_view);
        tvLogout = view.findViewById(R.id.tv_logout);

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        gridAdapter = new GridAdapter(mData);
        recyclerView.setAdapter(gridAdapter);
    }

    @Override
    public void initListener() {
        tvLogout.setOnClickListener(this);
        tvGoodsManager.setOnClickListener(this);
        swipeRefreshLayout.setOnRefreshListener(this);
        mPresenter.getList();

        gridAdapter.setOnItemclickListener(new OnRecyclerViewListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                MyMenuEntity.MenuBean menuBean = mData.get(position);
                if (!VerifyUtil.isEmpty(menuBean.getUrl())) {
                    WebActivity.start(getActivity(), RequestUrl.BASE_URL + menuBean.getUrl());
                }
            }
        });


    }

    @Override
    public void showMessage(String msg) {

    }

    @Override
    public void getListSuccess(MyMenuEntity entity) {

        mData.clear();
        mData.addAll(entity.getMenu());
        gridAdapter.notifyDataSetChanged();
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(false);
        }

        MyMenuEntity.DayStatBean dayStat = entity.getDay_stat();

        tvDayMoney.setText(dayStat.getPay_money());
        tvDayBrowseNum.setText(dayStat.getView_num());
        tvDayPayNum.setText(dayStat.getPay_num());

        MyMenuEntity.OrderStatBean orderStat = entity.getOrder_stat();
        tvWaitReceipt.setText(orderStat.getWait_grab());
        tvWaitSend.setText(orderStat.getWait_send());
        tvWaitCome.setText(orderStat.getWait_succ());

        MyMenuEntity.MonthStatBean monthStat = entity.getMonth_stat();
        tvMonthOrderNum.setText(monthStat.getPay_num());
        tvMonthTurnover.setText(monthStat.getPay_money());
        tvMonthViewNum.setText(monthStat.getView_num());
    }

    @Override
    public void logoutSuccess() {
        Session.logout();
        LoginActivity.start(getActivity());
    }

    @Override
    public void onRefresh() {
        mPresenter.getList();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_logout:
                new HiDialog.Builder(getActivity())
                        .setContent("退出登录后将收不到新订单通知，确定要退出登录吗？")
                        .setLeftBtnText("取消")
                        .setRightBtnText("确定")
                        .setRightCallBack(new HiDialog.RightClickCallBack() {
                            @Override
                            public void dialogRightBtnClick() {
                                mPresenter.logout();
                            }
                        }).build();
                break;
            case R.id.tv_goods_manager:
                GoodsSettingActivity.start(getActivity());
                break;
        }
    }
}
