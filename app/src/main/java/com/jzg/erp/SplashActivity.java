package com.jzg.erp;

import android.content.Intent;
import android.os.Bundle;
import android.support.multidex.MultiDex;
import android.text.TextUtils;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.jzg.erp.app.JzgApp;
import com.jzg.erp.base.BaseActivity;
import com.jzg.erp.global.Constant;
import com.jzg.erp.model.UserWrapper;
import com.jzg.erp.presenter.LoginPresenter;
import com.jzg.erp.ui.activity.HomeActivity;
import com.jzg.erp.ui.activity.LoginActivity;
import com.jzg.erp.utils.ACache;
import com.jzg.erp.utils.LogUtil;
import com.jzg.erp.view.ILogin;

import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import cat.ereza.customactivityoncrash.CustomActivityOnCrash;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;


public class SplashActivity extends BaseActivity implements ILogin {

    private String mLoginId;
    private String mLoginPwd;
    private LoginPresenter presenter;

    @Bind(R.id.ivSplash)
    ImageView ivSplash;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
    }


    @Override
    protected void setData() {
    }

    @Override
    protected void onStart() {
        super.onStart();
        wait2s();
    }

    private void wait2s() {
        AlphaAnimation aa = new AlphaAnimation(0, 1);
        aa.setDuration(100);
        ivSplash.setAnimation(aa);

        Observable.timer(1, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<Long>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                //这里可能报网络地址错误
                if(e!=null){
                    Toast.makeText(getApplication(), e.toString(), Toast.LENGTH_SHORT).show();
                    LogUtil.e(TAG,e.getMessage());
                }
            }

            @Override
            public void onNext(Long aLong) {
                Fresco.initialize(JzgApp.getAppContext());
                CustomActivityOnCrash.install(JzgApp.getAppContext());
                JzgApp.getAppContext().initApiServer();
                JzgApp.getAppContext().initCommonServer();
                JzgApp.getAppContext().initNetworkStatusDetector();
//                JzgApp.getAppContext().refWatcher = LeakCanary.install(JzgApp.getAppContext());
                MultiDex.install(JzgApp.getAppContext());
//              jump(LoginActivity.class, true);
                autoLogin();
            }
        });
    }


    /**
     * 检查是否符合自动登录的初步条件
     * @author zealjiang
     * @time 2016/7/7 17:04
     * @return 符合返回true，反之返回false
     */
    private boolean checkLogin(){
        mLoginId = ACache.get(this).getAsString("loginId");
        mLoginPwd = ACache.get(this).getAsString("pwd");

        if(TextUtils.isEmpty(mLoginId)){
            return false;
        }
        if(TextUtils.isEmpty(mLoginPwd)){
            return false;
        }

        return true;
    }

    /**
     * 自动登录
     * @author zealjiang
     * @time 2016/7/7 17:06
     */
    private void autoLogin(){

        if(!checkLogin()){
            jump(LoginActivity.class, true);
            return;
        }

        presenter = new LoginPresenter(this);
        presenter.login(mLoginId, mLoginPwd,false);
    }

    @Override
    public void loginSucceed(UserWrapper.User user) {
        //保存用户对象到JzgApp
        JzgApp.setUser(user);
        //保存用户对象到ACache中
        ACache.get(JzgApp.getAppContext()).put(Constant.KEY_ACache_UserWrapper,user);
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }

    @Override
    public void showError(String error) {
        super.showError(error);
        jump(LoginActivity.class, true);
    }
}
