package com.robotlinker.base;

import android.app.Application;

/**
 * Created by gaowubin on 2017/7/3.
 */

public class MobileTerminalApp extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(getApplicationContext());
    }
}
