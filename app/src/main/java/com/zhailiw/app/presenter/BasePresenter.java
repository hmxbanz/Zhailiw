package com.zhailiw.app.presenter;

import android.content.Context;
import android.content.SharedPreferences;

import com.zhailiw.app.Const;
import com.zhailiw.app.widget.LoadDialog;
import com.zhailiw.app.common.NToast;
import com.zhailiw.app.server.HttpException;
import com.zhailiw.app.server.UserAction;
import com.zhailiw.app.server.async.AsyncTaskManager;
import com.zhailiw.app.server.async.OnDataListener;
import com.zhailiw.app.R;
import com.zhailiw.app.widget.ACache;

import static android.content.Context.MODE_PRIVATE;
import static com.zhailiw.app.server.async.AsyncTaskManager.HTTP_ERROR_CODE;
import static com.zhailiw.app.server.async.AsyncTaskManager.HTTP_NULL_CODE;
import static com.zhailiw.app.server.async.AsyncTaskManager.REQUEST_ERROR_CODE;


/**
 * Created by hmxbanz on 2017/5/11.
 */

public class BasePresenter implements OnDataListener {
    private static BasePresenter instance;
    protected SharedPreferences sp;
    protected SharedPreferences.Editor editor;
    public boolean isLogin;
    protected Context context;
    public UserAction userAction;
    public AsyncTaskManager atm ;
    protected String userInfoId;
    public ACache aCache;
    protected String userName;
    protected String password;

    public BasePresenter(Context context)
    {
        this.context = context;
        atm = AsyncTaskManager.getInstance(context);
        userAction = UserAction.getInstance(context);
        if(context != null){
        aCache = ACache.get(context);
        sp = this.context.getSharedPreferences("UserConfig", MODE_PRIVATE);
        editor = sp.edit();
        initData();
        }

    }
    public static BasePresenter getInstance(Context context) {
        if (instance == null) {
            synchronized (BasePresenter.class) {
                if (instance == null) {
                    instance = new BasePresenter(context);
                }
            }
        }
        return instance;
    }
    public void initData()
    {
        isLogin = sp.getBoolean(Const.ISLOGIN, false);
        userInfoId = sp.getString(Const.USERINFOID, "0");
        userAction.token = GetToken();
        userName=sp.getString(Const.LOGIN_USERNAME,"");
        password=sp.getString(Const.LOGING_PASSWORD,"");
    }
    protected String GetToken(){
        return sp.getString("access_token","");
    }

    @Override
    public Object doInBackground(int requestCode, String parameter) throws HttpException {
        return null;
    }

    @Override
    public void onSuccess(int requestCode, Object result) {

    }

    @Override
    public void onFailure(int requestCode, int state, Object result) {
        LoadDialog.dismiss(context);
        String errorMsg=String.valueOf(requestCode)+" ";
        switch (state) {
                case REQUEST_ERROR_CODE:
                    errorMsg+=context.getString(R.string.request_fail);
                    break;
                case HTTP_ERROR_CODE:
                    errorMsg+=context.getString(R.string.network_error);
                    break;
            case HTTP_NULL_CODE:
                    errorMsg+=context.getString(R.string.network_not_available);
                    break;
                default:
                    errorMsg+=context.getString(R.string.unknow_error);
            }
        NToast.shortToast(context, errorMsg);
    }
}
