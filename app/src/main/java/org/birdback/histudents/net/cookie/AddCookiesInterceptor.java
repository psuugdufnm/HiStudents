package org.birdback.histudents.net.cookie;


import android.support.annotation.NonNull;

import org.birdback.histudents.utils.Session;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 添加请求头 cookie
 * Created by meixin.song on 2018/4/14.
 */

public class AddCookiesInterceptor implements Interceptor {

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {

        final Request.Builder builder = chain.request().newBuilder();

        builder.addHeader("cookie", Session.getCookie());
        return chain.proceed(builder.build());
    }
}
