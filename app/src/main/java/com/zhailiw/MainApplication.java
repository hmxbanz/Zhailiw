package com.zhailiw;

import android.app.Application;

import com.zhailiw.common.NLog;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        NLog.setDebug(true);//开log日志
        ZXingLibrary.initDisplayOpinion(this);
    }


}

