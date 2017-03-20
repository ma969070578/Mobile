package com.emotte.mobile.http;

import android.content.Context;

import com.emotte.mobile.bean.Return;
import com.emotte.mobile.utils.DeviceCommon;
import com.emotte.mobile.utils.MD5;
import com.emotte.mobile.utils.PreferencesHelper;
import com.emotte.mobile.utils.ScreenUtils;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okgo.request.PostRequest;
import com.lzy.okgo.request.PutRequest;
import com.orhanobut.logger.Logger;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by maq on 2016/11/26.
 */

public class HttpUtils {


    public static final String SERVER = "http://server.jeasonlzy.com/OkHttpUtils/";
    //    public static final String SERVER = "http://192.168.1.121:8080/OkHttpUtils/";
    public static final String URL_METHOD = SERVER + "method";
    public static final String URL_CACHE = SERVER + "cache";
    public static final String URL_IMAGE = SERVER + "image";
    public static final String URL_JSONOBJECT = SERVER + "jsonObject";
    public static final String URL_JSONARRAY = SERVER + "jsonArray";
    public static final String URL_FORM_UPLOAD = SERVER + "upload";
    public static final String URL_TEXT_UPLOAD = SERVER + "uploadString";
    public static final String URL_DOWNLOAD = SERVER + "download";
    public static final String URL_REDIRECT = SERVER + "redirect";

    //接口版本
    public static final String PARAMS_KEY_INFVERSION = "infversion";
    private static final String PARAMS_VALUE_INFVERSION = "1.0";


    //    uuid	用户城市
//    version_os	设备版本号
//    createTime_	请求时间
//    clientVersion	App 版本号
//    screen	屏幕分辨率
//    client	设备类型（android;iphone）
//    sign	校验码
//    terminal	20020003   微信终端
//    20020004   ios 终端
//    20020001   android终端
    public static void initRequest(Context ctx) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        PreferencesHelper.putString("uuid", DeviceCommon.getIMSI(ctx));
        PreferencesHelper.putString("version_os", DeviceCommon.getSysVersionCode());
        PreferencesHelper.putString("createTime", df.format(new Date()));
        PreferencesHelper.putString("clientVersion", DeviceCommon.getAppVersion(ctx));
        PreferencesHelper.putString("deviceName", DeviceCommon.getMode());
        PreferencesHelper.putString("screen", ScreenUtils.getScreen(ctx));
        PreferencesHelper.putString("client", "android");
        PreferencesHelper.putString("ip", DeviceCommon.getLocalIp(ctx));
        PreferencesHelper.putString("terminal", "20020001");
        PreferencesHelper.putString("clientType", android.os.Build.MODEL);
    }


    /**
     * 方法必须在initRequest后调用
     */
    public static void initHttpHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.put("commonHeaderKey1", "commonHeaderValue1");
        headers.put("uuid", PreferencesHelper.getString("uuid", ""));
        headers.put("version_os", PreferencesHelper.getString("version_os", ""));
        headers.put("createTime", PreferencesHelper.getString("createTime", ""));
        headers.put("clientVersion", PreferencesHelper.getString("clientVersion", ""));
        headers.put("deviceName", PreferencesHelper.getString("deviceName", ""));
        headers.put("screen", PreferencesHelper.getString("screen", ""));
        headers.put("client", PreferencesHelper.getString("client", ""));
        headers.put("terminal", PreferencesHelper.getString("terminal", ""));
        headers.put("ip", PreferencesHelper.getString("ip", ""));
        headers.put(PARAMS_KEY_INFVERSION, PARAMS_VALUE_INFVERSION);
        OkGo.getInstance().addCommonHeaders(headers);
    }

    public static void get(String url, TreeMap<String, String> map, StringCallback mcallback) {
        GetRequest request = OkGo.get(url);
//        .tag(map);

        for (Map.Entry<String, String> entry : map.entrySet()) {
            String name = entry.getKey();
            String value = entry.getValue();

            request.params(name, value);
        }
        request.execute(mcallback);
    }


    public static void post(String url, TreeMap<String, String> map, StringCallback mcallback) {
        PostRequest request = OkGo.post(url);
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String name = entry.getKey();
            String value = entry.getValue();

            request.params(name, value);
        }
        request.params("sign", sign(map));
        request.execute(mcallback);
    }


    public static void put(String url, TreeMap<String, String> map, StringCallback mcallback) {
        PutRequest request = OkGo.put(url).tag(map);
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String name = entry.getKey();
            String value = entry.getValue();

            request.params(name, value);
        }

        request.execute(mcallback);
    }


    public static <T> void testget(TreeMap<String, String> map, StringCallback mcallback) {
        post(URL_METHOD, map, mcallback);
    }

    public static <T> void testpost(TreeMap<String, String> map, StringCallback mcallback) {
        post(URL_METHOD, map, mcallback);
    }


    /**
     * 参数签名
     *
     * @param map
     * @return
     */
    private static String sign(TreeMap<String, String> map) {
        List<String> keyList = new ArrayList<String>();
        for (String key : map.keySet()) {
            if (key.subSequence(0, 1).equals("_") || key.contains("sign")
                    || key.contains("file") || key.equals("headPhoto")) {
                continue;
            }
            keyList.add(key);
        }
        // 将所有参数按key的名称排序然后将对应value组成sign
        String[] keys = (String[]) keyList.toArray(new String[keyList.size()]);
        Arrays.sort(keys);
        String sign = "mykk#@!321";
        for (String key : keys) {
            Object value = map.get(key);
            if (value != null) {
                sign += value.toString();
            }
        }
        // 为sign进行md5加密
        return MD5.str2MD5(sign);
    }

    public static final String HOST = "app.95081.com";
    //    public static final String APPMGR_HOST = "erp.95081.com";
    public static final String APPMGR_HOST = "http://192.168.10.12:8080";
    public static final String APP_UPDATE = "/appMgr/gjbUpdate.jsp";
    public static final int ERR_CODE_JSON = 101;

    public static <T> void checkUpdate(TreeMap<String, String> map, StringCallback
            callBack) {
        get(APPMGR_HOST + APP_UPDATE, map, callBack);

    }

    public static <T> void usernameLogin(TreeMap<String, String> map, StringCallback
            callBack) {
        post(APPMGR_HOST + "/appMgr/user/login", map, callBack);
    }

    /*public static  void usernameLogin2(TreeMap<String, String> map,  ShakeRequestCallBack<T> callBack,Class<T> clazz) {
        post2(APPMGR_HOST + "/appMgr/user/login", map, getRequestCallBack(callBack,clazz));
    }*/

    public static <T extends Return> void post2(String url, TreeMap<String, String> map, ShakeRequestCallBack<T> callBack,Class<T> clazz) {
        PostRequest request = OkGo.post(url);
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String name = entry.getKey();
            String value = entry.getValue();

            request.params(name, value);
        }
        request.params("sign", sign(map));
        request.execute(getRequestCallBack(callBack,clazz));
    }


    public static <T extends Return> StringCallback getRequestCallBack(
            final ShakeRequestCallBack<T> callBack, final Class<T> clazz) {
        return new StringCallback() {

            @Override
            public void onSuccess(String s, Call call, Response response) {
                if (s == null) return;
                if (callBack == null) return;


                Gson gson = new Gson();

                T entity = null;
                try {
                    entity = gson.fromJson(s, clazz);
                } catch (Exception e) {
                    Logger.e("parse json", e);
                    callBack.onFailed(ERR_CODE_JSON, "解析json错误");
                }

                if (entity != null&&Return.RET_SUCCESS==entity.getCode()) {
                    Logger.i("entity is null");
                    callBack.onsuccess(entity);
                }else {
                    callBack.onFailed(entity.getCode(), entity.getMsg());
                }

            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                callBack.onError(404, e.getMessage());
            }
        };

    }

    public static interface ShakeRequestCallBack<T extends Return> {
        public void onsuccess(T entity);

        public void onError(int code, String msg);

        public void onFailed(int code, String msg);
    }


}
