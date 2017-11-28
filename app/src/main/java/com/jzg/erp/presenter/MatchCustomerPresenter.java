package com.jzg.erp.presenter;/**
 * author: gcc
 * date: 2016/6/30 17:44
 * email:
 */

import com.jzg.erp.app.JzgApp;
import com.jzg.erp.model.CustomerDetail;
import com.jzg.erp.model.MatchCustomerData;
import com.jzg.erp.utils.NetworkExceptionUtils;
import com.jzg.erp.view.IMatchCustomer;

import java.util.Map;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * author: guochen
 * date: 2016/6/30 17:44
 * email: 
 */
public class MatchCustomerPresenter extends BasePresenter<IMatchCustomer>{
    public MatchCustomerPresenter(IMatchCustomer from) {
        super(from);
    }

    /**
     * 匹配
     * @param params
     */
    public void loadMatchCustomerData(Map<String, String> params) {
        baseView.showDialog();
        JzgApp.getApiServer().loadMatchCustomer(params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe(new Observer<MatchCustomerData>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e != null && baseView != null) {
                            baseView.dismissDialog();
                            baseView.showError(NetworkExceptionUtils.getErrorByException(e));
                        }
                    }

                    @Override
                    public void onNext(MatchCustomerData matchCustomerData) {
                        baseView.dismissDialog();
                        baseView.matchCustomerSuccess(matchCustomerData);
                    }
                });
    }

    public void loadClientDetails(Map<String, String> params) {
        baseView.showDialog();
        JzgApp.getApiServer().getCustomerDetail(params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe(new Observer<CustomerDetail>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                            if (e != null && baseView != null) {
                                baseView.dismissDialog();
                                baseView.showError(NetworkExceptionUtils.getErrorByException(e));
                            }
                    }

                    @Override
                    public void onNext(CustomerDetail customerDetail) {
                        baseView.dismissDialog();
                        if(customerDetail.getStatus()==0){
                            baseView.customerRedistribution(customerDetail.getMessage());
                        }else{
                            baseView.loadClientDetailSuccess(customerDetail);
                        }
                    }
                });
    }
}
