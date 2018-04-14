package org.birdback.histudents.service;


import org.birdback.histudents.entity.ResponseEntity;
import org.birdback.histudents.net.RequestUrl;


import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * 订单管理
 * data无数据返回泛型就传OBJECT
 */

public interface OrderService {


    @GET(RequestUrl.BASE_URL + RequestUrl.TEST)
    Observable<ResponseEntity<Object>> requestTest();

}
