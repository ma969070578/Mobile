package com.emotte.mobile.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.emotte.mobile.R;
import com.emotte.mobile.bean.DeviceInfo;
import com.orhanobut.logger.Logger;

import java.io.UnsupportedEncodingException;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Date;
import java.util.Enumeration;
import java.util.UUID;


public class DeviceCommon {

    public static final String TAG = "Shake";

    // 获得guid
    public static String genGUID() {
        UUID uuid = UUID.randomUUID();
        String guid = uuid.toString();
        return guid;
    }

    public static UUID uuid(Context ctx) {
        String androidId = getAndroidId(ctx);
        UUID uuid = null;
        if (!TextUtils.isEmpty(androidId)
                && !TextUtils.equals(androidId, "9774d56d682e549c")) {
            uuid = uuid(androidId);
        }

        if (uuid == null) {
            String deviceId = getDeviceId(ctx);
            if (!TextUtils.isEmpty(deviceId)) {
                uuid = uuid(deviceId);
            }
        }
        if (uuid == null)
            uuid = UUID.randomUUID();
        return uuid;
    }

    public static UUID uuid(String str) {
        if (TextUtils.isEmpty(str))
            return null;
        try {
            return UUID.nameUUIDFromBytes(str.getBytes("utf8"));
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    public static float shakeStrength(float weight, float speed) {
        return (float) (weight * speed * 102.04 / 1000);
    }

    public static String getAndroidId(Context ctx) {
        return Secure.getString(ctx.getContentResolver(), Secure.ANDROID_ID);
    }

    public static TelephonyManager getTelephonyManager(Context ctx) {
        return (TelephonyManager) ctx
                .getSystemService(Context.TELEPHONY_SERVICE);
    }

    public static String getDeviceId(Context ctx) {
        TelephonyManager manager = getTelephonyManager(ctx);
        return manager.getDeviceId();
    }

    public static String brand() {
        return android.os.Build.BRAND;
    }

    public static String model() {
        return android.os.Build.MODEL;
    }

    public static String sysCodeName() {
        return android.os.Build.VERSION.CODENAME;
    }

    public static String version() {
        return android.os.Build.VERSION.RELEASE;
    }

    public static PackageManager getPackageManager(Context ctx) {
        return ctx.getApplicationContext().getPackageManager();
    }

    public static PackageInfo getPackageInfo(Context ctx) {
        try {
            PackageManager manager = getPackageManager(ctx);
            return manager.getPackageInfo(ctx.getPackageName(), 0);
        } catch (NameNotFoundException e) {
            return null;
        }
    }

    /**
     * 获取应用程序名称信息
     */
    public static String getAppName(Context ctx) {
        PackageInfo info = getPackageInfo(ctx);
        if (info == null || TextUtils.isEmpty(info.applicationInfo.name))
            return ctx.getString(R.string.app_name);
        return info.applicationInfo.name;
    }

    /**
     * 获取app版本名
     */
    public static String getAppVersion(Context ctx) {
        PackageInfo info = getPackageInfo(ctx);
        if (info == null)
            return "";
        return info.versionName;
    }

    // 手机型号
    public static String getMode() {
        return android.os.Build.MODEL;
    }

    // 手机版本号
    public static String getSysVersionCode() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 获取app版本号
     */
    public static int getAppVersionCode(Context context) {

        PackageInfo info = getPackageInfo(context);
        if (info == null)
            return 0;
        return info.versionCode;
    }

    public static String unicodeToUtf8(String theString) {
        char aChar;
        int len = theString.length();
        StringBuffer outBuffer = new StringBuffer(len);
        for (int x = 0; x < len; ) {
            aChar = theString.charAt(x++);
            if (aChar == '\\') {
                aChar = theString.charAt(x++);
                if (aChar == 'u') {
                    int value = 0;
                    for (int i = 0; i < 4; i++) {
                        aChar = theString.charAt(x++);
                        switch (aChar) {
                            case '0':
                            case '1':
                            case '2':
                            case '3':
                            case '4':
                            case '5':
                            case '6':
                            case '7':
                            case '8':
                            case '9':
                                value = (value << 4) + aChar - '0';
                                break;
                            case 'a':
                            case 'b':
                            case 'c':
                            case 'd':
                            case 'e':
                            case 'f':
                                value = (value << 4) + 10 + aChar - 'a';
                                break;
                            case 'A':
                            case 'B':
                            case 'C':
                            case 'D':
                            case 'E':
                            case 'F':
                                value = (value << 4) + 10 + aChar - 'A';
                                break;
                            default:
                                throw new IllegalArgumentException(
                                        "Malformed   \\uxxxx   encoding.");
                        }
                    }
                    outBuffer.append((char) value);
                } else {
                    if (aChar == 't')
                        aChar = '\t';
                    else if (aChar == 'r')
                        aChar = '\r';
                    else if (aChar == 'n')
                        aChar = '\n';
                    else if (aChar == 'f')
                        aChar = '\f';
                    outBuffer.append(aChar);
                }
            } else {
                outBuffer.append(aChar);
            }
        }
        return outBuffer.toString();
    }

    /**
     * 获取渠道信息 友盟 DeviceCommon.getChannelName(ctx,"UMENG_CHANNEL")
     */

    public static String getChannelName(Context ctx, String key) {
        if (ctx == null) {
            return null;
        }
        String channelName = null;

        try {
            PackageManager packageManager = ctx.getPackageManager();
            if (packageManager != null) {
                ApplicationInfo applicationInfo = packageManager
                        .getApplicationInfo(ctx.getPackageName(),
                                PackageManager.GET_META_DATA);

                if (applicationInfo != null) {
                    channelName = applicationInfo.metaData.getString(key);
                }

            }
        } catch (NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return channelName;
    }


    //获取wifi服务
//    public static String getLocalIp(Context ctx) {
//
//        WifiManager wifiManager = (WifiManager) ctx.getSystemService(Context.WIFI_SERVICE);
//        //判断wifi是否开启
//        if (!wifiManager.isWifiEnabled()) {
//            //wifiManager.setWifiEnabled(true);
//        }
//        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
//        int ipAddress = wifiInfo.getIpAddress();
//        String ip = intToIp(ipAddress);
//        return ip;
//    }

    public static String getLocalIp(Context context) {
        String hostIp = null;
        try {
            Enumeration nis = NetworkInterface.getNetworkInterfaces();
            InetAddress ia = null;
            while (nis.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface) nis.nextElement();
                Enumeration<InetAddress> ias = ni.getInetAddresses();
                while (ias.hasMoreElements()) {
                    ia = ias.nextElement();
                    if (ia instanceof Inet6Address) {
                        continue;// skip ipv6
                    }
                    String ip = ia.getHostAddress();
                    if (!"127.0.0.1".equals(ip)) {
                        hostIp = ia.getHostAddress();
                        break;
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return hostIp;
    }


    /**
     * 将ip的整数形式转换成ip形式
     *
     * @param i
     * @return
     */
    public static String intToIp(int i) {

        return (i & 0xFF) + "." +
                ((i >> 8) & 0xFF) + "." +
                ((i >> 16) & 0xFF) + "." +
                (i >> 24 & 0xFF);
    }


    /**
     * 获取当前ip
     *
     * @return
     */
    public static String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf
                        .getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {
            Logger.e("WifiPreference IpAddress" + ex.toString());
        }
        return null;
    }

    /**
     * 获取imsi
     *
     * @param context
     * @return
     */
    public static String getIMSI(Context context) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);

        String subscriberId = tm.getSubscriberId();
        if (subscriberId != null && !"".equals(subscriberId)) {
            return subscriberId;
        }
        return null;

    }

    /**
     * 初始化保存设备信息
     *
     * @param ctx
     */
    public static void initDevice(Context ctx) {
        DeviceInfo deviceInfo = new DeviceInfo();
        deviceInfo.setDevicecode(DeviceCommon.getDeviceId(ctx));
        deviceInfo.setAppname(DeviceCommon.getAppName(ctx));
        deviceInfo.setAppversion(DeviceCommon.getAppVersion(ctx));
        deviceInfo.setMobilebrand(android.os.Build.BRAND);
        deviceInfo.setMobilemodel(android.os.Build.MODEL);
        deviceInfo.setSystemname(DeviceCommon.getAppName(ctx));
        deviceInfo.setSystemversion(android.os.Build.VERSION.CODENAME);
        deviceInfo.setSystemtype(1);
        deviceInfo
                .setChannel(DeviceCommon.getChannelName(ctx, "UMENG_CHANNEL"));
        deviceInfo.setCreatetime(new Date());
        // DeviceInfoDBHelper.saveDeviceInfo(ctx, deviceInfo);

        // deviceInfo.setSystemtype(AppType.ANDROID_PHONE.id);

        //initRequest(ctx);
    }

}
