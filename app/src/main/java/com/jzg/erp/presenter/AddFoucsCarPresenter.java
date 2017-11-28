package com.jzg.erp.presenter;

import com.jzg.erp.app.JzgApp;
import com.jzg.erp.global.Constant;
import com.jzg.erp.model.CarSourceData;
import com.jzg.erp.utils.NetworkExceptionUtils;
import com.jzg.erp.view.IAddFoucsCar;

import java.util.Map;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by JZG on 2016/7/1.
 */
public class AddFoucsCarPresenter extends BasePresenter<IAddFoucsCar> {
    public AddFoucsCarPresenter(IAddFoucsCar from) {
        super(from);
    }

    public void getFocusCarList(Map<String, String> params, final int pageIndex) {
        JzgApp.getApiServer().getFocusCarList(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<CarSourceData>() {
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
                    public void onNext(CarSourceData carSourceData) {
                        if (carSourceData != null)
                            baseView.showFocusCar(carSourceData, pageIndex);
                    }
                });
    }

    public void submit(Map<String, String> params) {
        JzgApp.getApiServer().getFocusCarList(params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe(new Observer<CarSourceData>() {
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
                    public void onNext(CarSourceData carSourceData) {
                        if (carSourceData.getStatus() == Constant.SUCCESS) {
                            baseView.submitFocusCar(carSourceData);
                        } else {
                            baseView.showError(carSourceData.getMessage());
                        }

                    }
                });
    }
}
