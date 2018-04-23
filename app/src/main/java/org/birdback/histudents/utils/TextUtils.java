package org.birdback.histudents.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

import org.birdback.histudents.base.BaseApplication;

/**
 * Created by Administrator on 2018/4/8.
 */

public class TextUtils {

    private static final Context mContext;
    private static Toast toast;
    static {
        mContext = BaseApplication.getApplication();
    }

    public static void makeText(String text){
        showToast(text);
    }

    public static void makeText(int text){
        showToast(String.valueOf(text));
    }

    public static void showToast(String text){
        if (toast == null) {
            toast= Toast.makeText(mContext,text, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,0);
        }else{
            toast.setText(text);
        }
        toast.show();
    }
}
