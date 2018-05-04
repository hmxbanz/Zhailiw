package com.zhailiw.app.server;

import android.content.Context;

import com.alibaba.fastjson.JSONException;

import com.orhanobut.logger.Logger;
import com.zhailiw.app.Const;
import com.zhailiw.app.common.json.JsonMananger;
import com.zhailiw.app.server.response.BindResponse;
import com.zhailiw.app.server.response.CaptchaResponse;
import com.zhailiw.app.server.response.CommonResponse;
import com.zhailiw.app.server.response.GalleryResponse;
import com.zhailiw.app.server.response.StyleResponse;
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

    //获取验证码
    public CaptchaResponse getCaptcha(String cellPhone) throws HttpException
    {
        String result = "";
        String uri = getURL("cli-comm-sendregmsg.php");
        Response response=null;
        try {
            response=OkHttpUtils
                    .get()
                    .addParams("phone_no",cellPhone)
                    .url(uri)
                    .build()
                    .execute();
            result =response.body().string();
            Logger.d(TAG+"::::::%s", result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        CaptchaResponse captchaResponse = null;
        try {
            captchaResponse = JsonMananger.jsonToBean(result, CaptchaResponse.class);
        } catch (JSONException e) {
            Logger.e(TAG+"::::::%s", "CaptchaResponse occurs JSONException e=" + e.toString());
            return null;
        }
        return captchaResponse;

    }

    //获取验证码(取回密码)
    public CommonResponse getCaptchaForget(String cellPhone) throws HttpException
    {
        String result = "";
        String uri = getURL("cli-comm-sendpwdmsg.php");
        Response response=null;
        try {
            response=OkHttpUtils
                    .get()
                    .addParams("phone_no",cellPhone)
                    .url(uri)
                    .build()
                    .execute();
            result =response.body().string();
            Logger.d(TAG+"::::::%s", result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        CommonResponse commonResponse = null;
        try {
            commonResponse = JsonMananger.jsonToBean(result, CommonResponse.class);
        } catch (JSONException e) {
            Logger.e(TAG+"::::::%s", "getCaptchaForget occurs JSONException e=" + e.toString());
            return null;
        }
        return commonResponse;

    }
    //注册
    public CommonResponse register(String headimgurl,String nickname, String password, String captcha) throws HttpException
    {
        String result = "";
        String uri = getURL("cli-comm-register.php");
        Response response=null;
        try {
            response=OkHttpUtils
                    .get()
                    .addParams("nick_name",nickname)
                    .addParams("head_img",headimgurl)
                    .addParams("pwd",password)
                    .addParams("rand_code",captcha)
                    .url(uri)
                    .build()
                    .execute();
            result =response.body().string();
            Logger.d(TAG+"::::::%s", result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        CommonResponse commonResponse = null;
        try {
            commonResponse = JsonMananger.jsonToBean(result, CommonResponse.class);
        } catch (JSONException e) {
            Logger.e(TAG+"::::::%s", "register occurs JSONException e=" + e.toString());
            return null;
        }
        return commonResponse;

    }
    //重置密码
    public CommonResponse resetPassword(String cellPhone, String password, String captcha) throws HttpException
    {
        String result = "";
        String uri = getURL("cli-comm-setpwd.php");
        Response response=null;
        try {
            response=OkHttpUtils
                    .get()
                    .addParams("nick_name",cellPhone)
                    .addParams("new_pwd",password)
                    .addParams("rand_code",captcha)
                    .url(uri)
                    .build()
                    .execute();
            result =response.body().string();
            Logger.d(TAG+"::::::%s", result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        CommonResponse commonResponse = null;
        try {
            commonResponse = JsonMananger.jsonToBean(result, CommonResponse.class);
        } catch (JSONException e) {
            Logger.e(TAG+"::::::%s", "CommonResponse occurs JSONException e=" + e.toString());
            return null;
        }
        return commonResponse;

    }
        //图库
    public GalleryResponse getGallery(String pageIndex) throws HttpException {
        String result = "";
        String uri = getURL("Home/getGallery");
        Response response=null;
        try {
            response=OkHttpUtils
                    .get()
                    .addParams("pageIndex",pageIndex)
                    .url(uri)
                    .build()
                    .execute();
            result =response.body().string();
            Logger.d(TAG+"::::::%s", result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        GalleryResponse galleryResponse = null;
        try {
            galleryResponse = JsonMananger.jsonToBean(result, GalleryResponse.class);
        } catch (JSONException e) {
            Logger.e(TAG+"::::::%s", "GalleryResponse occurs JSONException e=" + e.toString());
            return null;
        }
        return galleryResponse;
    }

    public CommonResponse login(String userName, String password, String normal) throws HttpException {
        String result = "";
        String uri = getURL("Home/Login");
        Response response=null;
        try {
            response=OkHttpUtils
                    .get()
                    .addParams("username",userName)
                    .addParams("password",password)
                    .addParams("type",normal)
                    .url(uri)
                    .build()
                    .execute();
            result =response.body().string();
            Logger.d(TAG+"::::::%s", result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        CommonResponse commonResponse = null;
        try {
            commonResponse = JsonMananger.jsonToBean(result, CommonResponse.class);
        } catch (JSONException e) {
            Logger.e(TAG+"::::::%s", "CommonResponse occurs JSONException e=" + e.toString());
            return null;
        }
        return commonResponse;
    }

    //取风格
    public StyleResponse getStyles() throws HttpException {
        String result = "";
        String uri = getURL("Home/getStyles");
        Response response=null;
        try {
            response=OkHttpUtils
                    .get()
                    .url(uri)
                    .build()
                    .execute();
            result =response.body().string();
            Logger.d(TAG+"::::::%s", result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        StyleResponse styleResponse = null;
        try {
            styleResponse = JsonMananger.jsonToBean(result, StyleResponse.class);
        } catch (JSONException e) {
            Logger.e(TAG+"::::::%s", "StyleResponse occurs JSONException e=" + e.toString());
            return null;
        }
        return styleResponse;
    }
}
