package org.birdback.histudents.web;

import org.json.JSONObject;

public class AjaxRequestInfo {

    // {
    // "url":"http://ztest3-mapi.jikeyue.com/pay/confirm/",
    // "postData":"buy_id=2&buy_num=1&pay_type=3&pay_money=98&_sid=coach_level",
    // "showLoader":1,
    // "successFunc":"Callback.run(1)",
    // "errorFunc":"Request.error",
    // "timeout":30000
    // }

    public String url;

    public String postData;

    public int showLoader = 1;

    public String successFunc;

    public String errorFunc;

    public int timeout;

    public AjaxRequestInfo() {

    }

    public AjaxRequestInfo(JSONObject jsonObject) {
        try {
            if (!jsonObject.isNull("url")) {
                url = jsonObject.getString("url");
            }
            if (!jsonObject.isNull("postData")) {
                postData = jsonObject.getString("postData");
            }
            if (!jsonObject.isNull("errorFunc")) {
                errorFunc = jsonObject.getString("errorFunc");
            }
            if (!jsonObject.isNull("showLoader")) {
                showLoader = jsonObject.getInt("showLoader");
            } else {
                showLoader = 1;
            }
            if (!jsonObject.isNull("successFunc")) {
                successFunc = jsonObject.getString("successFunc");
            }
            if (!jsonObject.isNull("timeout")) {
                timeout = jsonObject.getInt("timeout");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
