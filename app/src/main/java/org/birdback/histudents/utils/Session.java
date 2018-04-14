package org.birdback.histudents.utils;

/**
 *
 * Created by meixin.song on 2018/4/10.
 */

public class Session {

    private static String cookie = "";


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


}
