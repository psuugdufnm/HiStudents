package org.birdback.histudents.utils;

/**
 *
 * Created by meixin.song on 2018/4/10.
 */

public class Session {

    private static String cookie = "";
    private static String deviceToken = ""; //友盟推送设备唯一标识符


    public static String getCookie() {
        if (VerifyUtil.isEmpty(cookie)) {
            cookie = SharedPreUtil.getValue("cookie", "");
        }
        return cookie;
    }

    public static void setCookie(String cookie) {
        Session.cookie = cookie;
        SharedPreUtil.putValue("cookie", VerifyUtil.isEmpty(cookie) ? "" : cookie);
    }

    public static String getDeviceToken() {
        if (VerifyUtil.isEmpty(deviceToken)) {
            deviceToken = SharedPreUtil.getValue("deviceToken", "");
        }
        return deviceToken;
    }

    public static void setDeviceToken(String deviceToken) {
        Session.deviceToken = deviceToken;
        SharedPreUtil.putValue("deviceToken", VerifyUtil.isEmpty(deviceToken) ? "" : deviceToken);
    }

    public static void logout(){
        Session.setCookie("");
    }


}
