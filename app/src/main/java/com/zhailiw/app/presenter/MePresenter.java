package com.zhailiw.app.presenter;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.zhailiw.app.Const;
import com.zhailiw.app.common.NToast;
import com.zhailiw.app.common.PhotoUtils;
import com.zhailiw.app.listener.AlertDialogCallBack;
import com.zhailiw.app.loader.GlideImageLoader;
import com.zhailiw.app.server.HttpException;
import com.zhailiw.app.server.async.OnDataListener;
import com.zhailiw.app.server.broadcast.BroadcastManager;
import com.zhailiw.app.server.response.CommonResponse;
import com.zhailiw.app.server.response.UserInfoResponse;
import com.zhailiw.app.view.activity.MeActivity;
import com.zhailiw.app.widget.BottomMenuDialog;
import com.zhailiw.app.widget.LoadDialog;
import com.zhailiw.app.widget.SelectableRoundedImageView;
import com.zhailiw.app.widget.DialogWithYesOrNoUtils;

import java.io.File;


/**
 * Created by hmxbanz on 2017/4/5.
 */
public class MePresenter extends BasePresenter implements OnDataListener{
    private static final int UPLOADAVATOR = 1;
    private static final int GETINFO = 3;
    public static final String UPDATENICKNAME = "updateNickName";
    MeActivity mActivity;
    private TextView nickName, txtBirthday;
    private SelectableRoundedImageView avator;
    private GlideImageLoader glideImageLoader;
    private BottomMenuDialog dialog;
    private PhotoUtils photoUtils;
    private Uri selectUri;
    public static final int REQUEST_CODE_ASK_PERMISSIONS = 101;
    private File selectedFile;

    public MePresenter(Context context) {
        super(context);
        this.mActivity = (MeActivity) context;
        glideImageLoader = new GlideImageLoader();
        setPortraitChangeListener();
    }

    public void init(SelectableRoundedImageView selectableRoundedImageView, TextView nickName, TextView txtBirthday) {
        this.avator = selectableRoundedImageView;
        this.nickName = nickName;
        this.txtBirthday =txtBirthday;
        //mView.initData();
        LoadDialog.show(context);
        atm.request(GETINFO, this);

        BroadcastManager.getInstance(context).addAction(MePresenter.UPDATENICKNAME, new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String command = intent.getAction();
                String s=intent.getStringExtra("String");
                if (!TextUtils.isEmpty(command)) {
                    MePresenter.this.nickName.setText(s);
                }
            }
        });
    }

    @Override
    public Object doInBackground(int requestCode, String parameter) throws HttpException {
        switch (requestCode) {
            case UPLOADAVATOR:
                return userAction.uploadAvatar(selectedFile);
            case GETINFO:
                return userAction.getInfo();
        }
        return null;
    }

    @Override
    public void onSuccess(int requestCode, Object result) {
        LoadDialog.dismiss(context);
        switch (requestCode) {
            case UPLOADAVATOR:
                if(result != null){
                    CommonResponse commonResponse= (CommonResponse) result;
                    if (commonResponse.getState() == Const.SUCCESS) {
                        atm.request(GETINFO,this);
                    }
                    NToast.showToast(context,commonResponse.getMsg(), Toast.LENGTH_LONG);
                }
                break;
            case GETINFO:
                UserInfoResponse userInfoResponse = (UserInfoResponse) result;
                if (userInfoResponse.getState() == Const.SUCCESS) {
                    UserInfoResponse.DataBean entity = userInfoResponse.getData();
                    //glideImageLoader.displayImage(context, XtdConst.IMGURI+entity.getImg_path(), this.avator);
//                    Random random = new Random();
//                    int max=20;
//                    int min=10;
//                    int s = random.nextInt(max)%(max-min+1) + min;
                    Glide.with(context).load(Const.IMGURI+entity.getPhotoSmall()).skipMemoryCache(true).diskCacheStrategy( DiskCacheStrategy.NONE ).into(this.avator);

                    this.nickName.setText(entity.getNickName());
                    this.txtBirthday.setText(entity.getRoleName());
                }
                NToast.shortToast(context, userInfoResponse.getMsg());
                break;


        }

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PhotoUtils.INTENT_CROP:
            case PhotoUtils.INTENT_TAKE:
            case PhotoUtils.INTENT_SELECT:
                photoUtils.onActivityResult(mActivity, requestCode, resultCode, data);
                break;
        }
    }

    private void setPortraitChangeListener() {
        photoUtils = new PhotoUtils(new PhotoUtils.OnPhotoResultListener() {
            @Override
            public void onPhotoResult(Uri uri,File file) {
                if (uri != null && !TextUtils.isEmpty(uri.getPath())) {
                    selectUri = uri;
                    selectedFile = file;
                    //String path= FileUtils.getFilePathFromContentUri(uri,mActivity.getContentResolver());
                    //File f = new File(path);

                    //待3秒上传，确保刚才裁剪的照片保存完毕
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    LoadDialog.show(context);
                    atm.request(UPLOADAVATOR,MePresenter.this);
                }
            }

            @Override
            public void onPhotoCancel() {

            }
        });
    }

    /**
     * 弹出底部框
     */
    @TargetApi(23)
    public void showPhotoDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }

        dialog = new BottomMenuDialog(context);

        dialog.setConfirmListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
                photoUtils.takePicture(mActivity);
            }
        });
        dialog.setMiddleListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
                photoUtils.selectPicture(mActivity);
            }
        });

        //请求权限
        if (Build.VERSION.SDK_INT >= 23) {
            int checkPermission = context.checkSelfPermission(Manifest.permission.CAMERA);
            int checkReadPermission = context.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);

            if (checkPermission != PackageManager.PERMISSION_GRANTED
                    ||
                    checkReadPermission != PackageManager.PERMISSION_GRANTED ) {
                if (mActivity.shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)
                        ||
                        mActivity.shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    mActivity.requestPermissions(new String[] {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_ASK_PERMISSIONS);
                } else {
                    DialogWithYesOrNoUtils.getInstance().showDialog(context, "您需要打开相机权限", new AlertDialogCallBack() {
                        @Override
                        public void executeEvent() {
                            mActivity.requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_ASK_PERMISSIONS);
                        }

                        @Override
                        public void onCancle() {

                        }

                    });

                }
                return;
            }
            else {
                dialog.show();
            }
        }
        else
        {
            dialog.show();
        }
    }


    public void onRequestPermissionsResult() {
        dialog.show();
    }
}