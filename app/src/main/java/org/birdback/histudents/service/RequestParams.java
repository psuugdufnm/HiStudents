package org.birdback.histudents.service;


import org.birdback.histudents.entity.RequestVo;
import org.birdback.histudents.net.HttpServer;



/**
 * RequestParams
 * Created by Loong on 16/4/26.
 */
public class RequestParams {
    private static RequestParams requestParams;

    public static RequestParams getInstance() {
        if (requestParams == null) {
            synchronized (RequestParams.class) {
                if (requestParams == null) {
                    requestParams = new RequestParams();
                }
            }
        }
        return requestParams;
    }

    public RequestVo getTest() {
        RequestVo reqVo = new RequestVo();
        reqVo.hasDialog = true;
        reqVo.observable = HttpServer.getService(OrderService.class).requestTest();
        return reqVo;
    }


}
