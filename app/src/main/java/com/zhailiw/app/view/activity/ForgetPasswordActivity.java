package com.zhailiw.app.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zhailiw.app.R;
import com.zhailiw.app.presenter.ForgetPasswordPresenter;


public class ForgetPasswordActivity extends BaseActivity implements View.OnClickListener {
    private EditText userName,password;
    private Button mBtnFindPassword;
    private ForgetPasswordPresenter mForgetPasswordPresenter;
    private TextView txtCaptcha;
    private EditText captcha;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        initViews();
        mForgetPasswordPresenter = new ForgetPasswordPresenter(this);
        mForgetPasswordPresenter.init();
    }

    private void initViews() {
        layoutBack =  findViewById(R.id.layout_back);
        layoutBack.setOnClickListener(this);
        txtTitle =  findViewById(R.id.txt_title);
        txtTitle.setText("找回密码");

        userName =findViewById(R.id.username);
        userName.setOnClickListener(this);
        password =findViewById(R.id.password);
        password.setOnClickListener(this);
        captcha =findViewById(R.id.captcha);
        captcha.setOnClickListener(this);
        txtCaptcha=findViewById(R.id.txt_captcha);
        txtCaptcha.setOnClickListener(this);
        mBtnFindPassword=findViewById(R.id.btn_find_pwd);
        mBtnFindPassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_back:
                finish();
                break;
            case R.id.txt_captcha:
                mForgetPasswordPresenter.getCaptcha(txtCaptcha, userName);
                break;
            case R.id.btn_find_pwd:
                mForgetPasswordPresenter.resetPassword(userName, password,captcha);
                break;
        }

    }
}
