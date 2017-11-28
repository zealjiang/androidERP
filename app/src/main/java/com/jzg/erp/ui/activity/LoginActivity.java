package com.jzg.erp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.jzg.erp.R;
import com.jzg.erp.app.JzgApp;
import com.jzg.erp.base.BaseActivity;
import com.jzg.erp.global.Constant;
import com.jzg.erp.model.UserWrapper;
import com.jzg.erp.presenter.LoginPresenter;
import com.jzg.erp.utils.ACache;
import com.jzg.erp.utils.AppUtils;
import com.jzg.erp.utils.MyToast;
import com.jzg.erp.utils.Validator;
import com.jzg.erp.view.ILogin;
import com.jzg.erp.widget.ClearableEditText;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author: voiceofnet
 * email: pengkun@jingzhengu.com
 * phone:18101032717
 * 自动登录走登录接口 by zealjiang
 * @time: 2016/6/21 19:54
 * @desc: 登录页面
 */
public class LoginActivity extends BaseActivity implements ILogin {
    @Bind(R.id.etAccount)
    ClearableEditText etAccount;
    @Bind(R.id.etPassword)
    ClearableEditText etPassword;
    @Bind(R.id.tvVersion)
    TextView tvVersion;


    private LoginPresenter presenter;

    private String mLoginId;
    private String mLoginPwd;
    //退出登录传过来的参数时为true
    private boolean mIsLoginOut = false;

    @OnClick({R.id.btnLogin, R.id.tvFindPwd})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLogin:
                if (!check(true)) {
                    return;
                }
                String account = etAccount.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                if (!JzgApp.networkAvailable) {
                    MyToast.showShort("没有网络");
                    return;
                }
                presenter.login(account, password, true);
                break;
            case R.id.tvFindPwd:
                startActivity(new Intent(this, FindPwdStep1Activity.class));
                break;
        }
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        mIsLoginOut = getIntent().getBooleanExtra("login_out", false);//来自SettingActivity或ChangePwdActivity
        if (mIsLoginOut) {//从退出登录跳转进来
            ACache.get(this).put("pwd", "");
        }
        mLoginId = ACache.get(this).getAsString("loginId");
        mLoginPwd = ACache.get(this).getAsString("pwd");

        if (TextUtils.isEmpty(mLoginId)) {
            mLoginId = "";
        }
        if (TextUtils.isEmpty(mLoginPwd)) {
            mLoginPwd = "";
        }
        etAccount.setText(mLoginId);
        etPassword.setText(mLoginPwd);
        presenter = new LoginPresenter(this);
        tvVersion.setText("版本号"+AppUtils.getVersionName(this));
    }

    @Override
    protected void setData() {
        boolean boo = check(false);
        if (boo) {
            //用户名或密码不为空，自动登录
            String account = etAccount.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            presenter.login(account, password, true);
        }

        etPassword.setFilters(new InputFilter[]{filter});

    }

    private InputFilter filter = new InputFilter() {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

            if (source.equals(" ")) return "";
            else return null;
        }
    };

    /**
     * 检验用户名和密码是否为空
     *
     * @return 都不为空返回true，反之返回false
     */
    private boolean check(boolean showError) {
        String account = etAccount.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        if (TextUtils.isEmpty(account)) {
            if (showError)
                etAccount.setError(getString(R.string.phone_cannot_be_null));
            return false;
        }

        if (!Validator.isMobile(account)) {
            if (showError)
                etAccount.setError(getString(R.string.illegal_phone_number));
            return false;
        }

        if (TextUtils.isEmpty(password)) {
            if (showError)
                etPassword.setError(getString(R.string.pwd_cannot_be_null));
            return false;
        }
        return true;
    }

    @Override
    public void loginSucceed(UserWrapper.User user) {
        //保存用户对象到JzgApp
        JzgApp.setUser(user);
        //保存用户对象到ACache中
        ACache.get(JzgApp.getAppContext()).put(Constant.KEY_ACache_UserWrapper, user);
        //保存登录帐号和密码到ACache对象中
        String account = etAccount.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        ACache.get(this).put("loginId", account);
        ACache.get(this).put("pwd", password);
        startActivity(new Intent(this, HomeActivity.class));
        finish();

    }
}
