package org.birdback.histudents.utils;

/**
 *
 * Created by meixin.song on 2018/4/10.
 */

public class Session {

    private static String cookie = "";
    private static String deviceToken = ""; //友盟推送设备唯一标识符

    private static String bluetoothAddress = ""; //蓝牙打印机地址

    private static String uid = ""; //uid

    private static boolean isFirstOpen;//是否第一次打开应用
    private static boolean autoGet;//自动接单


    public static String getUid() {

        if (VerifyUtil.isEmpty(uid)) {
            uid = SharedPreUtil.getValue("uid", "");
        }
        return uid;
    }

    public static void setUid(String uid) {
        Session.uid = uid;
        SharedPreUtil.putValue("uid", VerifyUtil.isEmpty(uid) ? "" : uid);
    }


    public static boolean getAutoGet() {
        autoGet = SharedPreUtil.getValue("autoGet", false);
        return autoGet;
    }

    public static void setAutoGet(boolean autoGet) {
        Session.autoGet = autoGet;
        SharedPreUtil.putValue("autoGet", autoGet);
    }


    public static boolean isFirstOpen() {
        isFirstOpen = SharedPreUtil.getValue("isFirstOpen", false);
        return isFirstOpen;
    }

    public static void setIsFirstOpen(boolean isFirstOpen) {
        Session.isFirstOpen = isFirstOpen;
        SharedPreUtil.putValue("isFirstOpen", isFirstOpen);
    }



    public static String getBluetoothAddress() {
        if (VerifyUtil.isEmpty(bluetoothAddress)) {
            bluetoothAddress = SharedPreUtil.getValue("bluetoothAddress", "");
        }
        return bluetoothAddress;
    }

    public static void setBluetoothAddress(String bluetoothAddress) {
        Session.bluetoothAddress = bluetoothAddress;
        SharedPreUtil.putValue("bluetoothAddress", VerifyUtil.isEmpty(bluetoothAddress) ? "" : bluetoothAddress);
    }


    public static String getCookie() {
        if (VerifyUtil.isEmpty(cookie)) {
            cookie = SharedPreUtil.getValue("cookie", "");
        }
        return cookie;
    }

    public static void setCookie(String cookie) {
        Session.cookie = cookie;
        SharedPreUtil.putValue("cookie", VerifyUtil.isEmpty(cookie) ? "" : cookie);
    }

    public static String getDeviceToken() {
        if (VerifyUtil.isEmpty(deviceToken)) {
            deviceToken = SharedPreUtil.getValue("deviceToken", "");
        }
        return deviceToken;
    }

    public static void setDeviceToken(String deviceToken) {
        Session.deviceToken = deviceToken;
        SharedPreUtil.putValue("deviceToken", VerifyUtil.isEmpty(deviceToken) ? "" : deviceToken);
    }

    public static void logout(){
        Session.setCookie("");
        Session.setUid("");
    }


}
