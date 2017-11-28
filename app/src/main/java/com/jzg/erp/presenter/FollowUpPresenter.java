package com.jzg.erp.presenter;

import com.jzg.erp.app.JzgApp;
import com.jzg.erp.model.TaskItemGroupWrapper;
import com.jzg.erp.utils.NetworkExceptionUtils;
import com.jzg.erp.view.IGetFollowUp;

import java.util.Map;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author: voiceofnet
 * email: pengkun@jingzhengu.com
 * phone:18101032717
 * @time: 2016/6/30 17:38
 * @desc:
 */
public class FollowUpPresenter extends BasePresenter<IGetFollowUp> {
    public FollowUpPresenter(IGetFollowUp from) {
        super(from);
    }

    /***
     * 获取跟进记录
     * @param params
     */
    public void loadDataRec(Map<String,String> params){
        JzgApp.getApiServer().getFollowUpList(params).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(new Observer<TaskItemGroupWrapper>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if (e != null && baseView != null) {
                    String error = NetworkExceptionUtils.getErrorByException(e);
                    baseView.onError(error);
                }
            }

            @Override
            public void onNext(TaskItemGroupWrapper wrapper) {
                int status = wrapper.getStatus();
                if(status==1){
                    baseView.onSucceed(wrapper.getData());
                }else{
                    baseView.onError(wrapper.getMessage());
                }
            }
        });
    }

    /***
     * 获取跟进事项
     * @param params
     */
    public void loadDataItem(Map<String,String> params){
        JzgApp.getApiServer().getFollowUpItemList(params).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(new Observer<TaskItemGroupWrapper>() {
            @Override
            public void onCompleted() {}

            @Override
            public void onError(Throwable e) {
                if (e != null && baseView != null) {
                    String error = NetworkExceptionUtils.getErrorByException(e);
                    baseView.onError(error);
                }
            }

            @Override
            public void onNext(TaskItemGroupWrapper wrapper) {
                int status = wrapper.getStatus();
                if(status==1){
                    baseView.onSucceed(wrapper.getData());
                }else{
                    baseView.onError(wrapper.getMessage());
                }
            }
        });
    }


}
