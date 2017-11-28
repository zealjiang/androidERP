package com.jzg.erp.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;

import com.jzg.erp.R;
import com.jzg.erp.base.BaseActivity;
import com.jzg.erp.global.IntentKey;
import com.jzg.erp.presenter.ModifyPwdPresenter;
import com.jzg.erp.view.IModifyPwd;
import com.jzg.erp.utils.MyToast;
import com.jzg.erp.widget.ClearableEditText;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FindPwdStep2Activity extends BaseActivity implements IModifyPwd{

    @Bind(R.id.etNewPassword)
    ClearableEditText etNewPassword;
    @Bind(R.id.etRepeatePassword)
    ClearableEditText etRepeatePassword;
    private ModifyPwdPresenter presenter;
    private String phone;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_modify_pwd);
        ButterKnife.bind(this);
        presenter = new ModifyPwdPresenter(this);

    }

    @Override
    protected void setData() {
        setTitle(getString(R.string.modify_pwd));
        phone = getIntent().getStringExtra(IntentKey.KEY_PHONE);
    }

    @OnClick(R.id.btnNext)
    public void onClick() {
        String newPwd = etNewPassword.getText().toString().trim();
        String repeatePwd = etRepeatePassword.getText().toString().toString().trim();
        if(TextUtils.isEmpty(newPwd)){
            etNewPassword.setError(getString(R.string.new_pwd_null));
            return;
        }
        if(TextUtils.isEmpty(repeatePwd)){
            etRepeatePassword.setError(getString(R.string.repeate_pwd_null));
            return;
        }
        if(!TextUtils.equals(newPwd,repeatePwd)){
            MyToast.showLong(getString(R.string.twice_pwd_diff));
            return;
        }
    }

    @Override
    public void modifyOK() {
        jump(LoginActivity.class,true);
    }
}
