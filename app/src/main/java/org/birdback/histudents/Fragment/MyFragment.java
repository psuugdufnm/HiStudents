package org.birdback.histudents.Fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.birdback.histudents.Fragment.Model.MyFragmentModel;
import org.birdback.histudents.Fragment.Presenter.MyFragmentPresenter;
import org.birdback.histudents.Fragment.contract.MyContract;
import org.birdback.histudents.R;
import org.birdback.histudents.adapter.GridAdapter;
import org.birdback.histudents.core.CoreBaseFragment;
import org.birdback.histudents.entity.MyMenuEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/4/8.
 */

public class MyFragment extends CoreBaseFragment<MyFragmentPresenter,MyFragmentModel> implements MyContract.View  {

    private RecyclerView recyclerView;
    private List<MyMenuEntity.MenuBean> mData = new ArrayList<>();
    private GridAdapter gridAdapter;
    private TextView tvDayMoney,tvDayBrowseNum,tvDayPayNum,tvDayNum;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_my;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.recycler_view);
        tvDayMoney = view.findViewById(R.id.tv_day_money);
        tvDayBrowseNum = view.findViewById(R.id.tv_day_browse_num);
        tvDayPayNum = view.findViewById(R.id.tv_day_pay_num);
        tvDayNum = view.findViewById(R.id.tv_day_num);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),4));
        gridAdapter = new GridAdapter(mData);
        recyclerView.setAdapter(gridAdapter);
    }
    @Override
    public void initListener() {
        mPresenter.getList();
    }

    @Override
    public void showMessage(String msg) {

    }

    @Override
    public void getListSuccess(MyMenuEntity entity) {
        mData.clear();
        mData.addAll(entity.getMenu());
        gridAdapter.notifyDataSetChanged();

        MyMenuEntity.DayStatBean dayStat = entity.getDay_stat();

        tvDayMoney.setText(dayStat.getPay_money());
        tvDayBrowseNum.setText(dayStat.getView_num());
        tvDayPayNum.setText(dayStat.getPay_num());
        tvDayNum.setText(dayStat.getGoods_num());
    }
}
