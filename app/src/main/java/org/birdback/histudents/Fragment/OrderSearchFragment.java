package org.birdback.histudents.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import com.flyco.tablayout.SlidingTabLayout;
import org.birdback.histudents.Fragment.Model.OrderSearchModel;
import org.birdback.histudents.Fragment.Presenter.OrderSearchPresenter;
import org.birdback.histudents.Fragment.contract.OrderSearchContract;
import org.birdback.histudents.R;
import org.birdback.histudents.core.CoreBaseFragment;
import java.util.ArrayList;

/**
 * 订单查询fragment
 * Created by Administrator on 2018/4/8.
 */

public class OrderSearchFragment extends CoreBaseFragment<OrderSearchPresenter,OrderSearchModel> implements OrderSearchContract.View {


    private SlidingTabLayout slidingTabLayout;
    private ViewPager viewPager;
    private String[] titles = {"待送出", "待送达", "已完成", "已退单"};
    private ArrayList<Fragment> mFragments = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.fragment_order_search;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        slidingTabLayout = view.findViewById(R.id.sliding_tab_layout);
        viewPager = view.findViewById(R.id.view_pager);

        for (String title : titles) {
            mFragments.add(OrderItemFragment.getInstance(title));
        }
        MyPagerAdapter mAdapter = new MyPagerAdapter(getActivity().getSupportFragmentManager());
        viewPager.setAdapter(mAdapter);
        slidingTabLayout.setViewPager(viewPager);

    }

    @Override
    public void initListener() {

    }


    @Override
    public void showMessage(String msg) {

    }


    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }
}
