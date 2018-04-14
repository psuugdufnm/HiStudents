package org.birdback.histudents.net.helper;


import java.util.ArrayList;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

public class HttpCookie implements CookieJar {

    // cookies => [XXD_mobile_SESSIONID=9857421f2f0142b7b77957ebd1589123; path=/v5_mobile; httponly]
    // null:HTTP/1.1 200 OK;
    // Connection:keep-alive;
    // Content-Length:745;
    // Content-Type:application/json;
    // charset=UTF-8;
    // Date:Tue, 27 Sep 2016 09:10:22 GMT;
    // Server:Tengine;
    // Set-Cookie:XXD_mobile_SESSIONID=c9573f9c41a1456eacdaf848dc8b26c8;
    // Path=/v5_mobile;
    // HttpOnly;
    // X-Android-Received-Millis:1474967425521;
    // X-Android-Response-Source:NETWORK 200;
    // X-Android-Selected-Protocol:http/1.1;
    // X-Android-Sent-Millis:1474967425436;

    List<Cookie> cookies;

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        this.cookies = cookies;
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        return cookies != null ? cookies : new ArrayList<Cookie>();
    }
}

