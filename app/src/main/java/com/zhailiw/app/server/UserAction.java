package com.zhailiw.app.server;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSONException;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.zhailiw.app.Const;
import com.zhailiw.app.common.json.JsonMananger;
import com.zhailiw.app.server.request.BindPhoneRequest;
import com.zhailiw.app.server.request.RegisterRequest;
import com.zhailiw.app.server.request.UpdateRequest;
import com.zhailiw.app.server.response.ADResponse;
import com.zhailiw.app.server.response.AddressResponse;
import com.zhailiw.app.server.response.CaptchaResponse;
import com.zhailiw.app.server.response.CommonResponse;
import com.zhailiw.app.server.response.GalleryPicResponse;
import com.zhailiw.app.server.response.GalleryResponse;
import com.zhailiw.app.server.response.LoginResponse;
import com.zhailiw.app.server.response.ShopResponse;
import com.zhailiw.app.server.response.StyleResponse;
import com.zhailiw.app.server.response.SystemObjResponse;
import com.zhailiw.app.server.response.UserInfoResponse;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhailiw.app.server.request.LoginRequest;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import okhttp3.MediaType;
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

    //获取验证码
    public CaptchaResponse getCaptcha(String cellPhone,String type) throws HttpException
    {
        String uri = getURL("/Home/GetCaptcha");
        LinkedHashMap map=new LinkedHashMap<>();
        map.put("cellPhone",cellPhone);
        map.put("type",type);
        return getRequest(CaptchaResponse.class,map,uri);
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
    public CommonResponse register(String userName,String password, String nickName, String captcha) throws HttpException
    {
        String uri = getURL("User/Register");
        String json=JsonMananger.beanToJson(new RegisterRequest(userName,password,nickName,captcha));
        return postRequest(CommonResponse.class,json,uri);
    }
    //重置密码
    public CommonResponse resetPassword(String userName, String password, String captcha) throws HttpException
    {
        String uri = getURL("User/resetPassword");
        String json=JsonMananger.beanToJson(new RegisterRequest(userName,password,null,captcha));
        return postRequest(CommonResponse.class,json,uri);

    }
        //图库
    public GalleryResponse getGallery(String pageIndex,String galleryTypeId) throws HttpException {
        String result = "";
        String uri = getURL("Home/getGallery");
        Response response=null;
        try {
            response=OkHttpUtils
                    .get()
                    .addParams("pageIndex",pageIndex)
                    .addParams("galleryTypeId",galleryTypeId)
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

    public LoginResponse login(String userName, String password, String openId, String type) throws HttpException {
        String uri = getURL("User/Login");
        String json=JsonMananger.beanToJson(new LoginRequest(userName,password,openId,type));
        return postRequest(LoginResponse.class,json,uri);
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
                    .addParams(Const.TOKEN,token)
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
        String uri = getURL("User/getMyAddress");
        return getRequest(AddressResponse.class,null,uri);
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
    //手机绑定
    public CommonResponse bindPhone(String userName, String captcha,String openId, String bindType ) throws HttpException
    {
        String uri = getURL("/User/ThirdPartBind");
        String json=JsonMananger.beanToJson(new BindPhoneRequest(userName,captcha,openId,bindType));
        return postRequest(CommonResponse.class,json,uri);

    }
    //取系统对象
    public SystemObjResponse getSystemObj() throws HttpException {
        String uri = getURL("/Sys/GetSysObj");
        return getRequest(SystemObjResponse.class,null,uri);
    }
    //get请求
    public <T> T getRequest(Class<T> t, LinkedHashMap map, String uri)throws HttpException
    {
        String result="";
        Response response=null;
        try {
            response=OkHttpUtils
                    .get()
                    .addHeader("token",token)
                    .params(map)
                    .url(uri)
                    .build()
                    .execute();
            result =response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Logger.d("请求的：%s",map);
        Logger.d(t.getSimpleName()+"："+ result);
        T beanResponse = null;
        try {
            if(t.getSimpleName().equals("SystemObjResponse") || t.getSimpleName().equals("CityResponse"))
                beanResponse= new Gson().fromJson(result,t);
            else
                beanResponse = JsonMananger.jsonToBean(result, t);
        }
        catch (JSONException e) {
            Logger.e(TAG, t.getSimpleName()+" occurs JSONException e=" + e.toString());
            return null;
        }
        return beanResponse;
    }
    //post请求
    public <T> T postRequest(Class<T> t, String json, String uri)throws HttpException
    {
        String result="";
        Response response=null;
        try {
            response=OkHttpUtils
                    .postString()
                    .addHeader("token",token)
                    .url(uri)
                    .mediaType(MediaType.parse("application/json; charset=utf-8"))
                    .content(json)//.content(new Gson().toJson(new User("zhy", "123")))
                    .build()
                    .execute();
            result =response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Logger.d("请求的：%s",json);
        Logger.d(t.getSimpleName()+"："+ result);

        T beanResponse = null;
        try {
            beanResponse = JsonMananger.jsonToBean(result, t);
        } catch (JSONException e) {
            Logger.e(TAG, t.getSimpleName()+" occurs JSONException e=" + e.toString());
            return null;
        }
        return beanResponse;
    }
    //post请求
    public <T> T postFormRequest(Class<T> t, Map<String, String> params, String fileKey, String fileName, File file, String uri)throws HttpException
    {
        String result="";
        Response response=null;
        try {
            response=OkHttpUtils
                    .post()
                    .addHeader("access_token",token)
                    .params(params)
                    .addFile(fileKey, fileName,file)
                    .url(uri)
                    .build()
                    .execute();
            result =response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Logger.d("请求的：%s",params);
        Logger.d(t.getSimpleName()+"："+ result);

        T beanResponse = null;
        try {
            beanResponse = JsonMananger.jsonToBean(result, t);
        } catch (JSONException e) {
            Logger.e(TAG, t.getSimpleName()+" occurs JSONException e=" + e.toString());
            return null;
        }
        return beanResponse;
    }
}
