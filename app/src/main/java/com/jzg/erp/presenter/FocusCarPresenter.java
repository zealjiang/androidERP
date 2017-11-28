package com.jzg.erp.presenter;

import com.jzg.erp.app.JzgApp;
import com.jzg.erp.model.CarSourceData;
import com.jzg.erp.model.CarSourceTagData;
import com.jzg.erp.utils.NetworkExceptionUtils;
import com.jzg.erp.view.IFocusCar;
import com.orhanobut.logger.Logger;

import java.util.Map;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by wangyd on 16/6/29.
 * 关注车列表P层
 */
public class FocusCarPresenter extends BasePresenter<IFocusCar> {

    public FocusCarPresenter(IFocusCar from) {
        super(from);
    }

    public void getFocusCarList(Map<String, String> params,final int indext) {
        JzgApp.getApiServer().getFocusCarList(params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe(new Observer<CarSourceData>() {
                    @Override
                    public void onCompleted() {
                        //Logger.d("onCompleted");
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
                    public void onNext(CarSourceData carSourceData) {
                        if (carSourceData != null)
                            baseView.showFocusCar(carSourceData,indext);
                    }
                });
    }
    public void getFocusTagCarList(Map<String, String> params) {
        JzgApp.getApiServer().getFocusTagCarList(params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe(new Observer<CarSourceTagData>() {
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
                    public void onNext(CarSourceTagData carSourceData) {
                        if (carSourceData != null)
                            baseView.showFocusCarWithTag(carSourceData);
                    }
                });
    }
}
