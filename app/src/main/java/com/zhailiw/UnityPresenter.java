package com.zhailiw;

import android.content.Context;

import com.zhailiw.common.CommonTools;
import com.zhailiw.common.NLog;
import com.zhailiw.server.HttpException;
import com.zhailiw.server.async.OnDataListener;
import com.zhailiw.server.response.BindResponse;

/**
 * Created by hmxbanz on 2017/4/5.
 */

public class UnityPresenter extends BasePresenter implements OnDataListener {
    private static final int BINDQRCODE = 1;
    private UnityPlayerActivity mActivity;
    private String qrCode,phoneID;

    public UnityPresenter(Context context){
        super(context);
        mActivity = (UnityPlayerActivity) context;
    }

    public void init() {
        //LoadDialog.show(context);
    }

    @Override
    public Object doInBackground(int requestCode, String parameter) throws HttpException {
        return mUserAction.bindQRCode(this.qrCode, this.phoneID);
    }

    @Override
    public void onSuccess(int requestCode, Object result) {
        LoadDialog.dismiss(context);
        if (result == null) {
            return;
        }

        BindResponse response= (BindResponse)result;
        if (response.getCode() == 1) {
            NLog.e("response",response.getData().getExpiry_date());
        }
        else
        {
        }

    }

    //绑定二维码
    public void bindQrcode(String qrCode,String phoneID){
        String IMEI= CommonTools.getIMEI(mActivity);
        editor.putString("CellPhoneID",IMEI).commit();
        this.qrCode=qrCode;
        this.phoneID=IMEI;
        LoadDialog.show(context);
        atm.request(BINDQRCODE,this);

    }
}