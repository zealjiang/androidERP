package com.jzg.erp.presenter;

import android.text.TextUtils;

import com.jzg.erp.model.SMS;
import com.jzg.erp.view.IGetSMSCode;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * @author: voiceofnet
 * email: pengkun@jingzhengu.com
 * phone:18101032717
 * @time: 2016/6/22 16:23
 * @desc:
 */
public class SMSPresenter extends BasePresenter<IGetSMSCode> {
    public SMSPresenter(IGetSMSCode from) {
        super(from);
    }
    public void getSMSCode(final String phone){
        baseView.showDialog();
        Observable.timer(500, TimeUnit.MILLISECONDS).subscribeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                baseView.dismissDialog();
                if(!TextUtils.isEmpty(phone)){
                    baseView.getSMSCode(new SMS());
                }else{
                    baseView.showError("获取验证码失败");
                }
            }
        });
    }

    public void validate(final String phone, final String smsCode){
        baseView.showDialog();
        Observable.timer(500, TimeUnit.MILLISECONDS).subscribeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                baseView.dismissDialog();
                if(!TextUtils.isEmpty(phone) && !TextUtils.isEmpty(smsCode)){
                    baseView.validateOK();
                }else{
                    baseView.showError("验证码错误，请重新输入");
                }
            }
        });

    }
}
