package com.emotte.mobile.app;

import android.content.Context;
import android.content.pm.ApplicationInfo;

import com.emotte.library.base.LibApp;
import com.emotte.mobile.utils.ToastUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

import org.acra.ACRA;

import java.util.logging.Level;

import demo.temperature.com.locationlib.LocationUtil;


/**
 * Created by Administrator on 2016/11/23.
 */

public class App extends LibApp {

    private static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        init();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        // The following line triggers the initialization of ACRA
        // https://github.com/ACRA/acra
        ACRA.init(this);
    }

    public static App getInstance() {
        return instance;
    }


    /**
     * 初始化
     */
    private void init() {
        LocationUtil.init(this);
        ToastUtil.init(this);
        if ((0 != (getApplicationInfo().flags &= ApplicationInfo.FLAG_DEBUGGABLE)))
            Logger.init(); // for debug, print all log
        else
            Logger.init().logLevel(LogLevel.NONE);

        //必须调用初始化
        OkGo.init(this);
        OkGo.getInstance()

                // 打开该调试开关,打印级别INFO,并不是异常,是为了显眼,不需要就不要加入该行
                // 最后的true表示是否打印okgo的内部异常，一般打开方便调试错误
                .debug("OkGo", Level.INFO, true)

                //如果使用默认的 60秒,以下三行也不需要传
                .setConnectTimeout(OkGo.DEFAULT_MILLISECONDS)  //全局的连接超时时间
                .setReadTimeOut(OkGo.DEFAULT_MILLISECONDS)     //全局的读取超时时间
                .setWriteTimeOut(OkGo.DEFAULT_MILLISECONDS)    //全局的写入超时时间

                //可以全局统一设置缓存模式,默认是不使用缓存,可以不传,具体其他模式看 github 介绍 https://github.com/jeasonlzy/
                .setCacheMode(CacheMode.NO_CACHE)
                //可以全局统一设置超时重连次数,默认为三次,那么最差的情况会请求4次(一次原始请求,三次重连请求),不需要可以设置为0
                .setRetryCount(3);
    }
}
