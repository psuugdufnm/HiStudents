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
import org.birdback.histudents.adapter.GridAdapter;
import org.birdback.histudents.adapter.OnRecyclerViewListener;
import org.birdback.histudents.core.CoreBaseFragment;
import org.birdback.histudents.entity.MyMenuEntity;
import org.birdback.histudents.net.RequestUrl;
import org.birdback.histudents.utils.VerifyUtil;
import org.birdback.histudents.web.WebActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/4/8.
 */

public class MyFragment extends CoreBaseFragment<MyFragmentPresenter,MyFragmentModel> implements MyContract.View, SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView recyclerView;
    private List<MyMenuEntity.MenuBean> mData = new ArrayList<>();
    private GridAdapter gridAdapter;
    private TextView tvDayMoney,tvDayBrowseNum,tvDayPayNum,tvDayNum;
    private TextView tvWaitReceipt,tvWaitSend,tvWaitCome;
    private TextView tvMonthOrderNum,tvMonthTurnover,tvMonthViewNum;
    private SwipeRefreshLayout swipeRefreshLayout;

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

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),4));
        gridAdapter = new GridAdapter(mData);
        recyclerView.setAdapter(gridAdapter);
    }
    @Override
    public void initListener() {
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
        tvMonthOrderNum.setText(monthStat.getGoods_num());
        tvMonthTurnover.setText(monthStat.getPay_money());
        tvMonthViewNum.setText(monthStat.getView_num());
    }

    @Override
    public void onRefresh() {
        mPresenter.getList();
    }
}
