package com.jzg.erp.presenter;

import com.jzg.erp.app.JzgApp;
import com.jzg.erp.base.BaseObject;
import com.jzg.erp.utils.NetworkExceptionUtils;
import com.jzg.erp.view.IAddFollowup;

import java.util.Map;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author: voiceofnet
 * email: pengkun@jingzhengu.com
 * phone:18101032717
 * @time: 2016/6/30 15:46
 * @desc:
 */
public class AddFollowUpPresenter extends BasePresenter<IAddFollowup> {
    public AddFollowUpPresenter(IAddFollowup from) {
        super(from);
    }

    /***
     * 添加跟进记录
     * @param params
     */
    public void addFollowUpLog(Map<String,String> params){
        baseView.showDialog();
        JzgApp.getApiServer().addFollowUpLog(params).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(new Observer<BaseObject>() {
            @Override
            public void onCompleted() {
                baseView.dismissDialog();
            }

            @Override
            public void onError(Throwable e) {
                if (e != null && baseView != null) {
                    String error = NetworkExceptionUtils.getErrorByException(e);
                    baseView.dismissDialog();
                    baseView.showError(error);
                }
            }

            @Override
            public void onNext(BaseObject baseObject) {
                int status = baseObject.getStatus();
                if(status==1){
                    baseView.succeed();
                }else{
                    baseView.showError(baseObject.getMessage());
                }
            }
        });
    }
}
