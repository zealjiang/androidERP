package com.jzg.erp.presenter;

import android.util.Log;

import com.jzg.erp.app.JzgApp;
import com.jzg.erp.model.CustomerData;
import com.jzg.erp.utils.NetworkExceptionUtils;
import com.jzg.erp.view.IChooseCustomer;
import com.orhanobut.logger.Logger;

import java.util.Map;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by JZG on 2016/6/29.
 */
public class ChooseCustomerPresenter extends BasePresenter<IChooseCustomer> {
    public ChooseCustomerPresenter(IChooseCustomer from) {
        super(from);
    }

    public void getChooseCustomer(Map<String, String> params){
        JzgApp.getApiServer().getChooseCustomer(params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe(new Observer<CustomerData>() {
                    @Override
                    public void onCompleted() {
                        Logger.d("onCompleted");
                        if (baseView != null)
                            baseView.dismissDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e != null && baseView != null) {
                            Logger.d("onError " + e.toString());
                            baseView.dismissDialog();
                            baseView.showError(NetworkExceptionUtils.getErrorByException(e));
                        }
                    }

                    @Override
                    public void onNext(CustomerData customerData) {
                        if (customerData != null)
                            Logger.d("onNext" + customerData.toString());
                        baseView.dismissDialog();
                        baseView.showCustomerResult(customerData);
                    }
                });
    }
    public void getMoreChooseCustomer(Map<String, String> params){
        JzgApp.getApiServer().getChooseCustomer(params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe(new Observer<CustomerData>() {
                    @Override
                    public void onCompleted() {
                        Logger.d("onCompleted");
                        if (baseView != null)
                            baseView.dismissDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e != null && baseView != null) {
                            Logger.d("onError " + e.toString());
                            baseView.dismissDialog();
                            baseView.showError(NetworkExceptionUtils.getErrorByException(e));
                        }
                    }

                    @Override
                    public void onNext(CustomerData customerData) {
                        if (customerData != null)
                            Logger.d("onNext" + customerData.toString());
                        baseView.dismissDialog();
                        baseView.showMoreCustomerResult(customerData);
                    }
                });
    }

    /**
     * 添加
     * @param params
     */
    public void addChooseCustomer(Map<String, String> params){
        baseView.showDialog();
        Log.i(TAG,params.toString());
        JzgApp.getApiServer().addChooseCustomer(params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe(new Observer<CustomerData>() {
                    @Override
                    public void onCompleted() {
                        if (baseView != null)
                            baseView.dismissDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e != null && baseView != null) {
                            baseView.dismissDialog();
                            baseView.showError(NetworkExceptionUtils.getErrorByException(e));
                        }
                    }

                    @Override
                    public void onNext(CustomerData customerData) {
                        baseView.dismissDialog();
                       baseView.succeedResult(customerData);
                    }
                });
    }
}
