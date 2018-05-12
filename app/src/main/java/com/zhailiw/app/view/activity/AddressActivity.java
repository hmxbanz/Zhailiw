package com.zhailiw.app.view.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.zhailiw.app.R;
import com.zhailiw.app.presenter.AddressPresenter;

public class AddressActivity extends BaseActivity implements View.OnClickListener {
    private AddressPresenter presenter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        initViews();
        presenter = new AddressPresenter(this);
        presenter.init(recyclerView);
    }

    private void initViews() {
        ((TextView)findViewById(R.id.txt_title)).setText("管理收货地址");
        findViewById(R.id.layout_back).setOnClickListener(this);
        recyclerView = findViewById(R.id.recyclerView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_back:
                finish();
                break;
        }

    }
}