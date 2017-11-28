package com.jzg.erp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.jzg.erp.R;
import com.jzg.erp.adapter.MatchCustomerAdapter;
import com.jzg.erp.app.JzgApp;
import com.jzg.erp.base.BaseActivity;
import com.jzg.erp.global.Constant;
import com.jzg.erp.model.CustomerDetail;
import com.jzg.erp.model.MatchCustomerData;
import com.jzg.erp.presenter.MatchCustomerPresenter;
import com.jzg.erp.utils.LogUtil;
import com.jzg.erp.utils.MD5Utils;
import com.jzg.erp.utils.MyToast;
import com.jzg.erp.utils.UIUtils;
import com.jzg.erp.view.IMatchCustomer;
import com.jzg.erp.widget.EmptyView;
import com.jzg.erp.widget.ErrorView;
import com.jzg.erp.widget.RecycleViewDivider;
import com.jzg.erp.widget.XRecyclerView;
import com.zhy.base.adapter.recyclerview.OnItemClickListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 匹配客户
 */
public class MatchCustomerActivity extends BaseActivity implements IMatchCustomer, XRecyclerView.LoadingListener {

    @Bind(R.id.customer_xrc)
    XRecyclerView xrv;
    @Bind(R.id.err_layout)
    ErrorView errLayout;
    private List<MatchCustomerData.DataBean> customerList;
    private MatchCustomerPresenter matchCustomerPresenter;
    private MatchCustomerAdapter adapter;
    private EmptyView emptyView;
    private String makeName;
    private String modeName;
    private int pageIndex = 1;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_match_customer);
        ButterKnife.bind(this);
        customerList = new ArrayList<>();
        makeName = getIntent().getStringExtra(Constant.MAKE_NAME);
        modeName = getIntent().getStringExtra(Constant.MODE_NAME);
    }

    @Override
    protected void setData() {
        setTitle("匹配客户："+makeName);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        xrv.setLayoutManager(linearLayoutManager);
//        getMatchCustomerParams();
        //添加空界面
        emptyView = new EmptyView(this, "没有符合此车源的意向客户");
        ((ViewGroup) xrv.getParent()).addView(emptyView);
        xrv.setEmptyView(emptyView);
        //添加错误界面监听
        errLayout.setOnErrorListener(new ErrorView.OnErrorListener() {
            @Override
            public void onError() {
                matchCustomerPresenter.loadMatchCustomerData(getMatchCustomerParams());
            }
        });
        xrv.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.VERTICAL, 1, R.color.common_gray_line));
        xrv.setPullRefreshEnabled(true);
        xrv.setLoadingMoreEnabled(true);
        xrv.setLoadingListener(this);
        adapter = new MatchCustomerAdapter(MatchCustomerActivity.this, R.layout.item_matchcustomer, customerList, xrv);
        xrv.setAdapter(adapter);
        matchCustomerPresenter = new MatchCustomerPresenter(this);
        matchCustomerPresenter.loadMatchCustomerData(getMatchCustomerParams());
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View view, Object o, int position) {
                //获取客户详情
                matchCustomerPresenter.loadClientDetails(getclientParams(customerList.get(position).getCustomerID() + ""));
            }

            @Override
            public boolean onItemLongClick(ViewGroup parent, View view, Object o, int position) {
                return false;
            }
        });
    }


    //op=getCarConcernUserList&IntendCarName=大众&salesId=129&pageIndex=1 & pageSize = 10
    private Map<String, String> getMatchCustomerParams() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("op", "getCarConcernUserList");
        params.put("IntendCarName", makeName+" "+modeName);
        params.put("salesId", String.valueOf(JzgApp.getUser().getUserID()));
        params.put("pageIndex", pageIndex + "");
        params.put("pageSize", String.valueOf(Constant.PAGECOUNT));
        Map<String, Object> signMap = new HashMap<>();
        signMap.putAll(params);
        params.put("sign", MD5Utils.getMD5Sign(signMap));
        LogUtil.e(TAG, UIUtils.getUrl(params));
        return params;
    }

    private Map<String, String> getclientParams(String id) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("Op", "getCustomerInfo");
        params.put("userID", String.valueOf(JzgApp.getUser().getUserID()));
        if (!TextUtils.isEmpty(id)) {
            params.put("customerId", id);
        }
        Map<String, Object> signMap = new HashMap<>();
        signMap.putAll(params);
        params.put("sign", MD5Utils.getMD5Sign(signMap));
        return params;
    }


    @Override
    public void showError(String error) {
        xrv.setVisibility(View.GONE);
        errLayout.setVisibility(View.VISIBLE);
        errLayout.setErrorText(error);
        stopLoading();
    }

    @Override
    public void matchCustomerSuccess(MatchCustomerData matchCustomerData) {
        stopLoading();
        if (matchCustomerData.getStatus() == Constant.SUCCESS) {
            errLayout.setVisibility(View.GONE);
            xrv.setVisibility(View.VISIBLE);
            List<MatchCustomerData.DataBean> newList = matchCustomerData.getData();
            if (newList != null&& newList.size()>0) {
                if(pageIndex==1){
                    customerList.clear();
                }
                customerList.addAll(newList);
                adapter.notifyDataSetChanged();
            }
        } else {
            MyToast.showShort(matchCustomerData.getMessage());
        }

    }

    /**
     * 客户详情回调
     *
     * @param customerDetail
     */
    @Override
    public void loadClientDetailSuccess(CustomerDetail customerDetail) {
        errLayout.setVisibility(View.GONE);
        if (customerDetail != null && customerDetail.getStatus() == Constant.SUCCESS) {
            Intent intent = new Intent(this, CustomerDetailActivity.class);
            intent.putExtra("detail", (Parcelable) customerDetail);
            startActivity(intent);
        }

    }

    @Override
    public void customerRedistribution(String msg) {
        MyToast.showShort(msg);
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
    public void onRefresh() {
        pageIndex = 1;
        matchCustomerPresenter.loadMatchCustomerData(getMatchCustomerParams());
    }

    @Override
    public void onLoadMore() {
        if(customerList.size()>0 && customerList.size()%Constant.PAGECOUNT!=0){
            xrv.loadMoreComplete();
            return ;
        }
        if(customerList.size()==0){
            xrv.loadMoreComplete();
            return;
        }
        pageIndex++;
        matchCustomerPresenter.loadMatchCustomerData(getMatchCustomerParams());
    }


}
