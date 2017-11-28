package com.jzg.erp.presenter;

import com.jzg.erp.app.JzgApp;
import com.jzg.erp.base.BaseObject;
import com.jzg.erp.utils.NetworkExceptionUtils;
import com.jzg.erp.view.ISucceedWithoutNoResult;

import java.util.Map;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author: voiceofnet
 * email: pengkun@jingzhengu.com
 * phone:18101032717
 * @time: 2016/7/4 17:31
 * @desc:
 */
public class TaskStatusPresenter extends BasePresenter<ISucceedWithoutNoResult> {
    public TaskStatusPresenter(ISucceedWithoutNoResult from) {
        super(from);
    }

    public void changeTaskItemStatus(Map<String,String> params){
        JzgApp.getApiServer().changeTaskItemStatus(params).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(new Observer<BaseObject>() {

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
            public void onNext(BaseObject wrapper) {
                int status = wrapper.getStatus();
                if(status==1){
                    baseView.onSucceed();
                }else{
                    baseView.showError(wrapper.getMessage());
                }
            }
        });
    }
}
