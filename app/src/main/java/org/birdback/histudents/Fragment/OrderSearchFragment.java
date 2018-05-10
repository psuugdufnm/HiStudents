package org.birdback.histudents.Fragment;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.flyco.tablayout.SlidingTabLayout;

import org.birdback.histudents.Fragment.Model.OrderManagerModel;
import org.birdback.histudents.Fragment.Model.OrderSearchModel;
import org.birdback.histudents.Fragment.Presenter.OrderSearchPresenter;
import org.birdback.histudents.Fragment.contract.OrderSearchContract;
import org.birdback.histudents.R;
import org.birdback.histudents.core.CoreBaseFragment;
import org.birdback.histudents.utils.TextUtils;

/**
 * 订单查询fragment
 * Created by Administrator on 2018/4/8.
 */

public class OrderSearchFragment extends CoreBaseFragment<OrderSearchPresenter,OrderSearchModel> implements OrderSearchContract.View {


    private SlidingTabLayout slidingTabLayout;
    private ViewPager viewPager;
    private String[] titles = {"热门", "iOS", "Android"
            , "前端", "后端", "设计", "工具资源"};

    @Override
    public int getLayoutId() {
        return R.layout.fragment_order_search;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        slidingTabLayout = view.findViewById(R.id.sliding_tab_layout);
        viewPager = view.findViewById(R.id.view_pager);

    }

    @Override
    public void initListener() {

    }


    @Override
    public void showMessage(String msg) {

    }
}
