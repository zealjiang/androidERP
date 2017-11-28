package com.jzg.erp.presenter;

import com.jzg.erp.app.JzgApp;
import com.jzg.erp.global.Constant;
import com.jzg.erp.model.CustomerDetail;
import com.jzg.erp.model.CustomerInfo;
import com.jzg.erp.utils.LogUtil;
import com.jzg.erp.utils.MyToast;
import com.jzg.erp.utils.NetworkExceptionUtils;
import com.jzg.erp.utils.UIUtils;
import com.jzg.erp.view.ISearch;
import com.orhanobut.logger.Logger;

import java.util.HashMap;
import java.util.Map;

import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
*
* 搜索界面的控制层
 * SearchPresenter
* @author sunbl
* created at 2016/6/24 10:52
*/
public class SearchPresenter extends BasePresenter<ISearch> {
    public SearchPresenter(ISearch from) {
        super(from);
    }


    public void search(final String key, final int pageIndex) {
        Map<String, String> params = new HashMap<>();
        params.put("op", "getCustomerList");
        params.put("userId", JzgApp.getUser().getUserID()+"");
        params.put("pageIndex", pageIndex+"");
        params.put("pageCount", Constant.PAGECOUNT+"");
        params.put("customerStatus", "0");
        params.put("key", key);
        LogUtil.e(TAG, UIUtils.getUrl(params));
        JzgApp.getApiServer().getCustomerList(params).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(new Subscriber<CustomerInfo>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e != null && baseView != null) {
                            if(pageIndex==1){
                                baseView.showError(Constant.ERROR_FORNET);
                            }else{
                                baseView.searcheSucceed(null,false);
                            }

                        }
                    }

                    @Override
                    public void onNext(CustomerInfo successData) {
                        if(successData.getStatus()==Constant.SUCCESS){
                            if(pageIndex==1){
                                baseView.searcheSucceed(successData.getCustomerList(),true);
                            }else{
                                baseView.searcheSucceed(successData.getCustomerList(),false);
                            }
                        }else{
                            MyToast.showShort(successData.getMessage());
                        }


                    }
                });
    }
    public void getCustomerDetail(Map<String, String> params) {
        JzgApp.getApiServer().getCustomerDetail(params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe(new Observer<CustomerDetail>() {
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
                    public void onNext(CustomerDetail customerDetail) {
                        if (customerDetail != null&&customerDetail.getStatus()==Constant.SUCCESS){
                            baseView.showCustomerDetail(customerDetail);
                        }else {
                            MyToast.showShort(customerDetail.getMessage());
                        }

                    }
                });
    }
}
