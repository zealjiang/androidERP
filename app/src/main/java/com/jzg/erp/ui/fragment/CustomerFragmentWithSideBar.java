package com.jzg.erp.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jzg.erp.R;
import com.jzg.erp.adapter.CustomerAdapter;
import com.jzg.erp.base.NewBaseFragment;
import com.jzg.erp.model.CustomerInfo;
import com.jzg.erp.utils.CharacterParser;
import com.jzg.erp.utils.PinyinComparator;
import com.jzg.erp.widget.RecycleViewDivider;
import com.jzg.erp.widget.SideBar;
import com.jzg.erp.widget.XRecyclerView;

import org.honorato.multistatetogglebutton.MultiStateToggleButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 客户界面
 */
public class CustomerFragmentWithSideBar extends NewBaseFragment {
    @Bind(R.id.mstl)
    MultiStateToggleButton mstl;
    @Bind(R.id.customer_xrc)
    XRecyclerView xrv;
    @Bind(R.id.sidrbar)
    SideBar sidrbar;
    @Bind(R.id.dialog)
    TextView dialog;
    private CustomerAdapter adapter;
    private List<CustomerInfo> mSortList;
    private CharacterParser characterParser;

    @Override
    protected void setView() {
        mstl.setStates(new boolean[]{true, false, false, false});
        showBack(false);
        setTitle(R.string.customer);
        xrv.setAdapter(adapter);
    }

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_customer, container, false);
        ButterKnife.bind(this, rootView);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        xrv.setLayoutManager(linearLayoutManager);
        xrv.setRefreshProgressStyle(ProgressStyle.CubeTransition);
        xrv.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        xrv.addItemDecoration(new RecycleViewDivider(context, LinearLayoutManager.VERTICAL, 1, R.color.common_gray_line));

        sidrbar.setTextView(dialog);

        // 设置右侧触摸监听
        sidrbar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {

            }
        });
        return rootView;
    }

    @Override
    protected void initData() {
        filledData();
        // 根据a-z进行排序源数据
        PinyinComparator pinyinComparator = new PinyinComparator();
        Collections.sort(mSortList, pinyinComparator);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    private List<CustomerInfo> filledData() {
        mSortList = new ArrayList<>();
        return mSortList;


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }
}
