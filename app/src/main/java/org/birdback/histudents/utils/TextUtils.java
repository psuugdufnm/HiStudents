package org.birdback.histudents.utils;

import android.content.Context;
import android.widget.Toast;

import org.birdback.histudents.base.BaseApplication;

/**
 * Created by Administrator on 2018/4/8.
 */

public class TextUtils {

    private static final Context mContext;

    static {
        mContext = BaseApplication.getApplication();
    }

    public static void makeText(String text){
        Toast.makeText(mContext,text,Toast.LENGTH_SHORT).show();
    }

    public static void makeText(int text){
        Toast.makeText(mContext,String.valueOf(text),Toast.LENGTH_SHORT).show();
    }
}
