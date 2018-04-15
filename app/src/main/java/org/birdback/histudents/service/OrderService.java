package org.birdback.histudents.service;


import org.birdback.histudents.entity.ResponseEntity;
import org.birdback.histudents.net.RequestUrl;


import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.QueryName;
import retrofit2.http.Url;

/**
 * 订单管理
 * data无数据返回泛型就传OBJECT
 */

public interface OrderService {


    @GET/*(RequestUrl.BASE_URL + RequestUrl.TEST)*/
    Observable<ResponseEntity<Object>> getTest(@Url String url);


    @Multipart
    @POST
    Observable<ResponseEntity<Object>> requestTest(@Url String url, @PartMap Map<String, String> map);

    @POST
    Observable<ResponseEntity<Object>> requestTest(@Url String url);

}
