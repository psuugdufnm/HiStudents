package org.birdback.histudents.web;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.google.gson.Gson;

import org.birdback.histudents.MainActivity;
import org.birdback.histudents.R;
import org.birdback.histudents.activity.LoginActivity;
import org.birdback.histudents.base.TitleView;
import org.birdback.histudents.core.CoreBaseFragment;
import org.birdback.histudents.net.Callback.OnFailureCallBack;
import org.birdback.histudents.net.Callback.OnSuccessCallBack;
import org.birdback.histudents.net.HttpServer;
import org.birdback.histudents.service.RequestParams;
import org.birdback.histudents.utils.LogUtil;
import org.birdback.histudents.utils.Session;
import org.birdback.histudents.utils.TextUtils;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * WebView fragment
 * Created by meixin.song on 2018/4/8.
 */

public class WebFragment extends CoreBaseFragment implements SwipeRefreshLayout.OnRefreshListener {


    private WebView mWebView;
    private TitleView mTitleView;
    private String mWebUrl;
    private MainActivity mActivity;

    private SwipeRefreshLayout swipeRefreshLayout;
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
                    swipeRefreshLayout.setRefreshing(true);
                    mWebView.loadUrl(mWebUrl == null ? "http://store.birdback.org/" : mWebUrl);
                    break;
            }
        }
    };

    public void setUrl(String webUrl){
        this.mWebUrl = webUrl;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_web;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        mWebView = view.findViewById(R.id.web_view);
        mTitleView = view.findViewById(R.id.title_view);
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        mActivity = (MainActivity) getActivity();

        swipeRefreshLayout.setRefreshing(true);
        swipeRefreshLayout.setOnRefreshListener(this);
    }


    @Override
    public void initListener() {
        mWebView.loadUrl(mWebUrl == null ? "http://store.birdback.org/test/index" :mWebUrl);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setJavaScriptEnabled(true);
        //自适应屏幕
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);

        mWebView.addJavascriptInterface(new JSInterface(), "android");

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
                if (swipeRefreshLayout != null){
                    swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                if (swipeRefreshLayout != null){
                    swipeRefreshLayout.setRefreshing(false);
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
        public void ajaxRequest(String json){
            final AjaxRequestInfo info = new Gson().fromJson(json, AjaxRequestInfo.class);
            HttpServer.getDataFromServer(WebFragment.this,
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
                }
            });
        }


    }
}
