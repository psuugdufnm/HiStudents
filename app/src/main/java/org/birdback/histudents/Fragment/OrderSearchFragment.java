package org.birdback.histudents.Fragment;

import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.Toast;

import org.birdback.histudents.Fragment.Model.OrderManagerModel;
import org.birdback.histudents.Fragment.Model.OrderSearchModel;
import org.birdback.histudents.Fragment.Presenter.OrderSearchPresenter;
import org.birdback.histudents.Fragment.contract.OrderSearchContract;
import org.birdback.histudents.R;
import org.birdback.histudents.core.CoreBaseFragment;
import org.birdback.histudents.utils.TextUtils;

/**
 * Created by Administrator on 2018/4/8.
 */

public class OrderSearchFragment extends CoreBaseFragment<OrderSearchPresenter,OrderSearchModel> implements OrderSearchContract.View {


    private WebView mWebView;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_order_search;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        mWebView = view.findViewById(R.id.web_view);
    }

    @Override
    public void initListener() {
        mWebView.loadUrl("http://store.birdback.org/test/index");
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setJavaScriptEnabled(true);

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
        public void showJsText(String text){
            mWebView.loadUrl("javascript:jsText('"+ text +"')");
        }
    }

    @Override
    public void showMessage(String msg) {

    }
}
