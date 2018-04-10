package org.birdback.histudents.utils;

/**
 *
 * Created by meixin.song on 2018/4/10.
 */

public class Session {

    private static String bankNumber;


    public static String getBankNumber() {
        if (VerifyUtil.isEmpty(bankNumber)) {
            bankNumber = SharedPreUtil.getValue("bankNumber", "");
        }
        return bankNumber;
    }

    public static void setBankNumber(String bankNumber) {
        Session.bankNumber = bankNumber;
        SharedPreUtil.putValue("bankNumber", VerifyUtil.isEmpty(bankNumber) ? "" : bankNumber);
    }
}
