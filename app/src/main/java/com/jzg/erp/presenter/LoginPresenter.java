package com.jzg.erp.presenter;

import com.jzg.erp.app.JzgApp;
import com.jzg.erp.model.UserWrapper;
import com.jzg.erp.utils.LogUtil;
import com.jzg.erp.utils.MD5Utils;
import com.jzg.erp.utils.NetworkExceptionUtils;
import com.jzg.erp.utils.UIUtils;
import com.jzg.erp.view.ILogin;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author: voiceofnet
 * email: pengkun@jingzhengu.com
 * phone:18101032717
 * @time: 2016/6/22 16:07
 * @desc:
 */
public class LoginPresenter extends BasePresenter<ILogin> {
    public LoginPresenter(ILogin from) {
        super(from);
    }
    public void login(final String phone,final String password,final boolean isShowDialog){
        Map<String,String> params = new HashMap<>();
        params.put("Phone",phone);
        params.put("Pwd",password);
        params.put("Op","login");
        params = MD5Utils.encryptParams(params);
        LogUtil.e(TAG,UIUtils.getUrl(params));
        if(isShowDialog){
            baseView.showDialog();
        }

        ((Observable<UserWrapper>)JzgApp.getApiServer().login(params)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<UserWrapper>() {
            @Override
            public void onCompleted() {
                if(isShowDialog) {
                    baseView.dismissDialog();
                }
            }

            @Override
            public void onError(Throwable e) {
                if(isShowDialog) {
                    baseView.dismissDialog();
                }
                String error =  NetworkExceptionUtils.getErrorByException(e);
                baseView.showError(error);

            }

            @Override
            public void onNext(UserWrapper userWrapper) {
                int status = userWrapper.getStatus();
                if(status==1){
                    baseView.loginSucceed(userWrapper.getData());
                }else{
                    baseView.showError(userWrapper.getMessage());
                }
            }
        });
    }
}
