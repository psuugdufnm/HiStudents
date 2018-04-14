package org.birdback.histudents.net.cookie;

import android.support.annotation.NonNull;

import org.birdback.histudents.utils.Session;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.Response;


/**
 * 保存返回的cookie ，如果没有返回则不变
 * Created by meixin.song on 2018/4/14.
 */

public class ReceivedCookiesInterceptor implements Interceptor {


    @Override
    public okhttp3.Response intercept(@NonNull Chain chain) throws IOException {

        Response originalResponse = chain.proceed(chain.request());
        List<String> setCookies = originalResponse.headers("Set-Cookie");

        if (setCookies != null && setCookies.size() > 0) {
            for (int i = 0; i < setCookies.size(); i++) {
                String setCookie = setCookies.get(i);  //PHPSESSID=vib6ld0j81cfdapmmu5gfh2asg; path=/
                if (setCookie.contains(";")){

                    /**
                     * split[0]:PHPSESSID=vib6ld0j81cfdapmmu5gfh2asg
                     * split[1]:path=/
                     */
                    String[] split = setCookie.split(";");
                    for (int j = 0; j < split.length; j++) {
                        if (split[i].contains("PHPSESSID=")) {
                            Session.setCookie(split[i]);//PHPSESSID=vib6ld0j81cfdapmmu5gfh2asg
                        }
                    }
                }else if (setCookie.contains("PHPSESSID=")){
                    Session.setCookie(setCookie);//PHPSESSID=vib6ld0j81cfdapmmu5gfh2asg
                }
            }
        }


        return originalResponse;
    }
}
