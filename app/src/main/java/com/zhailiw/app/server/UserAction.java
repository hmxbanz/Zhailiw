package com.zhailiw.app.server;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSONException;

import com.orhanobut.logger.Logger;
import com.zhailiw.app.Const;
import com.zhailiw.app.common.json.JsonMananger;
import com.zhailiw.app.server.request.UpdateRequest;
import com.zhailiw.app.server.response.ADResponse;
import com.zhailiw.app.server.response.AddressResponse;
import com.zhailiw.app.server.response.BindResponse;
import com.zhailiw.app.server.response.CaptchaResponse;
import com.zhailiw.app.server.response.CommonResponse;
import com.zhailiw.app.server.response.GalleryPicResponse;
import com.zhailiw.app.server.response.GalleryResponse;
import com.zhailiw.app.server.response.ShopResponse;
import com.zhailiw.app.server.response.StyleResponse;
import com.zhailiw.app.server.response.UserInfoResponse;
import com.zhy.http.okhttp.OkHttpUtils;

import java.io.File;
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
    //图库图片
    public GalleryPicResponse getGalleryPic(String galleryId) throws HttpException {
        String result = "";
        String uri = getURL("Home/getGalleryPic");
        Response response=null;
        try {
            response=OkHttpUtils
                    .get()
                    .addParams("galleryId",galleryId)
                    .url(uri)
                    .build()
                    .execute();
            result =response.body().string();
            Logger.d(TAG+"::::::%s", result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        GalleryPicResponse galleryPicResponse = null;
        try {
            galleryPicResponse = JsonMananger.jsonToBean(result, GalleryPicResponse.class);
        } catch (JSONException e) {
            Logger.e(TAG+"::::::%s", "GalleryPicResponse occurs JSONException e=" + e.toString());
            return null;
        }
        return galleryPicResponse;
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

    //上传头像
    public CommonResponse uploadAvatar(File imgFile) throws HttpException {
        String result = "";
        String uri = getURL("User/updateAvatar");
//        final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
//        RequestBody requestBody = new MultipartBody.Builder()
//                .setType(MultipartBody.FORM)
//                .addFormDataPart("content", "Square Logo")
//                .addFormDataPart("image", "logo-square.png",RequestBody.create(MEDIA_TYPE_PNG, new File("website/static/logo-square.png")))
//                .build();
        Response response=null;
        try {
            response=OkHttpUtils
                    .post()
                    .addParams(Const.ACCESS_TOKEN,token)
                    .addFile("avatar", "imgFile.jpg",imgFile)
                    .url(uri)
                    .build()
                    .execute();
            result =response.body().string();
            Logger.d(TAG+"::::::%s", result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        CommonResponse commonResponse = null;
        if (!TextUtils.isEmpty(result)) {
            try {
                commonResponse = JsonMananger.jsonToBean(result, CommonResponse.class);
            } catch (JSONException e) {
                Logger.e(TAG+"::::::%s", "uploadAvatar occurs JSONException e=" + e.toString());
                return null;
            }
        }
        return commonResponse;
    }

    //取个人资料
    public UserInfoResponse getInfo() throws HttpException {
        String result = "";
        String uri = getURL("User/getMyInfo");
        Response response=null;
        try {
            response=OkHttpUtils
                    .get()
                    .addParams("access_key",token)
                    .url(uri)
                    .build()
                    .execute();
            result =response.body().string();
            Logger.d(TAG+"::::::%s", result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        UserInfoResponse userInfoResponse = null;
        try {
            userInfoResponse = JsonMananger.jsonToBean(result, UserInfoResponse.class);
        } catch (JSONException e) {
            Logger.e(TAG+"::::::%s", "UserInfoResponse occurs JSONException e=" + e.toString());
            return null;
        }
        return userInfoResponse;
    }

//修改个人资料
    public CommonResponse save(String nickName) throws HttpException {
        String result = "";
        String uri = getURL("User/updateUserInfo");
        String json = JsonMananger.beanToJson(new UpdateRequest(nickName,token));
        Log.w(TAG, "请求的："+json);
        Response response=null;
        try {
            response=OkHttpUtils
                    //.postString()
                    //.mediaType(MediaType.parse("application/json; charset=utf-8"))
                    //.content(json)//.content(new Gson().toJson(new User("zhy", "123")))
                    .get()
                    .url(uri)
                    .addParams("access_key",token)
                    .addParams("NickName",nickName)
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

    public AddressResponse getAddress() throws HttpException{
        String result = "";
        String uri = getURL("User/getMyAddress");
        Response response=null;
        try {
            response=OkHttpUtils
                    .get()
                    .addParams("access_key",token)
                    .url(uri)
                    .build()
                    .execute();
            result =response.body().string();
            Logger.d(TAG+"::::::%s", result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        AddressResponse addressResponse = null;
        try {
            addressResponse = JsonMananger.jsonToBean(result, AddressResponse.class);
        } catch (JSONException e) {
            Logger.e(TAG+"::::::%s", "AddressResponse occurs JSONException e=" + e.toString());
            return null;
        }
        return addressResponse;
    }

    public ShopResponse getProducts(String pageIndex, String styleId, String productTypeId) throws HttpException{
        String result = "";
        String uri = getURL("Home/getProducts");
        Response response=null;
        try {
            response=OkHttpUtils
                    .get()
                    .addParams("pageIndex",pageIndex)
                    .addParams("styleId",styleId)
                    .addParams("productTypeId",productTypeId)
                    .url(uri)
                    .build()
                    .execute();
            result =response.body().string();
            Logger.d(TAG+"::::::%s", result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ShopResponse shopResponse = null;
        try {
            shopResponse = JsonMananger.jsonToBean(result, ShopResponse.class);
        } catch (JSONException e) {
            Logger.e(TAG+"::::::%s", "ShopResponse occurs JSONException e=" + e.toString());
            return null;
        }
        return shopResponse;
    }

    public ADResponse getAds() throws HttpException{
        String result = "";
        String uri = getURL("Home/getAds");
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
        ADResponse adResponse = null;
        try {
            adResponse = JsonMananger.jsonToBean(result, ADResponse.class);
        } catch (JSONException e) {
            Logger.e(TAG+"::::::%s", "ADResponse occurs JSONException e=" + e.toString());
            return null;
        }
        return adResponse;
    }

}
