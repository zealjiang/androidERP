package com.jzg.erp.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jzg.erp.R;
import com.jzg.erp.adapter.ChooseCustomerAdapter;
import com.jzg.erp.app.JzgApp;
import com.jzg.erp.base.BaseActivity;
import com.jzg.erp.global.Constant;
import com.jzg.erp.model.CustomerData;
import com.jzg.erp.presenter.ChooseCustomerPresenter;
import com.jzg.erp.utils.LogUtil;
import com.jzg.erp.utils.MyToast;
import com.jzg.erp.utils.UIUtils;
import com.jzg.erp.view.IChooseCustomer;
import com.jzg.erp.widget.EmptyView;
import com.jzg.erp.widget.ErrorView;
import com.jzg.erp.widget.RecycleViewDivider;
import com.jzg.erp.widget.XRecyclerView;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * ChooseCustomerActivity
 * 车辆详情 关注 列表
 *
 * @author sunbl
 *         created at 2016/6/29 11:15
 */
public class ChooseCustomerActivity extends BaseActivity implements IChooseCustomer, XRecyclerView.LoadingListener {
    @Bind(R.id.customer_xrc)
    XRecyclerView xrv;
    @Bind(R.id.err_layout)
    ErrorView errLayout;
    private List<CustomerData.DataBean> mSortList;
    private ChooseCustomerPresenter chooseCustomerPresenter;
    private ChooseCustomerAdapter adapter;
    private String carSourceId;
    private String carId;
    private int pageIndex = 1;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_choose_customer);
        ButterKnife.bind(this);
        carSourceId = getIntent().getStringExtra(Constant.CARSOURCEID);
        carId = getIntent().getStringExtra(Constant.CARID);
        chooseCustomerPresenter = new ChooseCustomerPresenter(this);
        showDialog();
        chooseCustomerPresenter.getChooseCustomer(getParagms());
        mSortList = new ArrayList<>();
    }

    public Map<String, String> getParagms() {
        Map<String, String> params = new HashMap<>();
        params.put("Op", "getIntendCustomerBySellerIDAndCarID");
        params.put("carSourceID", carSourceId);
        params.put("userId", String.valueOf(JzgApp.getUser().getUserID()));
        params.put("pageIndex", pageIndex + "");
        params.put("pageCount", String.valueOf(Constant.PAGECOUNT));
        LogUtil.e(TAG, "/Customer/ToDoList.ashx"+ UIUtils.getUrl(params));
        return params;
    }

    public Map<String, String> getAddCustomerParagms() {
        Map<String, String> params = new HashMap<>();
        params.put("Op", "batchInsertFocusCarCustomer");
        String strId = jointId();
        if (strId.length() > 1) {
            String str = strId.substring(0, strId.length() - 1);
            params.put("customerIdList", str);
        } else {
            MyToast.showShort("请选择客户");
            return null;
        }

        if (!TextUtils.isEmpty(carSourceId)) {
            params.put("carsourceId", carSourceId);
        }
        if (!TextUtils.isEmpty(carId)) {
            params.put("carId", carId);
        }
        params.put("currentUserName", String.valueOf(JzgApp.getUser().getTrueName()));

        return params;
    }

    public String jointId() {
        String strId = "";
        for (int i = 0; i < adapter.getChooseItemPosition().size(); i++) {
            strId += mSortList.get(adapter.getChooseItemPosition().get(i)).getCustomerID() + "_";
        }
        return strId;
    }

    @Override
    protected void setData() {
        setTitle("选择关注客户");
        setTextRight("完成");
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        xrv.setLayoutManager(linearLayoutManager);
        //添加空界面
        EmptyView emptyView = new EmptyView(this, "暂无关注客户");
        ((ViewGroup) xrv.getParent()).addView(emptyView);
        xrv.setEmptyView(emptyView);
        //添加错误界面监听
        errLayout.setOnErrorListener(new ErrorView.OnErrorListener() {
            @Override
            public void onError() {
                pageIndex = 1;
                showDialog();
                chooseCustomerPresenter.getChooseCustomer(getParagms());
            }
        });
        xrv.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.VERTICAL, 1, R.color.common_gray_line));
        xrv.setRefreshProgressStyle(ProgressStyle.SysProgress);
        xrv.setLoadingMoreProgressStyle(ProgressStyle.SysProgress);
        xrv.setPullRefreshEnabled(true);
        xrv.setLoadingMoreEnabled(true);
        xrv.setLoadingListener(this);
        adapter = new ChooseCustomerAdapter(this, R.layout.item_customer_radio, mSortList, xrv);
        xrv.setAdapter(adapter);
    }

    @OnClick(R.id.tvRight)
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tvRight:
                Logger.e(adapter.getChooseItemPosition().toString());
                if (getAddCustomerParagms() != null) {
                    chooseCustomerPresenter.addChooseCustomer(getAddCustomerParagms());
                    EventBus.getDefault().post("0");
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void showCustomerResult(CustomerData customerData) {
        errLayout.setVisibility(View.GONE);
        xrv.loadMoreComplete();//停止加载
        mSortList.clear();
        if (customerData.getStatus() == Constant.SUCCESS) {
            if (customerData.getData() != null && customerData.getData().size() > 0) {
                mSortList.addAll(customerData.getData());
                adapter.initCheckDate();
            }

        } else {
            MyToast.showShort(customerData.getMessage());
        }

        adapter.notifyDataSetChanged();
    }

    /**
     * 加载更多回调
     *
     * @param customerData
     */
    @Override
    public void showMoreCustomerResult(CustomerData customerData) {
        stopLoading();
        if (customerData.getStatus() == Constant.SUCCESS) {
            if (customerData.getData() != null && customerData.getData().size() > 0) {
                if(pageIndex==1)
                    mSortList.clear();
                mSortList.addAll(customerData.getData());
                adapter.initMoreCheckDate(customerData.getData());
            }
        } else {
            MyToast.showShort(customerData.getMessage());
        }
        adapter.notifyDataSetChanged();

    }

    @Override
    public void succeedResult(CustomerData customerData) {
        if (customerData.getStatus() == Constant.SUCCESS) {
            MyToast.showShort(customerData.getMessage());
            finish();
        } else {
            MyToast.showShort(customerData.getMessage());
        }
    }

    //刷新
    @Override
    public void onRefresh() {
        pageIndex=1;
        chooseCustomerPresenter.getMoreChooseCustomer(getParagms());
    }

    private void stopLoading(){
        if(xrv==null)
            return;
        if(pageIndex==1)
            xrv.refreshComplete();
        else
            xrv.loadMoreComplete();
    }

    @Override
    public void onStop() {
        super.onStop();
        stopLoading();
    }

    @Override
    public void showError(String error) {
        super.showError(error);
        xrv.setVisibility(View.GONE);
        errLayout.setVisibility(View.VISIBLE);
        errLayout.setErrorText(error);
    }

    //加载更多
    @Override
    public void onLoadMore() {
        if(mSortList.size()==0){
            xrv.loadMoreComplete();
            return;
        }
        if(mSortList.size()>0 && mSortList.size()% Constant.PAGECOUNT!=0){
            xrv.loadMoreComplete();
            return;
        }
        pageIndex++;
        chooseCustomerPresenter.getMoreChooseCustomer(getParagms());
    }
}
