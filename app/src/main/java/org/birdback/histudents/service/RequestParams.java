package org.birdback.histudents.service;


import android.text.TextUtils;

import org.birdback.histudents.entity.RequestVo;
import org.birdback.histudents.net.HttpServer;
import org.birdback.histudents.net.RequestUrl;
import org.birdback.histudents.utils.VerifyUtil;

import java.util.HashMap;
import java.util.Map;


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
        reqVo.observable = HttpServer.getService(OrderService.class).getTest(RequestUrl.BASE_URL+ RequestUrl.TEST);
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
