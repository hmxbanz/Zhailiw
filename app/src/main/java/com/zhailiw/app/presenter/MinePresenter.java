package com.zhailiw.app.presenter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.zhailiw.app.Const;
import com.zhailiw.app.common.NToast;
import com.zhailiw.app.loader.GlideImageLoader;
import com.zhailiw.app.server.HttpException;
import com.zhailiw.app.server.async.OnDataListener;
import com.zhailiw.app.server.broadcast.BroadcastManager;
import com.zhailiw.app.server.response.LoginResponse;
import com.zhailiw.app.view.activity.MainActivity;
import com.zhailiw.app.widget.LoadDialog;
import com.zhailiw.app.widget.SelectableRoundedImageView;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by hmxbanz on 2017/4/5.
 */

public class MinePresenter extends BasePresenter implements OnDataListener {
    private static final int GETINFO = 2;
    private static final int GETMSGCOUNT = 3;
    public static final String UPDATEUNREAD = "updateUnread";
    private final MainActivity activity;
    private GlideImageLoader glideImageLoader;
    private final BasePresenter basePresenter;
    private SelectableRoundedImageView avator;
    private TextView nickName;

    public MinePresenter(Context context){
        super(context);
        basePresenter = BasePresenter.getInstance(context);
        glideImageLoader = new GlideImageLoader();
        activity=(MainActivity)context;
    }

    public void init(SelectableRoundedImageView selectableRoundedImageView, TextView nickName) {
        this.avator = selectableRoundedImageView;
        this.nickName = nickName;
        getInfo();
        BroadcastManager.getInstance(context).addAction(MinePresenter.UPDATEUNREAD, new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String command = intent.getAction();
                String s=intent.getStringExtra("String");
                if (!TextUtils.isEmpty(command)) {
                    switch (s){
                        case "updateUnread":
                            break;
                        case "loadAvator":
                            getInfo();
                            break;
                        default:
                    }
                }
            }
        });

    }
    public void getInfo(){
        basePresenter.initData();
        if(basePresenter.isLogin){
        LoadDialog.show(context);
        atm.request(GETINFO,this);
        }
    }

    @Override
    public Object doInBackground(int requestCode, String parameter) throws HttpException {
        switch (requestCode) {
            case GETINFO:
                return userAction.getCaptcha("");
            case GETMSGCOUNT:
                return userAction.getCaptcha("");
        }
        return null;
    }

    @Override
    public void onSuccess(int requestCode, Object result) {
        LoadDialog.dismiss(context);
        if (result==null)return;
        switch (requestCode) {
            case GETINFO:
                break;
        }
    }

}