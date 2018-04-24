package org.birdback.histudents.service;


import android.text.TextUtils;

import org.birdback.histudents.entity.RequestVo;
import org.birdback.histudents.net.HttpServer;
import org.birdback.histudents.net.RequestUrl;
import org.birdback.histudents.utils.Session;
import org.birdback.histudents.utils.VerifyUtil;

import java.util.HashMap;
import java.util.Map;


/**
 * RequestParams
 * Created by meixin.song on 18/4/15.
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


    public RequestVo login(String name,String pwd) {
        RequestVo reqVo = new RequestVo();
        reqVo.hasDialog = true;
        reqVo.observable = HttpServer.getService(OrderService.class).login(name,pwd, Session.getDeviceToken());
        return reqVo;
    }

    /**
     * 订单列表
     * @return
     */
    public RequestVo requestOrderList() {
        RequestVo reqVo = new RequestVo();
        reqVo.hasDialog = true;
        reqVo.observable = HttpServer.getService(OrderService.class).requestOrderList();
        return reqVo;
    }

    /**
     * 立即接单
     * @return
     */
    public RequestVo requestSubmit(String orderNo) {
        RequestVo reqVo = new RequestVo();
        reqVo.hasDialog = true;
        reqVo.observable = HttpServer.getService(OrderService.class).requestSubmit(orderNo);
        return reqVo;
    }
    /**
     * 立即接单
     * @return
     */
    public RequestVo getList() {
        RequestVo reqVo = new RequestVo();
        reqVo.hasDialog = true;
        reqVo.observable = HttpServer.getService(OrderService.class).getList();
        return reqVo;
    }

    public RequestVo getAjaxRequest(String url, String postData) {
        HashMap<String, String> map = bornPostParam(postData);
        RequestVo reqVo = new RequestVo();
        reqVo.hasDialog = true;
        if (!VerifyUtil.isEmpty(postData)){

            reqVo.observable = HttpServer.getService(OrderService.class).requestTest(url,map);
        }else {

            reqVo.observable = HttpServer.getService(OrderService.class).requestTest(url);
        }
        return reqVo;
    }

    /**
     * 将封装好的Post参数级别进行逻辑处理.
     *
     * @param postParam
     * @return
     */
    public HashMap<String, String> bornPostParam(String postParam) {
        HashMap hashMap = new HashMap();
        if (!TextUtils.isEmpty(postParam)) {
            String[] param = postParam.split("&");
            for (int i = 0; i < param.length; ++i) {
                int index = param[i].lastIndexOf("=");
                if (index != -1) {
                    hashMap.put(param[i].substring(0, index), param[i].substring(index + 1));
                }
            }
        }
        return hashMap;
    }

}
