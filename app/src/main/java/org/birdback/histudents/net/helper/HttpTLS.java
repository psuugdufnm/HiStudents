package org.birdback.histudents.net.helper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetManager;
import android.text.TextUtils;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;

public class HttpTLS {
    private static SSLSocketFactory mSSLSocketFactory;
    private final static String assetsCertificate = "xinxindai.crt";


    public static OkHttpClient.Builder addTLS(Context context, OkHttpClient.Builder okHttpClient) throws CertificateException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException {
        return okHttpClient.sslSocketFactory(getSSLSocketFactory(context));
    }

    private static SSLSocketFactory getSSLSocketFactory(Context context) throws KeyStoreException, CertificateException, NoSuchAlgorithmException, IOException, KeyManagementException {
        if (mSSLSocketFactory == null) {
            SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
            if (TextUtils.isEmpty(assetsCertificate)) {
                sslContext.init(null, new TrustManager[]{new X509TrustManager() {
                    @SuppressLint("TrustAllX509TrustManager")
                    @Override
                    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                    }

                    @SuppressLint("TrustAllX509TrustManager")
                    @Override
                    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
                }}, null);
            } else {
                AssetManager assetManager = context.getAssets();
                InputStream inputStream = assetManager.open(assetsCertificate);
                KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
                keyStore.load(null, null);
                keyStore.setCertificateEntry("certificate", CertificateFactory.getInstance("X.509").generateCertificate(inputStream));
                inputStream.close();

                TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
                trustManagerFactory.init(keyStore);

                sslContext.init(null, trustManagerFactory.getTrustManagers(), null);
            }

            mSSLSocketFactory = sslContext.getSocketFactory();
        }


        return mSSLSocketFactory;
    }
}
