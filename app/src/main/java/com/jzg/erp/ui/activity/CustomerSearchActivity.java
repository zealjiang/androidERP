package com.jzg.erp.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.jakewharton.rxbinding.widget.TextViewTextChangeEvent;
import com.jzg.erp.R;
import com.jzg.erp.adapter.CustomerAdapter;
import com.jzg.erp.app.JzgApp;
import com.jzg.erp.base.BaseActivity;
import com.jzg.erp.global.Constant;
import com.jzg.erp.model.CustomerDetail;
import com.jzg.erp.model.CustomerInfo;
import com.jzg.erp.presenter.SearchPresenter;
import com.jzg.erp.view.ISearch;
import com.jzg.erp.widget.ClearableEditText;
import com.jzg.erp.widget.ErrorView;
import com.jzg.erp.widget.RecycleViewDivider;
import com.jzg.erp.widget.SpecifiedTextView;
import com.jzg.erp.widget.XRecyclerView;
import com.zhy.base.adapter.recyclerview.OnItemClickListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;

/**
 * 客户搜索界面
 * CustomerSearchActivity
 *
 * @author sunbl
 *         created at 2016/6/27 11:58
 */
public class CustomerSearchActivity extends BaseActivity implements ISearch, XRecyclerView.LoadingListener {
    @Bind(R.id.content)
    ClearableEditText content;
    @Bind(R.id.list)
    XRecyclerView xrv;
    @Bind(R.id.text_tishi)
    SpecifiedTextView textTishi;
    @Bind(R.id.err_layout)
    ErrorView errLayout;

    private SearchPresenter presenter;
    private List<CustomerInfo.CustomerListEntity> customerInfos;
    private CustomerAdapter adapter;
    private int pageIndex = 1;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        xrv.setLayoutManager(linearLayoutManager);
        xrv.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.VERTICAL, 1, R.color.common_gray_line));
        xrv.setPullRefreshEnabled(true);
        xrv.setLoadingMoreEnabled(true);
        xrv.setLoadingListener(this);
        customerInfos = new ArrayList<>();
        adapter = new CustomerAdapter(this, R.layout.item_customer, customerInfos, xrv);
        xrv.setAdapter(adapter);
        presenter = new SearchPresenter(this);

        RxTextView.textChangeEvents(content)
                .debounce(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TextViewTextChangeEvent>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(TextViewTextChangeEvent onTextChangeEvent) {
                        String keyword = onTextChangeEvent.text().toString();
                        if (TextUtils.isEmpty(keyword)) {
                            xrv.setVisibility(View.GONE);
                            textTishi.setVisibility(View.VISIBLE);
                            textTishi.setText(getResources().getText(R.string.search_empty));
                        }else{
                            presenter.search(keyword, pageIndex);
                        }
                    }
                });
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View view, Object o, int position) {
                //System.out.println("item :"+getCustomerParams(customerInfos.get(position).getCustomerID() + ""));
                presenter.getCustomerDetail(getCustomerParams(customerInfos.get(position).getCustomerID() + ""));
            }

            @Override
            public boolean onItemLongClick(ViewGroup parent, View view, Object o, int position) {
                return false;
            }
        });
        //添加错误界面监听
        errLayout.setOnErrorListener(new ErrorView.OnErrorListener() {
            @Override
            public void onError() {
                pageIndex = 1;
                presenter.search(content.getText().toString(), pageIndex);
            }
        });
    }

    /**
     * 获取查询客户信息详情需要的参数集合
     *
     * @return 参数集合
     */
    private Map<String, String> getCustomerParams(String id) {
        Map<String, String> params = new HashMap<>();
        params.put("Op", "getCustomerInfo");
        params.put("customerId", id);
        params.put("userID", String.valueOf(JzgApp.getUser().getUserID()));
        return params;
    }

    @Override
    protected void setData() {
        setTitle("搜索客户");
    }


    @Override
    public void showError(String error) {
        xrv.loadMoreComplete();
        xrv.refreshComplete();
        xrv.setVisibility(View.GONE);
        textTishi.setVisibility(View.VISIBLE);
        String tishi = content.getText().toString();
        if (!TextUtils.isEmpty(tishi)) {
            textTishi.setSpecifiedTextsColor("没有搜索到包含“" + tishi + "”的结果", tishi, Color.parseColor("#FF9F3F"));
        } else {
            textTishi.setText(getResources().getText(R.string.search_empty));
        }
    }


    @Override
    public void searcheSucceed(List<CustomerInfo.CustomerListEntity> customerInfo, boolean isFirst) {
        xrv.loadMoreComplete();
        xrv.refreshComplete();
        if (isFirst) {
            textTishi.setVisibility(View.GONE);
            customerInfos.clear();
        }
        if (null != customerInfo) {
            customerInfos.addAll(customerInfo);
            if (xrv.getVisibility() == View.GONE) {
                xrv.setVisibility(View.VISIBLE);
            }
            if (customerInfo.size() == 0) {
                showError("");
            } else {
                adapter.notifyDataSetChanged();
            }
        }

    }

    @Override
    public void showCustomerDetail(CustomerDetail customerDetail) {
        Intent intent = new Intent(this, CustomerDetailActivity.class);
        intent.putExtra("detail", (Parcelable) customerDetail);
        startActivity(intent);
    }


    @OnClick({R.id.tvSearch,R.id.ivLeft})
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tvSearch:
                finish();
                break;
            case R.id.ivLeft:
                finish();
                break;

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    public void onRefresh() {
        pageIndex = 1;
        presenter.search(content.getText().toString(), pageIndex);
    }

    @Override
    public void onLoadMore() {
        if(customerInfos.size()>0 && (customerInfos.size()%Constant.PAGECOUNT!=0)){
            xrv.loadMoreComplete();
            return;
        }
        if(customerInfos.size()==0){
            xrv.loadMoreComplete();
            return;
        }
        pageIndex++;
        presenter.search(content.getText().toString(), pageIndex);
    }
}
