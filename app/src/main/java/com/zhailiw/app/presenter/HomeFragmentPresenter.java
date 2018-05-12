package com.zhailiw.app.presenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.NineGridView;
import com.lzy.ninegrid.NineGridViewAdapter;
import com.lzy.ninegrid.preview.ImagePreviewActivity;
import com.orhanobut.logger.Logger;
import com.zhailiw.app.Adapter.GalleryAdapter;
import com.zhailiw.app.Const;
import com.zhailiw.app.R;
import com.zhailiw.app.common.NToast;
import com.zhailiw.app.listener.AlertDialogCallBack;
import com.zhailiw.app.listener.EndlessRecyclerOnScrollListener;
import com.zhailiw.app.server.HttpException;
import com.zhailiw.app.server.async.OnDataListener;
import com.zhailiw.app.server.response.GalleryPicResponse;
import com.zhailiw.app.server.response.GalleryResponse;
import com.zhailiw.app.view.activity.LoginActivity;
import com.zhailiw.app.view.activity.MainActivity;
import com.zhailiw.app.widget.DialogWithYesOrNoUtils;
import com.zhailiw.app.widget.LoadDialog;
import com.zhailiw.app.widget.progressBar.MaterialProgressBar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static android.view.View.*;


public class HomeFragmentPresenter extends BasePresenter implements GalleryAdapter.ItemClickListener,OnDataListener,SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = HomeFragmentPresenter.class.getSimpleName();
    private static final int GETGALLERY = 1;
    private static final int GETGALLERYPICS = 2;
    public static int REQUEST_CODE=33;
    private final BasePresenter basePresenter;
    private List<GalleryResponse.DataBean> list=new ArrayList<>();
    private RecyclerView recyclerView;
    private GalleryAdapter dataAdapter;
    private GridLayoutManager gridLayoutManager;
    private MainActivity activity;
    private SwipeRefreshLayout swiper;
    private int localGalleryId;
    private ArrayList<ImageInfo> imageInfo;
    private EndlessRecyclerOnScrollListener onScrollListener;
    private int pageIndex=1,totalPages;
    private View footerView;

    public HomeFragmentPresenter(Context context){
        super(context);
        basePresenter = BasePresenter.getInstance(context);
        activity = (MainActivity) context;
        dataAdapter = new GalleryAdapter(this.context);
        dataAdapter.setListItems(list);
        dataAdapter.setOnItemClickListener(this);
        footerView=LayoutInflater.from(context).inflate(R.layout.recyclerview_footer,null);
        dataAdapter.setFooterView(footerView);
    }

    public void init(RecyclerView recyclerView, SwipeRefreshLayout swiper) {
        this.recyclerView=recyclerView;
        this.swiper=swiper;
        this.swiper.setOnRefreshListener(this);
        gridLayoutManager=new GridLayoutManager(context,2);
        this.recyclerView.setLayoutManager(gridLayoutManager);
        this.recyclerView.setAdapter(dataAdapter);
        this.recyclerView.setNestedScrollingEnabled(false);
        onScrollListener=new EndlessRecyclerOnScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                Logger.d("GETGALLERY currentPage:%s", currentPage);
                pageIndex = currentPage;
                TextView tips=footerView.findViewById(R.id.tips);
                MaterialProgressBar progressBar=footerView.findViewById(R.id.progress_wheel);
                footerView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                tips.setText(R.string.layout_dialog_loading);
                if(pageIndex<=totalPages) {
                    atm.request(GETGALLERY, HomeFragmentPresenter.this);
                }
                else
                {
                    progressBar.setVisibility(View.GONE);
                    tips.setText("我是有底线的");
                }
            }
        };
        this.recyclerView.addOnScrollListener(onScrollListener);
        LoadDialog.show(context);
        atm.request(GETGALLERY,HomeFragmentPresenter.this);
    }

    @Override
    public Object doInBackground(int requestCode, String parameter) throws HttpException {
        switch (requestCode) {
            case GETGALLERY:
                return userAction.getGallery(pageIndex+"");
            case GETGALLERYPICS:
                return userAction.getGalleryPic(String.valueOf(localGalleryId));
        }
        return null;
    }

    @Override
    public void onSuccess(int requestCode, Object result) {
        LoadDialog.dismiss(context);
        if (result==null)return;
        switch (requestCode) {
            case GETGALLERY:
                GalleryResponse galleryResponse = (GalleryResponse) result;
                if (galleryResponse.getState() == Const.SUCCESS) {
                    totalPages=galleryResponse.getTotalPages();
                    if (galleryResponse.getData().size() == 0) {
                    }
                    else {
                        list.addAll(galleryResponse.getData());
                        dataAdapter.notifyDataSetChanged();
                        footerView.setVisibility(View.GONE);
                        this.swiper.setRefreshing(false);
                    }
                }else {
                    NToast.shortToast(context, galleryResponse.getMsg());
                }
                break;
        case GETGALLERYPICS:
            GalleryPicResponse galleryPicResponse = (GalleryPicResponse) result;
        if (galleryPicResponse.getState() == Const.SUCCESS) {
            imageInfo=new ArrayList<>();
            for(GalleryPicResponse.DataBean photo: galleryPicResponse.getData()) {
                    ImageInfo info = new ImageInfo();
                info.setImageViewWidth(5);
                info.setImageViewHeight(5);
                info.setImageViewX(300);
                info.setImageViewY(150);
                    info.setThumbnailUrl(Const.IMGURI+photo.getPhotoSmall());
                    info.setBigImageUrl(Const.IMGURI+photo.getPhotoBig());
                    imageInfo.add(info);
                }
            Intent intent = new Intent(activity, ImagePreviewActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(ImagePreviewActivity.IMAGE_INFO, (Serializable) imageInfo);
            bundle.putInt(ImagePreviewActivity.CURRENT_ITEM, 0);
            intent.putExtras(bundle);
            activity.startActivity(intent);
            //activity.overridePendingTransition(0, 0);

        }
            NToast.shortToast(context, galleryPicResponse.getMsg());
        break;
    }
    }


    @Override
    public void onRefresh() {
        pageIndex=1;
        this.onScrollListener.reset();
        list.clear();
        atm.request(GETGALLERY,HomeFragmentPresenter.this);
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
    public void onItemClick(int position, GalleryResponse.DataBean item, GalleryAdapter.DataHolder dataHolder) {
            localGalleryId=item.getGalleryID();
            atm.request(GETGALLERYPICS,HomeFragmentPresenter.this);
    }

    @Override
    public void onTabItemClick(int position, String item) {
        NToast.shortToast(activity,item);
    }

}