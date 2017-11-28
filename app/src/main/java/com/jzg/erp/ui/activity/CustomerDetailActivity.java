package com.jzg.erp.ui.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jzg.erp.R;
import com.jzg.erp.base.BaseActivity;
import com.jzg.erp.event.RecordAddOkEvent;
import com.jzg.erp.global.IntentKey;
import com.jzg.erp.model.CustomerDetail;
import com.jzg.erp.presenter.CustomerDetailPresenter;
import com.jzg.erp.ui.fragment.CustomerInfoFragment;
import com.jzg.erp.ui.fragment.FocusCarFragment;
import com.jzg.erp.ui.fragment.ItemsFragment;
import com.jzg.erp.ui.fragment.RecordFragment;
import com.jzg.erp.utils.MyToast;
import com.jzg.erp.view.ISucceedWithoutNoResult;
import com.zhy.m.permission.MPermissions;
import com.zhy.m.permission.PermissionDenied;
import com.zhy.m.permission.PermissionGrant;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 客户详情界面
 */
public class CustomerDetailActivity extends BaseActivity implements ISucceedWithoutNoResult {
    private static final int CUSTOMER_INFO = 0;//资料
    private static final int TODO = 1;//跟进
    private static final int ITEM = 2;//事项
    private static final int FOCUS_CAR = 3;//关注车
    private static final int PERMISSION_CALL = 1;
    private static final int PERMISSION_SMS = 2;

    @Bind(android.R.id.tabs)
    TabLayout tabs;
    @Bind(R.id.viewPager)
    ViewPager viewPager;
    @Bind(R.id.ivHeader)
    SimpleDraweeView ivHeader;
    @Bind(R.id.tvName)
    AppCompatTextView tvName;
    @Bind(R.id.tvPhone)
    AppCompatTextView tvPhone;
    @Bind(R.id.ivCall)
    ImageView ivCall;
    @Bind(R.id.ivMsg)
    ImageView ivMsg;

    private CustomerDetailPresenter customerDetailPresenter;
    private CustomerDetail customerDetail;
    private String id;
    private CustomerDetail.DataBean mNewCusDataBean;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_customer_detail1);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        id = getIntent().getStringExtra("id");
        customerDetail = getIntent().getParcelableExtra("detail");
        customerDetailPresenter = new CustomerDetailPresenter(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void setData() {
        ivHeader.setImageURI(getCustomerStatusName(customerDetail.getData().getCustomerStatus()));
        setTitle("客户资料");
        setTextRight("匹配车源");
        if (customerDetail != null) {
            mNewCusDataBean = customerDetail.getData();

            String customerName = customerDetail.getData().getCustomerName();
            if(!TextUtils.isEmpty(customerDetail.getData().getLinkMan()))
                customerName = customerDetail.getData().getLinkMan();
            tvName.setText(customerName);
            tvPhone.setText(customerDetail.getData().getTelphone());
            viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {

                @Override
                public int getCount() {
                    return 4;
                }

                @Override
                public Fragment getItem(int position) {
                    switch (position) {
                        case CUSTOMER_INFO:
                            return CustomerInfoFragment.newInstance(customerDetail.getData());
                        case TODO:
                            return RecordFragment.newInstance(customerDetail.getData());
                        case ITEM:
                            return ItemsFragment.newInstance(customerDetail.getData());
                        case FOCUS_CAR:
                            return FocusCarFragment.newInstance(customerDetail.getData());
                        default:
                            return CustomerInfoFragment.newInstance(customerDetail.getData());
                    }
                }

                @Override
                public CharSequence getPageTitle(int position) {
                    switch (position) {
                        case CUSTOMER_INFO:
                            return getString(R.string.customer_info);
                        case TODO:
                            return getString(R.string.todo);
                        case ITEM:
                            return getString(R.string.item);
                        case FOCUS_CAR:
                            return getString(R.string.focus_car);
                        default:
                            return getString(R.string.customer_info);
                    }
                }
            });
            tabs.setupWithViewPager(viewPager);
        }


    }
    public Uri getCustomerStatusName(int status) {
        switch (status) {
            case 1:
                return Uri.parse("res:/"+R.mipmap.yixiang_tx);
            case 2:
                return Uri.parse("res:/"+R.mipmap.yuding_tx);
            case 3:
                return Uri.parse("res:/"+R.mipmap.chengjiao_tx);
            case 4:
                return Uri.parse("res:/"+R.mipmap.zhanbai_tx);
            default:
                return Uri.parse("res:/"+R.mipmap.zhanbai_tx);
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
    }

    @OnClick({ R.id.ivCall, R.id.ivMsg,R.id.tvRight,R.id.tvPhone})
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.tvRight:
                if (mNewCusDataBean != null) {
                    Intent intent1 = new Intent(this, MatchCarSourceActivity.class);
                    intent1.putExtra(IntentKey.FOR_DETIAL, mNewCusDataBean);
                    startActivity(intent1);
                }
                break;
            case R.id.ivCall:
                MPermissions.requestPermissions(this, PERMISSION_CALL, Manifest.permission.CALL_PHONE);
                break;
            case R.id.tvPhone:
                MPermissions.requestPermissions(this, PERMISSION_CALL, Manifest.permission.CALL_PHONE);
                break;
            case R.id.ivMsg:
                MPermissions.requestPermissions(this, PERMISSION_SMS, Manifest.permission.SEND_SMS);
                break;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions, int[] grantResults) {
        MPermissions.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @PermissionDenied(PERMISSION_SMS)
    public void requestFailed() {
        MyToast.showShort("发送短信授权被拒!");
    }

    @PermissionDenied(PERMISSION_CALL)
    public void requestSdcardFailed() {
        MyToast.showShort("拨打电话授权被拒!");
    }


    @PermissionGrant(PERMISSION_CALL)
    public void requestCallSuccess() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this).setTitle("提示").setMessage("确定拨打" + tvPhone.getText().toString() + "吗？").setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                customerDetailPresenter.uploadDailRecord(String.valueOf(customerDetail.getData().getCustomerID()), customerDetail.getData().getCustomerName());
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + tvPhone.getText().toString()));
                jump(intent, false);
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).setCancelable(true);
        builder.create().show();
    }

    @PermissionGrant(PERMISSION_SMS)
    public void requestSendSmsSuccess() {
        AlertDialog.Builder builder2 = new AlertDialog.Builder(this).setTitle("提示").setMessage("确定给" + tvPhone.getText().toString() + "发送短信吗？").setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Uri uri = Uri.parse("smsto:" + tvPhone.getText().toString());
                Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
                jump(intent, false);
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).setCancelable(true);
        builder2.create().show();
    }

    @Override
    public void onSucceed() {
        EventBus.getDefault().post(new RecordAddOkEvent(RecordAddOkEvent.EVENT_TYPE_CALL,""));
    }


    @Subscribe
    public void onEventMainThread(CustomerDetail.DataBean dataBean){
        mNewCusDataBean = dataBean;
    }

}

