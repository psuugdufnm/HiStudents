package org.birdback.histudents.net;



public interface FrameworkSupport {

    /**
     * token失效
     */
    void onTokenInvalid();

    /**
     * 网络异常,网络请求没有200
     */
    void onNetWorkError();
    /**
     * 当服务端维护时
     */
    void onSystemMaintenance();
}
