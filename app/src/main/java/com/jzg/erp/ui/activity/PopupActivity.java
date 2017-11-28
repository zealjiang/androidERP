package com.jzg.erp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.jzg.erp.R;
import com.jzg.erp.app.JzgApp;
import com.jzg.erp.global.IntentKey;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class PopupActivity extends Activity {
    private String customerID;
    private String PreBuyTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.popupwindow_item_view);
        ButterKnife.bind(this);
        JzgApp.getAppContext().add(this);
        customerID = getIntent().getStringExtra("customerID");
        PreBuyTime = getIntent().getStringExtra("PreBuyTime");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        JzgApp.getAppContext().remove(this);
    }

    @OnClick({R.id.tvVisit, R.id.tvFollowup, R.id.tvBattleFailed, R.id.ivClose})
    public void onClick(View view) {
        if(view.getId()==R.id.ivClose){
            finish();
            return;
        }
        Intent intent = new Intent(this,AddFollowUpRecActivity.class);
        switch (view.getId()) {
            case R.id.tvVisit:
                intent.putExtra(IntentKey.KEY_REC_TYPE,IntentKey.REC_TYPE_NEW);
                break;
            case R.id.tvFollowup:
                intent.putExtra(IntentKey.KEY_REC_TYPE,IntentKey.REC_TYPE_FOLLOWUP);
                break;
            case R.id.tvBattleFailed:
                intent.putExtra(IntentKey.KEY_REC_TYPE,IntentKey.REC_TYPE_FAILED);
                break;
        }
        intent.putExtra("customerID",customerID);
        intent.putExtra("PreBuyTime",PreBuyTime);
        startActivity(intent);
        finish();
    }
}
