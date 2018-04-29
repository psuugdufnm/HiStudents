package org.birdback.histudents.service;


import org.birdback.histudents.entity.MyMenuEntity;
import org.birdback.histudents.entity.OrderListEntity;
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


    @Multipart
    @POST
    Observable<ResponseEntity<Object>> requestTest(@Url String url, @Part List<MultipartBody.Part> list);

    @POST
    Observable<ResponseEntity<Object>> requestTest(@Url String url);

    @FormUrlEncoded
    @POST(RequestUrl.BASE_URL + RequestUrl.LOGIN)
    Observable<ResponseEntity<Object>> login(@Field("mobile") String mobile,
                                             @Field("pwd") String pwd,
                                                @Field("deviceToken") String deviceToken);

    @GET(RequestUrl.BASE_URL + RequestUrl.ORDER_LIST)
    Observable<ResponseEntity<OrderListEntity>> requestOrderList();

    @POST(RequestUrl.BASE_URL + RequestUrl.HOME_INDEX)
    Observable<ResponseEntity<MyMenuEntity>> getList();

    @FormUrlEncoded
    @POST(RequestUrl.BASE_URL + RequestUrl.ORDER_SUBMIT)
    Observable<ResponseEntity<Object>> requestSubmit(@Field("order_no") String orderNo);

}
