package com.zhailiw.app.presenter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.zhailiw.app.Adapter.AddressAdapter;
import com.zhailiw.app.Const;
import com.zhailiw.app.common.NToast;
import com.zhailiw.app.server.HttpException;
import com.zhailiw.app.server.async.OnDataListener;
import com.zhailiw.app.server.response.AddressResponse;
import com.zhailiw.app.view.activity.AddressActivity;
import com.zhailiw.app.widget.LoadDialog;

import java.util.ArrayList;
import java.util.List;

public class AddressPresenter extends BasePresenter implements OnDataListener, AddressAdapter.ItemClickListener {
    private final AddressActivity activity;
    private final AddressAdapter dataAdapter;
    private final List<AddressResponse.DataBean> list=new ArrayList<>();
    private static final int GETADDRESS=1;
    private GridLayoutManager gridLayoutManager;
    private RecyclerView recyclerView;

    public AddressPresenter(Context context){
        super(context);
        activity = (AddressActivity) context;
        dataAdapter = new AddressAdapter(this.context);
        dataAdapter.setListItems(list);
        dataAdapter.setOnItemClickListener(this);
    }

    public void init(RecyclerView recyclerView) {
        LoadDialog.show(context);
        atm.request(GETADDRESS,this);

        this.recyclerView=recyclerView;
        this.recyclerView.setAdapter(dataAdapter);
        this.recyclerView.setNestedScrollingEnabled(false);
        gridLayoutManager=new GridLayoutManager(context,1);
        this.recyclerView.setLayoutManager(gridLayoutManager);
    }

    @Override
    public void onItemClick(int position, AddressResponse.DataBean item) {

    }

    @Override
    public Object doInBackground(int requestCode, String parameter) throws HttpException {
        switch (requestCode) {
            case GETADDRESS :
                return userAction.getAddress();
        }
        return null;
    }

    @Override
    public void onSuccess(int requestCode, Object result) {
        LoadDialog.dismiss(context);
        if (result==null)return;
        switch (requestCode) {
            case GETADDRESS:
                AddressResponse addressResponse = (AddressResponse) result;
                if (addressResponse.getState() == Const.SUCCESS) {
                    if (addressResponse.getData().size() == 0) {
                    }
                    else {
                        list.clear();
                        list.addAll(addressResponse.getData());
                        dataAdapter.notifyDataSetChanged();
                    }
                }else {
                    NToast.shortToast(context, addressResponse.getMsg());
                }
                break;
        }
    }
}