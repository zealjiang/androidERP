package com.jzg.erp.ui.fragment;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jzg.erp.R;
import com.jzg.erp.adapter.RecordAdapter;
import com.jzg.erp.app.JzgApp;
import com.jzg.erp.event.RecordAddOkEvent;
import com.jzg.erp.model.CustomerDetail;
import com.jzg.erp.model.TaskItemGroupWrapper;
import com.jzg.erp.presenter.FollowUpPresenter;
import com.jzg.erp.ui.activity.PopupActivity;
import com.jzg.erp.utils.LogUtil;
import com.jzg.erp.utils.MD5Utils;
import com.jzg.erp.utils.MyToast;
import com.jzg.erp.utils.UIUtils;
import com.jzg.erp.view.IGetFollowUp;
import com.jzg.erp.widget.CustomButton;
import com.jzg.erp.widget.EmptyView;
import com.jzg.erp.widget.ErrorView;
import com.jzg.erp.widget.RecycleViewDivider;
import com.jzg.erp.widget.XRecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

@SuppressLint("ValidFragment")
public class RecordFragment extends Fragment implements IGetFollowUp {

    private static final String TAG ="RecordFragment";
    @Bind(R.id.xrv)
    XRecyclerView xrv;
    @Bind(R.id.errView)
    ErrorView errView;
    @Bind(R.id.btnAddNew)
    CustomButton btnAddNew;
    private String customerID;
    private FollowUpPresenter presenter;
    private List<TaskItemGroupWrapper.TaskGroup.TaskItem> itemList;
    private RecordAdapter adapter;
    private CustomerDetail.DataBean customer;

    public static RecordFragment newInstance(CustomerDetail.DataBean detail) {
        RecordFragment recordFragment = new RecordFragment();
        Bundle args = new Bundle();
        args.putParcelable("CustomerDetail.DataBean", detail);
        recordFragment.setArguments(args);
        return recordFragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new FollowUpPresenter(this);
        EventBus.getDefault().register(this);
        Bundle args = getArguments();
        if (args != null) {
            this.customer = args.getParcelable("CustomerDetail.DataBean");
            this.customerID = String.valueOf(customer.getCustomerID());
            itemList = new ArrayList<>();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.fragment_common_followup, container, false);
        ButterKnife.bind(this, parent);
        xrv.setLayoutManager(new LinearLayoutManager(getActivity()));
        xrv.setPullRefreshEnabled(false);
        xrv.setLoadingMoreEnabled(false);
        xrv.addItemDecoration(new RecycleViewDivider(getActivity(), LinearLayoutManager.VERTICAL, 6, R.color.common_gray_bg));
        EmptyView emptyView = new EmptyView(getActivity(), "暂无跟进记录");
        ((ViewGroup) xrv.getParent()).addView(emptyView);
        xrv.setEmptyView(emptyView);
        adapter =  new RecordAdapter(getActivity(),R.layout.item_followup_rec2,itemList,xrv);
        xrv.setAdapter(adapter);
        btnAddNew.setVisibility(View.VISIBLE);
        return parent;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        btnAddNew.setText("+填写跟进记录");
        loadData();
    }

    private void loadData(){
        Map<String, String> params = new HashMap<>();
        params.put("customerID", customerID);
        params.put("userID", String.valueOf(JzgApp.getUser().getUserID()));
        Map<String, Object> map = new HashMap<>();
        map.putAll(params);
        String sign = MD5Utils.getMD5Sign(map);
        params.put("sign", sign);
        LogUtil.e(TAG, UIUtils.getUrl(params));
        presenter.loadDataRec(params);
    }
    @Subscribe
    public void onEventMainThread(RecordAddOkEvent event){
        if(!TextUtils.isEmpty(event.getMsg()))
            customer.setPreBuyTime(event.getMsg());
        loadData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @OnClick(R.id.btnAddNew)
    public void onClick() {
        if(customer.getCustomerStatus()==4){
            MyToast.showShort("战败客户不能添加跟进记录");
            return;
        }
        if(customer.getCustomerStatus()==3){
            MyToast.showShort("成交客户不能添加跟进记录");
            return;
        }
        Intent intent = new Intent(getActivity(), PopupActivity.class);
        intent.putExtra("customerID",customerID);
        intent.putExtra("PreBuyTime", customer.getPreBuyTime());
        getActivity().startActivity(intent);
    }

    @Override
    public void onSucceed(List<TaskItemGroupWrapper.TaskGroup> resultList) {
        itemList.clear();
        if(resultList!=null && resultList.size()>0){
            for(TaskItemGroupWrapper.TaskGroup group:resultList){
                String key = group.getKey();
                List<TaskItemGroupWrapper.TaskGroup.TaskItem> items = group.getDataList();
                items.get(0).setTag(key);
                itemList.addAll(items);
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onError(String msg) {

    }
}
