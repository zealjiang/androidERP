package com.jzg.erp.ui.fragment;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jzg.erp.R;
import com.jzg.erp.adapter.CarSourceTagAdapter;
import com.jzg.erp.app.JzgApp;
import com.jzg.erp.base.BaseActivity;
import com.jzg.erp.base.NewBaseFragment;
import com.jzg.erp.global.Constant;
import com.jzg.erp.model.CarSourceData;
import com.jzg.erp.model.CarSourceTagData;
import com.jzg.erp.model.CustomerDetail;
import com.jzg.erp.presenter.FocusCarPresenter;
import com.jzg.erp.ui.activity.AddFocusCarActivity;
import com.jzg.erp.ui.activity.CarDetailActivity;
import com.jzg.erp.utils.LogUtil;
import com.jzg.erp.utils.MyToast;
import com.jzg.erp.utils.UIUtils;
import com.jzg.erp.view.IFocusCar;
import com.jzg.erp.widget.CustomButton;
import com.jzg.erp.widget.EmptyView;
import com.jzg.erp.widget.ErrorView;
import com.jzg.erp.widget.RecycleViewDivider;
import com.jzg.erp.widget.XRecyclerView;
import com.zhy.base.adapter.recyclerview.OnItemClickListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/***
 * 客户详情关注车页面
 */
@SuppressLint("ValidFragment")
public class FocusCarFragment extends NewBaseFragment implements IFocusCar {

    private CustomerDetail.DataBean detail = null;
    @Bind(R.id.btnAddFocusCar)
    CustomButton btnAddFocusCar;
    @Bind(R.id.customer_xrc)
    XRecyclerView xrv;
    @Bind(R.id.err_layout)
    ErrorView errorView;
    private FocusCarPresenter presenter;
    private List<CarSourceTagData.DataBean.DataListBean> carsourceDatas = new ArrayList<>();
    private CarSourceTagAdapter carSourceAdapter;
    private int pageIndex = 1;


    public static FocusCarFragment newInstance(CustomerDetail.DataBean detail) {
        FocusCarFragment focusCarFragment = new FocusCarFragment();
        Bundle args = new Bundle();
        args.putParcelable("CustomerDetail.DataBean", detail);
        focusCarFragment.setArguments(args);
        return focusCarFragment;
    }

    @Override
    protected void initData() {
        Bundle args = getArguments();
        if (args != null) {
            detail = args.getParcelable("CustomerDetail.DataBean");
        }
        presenter = new FocusCarPresenter(this);
        ((BaseActivity)getActivity()).showDialog();
        presenter.getFocusTagCarList(getParams(pageIndex));
        EventBus.getDefault().register(this);
    }

    @Override
    protected void setView() {

    }

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_focus_car, container, false);
        ButterKnife.bind(this, view);
        //内容显示区域
        xrv.setLayoutManager(new LinearLayoutManager(context));
        xrv.setPullRefreshEnabled(false);
        xrv.setLoadingMoreEnabled(false);
        xrv.addItemDecoration(new RecycleViewDivider(context, LinearLayoutManager.VERTICAL, 6, R.color.common_gray_bg));
        EmptyView emptyView = new EmptyView(context, "暂无关注车辆");
        ((ViewGroup) xrv.getParent()).addView(emptyView);
        xrv.setEmptyView(emptyView);
        errorView.setOnErrorListener(new ErrorView.OnErrorListener() {
            @Override
            public void onError() {
                pageIndex = 1;
                presenter.getFocusTagCarList(getParams(pageIndex));
            }
        });

        carSourceAdapter = new CarSourceTagAdapter(context, R.layout.carsource_item_tag, carsourceDatas, xrv);
        xrv.setAdapter(carSourceAdapter);
        carSourceAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View view, Object o, int position) {
                Intent intent = new Intent(context, CarDetailActivity.class);
                intent.putExtra(Constant.MAKE_NAME, carsourceDatas.get(position).getMakeName());
                intent.putExtra(Constant.MODE_NAME, carsourceDatas.get(position).getModelName());
                intent.putExtra(Constant.CARSTATUS, carsourceDatas.get(position).getCarStatus2()+"");
                intent.putExtra(Constant.CARSOURCEID, carsourceDatas.get(position).getCarSourceID()+"");
                intent.putExtra(Constant.CARID, carsourceDatas.get(position).getID()+"");
                intent.putExtra(Constant.CARDETAILPATH,carsourceDatas.get(position).getCarDetailPath());
                startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(ViewGroup parent, View view, Object o, int position) {
                return false;
            }
        });
        return view;
    }

    @Override
    public void showError(String error) {
        super.showError(error);
        xrv.refreshComplete();
        xrv.loadMoreComplete();
        xrv.setVisibility(View.GONE);
        errorView.setVisibility(View.VISIBLE);
    }



    private Map<String, String> getParams(int indext) {
        Map<String, String> map = new HashMap<>();
        map.put("op", "focusCarList");
        map.put("customerId", detail.getCustomerID() + "");
        map.put("userID", String.valueOf(JzgApp.getUser().getUserID()));
        map.put("pageIndex", indext + "");
        map.put("pageCount", Integer.MAX_VALUE + "");
        LogUtil.e(TAG, UIUtils.getUrl(map));
        return map;
    }

    @Subscribe
    public void onEventMainThread(String objects) {
        xrv.setRefreshing(true);
        pageIndex = 1;
        presenter.getFocusTagCarList(getParams(pageIndex));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }

    @OnClick(R.id.btnAddFocusCar)
    public void onClick() {
        if(detail.getCustomerStatus()==4){
            MyToast.showShort("战败客户不能添加关注车");
            return;
        }
        if(detail.getCustomerStatus()==3){
            MyToast.showShort("成交客户不能添加关注车");
            return;
        }
        if(detail.getCustomerStatus()==2){
            MyToast.showShort("已预订客户不能添加关注车");
            return;
        }
        Intent intent = new Intent(context, AddFocusCarActivity.class);
        intent.putExtra("id", detail.getCustomerID() + "");
        startActivity(intent);
    }

    @Override
    public void showFocusCar(CarSourceData carSourceData, int pageIdex) {

    }

    @Override
    public void showFocusCarWithTag (CarSourceTagData carSourceData){
        if (carSourceData.getStatus() == Constant.SUCCESS) {
            List<CarSourceTagData.DataBean> dataList = carSourceData.getData();
            if(dataList!=null && dataList.size()>0){
                if (errorView.getVisibility() == View.VISIBLE) {
                    errorView.setVisibility(View.GONE);
                }
                carsourceDatas.clear();
                for (int i = 0; i < dataList.size(); i++) {
                    dataList.get(i).getDataList().get(0).setTag(dataList.get(i).getKey());
                    carsourceDatas.addAll(dataList.get(i).getDataList());
                }
            }
            carSourceAdapter.notifyDataSetChanged();
        } else {
            MyToast.showShort(carSourceData.getMessage());
        }
    }

}
