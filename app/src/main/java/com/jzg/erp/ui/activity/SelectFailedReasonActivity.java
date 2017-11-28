package com.jzg.erp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;

import com.jzg.erp.R;
import com.jzg.erp.base.BaseActivity;
import com.jzg.erp.global.Constant;
import com.jzg.erp.global.IntentKey;
import com.jzg.erp.utils.MyToast;
import com.jzg.erp.utils.UIUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/***
 * 选择战败原因界面
 */
public class SelectFailedReasonActivity extends BaseActivity {


    @Bind(R.id.cbReasonPrice)
    CheckBox cbReasonPrice;
    @Bind(R.id.cbReasonCar)
    CheckBox cbReasonCar;
    @Bind(R.id.cbZonePolicy)
    CheckBox cbZonePolicy;
    @Bind(R.id.tvReasonOther)
    AppCompatTextView tvReasonOther;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_select_failed);
        ButterKnife.bind(this);
//        String failedReason = getIntent().getStringExtra("FailedReason");
//        if(!TextUtils.isEmpty(failedReason)){
//            tvReasonOther.setText(failedReason);
//        }
    }

    @Override
    protected void setData() {
        setTitle("编辑战败原因");
        setTextRight("完成");
    }

    @OnClick({R.id.ivLeft,R.id.tvRight, R.id.llReasonPrice, R.id.llReasonCar, R.id.llZonePolicy, R.id.llReasonOther})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvRight:
                checkAndConfirm();
                break;
            case R.id.ivLeft:
                finish();
                break;
            case R.id.llReasonPrice:
                cbReasonPrice.setChecked(!cbReasonPrice.isChecked());
                break;
            case R.id.llReasonCar:
                cbReasonCar.setChecked(!cbReasonCar.isChecked());
                break;
            case R.id.llZonePolicy:
                cbZonePolicy.setChecked(!cbZonePolicy.isChecked());
                break;
            case R.id.llReasonOther:
                startInputTextActivity(0,"编辑其他原因", "最多可输入20个字",tvReasonOther.getText().toString().trim(),  20,-1);
                break;
        }
    }

    private void checkAndConfirm(){
        StringBuffer sb = new StringBuffer();
        if(cbReasonPrice.isChecked())
            sb.append(UIUtils.getString(R.string.reason_price)).append(" ");
        if(cbReasonCar.isChecked())
            sb.append(UIUtils.getString(R.string.reason_car)).append(" ");
        if(cbZonePolicy.isChecked())
            sb.append(UIUtils.getString(R.string.zone_policy)).append(" ");
        String otherReason = tvReasonOther.getText().toString().trim();
        if(!TextUtils.isEmpty(otherReason))
            sb.append(otherReason);
        String result = sb.toString().trim();
        if(TextUtils.isEmpty(result)){
            MyToast.showShort("请至少选择一个战败原因");
            return;
        }
        Intent intent = getIntent();
        intent.putExtra(IntentKey.KEY_RESULT,result);
        setResult(IntentKey.REQ_CODE_FAILED_REASON,intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && data != null) {
            tvReasonOther.setText(data.getStringExtra(Constant.ACTIVITY_INPUT));
        }
    }
}
