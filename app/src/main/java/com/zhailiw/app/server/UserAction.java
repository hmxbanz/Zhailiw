package com.zhailiw.app.server;

import android.content.Context;
import android.text.TextUtils;

import com.alibaba.fastjson.JSONException;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.zhailiw.app.Const;
import com.zhailiw.app.common.json.JsonMananger;
import com.zhailiw.app.server.request.BindPhoneRequest;
import com.zhailiw.app.server.request.LoginRequest;
import com.zhailiw.app.server.request.RegisterRequest;
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
        String uri = getURL("Home/getGallery");
        LinkedHashMap map=new LinkedHashMap<>();
        map.put("pageIndex",pageIndex);
        map.put("galleryTypeId",galleryTypeId);
        return getRequest(GalleryResponse.class,map,uri);
    }
    //图库图片
    public GalleryPicResponse getGalleryPic(String galleryId) throws HttpException {
        String uri = getURL("Home/getGalleryPic");
        LinkedHashMap map=new LinkedHashMap<>();
        map.put("galleryId",galleryId);
        return getRequest(GalleryPicResponse.class,map,uri);
    }
    //登录
    public LoginResponse login(String userName, String password, String openId, String type) throws HttpException {
        String uri = getURL("User/Login");
        String json=JsonMananger.beanToJson(new LoginRequest(userName,password,openId,type));
        return postRequest(LoginResponse.class,json,uri);
    }
    //取风格
    public StyleResponse getStyles() throws HttpException {
        String uri = getURL("Home/getStyles");
        LinkedHashMap map=new LinkedHashMap<>();
        return getRequest(StyleResponse.class,map,uri);
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
        String uri = getURL("User/getMyInfo");
        LinkedHashMap map=new LinkedHashMap<>();
        return getRequest(UserInfoResponse.class,map,uri);
    }
    //修改个人资料
    public CommonResponse save(String nickName) throws HttpException {
        String uri = getURL("User/updateUserInfo");
        LinkedHashMap map=new LinkedHashMap<>();
        map.put("nickName",nickName);
        return getRequest(CommonResponse.class,map,uri);
    }
    //取收货地址
    public AddressResponse getAddress() throws HttpException{
        String uri = getURL("User/getMyAddress");
        return getRequest(AddressResponse.class,null,uri);
    }
    //取商品列表
    public ShopResponse getProducts(String pageIndex, String styleId, String productTypeId) throws HttpException{
        String uri = getURL("Home/getProducts");
        LinkedHashMap map=new LinkedHashMap<>();
        map.put("pageIndex",pageIndex);
        map.put("styleId",styleId);
        map.put("productTypeId",productTypeId);
        return getRequest(ShopResponse.class,map,uri);
    }
    //取广告
    public ADResponse getAds() throws HttpException{
        String uri = getURL("Home/getAds");
        return getRequest(ADResponse.class,null,uri);
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
