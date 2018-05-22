package com.zhailiw.app.presenter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.EditText;

import com.zhailiw.app.Const;
import com.zhailiw.app.R;
import com.zhailiw.app.common.NToast;
import com.zhailiw.app.server.HttpException;
import com.zhailiw.app.server.response.LoginResponse;
import com.zhailiw.app.view.activity.LoginActivity;
import com.zhailiw.app.view.activity.MainActivity;
import com.zhailiw.app.widget.LoadDialog;

/**
 * Created by hmxbanz on 2017/4/5.
 */

public class LoginPresenter extends BasePresenter  {
    private static final String TAG = LoginPresenter.class.getSimpleName();
    private static final int LOGIN = 1;
    private final BasePresenter basePresenter;
    private LoginActivity activity;
    private EditText mUsername;
    private EditText mPassword;
    private String loginType;

    public LoginPresenter(Context context){
        super(context);
        activity = (LoginActivity) context;
        basePresenter = BasePresenter.getInstance(context);
    }

    public void init(EditText username, EditText password) {
        this.mUsername=username;
        this.mPassword=password;
    }

    public void login(String type) {
        this.loginType=type;

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
                return userAction.login(mUsername.getText().toString(), mPassword.getText().toString(),null,this.loginType);

        }
        return null;
    }
    @Override
    public void onSuccess(final int requestCode, Object result) {
        LoadDialog.dismiss(context);
        if (result==null)return;
            switch (requestCode) {
                case LOGIN:
                    LoginResponse loginResponse = (LoginResponse) result;
                    if ( loginResponse.getState()== Const.SUCCESS) {
                        loginWork(loginResponse.getToken());
                        MainActivity.StartActivity(activity,2);
                    }
                    NToast.shortToast(context, loginResponse.getMsg());
                    break;
            }
    }


    public void getInfo(){
        basePresenter.initData();
        if(basePresenter.isLogin){
            LoadDialog.show(context);
            //atm.request(GETINFO,this);
        }
    }

}
