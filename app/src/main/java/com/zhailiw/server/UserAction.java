package com.zhailiw.server;

import android.content.Context;

import com.alibaba.fastjson.JSONException;

import com.zhailiw.Const;
import com.zhailiw.common.json.JsonMananger;
import com.zhailiw.server.response.BindResponse;
import com.zhy.http.okhttp.OkHttpUtils;

import java.io.IOException;

import okhttp3.Response;


/**
 * Created by AMing on 16/1/14.
 * Company RongCloud
 */
@SuppressWarnings("deprecation")
public class UserAction extends BaseAction {
    private final String TAG=UserAction.class.getSimpleName();
    private final String CONTENT_TYPE = "application/json";
    private final String ENCODING = "utf-8";
    public String token;
    public static UserAction instance;
    private Object relateRecommend;

    /**
     * 构造方法
     *
     * @param context 上下文
     */
    public UserAction(Context context) {
        super(context);
    }
    public static UserAction getInstance(Context context) {
        if (instance == null) {
            synchronized (UserAction.class) {
                if (instance == null) {
                    instance = new UserAction(context);
                }
            }
        }
        return instance;
    }

//绑定
    public Object bindQRCode(String qrCode,String phoneID) throws  HttpException{
        String result = "";
        String uri = Const.SERVERURI+"cli-api-bindbysn.php";
        Response response=null;
        try {
            response= OkHttpUtils
                    .get()
                    .url(uri)
                    .addParams("sn_qr",qrCode)
                    .addParams("phone_id",phoneID)
                    .build()
                    .execute();
            result =response.body().string();
        } catch (IOException e) {
            e.printStackTrace();

        }
        BindResponse bindResponse = null;
        try {
            bindResponse = JsonMananger.jsonToBean(result, BindResponse.class);
        } catch (JSONException e) {
            //Logger.e(TAG+"::::::%s", "BindResponse occurs JSONException e=" + e.toString());
            return null;
        }

        return bindResponse;
    }
}
