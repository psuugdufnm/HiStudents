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
import org.birdback.histudents.activity.LoginActivity;
import org.birdback.histudents.activity.SettingActivity;
import org.birdback.histudents.adapter.GridAdapter;
import org.birdback.histudents.adapter.OnRecyclerViewListener;
import org.birdback.histudents.base.TitleView;
import org.birdback.histudents.core.CoreBaseFragment;
import org.birdback.histudents.entity.MyMenuEntity;
import org.birdback.histudents.net.Callback.OnFailureCallBack;
import org.birdback.histudents.net.Callback.OnSuccessCallBack;
import org.birdback.histudents.net.HttpServer;
import org.birdback.histudents.net.RequestUrl;
import org.birdback.histudents.service.RequestParams;
import org.birdback.histudents.utils.Session;
import org.birdback.histudents.utils.TextUtils;
import org.birdback.histudents.utils.VerifyUtil;
import org.birdback.histudents.view.HiDialog;
import org.birdback.histudents.web.WebActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/4/8.
 */

public class MyFragment extends CoreBaseFragment<MyFragmentPresenter,MyFragmentModel> implements MyContract.View, SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    private RecyclerView recyclerView;
    private List<MyMenuEntity.MenuBean> mData = new ArrayList<>();
    private GridAdapter gridAdapter;
    private TextView tvDayMoney,tvDayBrowseNum,tvDayPayNum,tvDayNum;
    private TextView tvWaitReceipt,tvWaitSend,tvWaitCome;
    private TextView tvMonthOrderNum,tvMonthTurnover,tvMonthViewNum;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView tvLogout;
    private TitleView titleView;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_my;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        recyclerView = view.findViewById(R.id.recycler_view);
        tvDayMoney = view.findViewById(R.id.tv_day_money);
        tvDayBrowseNum = view.findViewById(R.id.tv_day_browse_num);
        tvDayPayNum = view.findViewById(R.id.tv_day_pay_num);
        tvDayNum = view.findViewById(R.id.tv_day_num);
        tvWaitReceipt = view.findViewById(R.id.tv_wait_receipt);
        tvWaitSend = view.findViewById(R.id.tv_wait_send);
        tvWaitCome = view.findViewById(R.id.tv_wait_come);

        tvMonthOrderNum = view.findViewById(R.id.tv_month_order_num);
        tvMonthTurnover = view.findViewById(R.id.tv_month_turnover);
        tvMonthViewNum = view.findViewById(R.id.tv_month_view_num);
        tvLogout = view.findViewById(R.id.tv_logout);
        titleView = view.findViewById(R.id.title_view);

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),4));
        gridAdapter = new GridAdapter(mData);
        recyclerView.setAdapter(gridAdapter);
    }
    @Override
    public void initListener() {
        tvLogout.setOnClickListener(this);
        swipeRefreshLayout.setOnRefreshListener(this);
        mPresenter.getList();

        gridAdapter.setOnItemclickListener(new OnRecyclerViewListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                MyMenuEntity.MenuBean menuBean = mData.get(position);
                if (!VerifyUtil.isEmpty(menuBean.getUrl())){
                    WebActivity.start(getActivity(), RequestUrl.BASE_URL + menuBean.getUrl());
                }
            }
        });

        titleView.setOnTitleClickListener(new TitleView.OnTitleClickListener() {
            @Override
            public void onLeftClick(View v) {}
            @Override
            public void onRightClick(View v) {
                SettingActivity.start(getActivity());
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
        if (swipeRefreshLayout != null){
            swipeRefreshLayout.setRefreshing(false);
        }

        MyMenuEntity.DayStatBean dayStat = entity.getDay_stat();

        tvDayMoney.setText(dayStat.getPay_money());
        tvDayBrowseNum.setText(dayStat.getView_num());
        tvDayPayNum.setText(dayStat.getPay_num());
        tvDayNum.setText(dayStat.getGoods_num());

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
    public void onRefresh() {
        mPresenter.getList();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.tv_logout) {
            new HiDialog.Builder(getActivity())
                    .setContent("退出登录后将收不到新订单通知，确定要退出登录吗？")
                    .setLeftBtnText("取消")
                    .setRightBtnText("确定")
                    .setRightCallBack(new HiDialog.RightClickCallBack() {
                        @Override
                        public void dialogRightBtnClick() {
                            mPresenter.logout();
                            Session.logout();
                            LoginActivity.start(getActivity());
                        }
                    }).build();

        }
    }
}
