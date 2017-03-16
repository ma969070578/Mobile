package com.emotte.library.base;

import android.app.Application;

import com.orhanobut.logger.Logger;

/**
 * Created by Administrator on 2016/12/1.
 */
public class LibApp extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        initLogger();
    }

    private void initLogger() {
        Logger.init();
    }
}
