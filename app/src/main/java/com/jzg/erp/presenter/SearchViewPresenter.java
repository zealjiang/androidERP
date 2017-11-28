package com.jzg.erp.presenter;

import com.jzg.erp.app.JzgApp;
import com.jzg.erp.model.CarSourceData;
import com.jzg.erp.utils.NetworkExceptionUtils;
import com.jzg.erp.view.IsearchView;

import java.util.Map;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * author: guochen
 * date: 2016/6/24 16:57
 * email: 
 */
public class SearchViewPresenter extends BasePresenter<IsearchView>{

    public SearchViewPresenter(IsearchView from) {
        super(from);
    }

    public void loadData(Map<String, String> params) {
        baseView.showDialog();
        JzgApp.getApiServer().loadSearchDatas(params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe(new Observer<CarSourceData>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e != null && baseView != null) {
                            baseView.dismissDialog();
                          String error =   NetworkExceptionUtils.getErrorByException(e);
                            baseView.showError(error);
                        }
                    }

                    @Override
                    public void onNext(CarSourceData carSourceData) {
                        baseView.dismissDialog();

                        baseView.SearchSuccess(carSourceData);

                    }
                });
    }

    public void loadMoreData(Map<String, String> params) {
        baseView.showDialog();
        JzgApp.getApiServer().loadSearchDatas(params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe(new Observer<CarSourceData>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e != null && baseView != null) {
                            baseView.dismissDialog();
                            String error =   NetworkExceptionUtils.getErrorByException(e);
                            baseView.showError(error);
                        }
                    }

                    @Override
                    public void onNext(CarSourceData carSourceData) {
                        baseView.dismissDialog();
                        baseView.searchMoreSuccess(carSourceData);

                    }
                });
    }
}
