package com.jzg.erp.presenter;

import com.jzg.erp.app.JzgApp;
import com.jzg.erp.global.Constant;
import com.jzg.erp.model.CustomerDetail;
import com.jzg.erp.model.CustomerInfo;
import com.jzg.erp.utils.MyToast;
import com.jzg.erp.utils.NetworkExceptionUtils;
import com.jzg.erp.view.ICustomer;

import java.util.HashMap;
import java.util.Map;

import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 客户界面的控制层
 * CustomerPresenter
 *
 * @author sunbl
 *         created at 2016/6/24 10:52
 */
public class CustomerPresenter extends BasePresenter<ICustomer> {
    public CustomerPresenter(ICustomer from) {
        super(from);
    }

    public void getCustomerInfo(final String status,final int pageIndex) {
        String customerStatus = "";
        switch (status) {
            case "意向":
                customerStatus = "1";
                break;
            case "预订":
                customerStatus = "2";
                break;
            case "成交":
                customerStatus = "3";
                break;
            case "战败":
                customerStatus = "4";
                break;
            default:
                customerStatus = "5";
                break;
        }
        Map<String, String> params = new HashMap<>();
        params.put("op", "getCustomerList");
        params.put("userId", JzgApp.getUser().getUserID()+"");
        params.put("pageIndex", pageIndex+"");
        params.put("pageCount", Constant.PAGECOUNT+"");
        params.put("customerStatus", customerStatus);
        params.put("key", "");
        JzgApp.getApiServer().getCustomerList(params).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(new Subscriber<CustomerInfo>() {
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
            public void onNext(CustomerInfo successData) {
                baseView.dismissDialog();
                if (successData.getStatus() == Constant.SUCCESS) {
                    baseView.succeedResult(successData,pageIndex);
                } else {
                    MyToast.showShort(successData.getMessage());
                }
            }
        });
    }

    public void getCustomerDetail(Map<String, String> params) {
        baseView.showDialog();
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
