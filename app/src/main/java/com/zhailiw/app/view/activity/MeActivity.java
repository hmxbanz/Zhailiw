package com.zhailiw.app.view.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhailiw.app.R;
import com.zhailiw.app.presenter.MePresenter;
import com.zhailiw.app.widget.SelectableRoundedImageView;


public class MeActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout m;
    private MePresenter mMePresenter;

    public static final int REQUEST_CODE_ASK_PERMISSIONS = 101;
    private Uri selectUri;
    private SelectableRoundedImageView selectableRoundedImageView;
    private TextView txtNickName,txtBirthday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me);
        initViews();
        mMePresenter =new MePresenter(this);
        mMePresenter.init(selectableRoundedImageView, txtNickName,txtBirthday);
    }

    public void initViews(){
        ((TextView)findViewById(R.id.txt_title)).setText("资料");
        findViewById(R.id.layout_back).setOnClickListener(this);
        selectableRoundedImageView= findViewById(R.id.img_avator);
        selectableRoundedImageView.setOnClickListener(this);
        findViewById(R.id.layout_nickname).setOnClickListener(this);
        findViewById(R.id.layout_birthday).setOnClickListener(this);
        txtNickName =  findViewById(R.id.txt_nickname);
        txtBirthday =  findViewById(R.id.txt_birthday);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_back:
                finish();
                break;
            case R.id.img_avator:
                mMePresenter.showPhotoDialog();
                break;
            case R.id.layout_nickname:
                startActivity(new Intent(this,UpdateActivity.class));
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mMePresenter.onActivityResult(requestCode, resultCode, data);
    }


}
