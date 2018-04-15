package org.birdback.histudents.net.helper;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import org.birdback.histudents.net.RequestUrl;
import org.birdback.histudents.utils.GsonStringConverterFactory;

import retrofit2.Retrofit;



public class RetrofitHelper {

    private static Retrofit.Builder mRetrofitHttp;
    private static Retrofit.Builder mRetrofitHttps;

    //单例 保证对象唯一
    public static synchronized Retrofit getInstance(boolean isHttps) {
        if (isHttps) {
            if (mRetrofitHttps == null)
                // 关联okHttp
                mRetrofitHttps = createRetrofit();//.client(OkHttpClientHelper.getInstance(isHttps));
            return mRetrofitHttps.build();
        } else {
            if (mRetrofitHttp == null)
                // 关联okHttp
                mRetrofitHttp = createRetrofit();//.client(OkHttpClientHelper.getInstance(isHttps));
            return mRetrofitHttp.build();
        }
    }

    private static Retrofit.Builder createRetrofit() {

        return new Retrofit.Builder()
                .baseUrl(RequestUrl.BASE_URL)
                // 添加Gson支持
                .addConverterFactory(GsonStringConverterFactory.create())
                // 添加RxJava支持
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(OkHttpClientHelper.getInstance(false));
    }

    //获取服务对象
    public static <T> T getService(boolean isHttps, Class<T> clz) {
        return RetrofitHelper.getInstance(isHttps).create(clz);
    }


}


