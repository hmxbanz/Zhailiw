package com.zhailiw.app.presenter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import com.zhailiw.app.widget.LoadDialog;
import com.zhailiw.app.view.activity.MainActivity;
import com.zhailiw.app.common.CommonTools;
import com.zhailiw.app.view.activity.GuideActivity;
import com.zhailiw.app.view.activity.StartActivity;


public class StartPresenter extends BasePresenter {
    private static final int GETSYSTEMOBJ = 1036;
    private static final int GETCITIES = 1037;
    private StartActivity activity;
    private Handler hand = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Intent intent = new Intent(activity, MainActivity.class);
            activity.startActivity(intent);
            activity.finish();
        }
    };

    public StartPresenter(Context context) {
        super(context);
        activity = (StartActivity) context;
    }

    public void init() {
        if (CommonTools.isFristRun(activity)) {
            Intent intent = new Intent(activity, GuideActivity.class);
            activity.startActivity(intent);
            activity.finish();
            return;
        }
        new Thread() {
            public void run() {
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                Message msg = hand.obtainMessage();
                hand.sendMessage(msg);
            }
        }.start();
    }

    public void getSystemObj() {
        LoadDialog.show(context);
        atm.request(GETSYSTEMOBJ, this);
    }

    public void getCities() {
        LoadDialog.show(context);
        atm.request(GETCITIES, this);
    }

}
