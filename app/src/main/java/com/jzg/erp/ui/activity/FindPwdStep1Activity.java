package com.jzg.erp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.jzg.erp.R;
import com.jzg.erp.base.BaseActivity;
import com.jzg.erp.global.IntentKey;
import com.jzg.erp.model.SMS;
import com.jzg.erp.presenter.SMSPresenter;
import com.jzg.erp.utils.Validator;
import com.jzg.erp.view.IGetSMSCode;
import com.jzg.erp.widget.ClearableEditText;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * @author: voiceofnet
 * email: pengkun@jingzhengu.com
 * phone:18101032717
 * @time: 2016/6/21 19:54
 * @desc: 验证手机号
 */
public class FindPwdStep1Activity extends BaseActivity implements IGetSMSCode{
    @Bind(R.id.etAccount)
    ClearableEditText etAccount;
    @Bind(R.id.etSMSCode)
    ClearableEditText etSMSCode;
    SMSPresenter presenter;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_find_pwd_step1);
        ButterKnife.bind(this);
        presenter = new SMSPresenter(this);
    }

    @Override
    protected void setData() {
        setTitle(getString(R.string.forget_pwd));
    }

    @OnClick({R.id.tvSendCode, R.id.btnNext})
    public void onClick(View view) {
        String phone = etAccount.getText().toString().trim();
        if(TextUtils.isEmpty(phone)){
            etAccount.setError(getString(R.string.input_phone_number));
            return;
        }
        boolean isMobile = Validator.isMobile(phone);
        if(!isMobile){
            etAccount.setError(getString(R.string.illegal_phone_number));
            return;
        }
        switch (view.getId()) {
            case R.id.tvSendCode:
                presenter.getSMSCode(phone);
                break;
            case R.id.btnNext:
                String smsCode = etSMSCode.getText().toString().trim();
                if(TextUtils.isEmpty(smsCode)){
                    etSMSCode.setError(getString(R.string.smscode_canot_be_null));
                    return;
                }
                presenter.validate(phone,smsCode);
                break;
        }
    }

    @Override
    public void getSMSCode(SMS sms) {

    }

    @Override
    public void validateOK() {
        Intent intent = new Intent(this,FindPwdStep2Activity.class);
        String phone = etAccount.getText().toString().trim();
        intent.putExtra(IntentKey.KEY_PHONE,phone);
        jump(intent,true);
    }

}
