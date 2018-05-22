package com.zhailiw.app.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhailiw.app.R;
import com.zhailiw.app.presenter.GalleryFragmentPresenter;

/**
 * Created by AMing on 16/6/21.
 * Company RongCloud
 */
public class DoneFragment extends Fragment implements View.OnClickListener  {
    private View view;
    public static DoneFragment instance = null;
    private GalleryFragmentPresenter presenter;

    public static DoneFragment getInstance() {
        if (instance == null) {
            instance = new DoneFragment();
        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_favor, null);
        initViews();
//        initData();
//        presenter = new GalleryFragmentPresenter(getContext());
//        presenter.init(recycleView,swiper);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void initViews() {
//        recycleView=  view.findViewById(R.id.recyclerView);
//        swiper=  view.findViewById(R.id.swiper);
    }

    @Override
    public void onClick(View v) {
      presenter.onMeClick(v);
    }
}
