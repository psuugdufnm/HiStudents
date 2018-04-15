package org.birdback.histudents.web;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.webkit.WebView;

import org.birdback.histudents.R;
import org.birdback.histudents.core.CoreBaseActivity;

/**
 * Created by meixin.song on 2018/4/15.
 */

public class WebActivity extends CoreBaseActivity {

    private SwipeRefreshLayout mRefreshLayout;
    private WebView mWebView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_web;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        mWebView = findViewById(R.id.web_view);
    }

    @Override
    public void initListener() {

    }
}
