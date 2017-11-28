package com.jzg.erp.presenter;

import com.jzg.erp.app.JzgApp;
import com.jzg.erp.global.Constant;
import com.jzg.erp.model.City;
import com.jzg.erp.view.IAreaCity;

import java.util.HashMap;
import java.util.Map;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 获取省市数据
 * @author zealjiang
 * @time 2016/6/28 14:03
 */
public class AreaCityPresenter extends BasePresenter<IAreaCity> {
    public AreaCityPresenter(IAreaCity from) {
        super(from);
    }

    private Subscription mSubscription;

    /**
     * 获取市数据
     * @author zealjiang
     * @time 2016/6/28 14:18
     */
    public void getCity(final String baseUrl,final String provId,final String sign) {
        Map<String, String> params = new HashMap<>();
        params.put("sign", sign);
        params.put("ProvId", provId);
        baseView.showDialog();
        mSubscription = JzgApp.getAppContext().initOtherServer(baseUrl).getCity(params).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(new Subscriber<City>() {
                    @Override
                    public void onCompleted() {
                        baseView.dismissDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        baseView.dismissDialog();
                        if (e != null && baseView != null) {
                            baseView.showError(Constant.ERROR_FORNET);
                        }
                    }

                    @Override
                    public void onNext(City data) {
                        //这个接口的100表示成功
                        int status = data.getStatus();
                        if(status==100){
                            baseView.succeedCity(data.getContent());
                        }else{
                            baseView.showError(data.getMsg());
                        }
                    }
                });
    }
}
