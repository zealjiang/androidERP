package com.jzg.erp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jzg.erp.R;
import com.jzg.erp.base.NewBaseFragment;
import com.jzg.erp.model.CustomerInfo;
import com.jzg.erp.ui.activity.CustomerSearchActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JZG on 2016/6/23.
 */
public class CustomerTabFragment extends NewBaseFragment {

    @Bind(android.R.id.tabs)
    TabLayout tabs;
    @Bind(R.id.viewPager)
    ViewPager viewPager;


    @Override
    protected void setView() {
        setTitle("客户");
        showBack(false);
        setImageRight(R.mipmap.sousuo003);

    }
    @Subscribe
    public void onEnventMainThread(CustomerInfo customerInfo) {
//        xrv.setRefreshing(true);
        tabs.getTabAt(0).setText("意向 ("+customerInfo.getCountIntend()+")");
        tabs.getTabAt(1).setText("预订 ("+customerInfo.getCountBooking()+")");
        tabs.getTabAt(2).setText("成交 ("+customerInfo.getCountDeal()+")");
        tabs.getTabAt(3).setText("战败 ("+customerInfo.getCountFailure()+")");
    }
    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tab_customer, container, false);
        ButterKnife.bind(this, rootView);
        viewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public int getCount() {
                return 4;
            }

            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return new CustomerFragment("意向");
                    case 1:
                        return new CustomerFragment("预订");
                    case 2:
                        return new CustomerFragment("成交");
                    case 3:
                        return new CustomerFragment("战败");
                    default:
                        return new CustomerFragment("战败");
                }
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position) {
                    case 0:
                        return "意向";
                    case 1:
                        return "预订";
                    case 2:
                        return "成交";
                    case 3:
                        return "战败";
                    default:
                        return "成交";
                }
            }
        });
        tabs.setupWithViewPager(viewPager);
        return rootView;
    }

    @Override
    protected void initData() {
        EventBus.getDefault().register(this);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }

    @OnClick(R.id.ivRight)
    public void onClick(View view) {
        startActivity(new Intent(getActivity(), CustomerSearchActivity.class));
    }
}
