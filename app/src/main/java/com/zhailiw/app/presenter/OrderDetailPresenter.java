package com.zhailiw.app.presenter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.mob.wrappers.PaySDKWrapper;
import com.zhailiw.app.Adapter.OrderDetailAdapter;
import com.zhailiw.app.Adapter.ShopCarAdapter;
import com.zhailiw.app.Const;
import com.zhailiw.app.R;
import com.zhailiw.app.server.HttpException;
import com.zhailiw.app.server.response.DefaultAddressResponse;
import com.zhailiw.app.server.response.OrderDetailResponse;
import com.zhailiw.app.server.response.ShopCarResponse;
import com.zhailiw.app.view.activity.OrderDetailActivity;
import com.zhailiw.app.widget.LoadDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hmxbanz on 2017/4/5.
 */

public class OrderDetailPresenter extends BasePresenter  {
    private static final String TAG = FavorPresenter.class.getSimpleName();
    private final OrderDetailAdapter dataAdapter;
    private final List<OrderDetailResponse.DataBean.OrderListBean> list=new ArrayList<OrderDetailResponse.DataBean.OrderListBean>();
    private OrderDetailActivity activity;
    private final int GETORDERDETAIL=1,GETDEFAULTADDRESS=2;
    private String orderId;
    private TextView txtContact,txtAddress,txtOrderNo,txtOrderTotal;
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;

    public OrderDetailPresenter(Context context){
        super(context);
        activity = (OrderDetailActivity) context;
        dataAdapter = new OrderDetailAdapter(this.context);
        dataAdapter.setListItems(list);
        Intent intent = activity.getIntent();
        orderId=intent.getStringExtra("orderId");
    }

    public void init() {
        this.txtContact = activity.findViewById(R.id.txt_contact);
        this.txtAddress = activity.findViewById(R.id.txt_address);
        this.txtOrderNo = activity.findViewById(R.id.txt_order_no);
        this.txtOrderTotal = activity.findViewById(R.id.txt_order_total);
        this.recyclerView = activity.findViewById(R.id.recyclerView);
        this.recyclerView.setAdapter(dataAdapter);
        this.recyclerView.setNestedScrollingEnabled(false);
        gridLayoutManager=new GridLayoutManager(context,1);
        this.recyclerView.setLayoutManager(gridLayoutManager);
        atm.request(GETORDERDETAIL,this);
    }

    @Override
    public Object doInBackground(int requestCode, String parameter) throws HttpException {
        switch (requestCode) {
            case GETORDERDETAIL:
                return userAction.getOrderDetail(orderId);
            case GETDEFAULTADDRESS:
                return userAction.getDefaultAddress();
        }
        return null;
    }

    @Override
    public void onSuccess(int requestCode, Object result) {
        LoadDialog.dismiss(context);
        if (result == null)     return;
        switch (requestCode) {
            case GETORDERDETAIL:
                OrderDetailResponse orderDetailResponse = (OrderDetailResponse) result;
                if (orderDetailResponse.getState() == Const.SUCCESS) {
                    this.txtOrderNo.setText("订单号："+orderDetailResponse.getData().getOrderNo());
                    this.txtOrderTotal.setText("订单总价："+orderDetailResponse.getData().getTotal()+"元");
                    list.addAll(orderDetailResponse.getData().getOrderList());
                    dataAdapter.notifyDataSetChanged();
                    atm.request(GETDEFAULTADDRESS,this);
                }
                //NToast.shortToast(context, commonResponse.getMsg());
                break;
            case GETDEFAULTADDRESS:
                DefaultAddressResponse defaultResponse = (DefaultAddressResponse) result;
                //NToast.shortToast(context, commonResponse.getMsg());
                this.txtContact.setText("联系人："+defaultResponse.getDefaultAddress().getContact()+" 手机："+defaultResponse.getDefaultAddress().getCellphone());
                this.txtAddress.setText("送货地址："+defaultResponse.getDefaultAddress().getAddress());
                break;
                }
        }
    }

