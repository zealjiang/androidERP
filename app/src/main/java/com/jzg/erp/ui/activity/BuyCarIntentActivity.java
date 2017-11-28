package com.jzg.erp.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jzg.erp.R;
import com.jzg.erp.base.BaseActivity;
import com.jzg.erp.global.Constant;
import com.jzg.erp.global.IntentKey;
import com.jzg.erp.model.BuyCarIntent;
import com.jzg.erp.model.CustomerDetail;
import com.jzg.erp.presenter.BuyCarIntentPresenter;
import com.jzg.erp.utils.MyToast;
import com.jzg.erp.view.IBuyCarIntent;
import com.jzg.erp.widget.MActionSheet;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 修改购车意向界面
 * BuyCarIntentActivity
 *
 * @author sunbl
 *         created at 2016/6/24 15:52
 */
public class BuyCarIntentActivity extends BaseActivity implements IBuyCarIntent {

    @Bind(R.id.tvWantedCar)
    TextView tvWantedCar;
    @Bind(R.id.tvGearLeft)
    TextView tvGearLeft;
    @Bind(R.id.tvWantedCarLeft)
    TextView tvWantedCarLeft;
    @Bind(R.id.linear_style)
    RelativeLayout linearStyle;
    @Bind(R.id.tvGear)
    AppCompatTextView tvGear;
    @Bind(R.id.linear_biansu)
    LinearLayout linearBiansu;
    @Bind(R.id.tvBudget)
    AppCompatTextView tvBudget;
    @Bind(R.id.linear_yusuan)
    LinearLayout linearYusuan;
    @Bind(R.id.tvExtraInfo)
    TextView tvExtraInfo;
    @Bind(R.id.linear_beizhu)
    RelativeLayout linearBeizhu;

    private CustomerDetail.DataBean customerDetail;
    private BuyCarIntentPresenter presenter;
    private String ids;
    private String names;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_buy_car_intent);
        ButterKnife.bind(this);
        presenter = new BuyCarIntentPresenter(this);
    }

    private void setTextViewNeccessary(TextView tv, int index) {
        CharSequence text = tv.getText();
        SpannableString sp = new SpannableString(text);
        sp.setSpan(new ForegroundColorSpan(Color.RED), index, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.setText(sp);
    }

    @Override
    protected void setData() {
        setTitle("修改购车意向");
        setTextRight("完成");
        customerDetail = getIntent().getParcelableExtra("detail");
        if (customerDetail != null) {
            ids = customerDetail.getIntendCar();
            names = customerDetail.getIntendCarName();
            tvWantedCar.setText(names);
            tvGear.setText(customerDetail.getTransmissionName());
            tvBudget.setText(customerDetail.getIntendMoney());
            tvExtraInfo.setText(customerDetail.getIntendRemark());
        }

        tvWantedCarLeft.setText("意向车型*");
        tvGearLeft.setText("变速箱*");
        setTextViewNeccessary(tvWantedCarLeft,4);
        setTextViewNeccessary(tvGearLeft,3);

    }

    @Override
    @OnClick({R.id.linear_style, R.id.linear_biansu, R.id.linear_yusuan, R.id.linear_beizhu, R.id.tvRight})
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.tvRight:
                if(TextUtils.isEmpty(names) || TextUtils.isEmpty(ids)){
                    MyToast.showShort("意向车型不能为空");
                    return;
                }
                presenter.modifyBuyCarIntent(getCustomerParams());
                break;
            case R.id.linear_style:
                Intent intent = new Intent(this, StyleIntentActivity.class);
                intent.putExtra("name", names);
                intent.putExtra("id", ids);
                startActivityForResult(intent, IntentKey.FOR_STYLE);
                break;
            case R.id.linear_biansu:
                MActionSheet.createBuilder(this, getSupportFragmentManager())
                        .setCancelButtonTitle("取消")
                        .setOtherButtonTitles(Constant.BIANSUXIANG)
                        .setCancelableOnTouchOutside(true)
                        .setListener(new MActionSheet.ActionSheetListener() {
                            @Override
                            public void onDismiss(MActionSheet actionSheet, boolean isCancel) {
                            }

                            @Override
                            public void onOtherButtonClick(MActionSheet actionSheet, int index) {
                                tvGear.setText(Constant.BIANSUXIANG[index]);
                            }
                        }).show();

                break;

            case R.id.linear_yusuan:
                startInputTextActivity(0,"修改预算", "请填写预算",tvBudget.getText().toString(),20,-1);
                break;
            case R.id.linear_beizhu:
                startInputTextActivity(1,"备注信息", "请填写备注信息",tvExtraInfo.getText().toString(),300,-1);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        switch (requestCode) {
            case 0:
                tvBudget.setText(data.getStringExtra(Constant.ACTIVITY_INPUT));
                break;
            case 1:
                tvExtraInfo.setText(data.getStringExtra(Constant.ACTIVITY_INPUT));
                break;
            case IntentKey.FOR_STYLE:
                ids = data.getStringExtra("id");
                names = data.getStringExtra("name");
                tvWantedCar.setText(names);
                break;
            default:

                break;
        }
    }

    /**
     * 修改购车意向
     */
    @Override
    public void modifyBuyCarIntent(BuyCarIntent buyCarIntent) {
        if (customerDetail == null)
            return;

        if (buyCarIntent.getStatus() == Constant.SUCCESS) {
            Intent intent1 = new Intent();
            customerDetail.setTransmissionName(tvGear.getText().toString());
            customerDetail.setIntendRemark(tvExtraInfo.getText().toString());
            customerDetail.setIntendCarName(names);
            customerDetail.setIntendCar(ids);
            customerDetail.setIntendMoney(tvBudget.getText().toString());
            intent1.putExtra("detail", customerDetail);
            setResult(RESULT_OK, intent1);
            finish();
        } else {
            MyToast.showShort(buyCarIntent.getMessage());
        }


    }

    /**
     * intendCar ; 意向车型
     * transmission;变速类型
     * intendMoney;预算
     * remark;意向备注
     * intendCarMakeModelID ：意向车型的 makeID_modelID , 多个的话用 逗号 隔开 。例如 2_2355,4_878
     *
     * @return
     */
    private Map<String, String> getCustomerParams() {
        Map<String, String> params = new HashMap<>();
        params.put("Op", "updateIntendBuyCar");
        params.put("intendCar", names);
        params.put("transmission", tvGear.getText().toString());
        params.put("remark", tvExtraInfo.getText().toString());
        params.put("intendCarMakeModelID", ids);
        params.put("customerID", customerDetail.getCustomerID() + "");
        params.put("intendMoney", tvBudget.getText().toString());
        return params;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
