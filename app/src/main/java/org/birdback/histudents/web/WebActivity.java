package org.birdback.histudents.web;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.gson.Gson;

import org.birdback.histudents.R;
import org.birdback.histudents.activity.LoginActivity;
import org.birdback.histudents.base.TitleView;
import org.birdback.histudents.core.CoreBaseActivity;
import org.birdback.histudents.net.Callback.OnFailureCallBack;
import org.birdback.histudents.net.Callback.OnSuccessCallBack;
import org.birdback.histudents.net.HttpServer;
import org.birdback.histudents.service.RequestParams;
import org.birdback.histudents.utils.LogUtil;
import org.birdback.histudents.utils.Session;
import org.birdback.histudents.utils.TextUtils;
import org.birdback.histudents.utils.VerifyUtil;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by meixin.song on 2018/4/15.
 */

public class WebActivity extends CoreBaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout mRefreshLayout;
    private WebView mWebView;
    private String mWebUrl;
    private TitleView mTitleView;

    public static void start(Context context, String url) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra("url",url);
        context.startActivity(intent);
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    //修改title
                    mTitleView.setTitleText(String.valueOf(msg.obj));
                    break;
                case 2:
                    mRefreshLayout.setRefreshing(true);
                    loadWebView();
                    break;
            }
        }
    };

    @Override
    public int getLayoutId() {
        return R.layout.activity_web;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        mWebView = findViewById(R.id.web_view);
        mTitleView = findViewById(R.id.title_view);

        Intent intent = getIntent();
        mWebUrl = intent.getStringExtra("url");


        mRefreshLayout.setRefreshing(true);
        mRefreshLayout.setOnRefreshListener(this);
    }

    private void loadWebView(){
        if (VerifyUtil.isEmpty(mWebUrl)) {
            return;
        }
        synCookies(this,mWebUrl,Session.getCookie());
        mWebView.loadUrl(mWebUrl);
    }

    /**
     * 给webview请求的url设置cookie
     * 如果不设置，webview会有一个自动配置的cookie
     * @param context
     * @param url
     * @param cookie
     */
    public void synCookies(Context context, String url, String cookie) {
        CookieSyncManager.createInstance(context);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.setCookie(url, cookie);
        CookieSyncManager.getInstance().sync();
    }

    public static void removeCookie(Context context) {
        CookieSyncManager.createInstance(context);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
        CookieSyncManager.getInstance().sync();
    }


    @Override
    public void initListener() {
        loadWebView();
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setJavaScriptEnabled(true);
        //自适应屏幕
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);

        mWebView.addJavascriptInterface(new WebActivity.JSInterface(), "android");

        mWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                return super.onJsAlert(view, url, message, result);
            }

        });

        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //其他问题
                if (mRefreshLayout != null){
                    mRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                //网络问题
                if (mRefreshLayout != null){
                    mRefreshLayout.setRefreshing(false);
                }
            }
        });
    }

    @Override
    public void onRefresh() {
        mHandler.sendEmptyMessage(2);
    }


    private final class JSInterface {

        @JavascriptInterface
        public void showTips(String json){

            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(json);
                String msg = jsonObject.optString("msg");
                TextUtils.makeText(msg);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @JavascriptInterface
        public void setTitle(String json){
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(json);
                String title = jsonObject.optString("title");
                Message message = new Message();
                message.what = 1;
                message.obj = title;
                mHandler.sendMessage(message);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        @JavascriptInterface
        public void openNewActivity(String json){
            OpenPageRequestInfo info = new Gson().fromJson(json, OpenPageRequestInfo.class);
            WebActivity.start(WebActivity.this,info.url);//todo: 添加请求参数，请求方式等，调试
        }

        @JavascriptInterface
        public void swipeRefresh(String json){
            mHandler.sendEmptyMessage(2);
        }



        @JavascriptInterface
        public void ajaxRequest(String json){
            final AjaxRequestInfo info = new Gson().fromJson(json, AjaxRequestInfo.class);

            HttpServer.getDataFromServer(WebActivity.this,
                    RequestParams.getInstance().getAjaxRequest(info.url,info.postData),
                    new OnSuccessCallBack<Object>() {
                        @Override
                        public void onSuccess(Object entity) {
                            String json = new Gson().toJson(entity);

                            String callBack = info.successFunc.replace(")",",'" + json + "')");

                            final String js = "javascript:window."+ callBack;
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                        mWebView.evaluateJavascript(js, new ValueCallback<String>() {
                                            @Override
                                            public void onReceiveValue(String value) {
                                                LogUtil.i("onReceiveValue value: " + value);
                                            }
                                        });
                                    } else {
                                        mWebView.loadUrl(js);
                                    }
                                }
                            });

                        }
                    }, new OnFailureCallBack() {
                        @Override
                        public void onFailure(int code, String msg) {
                            mWebView.loadUrl("javascript:'"+ info.errorFunc + "'()");
                            if (code == -1) {
                                Session.logout();
                                LoginActivity.start(WebActivity.this);
                            }
                        }
                    });
        }

    }

}
