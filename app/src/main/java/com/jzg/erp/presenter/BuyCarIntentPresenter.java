package com.jzg.erp.presenter;

import com.jzg.erp.app.JzgApp;
import com.jzg.erp.model.BuyCarIntent;
import com.jzg.erp.utils.NetworkExceptionUtils;
import com.jzg.erp.view.IBuyCarIntent;
import com.orhanobut.logger.Logger;

import java.util.Map;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by wangyd on 16/6/28.
 * 修改购车意向P层
 */
public class BuyCarIntentPresenter extends BasePresenter<IBuyCarIntent> {

    public BuyCarIntentPresenter(IBuyCarIntent from) {
        super(from);
    }

    public void modifyBuyCarIntent(Map<String, String> params) {
        baseView.showDialog();
        JzgApp.getApiServer().modifyBuyCarIntent(params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe(new Observer<BuyCarIntent>() {
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
                    public void onNext(BuyCarIntent buyCarIntent) {
                        baseView.modifyBuyCarIntent(buyCarIntent);
                    }
                });
    }
}
