package com.jzg.erp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.jzg.erp.R;

import java.util.List;

/**
 * Fragment显示适配器类 可用于界面切换和界面数据状态保存
 * 
 * @author 作者 E-mail: 汪渝栋
 * @version 创建时间：2014-12-23 上午9:44:37 类说明
 */
public class FragmentTabAdapter
{
	private List<Fragment> mFragments;// 所有Fragment页

	private FragmentActivity mFragmentActivity;

	private int currentTab;// 当前Tab页面索引

	public FragmentTabAdapter(List<Fragment> mFragments,
							  FragmentActivity mFragmentActivity, int currentTab)
	{
		super();
		this.mFragments = mFragments;
		this.mFragmentActivity = mFragmentActivity;
		this.currentTab = currentTab;
	}


	/**
	 * 设置需要显示的fragment
	 * 
	 * @param index
	 *            当前tab的索引
	 * @param layoutId
	 *            添加fragment的容器
	 */
	public void settingFragment(int index,int layoutId)
	{
		Fragment fragment = mFragments.get(index);
		FragmentTransaction ft = obtainFragmentTransaction(index);
		mFragments.get(currentTab).onPause(); // 暂停当前tab

		
		if (fragment.isAdded())
		{
			fragment.onResume(); // 启动目标tab的onResume()
		} else
		{
			ft.add(layoutId, fragment);
		}
		showTab(index); // 显示目标tab
		ft.commitAllowingStateLoss();

	}

	/**
	 * 切换tab
	 * 
	 * @param idx
	 */
	private void showTab(int idx)
	{
		for (int i = 0; i < mFragments.size(); i++)
		{
			Fragment fragment = mFragments.get(i);
			FragmentTransaction ft = obtainFragmentTransaction(idx);

			if (idx == i)
			{
				ft.show(fragment);
			} else
			{
				ft.hide(fragment);
			}
			ft.commit();
		}
		currentTab = idx; // 更新目标tab为当前tab
	}

	/**
	 * 获取一个带动画的FragmentTransaction
	 * 
	 * @param index
	 * @return
	 */
	private FragmentTransaction obtainFragmentTransaction(int index)
	{
		FragmentTransaction ft = mFragmentActivity.getSupportFragmentManager()
				.beginTransaction();
		// 设置切换动画
		if (index > currentTab)
		{
			ft.setCustomAnimations(R.anim.slide_left_in, R.anim.slide_left_out);
		} else
		{
			ft.setCustomAnimations(R.anim.slide_right_in,
					R.anim.slide_right_out);
		}
		return ft;
	}
}
