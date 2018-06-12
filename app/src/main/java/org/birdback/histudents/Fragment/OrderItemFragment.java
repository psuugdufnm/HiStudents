package org.birdback.histudents.Fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.birdback.histudents.R;
import org.birdback.histudents.core.CoreBaseFragment;

public class OrderItemFragment extends CoreBaseFragment {


    private TextView mTvTitle;
    private String mTitle;

    public static OrderItemFragment getInstance(String title) {
        OrderItemFragment sf = new OrderItemFragment();
        sf.mTitle = title;
        return sf;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_order_item;
    }

    @Override
    public void initListener() {

    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        mTvTitle = view.findViewById(R.id.tv_title);

        mTvTitle.setText(mTitle);
    }
}
