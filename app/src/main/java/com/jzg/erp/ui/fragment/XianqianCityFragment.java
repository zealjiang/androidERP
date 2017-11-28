package com.jzg.erp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jzg.erp.R;
import com.jzg.erp.model.XianqianCity;
import com.jzg.erp.presenter.XianqianCityPresenter;
import com.jzg.erp.ui.activity.RegionSellActivity;
import com.jzg.erp.utils.MD5Utils;
import com.jzg.erp.utils.MyToast;
import com.jzg.erp.utils.ShowDialogTool;
import com.jzg.erp.view.IXianqianCity;

import java.util.HashMap;
import java.util.Map;


public class XianqianCityFragment extends Fragment implements OnClickListener,IXianqianCity {

	private RelativeLayout mCityBtn;
	private TextView mCityText;
	private TextView mPfbzResult;
	private TextView mTvCity;
	private static final int TO_REGION_CODE = 2001;
	// 选择地区线上
	private static final String jzgGuyeHttp = "http://api.jingzhengu.com";

	private XianqianCityPresenter mXianqianCityPresenter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_tools_xianqian_city,null);
		 init(view);
		return view;
	}
	private void init(View view){
		mCityBtn = (RelativeLayout) view.findViewById(R.id.tools_xianqian_city_Btn);
		mCityBtn.setOnClickListener(this);
		mCityText = (TextView) view.findViewById(R.id.tools_xianqian_city_choose);
		mPfbzResult = (TextView) view.findViewById(R.id.tools_xianqian_city_result);
		mTvCity = (TextView) view.findViewById(R.id.tv_city);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tools_xianqian_city_Btn:
			//选择城市
			Intent in = new Intent(getActivity(),RegionSellActivity.class);
			in.putExtra("hideAllRegion", "hideAllRegion");
			startActivityForResult(in, TO_REGION_CODE);
			break;
		default:
			break;
		}
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case TO_REGION_CODE:
			if(data!=null){
				String province = data.getStringExtra("province");
				String city = data.getStringExtra("city");
				int cityId = data.getIntExtra("mCityId", 0);
				int provinceId = data.getIntExtra("mProvinceId", 0);
				mCityId = String.valueOf(cityId);
				if(city.equals(province)){
					mCityText .setText(province);
				}else{
					mCityText.setText(province+" "+city);
				}

				//查询
				if("0".equals(mCityId)){
					ShowDialogTool.showCenterToast(getActivity(), "请选择城市！");
				}else{
					toResuestXQCity();
				}
			}
			break;

		default:
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	private String mCityId = "0";
	
	private void toResuestXQCity(){

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cityId", mCityId);
		//获取用户信息
		mXianqianCityPresenter = new XianqianCityPresenter(this);
		mXianqianCityPresenter.getCity(jzgGuyeHttp,mCityId,MD5Utils.getMD5Sign(map,MD5Utils.PRIVATE_KEY_XIANQIAN));
	}

	@Override
	public void succeed(XianqianCity data) {
		mTvCity.setText(mCityText.getText().toString());
		mPfbzResult.setText(data.getCityStandar());
	}

	/**
	 * 显示错误信息
	 *
	 * @param error 错误信息
	 */
	@Override
	public void showError(String error) {
		MyToast.showLong(getResources().getString(R.string.error_noConnect));
	}

	/**
	 * 显示加载
	 */
	@Override
	public void showDialog() {
		ShowDialogTool.showLoadingDialog(this.getActivity());
	}

	/**
	 * 关闭加载
	 */
	@Override
	public void dismissDialog() {
		ShowDialogTool.dismissLoadingDialog();
	}
}
