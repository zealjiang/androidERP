package com.jzg.erp.presenter;

import com.jzg.erp.app.JzgApp;
import com.jzg.erp.base.BaseObject;
import com.jzg.erp.utils.LogUtil;
import com.jzg.erp.utils.MD5Utils;
import com.jzg.erp.utils.NetworkExceptionUtils;
import com.jzg.erp.view.ISucceedWithoutNoResult;

import java.util.HashMap;
import java.util.Map;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by wangyd on 16/6/28.
 * 客户信息详情P层
 */
public class CustomerDetailPresenter extends BasePresenter<ISucceedWithoutNoResult> {


    public CustomerDetailPresenter(ISucceedWithoutNoResult from) {
        super(from);
    }


    /***
     *  Op 操作类型 固定传入insertContractRecord
        CustomerID : 客户Id
        CustomerName ：客户名称
        SalesId : 销售Id
        SalesName : 销售名称
        TalkingTime : 通话时长
     */
    public void uploadDailRecord(String CustomerID,String CustomerName) {
        Map<String, String> params = new HashMap<>();
        params.put("Op","insertContractRecord");
        params.put("CustomerID",CustomerID);
        params.put("CustomerName",CustomerName);
        params.put("SalesId",String.valueOf(JzgApp.getUser().getUserID()));
        params.put("SalesName",JzgApp.getUser().getTrueName());
        params.put("TalkingTime","0");
        Map<String,Object> map = new HashMap<>();
        map.putAll(params);
        String sign = MD5Utils.getMD5Sign(map);
        params.put("sign",sign);
        JzgApp.getApiServer().insertDialRecord(params).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(new Observer<BaseObject>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if (e != null && baseView != null) {
                    String error = NetworkExceptionUtils.getErrorByException(e);
                    LogUtil.e(TAG,"error:"+error);
                }
            }

            @Override
            public void onNext(BaseObject baseObject) {
                int status = baseObject.getStatus();
                if(status==1){
                    baseView.onSucceed();
                }
            }
        });
    }

}
