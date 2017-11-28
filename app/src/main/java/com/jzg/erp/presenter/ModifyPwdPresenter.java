package com.jzg.erp.presenter;

import com.jzg.erp.app.JzgApp;
import com.jzg.erp.base.BaseObject;
import com.jzg.erp.global.Constant;
import com.jzg.erp.utils.MD5Utils;
import com.jzg.erp.view.IModifyPwd;

import java.util.HashMap;
import java.util.Map;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 修改密码
 * @author zealjiang
 * @time 2016/6/28 14:34
 */
public class ModifyPwdPresenter extends BasePresenter<IModifyPwd> {
    public ModifyPwdPresenter(IModifyPwd from) {
        super(from);
    }

    private Subscription mSubscription;

    public void modifyPwd(final String userid,final String oldpwd,final String newPwd) {
        Map<String, String> params = new HashMap<>();
        params.put("op", "modifypwd");
        params.put("userid", userid);
        params.put("oldpwd", oldpwd);
        params.put("pwd", newPwd);
        //加sign
        Map<String, Object> signMap = new HashMap<>();
        signMap.putAll(params);
        params.put("sign", MD5Utils.getMD5Sign(signMap));
        baseView.showDialog();
        mSubscription = JzgApp.getApiServer().changePwd(params).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(new Subscriber<BaseObject>() {
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
                    public void onNext(BaseObject data) {

                        int status = data.getStatus();
                        if(status==1){
                            baseView.modifyOK();
                        }else{
                            baseView.showError(data.getMessage());
                        }
                    }
                });
    }
}
