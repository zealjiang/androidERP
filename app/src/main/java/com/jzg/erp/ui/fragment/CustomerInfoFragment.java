package com.jzg.erp.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jzg.erp.R;
import com.jzg.erp.base.NewBaseFragment;
import com.jzg.erp.global.IntentKey;
import com.jzg.erp.model.CustomerDetail;
import com.jzg.erp.ui.activity.BuyCarIntentActivity;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author: voiceofnet
 * email: pengkun@jingzhengu.com
 * phone:18101032717
 * @time: 2016/6/23 21:45
 * @desc: 客户资料--资料页
 */
@SuppressLint("ValidFragment")
public class CustomerInfoFragment extends NewBaseFragment {
    @Bind(R.id.tvWantedCar)
    AppCompatTextView tvWantedCar;
    @Bind(R.id.tvGear)
    AppCompatTextView tvGear;
    @Bind(R.id.tvBudget)
    AppCompatTextView tvBudget;
    @Bind(R.id.tvExtraInfo)
    AppCompatTextView tvExtraInfo;
    @Bind(R.id.tvCustomerName)
    AppCompatTextView tvCustomerName;
    @Bind(R.id.tvContact)
    AppCompatTextView tvContact;
    @Bind(R.id.tvMobile)
    AppCompatTextView tvMobile;
    @Bind(R.id.tvGender)
    AppCompatTextView tvGender;
    @Bind(R.id.tvIDCard)
    AppCompatTextView tvIDCard;
    @Bind(R.id.tvAddress)
    AppCompatTextView tvAddress;
    @Bind(R.id.tvCustomerStatus)
    AppCompatTextView tvCustomerStatus;
    @Bind(R.id.tvPrebuyTime)
    AppCompatTextView tvPrebuyTime;
    @Bind(R.id.tvCustomerType)
    AppCompatTextView tvCustomerType;
    @Bind(R.id.tvBuyVia)
    AppCompatTextView tvBuyVia;
    @Bind(R.id.tvFrom)
    AppCompatTextView tvFrom;
    private CustomerDetail.DataBean detail;


    public static CustomerInfoFragment newInstance(CustomerDetail.DataBean detail) {
        CustomerInfoFragment customerInfoFragment = new CustomerInfoFragment();
        Bundle args = new Bundle();
        args.putParcelable("CustomerDetail.DataBean", detail);
        customerInfoFragment.setArguments(args);
        return customerInfoFragment;
    }

    @Override
    protected void initData() {
        Bundle args = getArguments();
        if (args != null) {
            detail = args.getParcelable("CustomerDetail.DataBean");
        }
    }


    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_customer_info, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    protected void setView() {
        tvWantedCar.setText(detail.getIntendCarName());//意向车型
        tvCustomerName.setText(detail.getCustomerName());//客户名称
        tvAddress.setText(detail.getAddress());//地址
        tvGear.setText(detail.getTransmissionName());//变速箱
        tvMobile.setText(detail.getTelphone());//手机号
        tvGender.setText(detail.getSex());//性别
        tvIDCard.setText(detail.getIdentitinfy());//身份证
        tvBudget.setText(detail.getIntendMoney());//预算
        tvCustomerStatus.setText(detail.getCustomerStatusName());//getCustomerStatusName(detail.getCustomerStatus()));//客户状态
        tvPrebuyTime.setText(detail.getPreBuyTime());//预购时间
        tvBuyVia.setText(detail.getBuyCarTypeName());//购车方式
        tvExtraInfo.setText(detail.getIntendRemark());//备注
        tvExtraInfo.setText(detail.getIntendRemark());//备注
        tvFrom.setText(detail.getCustomerSourceName());//来源
        tvContact.setText(detail.getLinkMan());//联系人
        tvCustomerType.setText(detail.getCustomerTypeName());//客户类型
    }

    public String getCustomerStatusName(int status) {
        switch (status) {
            case 1:
                return "意向";
            case 2:
                return "预订";
            case 3:
                return "成交";
            case 4:
                return "战败";
            default:
                return "无效";
        }
    }

    @OnClick(R.id.btn_intent)
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_intent:
                Intent intent = new Intent(context, BuyCarIntentActivity.class);
                intent.putExtra("detail", detail);
                startActivityForResult(intent, IntentKey.FOR_CHANGESTYLE);
                break;
            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        switch (requestCode) {
            case IntentKey.FOR_CHANGESTYLE:
                detail = data.getParcelableExtra("detail");
                setView();
                EventBus.getDefault().post(detail);
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
