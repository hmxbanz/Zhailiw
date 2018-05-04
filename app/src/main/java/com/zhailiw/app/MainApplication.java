package com.zhailiw.app;

import android.app.Application;

import com.mob.MobSDK;
import com.zhailiw.app.common.NLog;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        NLog.setDebug(true);//开log日志
        MobSDK.init(this);
        ZXingLibrary.initDisplayOpinion(this);
    }


}

