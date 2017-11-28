package com.jzg.erp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.jzg.erp.R;
import com.jzg.erp.base.NewBaseFragment;
import com.jzg.erp.ui.activity.AccountActivity;
import com.jzg.erp.ui.activity.SettingActivity;
import com.jzg.erp.ui.activity.ToolActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MineFragment extends NewBaseFragment {

    @Bind(R.id.rl_account_inf)
    RelativeLayout mRlAccountInf;


    @Override
    protected void setView() {
        showBack(false);
        setTitle(R.string.mine);
    }

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_mine, container, false);
        ButterKnife.bind(this, rootView);


        return rootView;
    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.rl_account_inf)
    void toAccoutActivity(View view) {
        jump(new Intent(context, AccountActivity.class));
    }

    @OnClick(R.id.rl_tool)
    void toToolActivity(View view) {
        jump(new Intent(context, ToolActivity.class));
    }

    @OnClick(R.id.rl_setting)
    void toSettingActivity(View view) {
        jump(new Intent(context, SettingActivity.class));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
