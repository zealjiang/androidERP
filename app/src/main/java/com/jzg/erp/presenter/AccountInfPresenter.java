package com.jzg.erp.presenter;

import com.jzg.erp.app.JzgApp;
import com.jzg.erp.global.Constant;
import com.jzg.erp.model.AccountInf;
import com.jzg.erp.utils.MD5Utils;
import com.jzg.erp.view.IAccountInf;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
*
* 帐户信息界面的控制层
* @author zealjiang
* created at 2016/6/27 12:52
*/
public class AccountInfPresenter extends BasePresenter<IAccountInf> {
    public AccountInfPresenter(IAccountInf from) {
        super(from);
    }

    private Subscription mSubscription;

    public void getInf(final String userid) {
        Map<String, String> params = new HashMap<>();
        params.put("op", "browseAccountinformation");
        params.put("userid", userid);
        //加sign
        Map<String, Object> signMap = new HashMap<>();
        signMap.putAll(params);
        params.put("sign", MD5Utils.getMD5Sign(signMap));
        mSubscription = JzgApp.getApiServer().getAccountInf(params).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(new Subscriber<AccountInf>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e != null && baseView != null) {
                            baseView.showError(Constant.ERROR_FORNET);
                        }
                    }

                    @Override
                    public void onNext(AccountInf data) {
                        int status = data.getStatus();
                        if(status==1){
                            baseView.succeed(data.getData());
                        }else{
                            baseView.showError(data.getMessage());
                        }
                    }
                });
    }

    /**
     * 上传头像
     * @author zealjiang
     * @time 2016/6/27 15:01
     */
    public void uploadImage(String userid,String path) {

        File imgFile = new File(path);

        RequestBody fileBody = RequestBody.create(MediaType.parse("image/png"), imgFile);
        RequestBody userId = RequestBody.create(MediaType.parse("text/plain"), userid);
        RequestBody p = RequestBody.create(MediaType.parse("text/plain"), "modifyIcon");
        Map<String, RequestBody> params = new HashMap<String, RequestBody>();
        params.put("image\"; filename=\"" + imgFile.getName() + "", fileBody);
        params.put("userid", userId);
        params.put("op", p);
        //加sign
        Map<String, Object> signMap = new HashMap<>();
        signMap.putAll(params);
        RequestBody sign = RequestBody.create(MediaType.parse("text/plain"), MD5Utils.getMD5Sign(signMap));
        params.put("sign",sign);

        baseView.showDialog();
        mSubscription = JzgApp.getApiServer().updateImage(params).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(new Subscriber<AccountInf>() {
                    @Override
                    public void onCompleted() {
                        baseView.dismissDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        baseView.dismissDialog();
                        if (e != null && baseView != null) {
                            baseView.errorHead(Constant.ERROR_FORNET);
                        }
                    }

                    @Override
                    public void onNext(AccountInf data) {

                        int status = data.getStatus();
                        if(status==1){
                            baseView.succeedHead(data.getData());
                        }else{
                            baseView.errorHead(data.getMessage());
                        }
                    }
                });
    }
}
