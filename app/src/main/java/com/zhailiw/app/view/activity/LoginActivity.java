package com.zhailiw.app.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.zhailiw.app.R;
import com.zhailiw.app.presenter.LoginPresenter;


public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private EditText mUsername,mPassword;
    private Button mBtnLogin;
    private RelativeLayout mLayoutQqLogin,mLayoutWxLogin;
    private TextView mTextForgetPassword;
    private LoginPresenter presenter;
    private TextView mTextRight;
    private String openId,loginType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
        presenter = new LoginPresenter(this);
        presenter.init(mUsername,mPassword);
    }


    public static void StartActivity(Context context, String openId, String type) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.putExtra("openId",openId);
        intent.putExtra("type",type);
        context.startActivity(intent);
    }

    private void initViews() {
        Intent intent=getIntent();
        openId = intent.getStringExtra("openId");
        loginType = intent.getStringExtra("type");

        layoutBack =  findViewById(R.id.layout_back);
        layoutBack.setOnClickListener(this);
        txtTitle = findViewById(R.id.text_title);
        mTextRight = findViewById(R.id.text_right);
        mTextRight.setOnClickListener(this);

        mTextForgetPassword =  findViewById(R.id.text_forget_password);
        mLayoutQqLogin =  findViewById(R.id.layout_qq_login);
        mLayoutQqLogin.setOnClickListener(this);
        mLayoutWxLogin =  findViewById(R.id.layout_wx_login);
        mLayoutWxLogin.setOnClickListener(this);
        mTextForgetPassword.setOnClickListener(this);
        mUsername =  findViewById(R.id.username);
        mPassword =  findViewById(R.id.password);
        mBtnLogin =  findViewById(R.id.btn_login);
        mBtnLogin.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_back:
                finish();
                break;
            case R.id.text_right:
                startActivity(new Intent(this,RegisterActivity.class));
                break;
            case R.id.btn_login:
                if(TextUtils.isEmpty(openId)){
                    presenter.login("nomal");  return;
                    }
                presenter.login("bind");
                break;
            case R.id.text_forget_password:
                startActivity(new Intent(this,ForgetPasswordActivity.class));
                break;
            case R.id.layout_wx_login:
                //presenter.wxLogin();
                break;
            case R.id.layout_qq_login:
                //presenter.qqLogin();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
