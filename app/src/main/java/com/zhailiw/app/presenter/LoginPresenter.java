package com.zhailiw.app.presenter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.EditText;

import com.zhailiw.app.Const;
import com.zhailiw.app.R;
import com.zhailiw.app.common.NToast;
import com.zhailiw.app.server.HttpException;
import com.zhailiw.app.server.response.CommonResponse;
import com.zhailiw.app.view.activity.LoginActivity;
import com.zhailiw.app.widget.LoadDialog;

/**
 * Created by hmxbanz on 2017/4/5.
 */

public class LoginPresenter extends BasePresenter  {
    private static final String TAG = LoginPresenter.class.getSimpleName();
    private static final int LOGIN = 1;
    public static final int UPLOADWXOPENID = 3;
    private static final int WXLOGIN = 4;
    private static final int WXBIND = 5;
    public static final int UPLOADQQOPENID = 6;
    private static final int QQBIND = 7;
    private static final int UPLOADRID = 8;
    public static final int UPLOADWBOPENID = 9;
    private static final int WBBIND = 10;
    private static final int GETINFO = 11;
    private final BasePresenter basePresenter;
    private LoginActivity mActivity;
    private EditText mUsername;
    private EditText mPassword;
    private String access_key;
    private String rid;
    private String headimgurl;
    private String nickname;

    public LoginPresenter(Context context){
        super(context);
        mActivity = (LoginActivity) context;
        basePresenter = BasePresenter.getInstance(context);
    }

    public void init(EditText username, EditText password) {
        this.mUsername=username;
        this.mPassword=password;
    }

    public void login(String type) {

        if(TextUtils.isEmpty(mUsername.getText().toString()))
        {
            NToast.shortToast(context, R.string.phone_number_be_null);
            return;
        }
        if (TextUtils.isEmpty(mPassword.getText().toString())) {
            NToast.shortToast(context, R.string.password_be_null);
            return;
        }
        if (mPassword.getText().toString().contains(" ")) {
            NToast.shortToast(context, R.string.password_cannot_contain_spaces);
            return;
        }

            LoadDialog.show(context);
            atm.request(LOGIN,this);
    }

    @Override
    public Object doInBackground(int requestCode, String id) throws HttpException {
        switch (requestCode) {
            case LOGIN:
                return userAction.login(mUsername.getText().toString(), mPassword.getText().toString(),"normal");
        }
        return null;
    }
    @Override
    public void onSuccess(final int requestCode, Object result) {
        LoadDialog.dismiss(context);
        if (result==null)return;
            switch (requestCode) {
                case LOGIN:
                    CommonResponse commonResponse = (CommonResponse) result;
                    if ( commonResponse.getState()== Const.SUCCESS) {
                        //loginWork2(entity.getAccess_key());
                        LoadDialog.show(context);
                    }
                    NToast.shortToast(context, commonResponse.getMsg());
                    break;
            }
    }

//    public void wxLogin() {
//        LoadDialog.show(context);
//        final Platform weixin = ShareSDK.getPlatform(Wechat.NAME);
//        String name = weixin.getName();
//
//
//        //设置监听回调
//        weixin.setPlatformActionListener(new PlatformActionListener() {
//            @Override
//            public void onComplete(Platform platform, int i, final HashMap<String, Object> hashMap) {
//                Log.d(TAG, " _Weixin: -->> onComplete: Platform:" + platform.toString());
//                Log.d(TAG, " _Weixin: -->> onComplete: hashMap:" + hashMap);
//
//                //当前线程不能执行UI操作，需要放到主线程中去
//                mActivity.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        showUser_WeiXin(hashMap);
//                    }
//                });
//            }
//
//            @Override
//            public void onError(Platform platform, int i, Throwable throwable) {
//                Log.d(TAG, " _Weixin: -->> onError:  " + throwable.toString());
//                throwable.printStackTrace();
//                weixin.removeAccount(true);
//            }
//
//            @Override
//            public void onCancel(Platform platform, int i) {
//                NToast.shortToast(context,"取消了");
//            }
//        });
//
//        //授权并获取用户信息
//        weixin.showUser(null);
//
////        if(!wechat.isAuthValid()){
////            NToast.shortToast(context,"aaaaaaaaaaaaa");
////        } else {
////            NToast.shortToast(context,"bbbbbbbbbbbbb");
////        }
//    }
//    private void showUser_WeiXin(HashMap<String, Object> hashMap) {
//        String avator =  (String) hashMap.get("nickname");
//        Logger.d(hashMap.toString());
//        nickname = (String) hashMap.get("nickname");
//        openId =(String)hashMap.get("openid");
//        NToast.shortToast(context,nickname);
//        headimgurl = (String) hashMap.get("headimgurl");
//        Logger.d("nickname:"+nickname);
//        Logger.d("headimgurl:"+headimgurl);
//        editor.putString(XtdConst.OPENID, openId);
//        editor.putString("loginType", "wx");
//        editor.apply();
//        atm.request(UPLOADWXOPENID,this);
////        Glide.with(ShareLogin.this)
////                .load(url)
////                .placeholder(R.mipmap.ic_launcher)
////                .error(R.mipmap.ic_launcher)
////                .into(ivPortrait);
//    }
//
//    public void qqLogin() {
//
//        LoadDialog.show(context);
//        //获取QQ平台手动授权
//        final Platform qq = ShareSDK.getPlatform(QQ.NAME);
//        //设置回调监听
//        qq.setPlatformActionListener(new PlatformActionListener() {
//            @Override
//            public void onComplete(Platform platform, int action, final HashMap<String, Object> hashMap) {
//                Log.d(TAG, " _QQ: -->> onComplete: Platform:" + platform.toString());
//                Log.d(TAG, " _QQ: -->> onComplete: hashMap:" + hashMap);
//                if (action == Platform.ACTION_USER_INFOR) {
//                    final PlatformDb platDB = platform.getDb();//获取数平台数据DB
//                    //通过DB获取各种数据
//                    platDB.getToken();
//                    platDB.getUserGender();
//                    platDB.getUserIcon();
//                    platDB.getUserId();
//                    platDB.getUserName();
//
//                    //当前线程不能执行UI操作，需要放到主线程中去
//                    mActivity.runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            showUser_QQ(platDB,hashMap);
//                        }
//                    });
//
//                }
//
//            }
//
//            @Override
//            public void onError(Platform platform, int action, Throwable throwable) {
//                Log.d(TAG, " _QQ: -->> onError:  " + throwable.toString());
//                throwable.printStackTrace();
//            }
//
//            @Override
//            public void onCancel(Platform platform, int i) {
//                qq.removeAccount(true);
//            }
//        });
//        //单独授权,进入输入用户名和密码界面
//        /*qq.authorize();*/
//        //授权并获得用户信息
//        qq.showUser(null);
//
//
//    }
//    private void showUser_QQ(PlatformDb platDB, HashMap<String, Object> hashMap) {
//        nickname = (String) hashMap.get("nickname");
//        openId =platDB.getUserId();
//        headimgurl=platDB.getUserIcon();
//        headimgurl=headimgurl.substring(0,headimgurl.lastIndexOf('/'))+"/100";
//
//        NToast.shortToast(context,nickname);
//
//        String url = (String) hashMap.get("figureurl_qq_1");
//
//        Logger.d("nickname:"+nickname);
//        Logger.d("headimgurl:"+headimgurl);
//
//        editor.putString("openId", openId);
//        editor.putString("loginType", "qq");
//        editor.apply();
//        atm.request(UPLOADQQOPENID,this);
//
//    }
//
//    /**
//     * 新浪授权
//     */
//    public void weiboLogin() {
//        LoadDialog.show(context);
//        //获取具体的平台手动授权
//        final Platform weibo = ShareSDK.getPlatform(SinaWeibo.NAME);
//        //设置回调监听
//        weibo.setPlatformActionListener(new PlatformActionListener() {
//            @Override
//            public void onComplete(Platform platform, int i, final HashMap<String, Object> hashMap) {
//                Log.d(TAG, " _XinLang: -->> onComplete: Platform:" + platform.toString());
//                Log.d(TAG, " _XinLang: -->> onComplete: hashMap:" + hashMap);
////获取微博平台手动授权
//                final Platform weibo = ShareSDK.getPlatform(SinaWeibo.NAME);
//
//                //当前线程不能执行UI操作，需要放到主线程中去
//                mActivity.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        showUser_XinLang(hashMap,weibo);
//                    }
//                });
//            }
//
//            @Override
//            public void onError(Platform platform, int i, Throwable throwable) {
//                Log.d(TAG, " _XinLang: -->> onError:  " + throwable.toString());
//                throwable.printStackTrace();
//            }
//
//            @Override
//            public void onCancel(Platform platform, int i) {
//                weibo.removeAccount(true);
//            }
//        });
//        //authorize与showUser单独调用一个即可，
//        //单独授权,进入输入用户名和密码界面
//        /*weibo.authorize();*/
//        //授权并获取用户信息
//        weibo.showUser(null);
//        //移除授权
//        /*weibo.removeAccount(true);*/
//    }
//    public void showUser_XinLang(HashMap<String, Object> params, Platform weibo) {
//        openId =weibo.getDb().getToken();
//        LoadDialog.show(context);
//        atm.request(UPLOADWBOPENID,this);
//        nickname=weibo.getDb().getUserName();
//        headimgurl=weibo.getDb().getUserIcon();
//        Logger.d("nickname:"+nickname);
//        Logger.d("headimgurl:"+headimgurl);
//        editor.putString("openId", openId);
//        editor.putString("loginType", "wb");
//        editor.apply();
//    }

    private void loginWork2(String access_key)
    {
//        editor.putString(XtdConst.ACCESS_TOKEN, access_key);
//        editor.putString(XtdConst.LOGIN_USERNAME, mUsername.getText().toString());
//        editor.putString(XtdConst.LOGING_PASSWORD, mPassword.getText().toString());
//        editor.putBoolean(XtdConst.ISLOGIN, true);
//        editor.apply();
//        basePresenter.initData();
//        getInfo();
//        rid = JPushInterface.getRegistrationID(context.getApplicationContext());
//        BroadcastManager.getInstance(context).sendBroadcast(MineFragmentPresenter.UPDATEUNREAD, "loadAvator");
//        BroadcastManager.getInstance(context).sendBroadcast(HomeFragmentPresenter.LOADDEVICE, "loadDevice");

    }

    public void getInfo(){
        basePresenter.initData();
        if(basePresenter.isLogin){
            LoadDialog.show(context);
            atm.request(GETINFO,this);
        }
    }

}
