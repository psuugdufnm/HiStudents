package org.birdback.histudents.event;

import com.google.gson.Gson;

/**
 * Created by Administrator on 2018/4/27.
 */

public class MessageEvent {
    private String message = "";
    private String orderNo = "";

    public String getOrderNo() {

        UmengNotifyEntity entity = new Gson().fromJson(getMessage(), UmengNotifyEntity.class);

        return entity.getExtra().getOrder_no();
    }

    public void setOrderNo(String orderNo) {



        this.orderNo = orderNo;
    }



    public MessageEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
