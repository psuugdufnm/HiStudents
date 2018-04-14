package org.birdback.histudents.net;


public class FrameWorkConfig {

    public static int CODE_SUCCESS = 200000;
    public static int CODE_TOKEN_EXPIRE = 200301;//token 已过期
    public static int CODE_TOKEN_INVALID = 200304;//token 自动失效
    public static int CODE_TOKEN_INCONSISTENT = 200305;//token 不一致(重复登录)
    public static boolean isTokenInvalid =false;//是否token过期
    public static boolean isShowingNetworkErrorDialog =false;//网络异常弹出是否显示

    public static FrameworkSupport frameworkSupport;
}
