package org.birdback.histudents.net;

/**
 * Created by meixin.song on 2018/4/10.
 */

public class RequestUrl {


    public static final String BASE_URL = "http://store.birdback.org";
    public static final String TEST = "/";

    /**
     * 登录
     */
    public static final String LOGIN = "/account/login";


    /**
     * 订单列表
     */
    public static final String ORDER_LIST = "/order/grab_list";
    /**
     * 接单
     */
    public static final String ORDER_SUBMIT = "/order/grab_order";
    /**
     * 拒单
     */
    public static final String REFUND_ORDER = "/order/refund_order";
    public static final String HOME_INDEX = "/home/index";

    /**
     * 退出登录
     */
    public static final String LOGOUT = "/account/logout";



}
