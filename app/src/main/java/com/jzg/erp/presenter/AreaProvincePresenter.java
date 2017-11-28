package com.jzg.erp.presenter;

import com.jzg.erp.app.JzgApp;
import com.jzg.erp.global.Constant;
import com.jzg.erp.model.Province;
import com.jzg.erp.view.IAreaProvince;

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
public class AreaProvincePresenter extends BasePresenter<IAreaProvince> {
    public AreaProvincePresenter(IAreaProvince from) {
        super(from);
    }

    private Subscription mSubscription;

    /**
     * 获取省数据
     * @author zealjiang
     * @time 2016/6/28 14:18
     */
    public void getProvince(final String baseUrl,final String sign) {
        Map<String, String> params = new HashMap<>();
        params.put("sign", sign);
        baseView.showDialog();
        mSubscription = JzgApp.getAppContext().initOtherServer(baseUrl).getProvince(params).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(new Subscriber<Province>() {
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
                    public void onNext(Province data) {
                        //这个接口的100表示成功
                        int status = data.getStatus();
                        if(status==100){
                            baseView.succeed(data.getContent());
                        }else{
                            baseView.showError(data.getMsg());
                        }
                    }
                });
    }
}
