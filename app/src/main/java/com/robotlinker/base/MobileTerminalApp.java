package com.robotlinker.base;

import android.app.Application;

import com.umeng.analytics.MobclickAgent;

/**
 * Created by gaowubin on 2017/7/3.
 */

public class MobileTerminalApp extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        MobclickAgent.setDebugMode(true);
    }
}
