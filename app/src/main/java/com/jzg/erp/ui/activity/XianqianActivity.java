package com.jzg.erp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.jzg.erp.R;
import com.jzg.erp.adapter.FragmentTabAdapter;
import com.jzg.erp.base.BaseActivity;
import com.jzg.erp.ui.fragment.XianqianCityFragment;
import com.jzg.erp.ui.fragment.XianqianPfbzFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Function: 限迁查询. <br/>
 * Date: 2014-11-21 下午8:29:11 <br/>
 * 
 * @author 郑有权
 * @version
 * @since JDK 1.6
 * @see
 */
public class XianqianActivity extends BaseActivity
implements OnClickListener {

	private List<Fragment> mFragmentList;
	private FragmentTabAdapter mFragmentTabAdapter;
	private int currentTabIndex;
	private Button mCityBtn, mPfbzBtn;


	@Override
	protected void initViews(Bundle savedInstanceState) {
		setContentView(R.layout.activity_xianqian);
		ButterKnife.bind(this);
		init();
	}

	@Override
	protected void setData() {
		setTitle("限迁查询");
	}


	public void init() {

		mCityBtn = (Button) findViewById(R.id.tools_xianqian_city);
		mPfbzBtn = (Button) findViewById(R.id.tools_xianqian_paifangbz);
		mCityBtn.setOnClickListener(this);
		mPfbzBtn.setOnClickListener(this);
		mFragmentList = new ArrayList<Fragment>();
		mFragmentList.add(new XianqianCityFragment());
		mFragmentList.add(new XianqianPfbzFragment());
		mFragmentTabAdapter = new FragmentTabAdapter(mFragmentList, this,
				currentTabIndex);
		// 默认显示第一页
		mCityBtn.setSelected(true);
		mPfbzBtn.setSelected(false);
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.add(R.id.xianqian_container, mFragmentList.get(0));
		ft.commit();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tools_xianqian_city:
			mCityBtn.setSelected(true);
			mPfbzBtn.setSelected(false);
			mFragmentTabAdapter.settingFragment(0,
					R.id.xianqian_container);
			currentTabIndex = 0;
			break;
		case R.id.tools_xianqian_paifangbz:
			mCityBtn.setSelected(false);
			mPfbzBtn.setSelected(true);
			mFragmentTabAdapter.settingFragment(1,
					R.id.xianqian_container);
			currentTabIndex = 1;
			break;
		case R.id.ivLeft:
				finish();
			break;
		case  R.id.tvLeft:
			finish();
			break;
		default:
			break;
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		Fragment fragment = (Fragment) mFragmentList.get(currentTabIndex);
		if(fragment!=null&data!=null){
			fragment.onActivityResult(requestCode, resultCode, data);
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
}
