package com.jzg.erp.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.jzg.erp.R;
import com.jzg.erp.base.NewBaseFragment;
import com.jzg.erp.event.TaskCountEvent;
import com.jzg.erp.utils.LogUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author: voiceofnet
 * email: pengkun@jingzhengu.com
 * phone:18101032717
 * @time: 2016/6/23 16:18
 * @desc:
 */
public class TaskTabFragment extends NewBaseFragment {
    @Bind(android.R.id.tabs)
    TabLayout tabs;
    @Bind(R.id.viewPager)
    ViewPager viewPager;
    TaskFragment todayTask;
    TaskFragment allTask;

    @Override
    protected void setView() {
        setTitle("待办事项");
        showBack(false);
        viewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public int getCount() {
                return 2;
            }

            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        if(todayTask==null){
                            todayTask = new TaskFragment(0);
                        }
                        return todayTask;
                    case 1:
                        if(allTask==null){
                            allTask = new TaskFragment(1);
                        }
                        return allTask;
                    default:
                        return new TaskFragment(0);
                }
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position) {
                    case 0:
                        return "今日事项(0)";
                    case 1:
                        return "全部待办(0)";
                    default:
                        return "今日事项(0)";
                }
            }
        });
        tabs.setupWithViewPager(viewPager);
    }
    @Subscribe
    public void onEventMainThread(TaskCountEvent event){
        if(event!=null){
            tabs.getTabAt(0).setText("今日事项("+event.getTodaySum()+")");
            tabs.getTabAt(1).setText("全部待办("+event.getAllSum()+")");
        }
    }

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tab_task, container, false);
        ButterKnife.bind(this, rootView);
        tabs.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
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
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.e(TAG,"TaskTabFragment....onDestroy");
        EventBus.getDefault().unregister(this);
    }
}
