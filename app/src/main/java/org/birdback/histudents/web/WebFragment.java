package org.birdback.histudents.web;

import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.birdback.histudents.Fragment.Model.OrderSearchModel;
import org.birdback.histudents.Fragment.Presenter.OrderSearchPresenter;
import org.birdback.histudents.Fragment.contract.OrderSearchContract;
import org.birdback.histudents.R;
import org.birdback.histudents.base.TitleView;
import org.birdback.histudents.core.CoreBaseFragment;
import org.birdback.histudents.net.Callback.OnFailureCallBack;
import org.birdback.histudents.net.Callback.OnSuccessCallBack;
import org.birdback.histudents.net.HttpServer;
import org.birdback.histudents.service.RequestParams;
import org.birdback.histudents.utils.LogUtil;
import org.birdback.histudents.utils.TextUtils;
import org.birdback.histudents.utils.VerifyUtil;

import retrofit2.converter.gson.GsonConverterFactory;

/**
 * WebView fragment
 * Created by meixin.song on 2018/4/8.
 */

public class WebFragment extends CoreBaseFragment{


    private WebView mWebView;
    private TitleView mTitleView;
    private String mWebUrl;

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
    }

    private final class JSInterface {
        /**
         * 注意这里的@JavascriptInterface注解， target是4.2以上都需要添加这个注解，否则无法调用
         * @param text
         */
        @JavascriptInterface
        public void showTips(String text){
            TextUtils.makeText(text);
        }

        @JavascriptInterface
        public void setTitle(String text){
            mTitleView.setTitleText(text);
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
