package org.birdback.histudents.utils;

import android.content.Context;
import android.util.Log;


/**
 * 日志工具 - 二次封装
 */
public class LogUtil {
    /**
     * 调试开关，无论是自动还是手动都能很好的控制调试状态 true 为显示log日志，false为不显示（项目上线前必须改成false）
     */
    private static boolean mDebug = false;
    private static boolean mWriteToFile = false;
    public static String TAG = "STUDENT-HI";
    private Context context = null;

    public static void v(String msg) {
        Log.v(TAG, msg);
    }

    public static void v(String tag, String msg) {
        Log.v(TAG + " -> " + tag, msg);
    }


    public static void d(String msg) {
        Log.d(TAG, msg);
    }

    public static void d(String tag, String msg) {
        Log.d(TAG + " -> " + tag, msg);
    }


    public static void i(String msg) {
        Log.i(TAG, msg);
    }

    public static void i(String tag, String msg) {
        Log.i(TAG + " -> " + tag, msg);
    }


    public static void w(String msg) {
        Log.w(TAG, msg);
    }

    public static void w(String tag, String msg) {
        Log.w(TAG + " -> " + tag, msg);
    }

    public static void e(String msg) {
        Log.e(TAG, msg);
    }

    public static void e(String tag, String msg) {
        Log.e(TAG + " -> " + tag, msg);
    }

    public static void e(String tag, String msg, Throwable tr) {
        Log.e(TAG + " -> " + tag, msg, tr);
    }

    public static void e(Throwable tr, String msg) {
        Log.e(TAG, msg, tr);
    }

    public static void e(Throwable tr) {
        Log.e(TAG, tr.getMessage());
    }

    public static void e(String tag, Throwable tr) {
        Log.e(TAG + " -> " + tag, tr.getMessage());
    }

    public static void wtf(String msg) {
        Log.wtf(TAG, msg);
    }

    public static void wtf(String tag, String msg) {
        Log.wtf(TAG + " -> " + tag, msg);
    }

    public static void wtf(String tag, String msg, Throwable tr) {
        Log.wtf(TAG + " -> " + tag, msg, tr);
    }


    public static void wtf(Throwable tr) {
        Log.wtf(TAG, tr);
    }

    public static void wtf(String tag, Throwable tr) {
        Log.wtf(TAG + " -> " + tag, tr);
    }



    public static boolean isDebug() {
        return mDebug;
    }
}
