package com.jzg.erp.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jzg.erp.R;
import com.jzg.erp.adapter.TaskAdapter;
import com.jzg.erp.app.JzgApp;
import com.jzg.erp.base.NewBaseFragment;
import com.jzg.erp.event.TaskCountEvent;
import com.jzg.erp.global.Constant;
import com.jzg.erp.model.WaitingItemWrapper;
import com.jzg.erp.presenter.WaitItemPresenter;
import com.jzg.erp.ui.activity.TaskDetailActivity;
import com.jzg.erp.utils.LogUtil;
import com.jzg.erp.utils.MD5Utils;
import com.jzg.erp.utils.UIUtils;
import com.jzg.erp.view.IWaitingItems;
import com.jzg.erp.widget.EmptyView;
import com.jzg.erp.widget.ErrorView;
import com.jzg.erp.widget.RecycleViewDivider;
import com.jzg.erp.widget.XRecyclerView;
import com.zhy.base.adapter.recyclerview.OnItemClickListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

@SuppressLint("ValidFragment")
public class TaskFragment extends NewBaseFragment implements OnItemClickListener, XRecyclerView.LoadingListener ,IWaitingItems{
    @Bind(R.id.err_layout)
    ErrorView errLayout;
    @Bind(R.id.xrv)
    XRecyclerView xrv;
    private View rootView;
    private TaskAdapter adapter;
    private WaitItemPresenter presenter;
    private List<WaitingItemWrapper.WaitingItem> totalList;
    private int type;
    private int pageIndex = 1;

    private boolean isRefreshing = false;
    public TaskFragment(int type) {
        this.type = type;
        presenter = new WaitItemPresenter(this);
    }
    public TaskFragment() {
    }

    @Override
    protected void setView() {

    }

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_task, container, false);
        ButterKnife.bind(this, rootView);
        xrv.setLayoutManager(new LinearLayoutManager(context));
        xrv.setRefreshProgressStyle(ProgressStyle.SysProgress);
        xrv.setLoadingMoreProgressStyle(ProgressStyle.SysProgress);
        xrv.addItemDecoration(new RecycleViewDivider(context, LinearLayoutManager.VERTICAL, 2,R.color.common_gray_dark));
        xrv.setLoadingListener(this);
        errLayout.setOnErrorListener(new ErrorView.OnErrorListener() {
            @Override
            public void onError() {
                onRefresh();
            }
        });
        EmptyView emptyView = null;
        if(type==0)
            emptyView = new EmptyView(context, "暂无今日待办事项");
        else
            emptyView = new EmptyView(context, "暂无待办事项");
        ((ViewGroup) xrv.getParent()).addView(emptyView);
        emptyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRefresh();
            }
        });
        xrv.setEmptyView(emptyView);
        adapter = new TaskAdapter(context, R.layout.item_task, totalList,xrv);
        xrv.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        return rootView;
    }

    @Override
    protected void initData() {
        totalList = new ArrayList<>();
    }

    @Override
    public void onResume() {
        super.onResume();
        onRefresh();
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser && !isRefreshing){
            onRefresh();
        }
    }

    @Override
    public void showError(String error) {
        super.showError(error);
        stopLoading();
        if (xrv == null) {
            xrv = (XRecyclerView) rootView.findViewById(R.id.xrv);
            errLayout = (ErrorView) rootView.findViewById(R.id.err_layout);
        }
        xrv.setVisibility(View.GONE);
        errLayout.setVisibility(View.VISIBLE);
        errLayout.setErrorText(error);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onItemClick(ViewGroup parent, View view, Object o, int position) {
        WaitingItemWrapper.WaitingItem item = totalList.get(position);
        Intent intent  = new Intent(getActivity(),TaskDetailActivity.class);
        intent.putExtra("item",item);
        jump(intent);
    }

    @Override
    public boolean onItemLongClick(ViewGroup parent, View view, Object o, int position) {
        return false;
    }


    @Override
    public void onRefresh() {
        if(isRefreshing){
            stopLoading();
            return;
        }
        pageIndex=1;
        getData();
    }

    private void getData(){
        Map<String,String> params = new HashMap<>();
        if(type==0){
            params.put("op","today");
        }else{
            params.put("op","all");
        }
        params.put("pageIndex",String.valueOf(pageIndex));
        params.put("pageSize",String.valueOf(Constant.PAGECOUNT));
        params.put("SalesId", String.valueOf(JzgApp.getUser().getUserID()));
        Map<String, Object> map = new HashMap<>();
        map.putAll(params);
        String sign = MD5Utils.getMD5Sign(map);
        params.put("sign", sign);
        LogUtil.e(TAG, UIUtils.getUrl(params));
        presenter.loadData(params);
    }

    @Override
    public void onLoadMore() {
        if(totalList.size()==0){
            xrv.loadMoreComplete();
            return;
        }
        if(totalList.size()>0 && totalList.size()% Constant.PAGECOUNT!=0){
            xrv.loadMoreComplete();
            return;
        }
        pageIndex++;
        getData();
    }

    private void stopLoading(){
        isRefreshing = false;
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
    public void onSucceed(WaitingItemWrapper wrapper) {
        stopLoading();
        xrv.setVisibility(View.VISIBLE);
        errLayout.setVisibility(View.GONE);
        List<WaitingItemWrapper.WaitingItem> resultList = wrapper.getData();
        int allSum = wrapper.getAllSum();
        int todaySum = wrapper.getTodaySum();
        TaskCountEvent event = new TaskCountEvent(0,allSum,todaySum);
        EventBus.getDefault().post(event);
        if(pageIndex==1){
            totalList.clear();
        }
        if(resultList!=null && resultList.size()>0){
            totalList.addAll(resultList);
        }
        adapter.notifyDataSetChanged();

    }
}
