package org.birdback.histudents.net.cookie;


import android.support.annotation.NonNull;
import android.widget.Toast;

import org.birdback.histudents.activity.LoginActivity;
import org.birdback.histudents.base.BaseApplication;
import org.birdback.histudents.utils.Session;
import org.birdback.histudents.utils.TextUtils;
import org.birdback.histudents.utils.VerifyUtil;

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

        String cookie = Session.getCookie();

        if (!VerifyUtil.isEmpty(cookie)) {
            builder.addHeader("cookie", cookie);
        }


        return chain.proceed(builder.build());
    }
}
