package com.zhailiw.app.presenter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zhailiw.app.Adapter.GalleryAdapter;
import com.zhailiw.app.Adapter.StyleAdapter;
import com.zhailiw.app.Const;
import com.zhailiw.app.common.NToast;
import com.zhailiw.app.listener.AlertDialogCallBack;
import com.zhailiw.app.server.HttpException;
import com.zhailiw.app.server.async.OnDataListener;
import com.zhailiw.app.server.response.GalleryResponse;
import com.zhailiw.app.server.response.StyleResponse;
import com.zhailiw.app.view.activity.LoginActivity;
import com.zhailiw.app.view.activity.MainActivity;
import com.zhailiw.app.widget.DialogWithYesOrNoUtils;
import com.zhailiw.app.widget.LoadDialog;

import java.util.ArrayList;
import java.util.List;


public class ShopFragmentPresenter extends BasePresenter implements StyleAdapter.ItemClickListener,OnDataListener,SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = ShopFragmentPresenter.class.getSimpleName();
    private static final int GETSTYLE = 1;
    public static int REQUEST_CODE=33;
    private final BasePresenter basePresenter;
    private final List<StyleResponse.DataBean> list=new ArrayList<>();
    private RecyclerView recyclerView;
    private StyleAdapter dataAdapter;
    private GridLayoutManager gridLayoutManager;
    private MainActivity activity;
    private SwipeRefreshLayout swiper;


    public ShopFragmentPresenter(Context context){
        super(context);
        basePresenter = BasePresenter.getInstance(context);
        activity = (MainActivity) context;
        dataAdapter = new StyleAdapter(this.context);
        dataAdapter.setListItems(list);
        dataAdapter.setOnItemClickListener(this);
    }

    public void init(RecyclerView recyclerView, SwipeRefreshLayout swiper) {
        this.recyclerView=recyclerView;
        this.swiper=swiper;
        this.swiper.setOnRefreshListener(this);
        this.recyclerView.setAdapter(dataAdapter);
        this.recyclerView.setNestedScrollingEnabled(false);
        gridLayoutManager=new GridLayoutManager(context,1);
        this.recyclerView.setLayoutManager(gridLayoutManager);
        atm.request(GETSTYLE,ShopFragmentPresenter.this);
    }
//    public void loadData(){
//        if(basePresenter.isLogin){
//        }
//        else
//        {this.swiper.setVisibility(View.GONE);}
//    }

    @Override
    public Object doInBackground(int requestCode, String parameter) throws HttpException {
        switch (requestCode) {
            case GETSTYLE:
                return userAction.getStyles();
        }
        return null;
    }

    @Override
    public void onSuccess(int requestCode, Object result) {
        LoadDialog.dismiss(context);
        if (result==null)return;
        switch (requestCode) {
            case GETSTYLE:
                StyleResponse response = (StyleResponse) result;
                if (response.getState() == Const.SUCCESS) {
                    if (response.getData().size() == 0) {
                    }
                    else {
                        list.clear();
                        list.addAll(response.getData());
                        //设置列表
                        //dataAdapter.setHeaderView(LayoutInflater.from(context).inflate(R.layout.recyclerview_header,null));
                        dataAdapter.notifyDataSetChanged();
                        this.swiper.setRefreshing(false);
                    }
                }else {
                    NToast.shortToast(context, "获取设备列表："+response.getMsg());
                }

                break;
        }
    }

//    @Override
//    public void onItemClick(int position, MyDevicesResponse.DataBean item) {
//
//    }

    @Override
    public void onRefresh() {
        atm.request(GETSTYLE,ShopFragmentPresenter.this);
    }

    public void onMeClick(View v) {
        basePresenter.initData();
        if(!basePresenter.isLogin){
            DialogWithYesOrNoUtils.getInstance().showDialog(context, "请先登录", new AlertDialogCallBack() {
                @Override
                public void executeEvent() {
                    context.startActivity(new Intent(context, LoginActivity.class));
                }
            });
        }
        else {
            switch (v.getId()) {
            }
        }
    }

    @Override
    public void onItemClick(int position, StyleResponse.DataBean item) {

    }
}