package com.jzg.erp.presenter;

import com.jzg.erp.app.JzgApp;
import com.jzg.erp.model.WaitingItemWrapper;
import com.jzg.erp.utils.NetworkExceptionUtils;
import com.jzg.erp.view.IWaitingItems;

import java.util.Map;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author: voiceofnet
 * email: pengkun@jingzhengu.com
 * phone:18101032717
 * @time: 2016/7/2 19:57
 * @desc:
 */
public class WaitItemPresenter extends BasePresenter<IWaitingItems> {
    public WaitItemPresenter(IWaitingItems from) {
        super(from);
    }

    public void loadData(Map<String,String> param){
        JzgApp.getApiServer().getWaitDoneItems(param).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(new Observer<WaitingItemWrapper>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if (e != null && baseView != null) {
                    String error = NetworkExceptionUtils.getErrorByException(e);
                    baseView.showError(error);
                }
            }

            @Override
            public void onNext(WaitingItemWrapper wrapper) {
                int status = wrapper.getStatus();
                if(status==1){
                    baseView.onSucceed(wrapper);
                }else{
                    baseView.showError(wrapper.getMessage());
                }
            }
        });
    }
}
