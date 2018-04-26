package com.zhailiw.view.activity;

import android.os.Bundle;

import com.zhailiw.view.activity.BaseActivity;
import com.zhailiw.R;
import com.zhailiw.widget.LoadDialog;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LoadDialog.show(this);
    }
}
