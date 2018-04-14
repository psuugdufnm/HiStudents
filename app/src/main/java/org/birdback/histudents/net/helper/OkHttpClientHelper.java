package org.birdback.histudents.net.helper;

import org.birdback.histudents.base.BaseApplication;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;

import okhttp3.CookieJar;
import okhttp3.OkHttpClient;


public class OkHttpClientHelper {

    private final static long TIMEOUT = 30;  //超时时间
    private static HttpInterceptor mInterceptor;
    private static CookieJar mCookie;
    private static OkHttpClient.Builder mOkHttpClient;
    private static OkHttpClient.Builder mOkHttpClientHttps;

    public static synchronized OkHttpClient getInstance(boolean isHttps) {
        if (isHttps) {
            if (mOkHttpClientHttps == null)
                try {
                    mOkHttpClientHttps = HttpTLS.addTLS(BaseApplication.getApplication(), getOkHttpClient());
                } catch (CertificateException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (KeyStoreException e) {
                    e.printStackTrace();
                } catch (KeyManagementException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            return mOkHttpClientHttps.build();
        } else {
            if (mOkHttpClient == null)
                mOkHttpClient = getOkHttpClient();
            return mOkHttpClient.build();
        }
    }

    private static OkHttpClient.Builder getOkHttpClient() {
        if (mOkHttpClient == null) {
            mOkHttpClient = createOkHttpClient();
        }
        return mOkHttpClient;
    }

    private static OkHttpClient.Builder createOkHttpClient() {
//        cache = CacheHelper.getInstance().getCache();
        OkHttpClient.Builder client = new OkHttpClient.Builder()
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT, TimeUnit.SECONDS);
        // .cache(cache)// 设置缓存
        if (mInterceptor == null)
            mInterceptor = new HttpInterceptor();
        client.addInterceptor(mInterceptor);
        if (mCookie == null) {
            mCookie = new HttpCookie();
        }
        client.cookieJar(mCookie);
        return client;
    }

}
