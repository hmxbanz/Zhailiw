package com.zhailiw.app.view.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhailiw.app.R;
import com.zhailiw.app.common.NLog;
import com.zhailiw.app.common.PhotoUtils;
import com.zhailiw.app.presenter.MineFragmentPresenter;
import com.zhailiw.app.view.activity.AddressActivity;
import com.zhailiw.app.view.activity.MainActivity;
import com.zhailiw.app.view.activity.MeActivity;
import com.zhailiw.app.widget.SelectableRoundedImageView;
import com.zhailiw.app.widget.progressBar.MaterialProgressBar;


/**
 * Created by AMing on 16/6/21.
 * Company RongCloud
 */
public class MineFragment extends Fragment implements View.OnClickListener {
    private static final int COMPAREVERSION = 54;
    public static final String SHOWRED = "SHOWRED";
    public static MineFragment mFragment = null;
    private RelativeLayout mLayoutAr, mLayoutMyVideo, mLayoutMsg;
    private LinearLayout mLayoutTitle, mLayoutFavor, mLayoutSetting, mLayoutComment,mLayoutShopCar, layoutAddress,mLayoutScore;
    private View view;

    private SelectableRoundedImageView mImageView;
    private ImageView mImageSetting;
    private PhotoUtils photoUtils;
    public static final int REQUEST_CODE_ASK_PERMISSIONS = 101;
    private Uri selectUri;
    private MaterialProgressBar progressBar;
    private LinearLayout mTxtMe;
    private RelativeLayout layout_back;
    private TextView title;
    private MineFragmentPresenter presenter;
    private TextView nickName;

    public static MineFragment getInstance() {
        if (mFragment == null) {
            mFragment = new MineFragment();
        }
        return mFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mine, null);
        initViews();
        presenter = new MineFragmentPresenter(getActivity());
        presenter.init(mImageView,nickName);
        //initData();
//        compareVersion();
        NLog.d("fragment-----","onCreateView");
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        //presenter.getInfo();
        NLog.d("fragment-----","onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        NLog.d("fragment-----","onResume");
    }

    private void initViews() {
        ((TextView)view.findViewById(R.id.txt_title)).setText("我的");
        view.findViewById(R.id.layout_back).setVisibility(View.INVISIBLE);
        mImageView = (SelectableRoundedImageView) view.findViewById(R.id.img_avator);
        mImageView.setOnClickListener(this);
        nickName= view.findViewById(R.id.nick_name);

        mLayoutAr =  view.findViewById(R.id.layout_dynamic);
        mLayoutAr.setOnClickListener(this);
        mLayoutMsg =  view.findViewById(R.id.layout_message);
        mLayoutMsg.setOnClickListener(this);
        mLayoutMyVideo =  view.findViewById(R.id.layout_my_video);
        mLayoutMyVideo.setOnClickListener(this);

        mLayoutSetting =  view.findViewById(R.id.layout_setting);
        mLayoutSetting.setOnClickListener(this);

        mLayoutShopCar =  view.findViewById(R.id.layout_shop_car);
        mLayoutShopCar.setOnClickListener(this);
        layoutAddress =  view.findViewById(R.id.layout_address);
        layoutAddress.setOnClickListener(this);
        mLayoutTitle =  view.findViewById(R.id.layout_title);
        mLayoutTitle.setOnClickListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //BroadcastManager.getInstance(getActivity()).destroy(MineFragmentPresenter.UPDATEUNREAD);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.layout_dynamic:
                startActivity(new Intent(getActivity(), MainActivity.class));
                break;
            case R.id.layout_title:
                startActivity(new Intent(getActivity(), MeActivity.class));
                break;
            case R.id.layout_message:
                startActivity(new Intent(getActivity(), MainActivity.class));
                break;
            case R.id.layout_my_video:
                startActivity(new Intent(getActivity(), MainActivity.class));
                break;
            case R.id.layout_setting:
                startActivity(new Intent(getActivity(), MainActivity.class));
                break;
            case R.id.layout_address:
                startActivity(new Intent(getActivity(), AddressActivity.class));
                break;
        }
    }



}
