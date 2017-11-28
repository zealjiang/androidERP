package com.jzg.erp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.jzg.erp.R;
import com.jzg.erp.app.JzgApp;
import com.jzg.erp.base.BaseActivity;
import com.jzg.erp.model.CustomerDetail;
import com.jzg.erp.model.CustomerInfo;
import com.jzg.erp.model.WaitingItemWrapper;
import com.jzg.erp.presenter.CustomerPresenter;
import com.jzg.erp.presenter.TaskStatusPresenter;
import com.jzg.erp.utils.DateTimeUtils;
import com.jzg.erp.utils.MD5Utils;
import com.jzg.erp.view.ICustomer;
import com.jzg.erp.view.ISucceedWithoutNoResult;
import com.jzg.erp.widget.CustomTextView;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TaskDetailActivity extends BaseActivity implements ICustomer, ISucceedWithoutNoResult {


    @Bind(R.id.tvGenjinTime)
    AppCompatTextView tvGenjinTime;
    @Bind(R.id.tvGenjinTimeLeft)
    AppCompatTextView tvGenjinTimeLeft;
    @Bind(R.id.tvExtra)
    AppCompatTextView tvExtra;
    @Bind(R.id.tvExtraLeft)
    AppCompatTextView tvExtraLeft;
    @Bind(R.id.ivHeader)
    ImageView ivHeader;
    @Bind(R.id.tvTaskName)
    AppCompatTextView tvTaskName;
    @Bind(R.id.tvCustomer)
    AppCompatTextView tvCustomer;
    @Bind(R.id.ctvStatus)
    CustomTextView ctvStatus;
    private TaskStatusPresenter presenter;
    private WaitingItemWrapper.WaitingItem item;


    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_task_detail);
        ButterKnife.bind(this);
        presenter = new TaskStatusPresenter(this);
    }

    @Override
    protected void setData() {
        setTitle("跟进");
        item = (WaitingItemWrapper.WaitingItem) getIntent().getSerializableExtra("item");
        if (item != null) {
            String followType = item.getFollowType();
            if ("4".equals(followType)) {
                ivHeader.setImageResource(R.mipmap.genjin);
                tvGenjinTimeLeft.setText("跟进时间");
                tvExtraLeft.setText("特殊说明");
                setTitle("跟进");
                tvGenjinTime.setText(DateTimeUtils.formatMillsStr(item.getNextShopDate(),DateTimeUtils.YYYYMMDD) +" "+ (TextUtils.isEmpty(item.getNextShopTime())?"":item.getNextShopTime()));
                tvExtra.setText(item.getSpecialInstruction());
            } else if ("5".equals(followType)) {
                ivHeader.setImageResource(R.mipmap.daodian);
                tvGenjinTimeLeft.setText("到店时间");
                setTitle("客户到店");
                tvGenjinTime.setText(DateTimeUtils.formatMillsStr(item.getOnShopDate(),DateTimeUtils.YYYYMMDD)+" "+(TextUtils.isEmpty(item.getNextShopTime())?"":item.getNextShopTime()));
                tvExtraLeft.setVisibility(View.GONE);
                tvExtra.setVisibility(View.GONE);
            } else if ("6".equals(followType)) {
                tvGenjinTimeLeft.setText("分配时间");
                tvExtraLeft.setText("分配人");
                setTitle("安排跟进");
                tvExtra.setText(item.getCreateUser());
                ivHeader.setImageResource(R.mipmap.anpai);
                tvGenjinTime.setText(DateTimeUtils.formatMillsStr(item.getNextShopDate(),DateTimeUtils.YYYYMMDDHHMM));
            }
            tvTaskName.setText(item.getCustomerName());
            String customerName = item.getCustomerName();
            if(!TextUtils.isEmpty(item.getLinkMan()))
                customerName = item.getLinkMan();
            tvTaskName.setText(customerName);
            String customerStatus = item.getCustomerStatus();
            String customerLevel = item.getPreCharDate();
            tvCustomer.setText(customerStatus + " " + (TextUtils.isEmpty(customerLevel)?"无等级":customerLevel));
//            tvExtra.setText(item.getSpecialInstruction());
            int followUpItemState = item.getFollowUpItemState();
            if(followUpItemState==0){
                ctvStatus.setStrokeColorAndWidth(1,R.color.colorPrimary);
                ctvStatus.setSelectedTextColor(R.color.colorPrimary,R.color.colorPrimary);
                ctvStatus.setText("待处理");
            }else{
                ctvStatus.setText("已完成");
                ctvStatus.setStrokeColorAndWidth(1,R.color.common_gray_light);
                ctvStatus.setSelectedTextColor(R.color.common_gray_light,R.color.common_gray_light);
            }
            int isRead = item.getIsRead();
            if (isRead == 0) {
                Map<String, String> params = new HashMap<>();
                params.put("op", "changRead");
                params.put("FollowupId", String.valueOf(item.getFollowupId()));
                Map<String, Object> map = new HashMap<>();
                map.putAll(params);
                String sign = MD5Utils.getMD5Sign(map);
                params.put("sign", sign);
                presenter.changeTaskItemStatus(params);
            }
        }

    }


    @OnClick(R.id.rlCustomer)
    public void onClick() {
        CustomerPresenter presenter = new CustomerPresenter(this);
        presenter.getCustomerDetail(getCustomerParams(item.getCustomerId()+""));
    }

    /**
     * 获取查询客户信息详情需要的参数集合
     *
     * @return 参数集合
     */
    private Map<String, String> getCustomerParams(String id) {
        Map<String, String> params = new HashMap<>();
        params.put("Op", "getCustomerInfo");
        params.put("customerId",id);
        params.put("userID", String.valueOf(JzgApp.getUser().getUserID()));
        return params;
    }

    @Override
    public void succeedResult(CustomerInfo customerInfos, int pageIndex) {

    }

    @Override
    public void showCustomerDetail(CustomerDetail customerDetail) {
        Intent intent = new Intent(this, CustomerDetailActivity.class);
        intent.putExtra("detail", (Parcelable) customerDetail);
        jump(intent,true);
    }

    @Override
    public void onSucceed() {

    }
}
