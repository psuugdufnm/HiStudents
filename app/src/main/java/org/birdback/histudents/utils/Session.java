package org.birdback.histudents.utils;

/**
 *
 * Created by meixin.song on 2018/4/10.
 */

public class Session {

    private static String cookie = "";
    private static String deviceToken = ""; //友盟推送设备唯一标识符

    private static String bluetoothAddress = ""; //蓝牙打印机地址

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
        Session.setDeviceToken("");
    }


}
