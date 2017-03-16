package com.emotte.mobile.http;

import com.emotte.mobile.utils.MD5;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okgo.request.PostRequest;
import com.lzy.okgo.request.PutRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Administrator on 2016/11/26.
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

    public static <T> void checkUpdate(TreeMap<String, String> map, StringCallback
            callBack) {
        get(APPMGR_HOST + APP_UPDATE, map, callBack);

    }

    public static <T> void usernameLogin(TreeMap<String, String>map,StringCallback
            callBack) {
        post(APPMGR_HOST + "/appMgr/user/login", map, callBack);
    }









}
