package com.jzg.erp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.jzg.erp.R;
import com.jzg.erp.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 工具
 * @author zealjiang
 * @time 2016/6/24 17:00
 */
public class ToolActivity extends BaseActivity {


    @Bind(R.id.rl_limit_change)
    RelativeLayout mRlLimitChange;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_tool);
        ButterKnife.bind(this);
    }

    @Override
    protected void setData() {
        setTitle("工具");

    }

    @OnClick(R.id.rl_break_rule)
    void toBreakRuleActivity(View view) {
        jump(new Intent(this, BreakRuleActivity.class),false);
    }

    @OnClick(R.id.rl_limit_change)
    void toLimitChangeActivity(View view) {
        jump(new Intent(this, XianqianActivity.class),false);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
