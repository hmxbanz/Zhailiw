package com.zhailiw.app.view.activity;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.zhailiw.app.R;
import com.zhailiw.app.presenter.RegisterPresenter;


public class RegisterActivity extends BaseActivity implements View.OnClickListener{
    private EditText userName, password;
    private RegisterPresenter mRegisterPresenter;
    private TextView txtCaptcha;
    private Button btnRegister;
    private CheckBox checkBox;
    private EditText captcha;
    private TextView txtProtocol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initViews();
        mRegisterPresenter = new RegisterPresenter(this);
        mRegisterPresenter.init();
    }

    private void initViews() {
        layoutBack = findViewById(R.id.layout_back);
        layoutBack.setOnClickListener(this);
        txtTitle = findViewById(R.id.txt_title);
        txtTitle.setText("用户注册");
        txtProtocol = findViewById(R.id.txt_protocol);
        txtProtocol.setOnClickListener(this);

        checkBox = findViewById(R.id.checkbox);
        Drawable drawable = this.getResources().getDrawable(R.drawable.selector_checkbox);
        drawable.setBounds(0,0,50,50);
        if(Build.VERSION.SDK_INT>=21)
            drawable.setTint(getResources().getColor(R.color.mainColorBlue));
        checkBox.setCompoundDrawables(drawable,null,null,null);
        userName =findViewById(R.id.username);
        userName.setOnClickListener(this);
        password =findViewById(R.id.password);
        password.setOnClickListener(this);
        captcha =findViewById(R.id.captcha);
        captcha.setOnClickListener(this);
        txtCaptcha=findViewById(R.id.txt_captcha);
        txtCaptcha.setOnClickListener(this);
        btnRegister=findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_back:
                finish();
                break;
            case R.id.txt_captcha:
                mRegisterPresenter.getCaptcha(txtCaptcha, userName);
                break;
            case R.id.btn_register:
                mRegisterPresenter.register(checkBox, userName, password,captcha);
                break;
            case R.id.txt_protocol:
                //startActivity(new Intent(this, ProtocolActivity.class));
                break;
        }

    }
}
