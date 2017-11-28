package com.jzg.erp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.jzg.erp.R;
import com.jzg.erp.app.JzgApp;
import com.jzg.erp.base.BaseActivity;
import com.jzg.erp.presenter.ModifyPwdPresenter;
import com.jzg.erp.utils.MyToast;
import com.jzg.erp.view.IModifyPwd;
import com.jzg.erp.widget.ClearableEditText;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 修改密码
 *
 * @author zealjiang
 * @time 2016/6/24 17:00
 */
public class ChangePwdActivity extends BaseActivity implements IModifyPwd {

    private ModifyPwdPresenter mPresenter;

    @Bind(R.id.cet_cur_pw)
    ClearableEditText mCECurPwd;

    @Bind(R.id.cet_new_pw)
    ClearableEditText mCENewPwd;

    @Bind(R.id.cet_verify_pw)
    ClearableEditText mCENewPwdVerify;

    private String mStrCurPwd;
    private String mStrNewPwd;
    private String mUserId;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_change_pwd);
        ButterKnife.bind(this);
    }

    @Override
    protected void setData() {
        setTitle("修改密码");

        mCECurPwd.setFilters(new InputFilter[]{filter});
        mCENewPwd.setFilters(new InputFilter[]{filter});
        mCENewPwdVerify.setFilters(new InputFilter[]{filter});
    }

    private InputFilter filter = new InputFilter() {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

            if (source.equals(" ")) return "";
            else return null;
        }
    };

    @OnClick(R.id.btn_confirm)
    void changePwd(View view) {

        if (verify() == false) {
            return;
        }
        //获取userId
        mUserId = JzgApp.getUser().getUserID() + "";
        //获取当前密码
        mStrCurPwd = mCECurPwd.getText().toString();
        //获取新密码
        mStrNewPwd = mCENewPwdVerify.getText().toString();

        //获取用户信息
        mPresenter = new ModifyPwdPresenter(this);
        mPresenter.modifyPwd(mUserId, mStrCurPwd, mStrNewPwd);
    }

    private boolean verify() {
        String curPwd = mCECurPwd.getText().toString();
        String newPwd = mCENewPwd.getText().toString();
        String newPwdVerify = mCENewPwdVerify.getText().toString();

        if (TextUtils.isEmpty(curPwd)) {
            Toast.makeText(this, "请输入当前密码", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(newPwd)) {
            Toast.makeText(this, "请输入新密码", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (newPwd.length()<8) {
            Toast.makeText(this, "密码长度至少8位", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(newPwdVerify)) {
            Toast.makeText(this, "请输入确认密码", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (newPwd.equals(newPwdVerify)) {
            return true;
        } else {
            Toast.makeText(this, "两次密码不一致", Toast.LENGTH_SHORT).show();
            return false;
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    public void modifyOK() {
        //密码修改成功
        MyToast.showShort("密码修改成功");

        //将SettingActivity从要Stack中移除，防止finishAll()时程序退出
        JzgApp.getAppContext().remove(this);
        //关掉Stack中的Activity
        JzgApp.getAppContext().finishAll();
        //跳转到登录页
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra("login_out",true);
        startActivity(intent);
        //退出当前Activity
        this.finish();
    }

}
