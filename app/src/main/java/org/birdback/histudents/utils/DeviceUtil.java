package org.birdback.histudents.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.webkit.WebView;


import com.meituan.android.walle.WalleChannelReader;

import org.birdback.histudents.base.BaseApplication;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.UUID;


public class DeviceUtil {
    private static final String TAG = "DeviceUtils";

    /**
     * 获取手机序列号
     *
     * @param context
     * @return
     */
    @SuppressLint("MissingPermission")
    public static String getImei(Context context) {
        try {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (tm != null)
                return tm.getDeviceId();
        } catch (Exception e) {
            LogUtil.w("无法IMEI DeviceUtil.getLocation()\n" + e.getMessage() + "\n" + e.getStackTrace()[4] + "\n" + e.getStackTrace()[5]);
        }
        return "";
    }


    /**
     * 获取手机mac地址
     *
     * @param context
     * @return
     */
    public static String getMAC(Context context) {
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        if (wifi != null)
            return wifi.getConnectionInfo().getMacAddress();
        return "";
    }


    /**
     * @return 手机品牌
     */
    public static String getBrand() {
        if (Build.BRAND != null)
            return Build.BRAND;
        return "未知";
    }

    /**
     * @return 手机型号
     */
    public static String getModel() {
        if (TextUtils.isEmpty(Build.MODEL))
            return "未知";
        return Build.MODEL;

    }

    /**
     * @return 手机android系统版本号
     */

    public static String getRelease() {
        if (TextUtils.isEmpty(Build.VERSION.RELEASE)) {
            return "未知";
        }
        return Build.VERSION.RELEASE;
    }

    /**
     * @return 手机分辨率
     */
    public static String getResolution(Context context) {
        if (context == null) {
            return "0*0";
        }
        String resolution = SharedPreUtil.getValue("resolution", "");
        if (TextUtils.isEmpty(resolution)) {
            DisplayMetrics metrics = new DisplayMetrics();
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            wm.getDefaultDisplay().getMetrics(metrics);
            int widthPixels = metrics.widthPixels;
            int heightPixels = metrics.heightPixels;
            resolution = widthPixels + "*" + heightPixels;
            SharedPreUtil.putValue("resolution", resolution);
        }
        return resolution;
    }

    /**
     * @return 手机分辨率宽度
     */
    public static int getDeviceWidth(Activity context) {
        if (context == null) {
            return 0;
        }

        DisplayMetrics dm = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    /**
     * @return 手机分辨率高度
     */
    public static int getDeviceHeight(Activity context) {
        if (context == null) {
            return 0;
        }
        DisplayMetrics dm = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }

    /**
     * @return 手机APP高度
     */
    public static int getAppHeight(Activity context) {
        if (context == null) {
            return 0;
        }
        Rect rect = new Rect();
        context.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        return rect.height();
    }



    public static String getAPPVersionName(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            String version = info.versionName;
            return version;

        } catch (Exception e) {
            e.printStackTrace();
            return "null";
        }
    }

    public static int getAPPVersionCode(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            int versionCode = info.versionCode;
            return versionCode;

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static String getPhoneAllInfo(Context context) {
        String allInfo = "hi student Android AppVersionCode " + getAPPVersionCode(context)
                + " SystemVersion " + Build.VERSION.RELEASE
                + " SDK " + Build.VERSION.SDK_INT
                + " Brand " + getBrand()
                + " Model " + getModel()
                + " IMEI " + getImei(context)
                + " MAC " + getMAC(context);
        LogUtil.d(TAG, allInfo);
        return allInfo;
    }


    @SuppressLint("MissingPermission")
    public static String getUUID(Context context) {
        if (context == null) {
            return "";
        }
        String uniqueId = SharedPreUtil.getValue("uniqueId", "");
        if (TextUtils.isEmpty(uniqueId)) {
            final String tmDevice, tmSerial, androidId;
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                tmDevice = "" + tm.getDeviceId();
                tmSerial = "" + tm.getSimSerialNumber();
            } else {
                tmDevice = UUID.randomUUID().toString();
                tmSerial = "";
            }
            androidId = "" + android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
            UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
            uniqueId = deviceUuid.toString();
            SharedPreUtil.putValue("uniqueId", uniqueId);
        }
        return uniqueId;
    }

    /**
     * @return 渠道
     */
    public static String getChannel() {
        return SharedPreUtil.getValue("CHANNEL_NAME", "");
    }

    public static String getChannelUTM() {
        return SharedPreUtil.getValue("CHANNEL_UTM", "");
    }

    public static void initChannel() {
        Context context = BaseApplication.getApplication().getApplicationContext();
        int currentCode = DeviceUtil.getAPPVersionCode(context);
        int lastCode = SharedPreUtil.getValue("CHANNEL_VERSION_CODE", 0);
        if (currentCode == lastCode && !VerifyUtil.isEmpty(getChannel()) && !VerifyUtil.isEmpty(getChannelUTM())) {
            return;
        }
        String channel = WalleChannelReader.getChannel(context);
        Map<String, String> channelInfoMap = WalleChannelReader.getChannelInfoMap(context);
        if (!VerifyUtil.isEmpty(channelInfoMap)) {
            String utm = channelInfoMap.get("utmSource");
            SharedPreUtil.putValue("CHANNEL_NAME", channel);
            SharedPreUtil.putValue("CHANNEL_UTM", utm);
        }

    }

    /**
     * 隐藏键盘
     *
     * @param context
     */

    public static void hideInputPan(Activity context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(context.getWindow().getDecorView().getWindowToken(), 0);
    }

    public static void hideStatuBar(Activity context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = context.getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }


    /**
     * 根据wifi 获取ip
     *
     * @param context
     * @return
     */
    public static String getLocalIpForWifi(Context context) {
        //获取wifi服务
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        //判断wifi是否开启
        if (!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
        }
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipAddress = wifiInfo.getIpAddress();
        return intToIp(ipAddress);
    }

    private static String intToIp(int i) {
        return (i & 0xFF) + "." +
                ((i >> 8) & 0xFF) + "." +
                ((i >> 16) & 0xFF) + "." +
                (i >> 24 & 0xFF);
    }


    /**
     * 根据 GPRS获取ip
     *
     * @return
     */
    public static String getLocalIpForGPRS() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {
            Log.e("IpAddress", ex.toString());
        }
        return null;
    }

    /**
     * 获取app最新更新时间
     *
     * @return
     */
    public static long getLastInstallApp(Context context, String packageName) {
        PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        for (PackageInfo info : packageInfos) {
            if (info.packageName.equals(packageName)) {
                return info.lastUpdateTime;
            }
        }
        return -1;
    }

    public static String getWebViewUserAgent(Context context) {
        WebView webView = new WebView(context);
        WebSettings settings = webView.getSettings();
        return settings.getUserAgentString();
    }
}
