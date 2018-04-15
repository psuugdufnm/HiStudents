package org.birdback.histudents.net.helper;

import org.birdback.histudents.utils.LogUtil;
import org.birdback.histudents.utils.Session;
import org.birdback.histudents.utils.TextUtils;
import org.birdback.histudents.utils.VerifyUtil;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

/**
 * 请求日志
 */
public class HttpInterceptor implements Interceptor {


    @Override
    public Response intercept(Chain chain) throws IOException {
        long t1 = System.currentTimeMillis();

        Request original = chain.request();

        String body = bodyToString(original.body());

        Request request = original.newBuilder()
                .header("Accept", "application/json")
                .method(original.method(), original.body())
                .build();

        LogUtil.i(String.format("╔══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════\n" +
                        "║ " + getClass().getName() + ".intercept(Chain chain) \n" +
                        "║ 请求发起 \n" +
                        "╟──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────\n" +
                        "║ request method  : %s\n" +
                        "║ request url     : %s\n" +
                        "║ request body    : %s\n" +
                        "║ request headers : %s\n" +
                        "╚══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════"
                , request.method(), request.url().toString(),
                body, request.headers().toString().replaceAll("\\n", "\\\n║                   ")));

        Response response = chain.proceed(request);


        long t2 = System.currentTimeMillis();
        ResponseBody responseBody = response.peekBody(1024 * 1024);
        String responseBodyStr = responseBody.string().replaceAll("\\n", "");
        LogUtil.i(String.format("╔══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════\n" +
                        "║ " + getClass().getName() + ".intercept(Chain chain) \n" +
                        "║ 请求响应 \n" +
                        "╟──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────\n" +
                        "║ 耗时 (不准)       : %s 毫秒\n" +
                        "║ response url     : %s\n" +
                        "║ response body    : %s\n" +
                        "║ response headers : %s\n" +
                        "╚══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════",
                String.valueOf(t2 - t1),
                response.request().url(),
                responseBodyStr,
                response.headers().toString().replaceAll("\\n", "\\\n║                   ")));

        return response;
    }


    public static String bodyToString(final RequestBody request) {
        try {
            final RequestBody copy = request;
            if (copy == null) {
                return "";
            }
            final Buffer buffer = new Buffer();
            copy.writeTo(buffer);
            return buffer.readUtf8();
        } catch (final IOException e) {
            e.printStackTrace();
            return "";
        }
    }


}
