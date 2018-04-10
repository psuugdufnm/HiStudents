package org.birdback.histudents.utils;

import android.content.Context;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 文本验证
 */
public class VerifyUtil {
    /**
     * @param context     当前activity上下文
     * @param content     判断的内容
     * @param strRegExp   正则表达式(如为null则不判断正则)
     * @param tipIdNull   为null提示文字
     * @param tipIdRegExp 正则不匹配提示文字
     * @return Returns : true =>>通过验证;
     * @Title isVerify
     * @Description 判断是否为空
     */
    public static boolean isVerify(Context context, String content, String strRegExp, int tipIdNull, int tipIdRegExp) {
        if (isEmptyTip(context, content, tipIdNull)) {
            return false;
        }

        if (strRegExp != null && !content.matches(strRegExp)) {
            TextUtils.makeText(tipIdRegExp);
            return false;
        }
        return true;
    }

    /**
     * @param context     当前activity上下文
     * @param content     判断的内容
     * @param strRegExp   正则表达式(如为null则不判断正则)
     * @param tipIdNull   为null提示文字
     * @param tipIdRegExp 正则不匹配提示文字
     * @return Returns : true =>>通过验证;
     * @Title isVerify
     * @Description 判断是否为空
     */
    public static boolean isVerify(Context context, String content, String strRegExp, String tipIdNull,
                                   String tipIdRegExp) {
        if (isEmptyTip(context, content, tipIdNull)) {
            return false;
        }

        if (strRegExp != null && !content.matches(strRegExp)) {
            TextUtils.makeText(tipIdRegExp);
            return false;
        }
        return true;
    }

    /**
     * @param context   当前activity上下文
     * @param content   判断的内容
     * @param tipIdNull 为null提示文字
     * @return Returns : true =>>"null" or "length is zero";
     * @Title: isEmptyStr
     * @Description: 判断是否为空
     */
    public static boolean isEmptyTip(Context context, String content, int tipIdNull) {
        if (content == null || content.isEmpty()) {
            TextUtils.makeText(tipIdNull);
            return true;
        }
        return false;
    }

    public static boolean isEmptyTip(Context context, String content, String tipIdNull) {
        if (content == null || content.isEmpty()) {
            TextUtils.makeText(tipIdNull);
            return true;
        }
        return false;
    }

    public static boolean isEmpty(String str) {
        if (str == null) {
            return true;
        }
        if (str.equals("null")) {
            return true;
        }
        if (str.trim().length() == 0) {
            return true;
        }

        return false;
    }

    /**判断字符串是个否为金额*/
    public static boolean isEmptyMoney(String money) {
        if (money == null) {
            return true;
        }
        if (money.equals("null")) {
            return true;
        }
        if (money.trim().length() == 0) {
            return true;
        }
        if(Double.parseDouble(money) <= 0){
            return true;
        }

        return false;
    }

    public static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        }
        return false;
    }

    public static boolean isEmpty(Map map) {
        if (map == null || map.isEmpty()) {
            return true;
        }
        return false;
    }

    @SuppressWarnings("rawtypes")
    public static boolean isEmpty(List list) {
        if (list == null || list.size() == 0) {
            return true;
        }
        return false;
    }
    //判断是否为数字
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }
}
