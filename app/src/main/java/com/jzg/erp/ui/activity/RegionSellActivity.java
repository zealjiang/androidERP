package com.jzg.erp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SlidingDrawer;
import android.widget.SlidingDrawer.OnDrawerCloseListener;
import android.widget.SlidingDrawer.OnDrawerOpenListener;
import android.widget.TextView;
import android.widget.Toast;

import com.jzg.erp.R;
import com.jzg.erp.adapter.CustomArrayAdapter;
import com.jzg.erp.adapter.Simple2Adapter;
import com.jzg.erp.base.BaseActivity;
import com.jzg.erp.model.City;
import com.jzg.erp.model.CustomData;
import com.jzg.erp.model.Province;
import com.jzg.erp.model.SimpleList;
import com.jzg.erp.presenter.AreaCityPresenter;
import com.jzg.erp.presenter.AreaProvincePresenter;
import com.jzg.erp.utils.MD5Utils;
import com.jzg.erp.utils.MyToast;
import com.jzg.erp.view.IAreaCity;
import com.jzg.erp.view.IAreaProvince;

import java.util.ArrayList;
import java.util.List;


/**
 * Function: TODO 地区选择. <br/>
 */
@SuppressWarnings("deprecation")
public class RegionSellActivity extends BaseActivity implements OnItemClickListener,
		OnDrawerOpenListener, OnDrawerCloseListener, OnClickListener,IAreaProvince,IAreaCity
		{

	/**
	 * 从筛选设置界面选择地区跳转到本界面的标示符(根据这个标示符做数据操作)
	 */
	private String carFilterRegionFlag;

	/**
	 * 界面Handler控制
	 */
	private Handler mHandler;

	/**
	 * 省列表是否被点击
	 */
	private boolean isProvinceClick = false;

	/**
	 * 省份
	 */
	private String mProvince;

	/**
	 * 市
	 */
	private String mCity;

	/**
	 * 颜色
	 */
	private String mColor;

	/**
	 * 省列表被点击的位置
	 */
	private int mProvinceContentListClickPostion = -1;

	/**
	 * 上次选择位置
	 */
	private int oldPoc;

	/**
	 * 省结果集
	 */
	private ArrayList<Province.ContentBean> mProvinceList;

	/**
	 * 市结果集
	 */
	private ArrayList<City.ContentBean> mCityList;

	/**
	 * 省结果集带字体颜色、字体内容及位置的适配器缓存列表
	 */
	private List<SimpleList> list;

	/**
	 * 市结果集带字体颜色、字体内容及位置的适配器缓存列表
	 */
	private List<SimpleList> cityslist;

	/**
	 * 筛选过程中选择的市选项
	 */
	private List<City> filterCitys;

	/**
	 * 筛选过程中选择的省选项
	 */
	private List<Province> filterProvinces;

	private CustomArrayAdapter adapter = null;
	private static List<CustomData> mCustomData = new ArrayList<CustomData>();

	private boolean isFirstAdapter;

	private LinearLayout chooseLayout;

	private Simple2Adapter simple2Adpter;
	private ListView mProvinceContent;
	private SlidingDrawer mCarDetailInfoCityDrawer;
	private ImageView mCarDetailInfoCityHandle;
	private ListView mCarDetailInfoCityContent;

	/**
	 * 市适配器
	 */
	private Simple2Adapter cityAdapter;

	/**
	 * 市列表显示类容
	 */
	private List<String> cityList;


	private int mCityId;

	private int mProvinceId;

	/**
	 * 影藏全部地区标记(不为空表示隐藏全部地区)
	 */
	private String hideAllRegionFlag;

	private AreaProvincePresenter mAreaProvincePresenter;
	private AreaCityPresenter mAreaCityPresenter;

	@Override
	protected void initViews(Bundle savedInstanceState) {
		setContentView(R.layout.activity_region_sell);
		mHandler = getHandler();
		Intent in = getIntent();

		hideAllRegionFlag = in.getStringExtra("hideAllRegion");

		carFilterRegionFlag = in.getStringExtra("carfilter_region");
		if (in.getBooleanExtra("flag", false)) {
		}

	}

	@Override
	protected void setData() {
		setTitle("选择地区");
		init();
		startProvinceThead();
	}
	public void init() {
		filterCitys = new ArrayList<City>();
		filterCitys.clear();
		filterProvinces = new ArrayList<Province>();
		filterProvinces.clear();
		mCustomData.clear();

		mProvinceContent = (ListView) findViewById(R.id.province_content);
		mProvinceContent.setOnItemClickListener(this);

		// 初始化汽车市
		mCarDetailInfoCityDrawer = (SlidingDrawer) this
				.findViewById(R.id.car_detail_info_city_drawer);
		mCarDetailInfoCityHandle = (ImageView) this
				.findViewById(R.id.car_detail_info_city_handle);
		mCarDetailInfoCityContent = (ListView) this
				.findViewById(R.id.car_detail_info_city_content);
		mCarDetailInfoCityContent.setOnItemClickListener(this);
		mCarDetailInfoCityDrawer.setOnDrawerOpenListener(this);
		mCarDetailInfoCityDrawer.setOnDrawerCloseListener(this);

	}

	private Handler getHandler() {
		return new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if(!RegionSellActivity.this.isFinishing()) {
//					hideLoading();
				}
				int id = msg.what;
				switch (id) {
				case R.id.net_error:
					Toast.makeText(RegionSellActivity.this, "网络异常，请在网络正常的时候访问！！！", Toast.LENGTH_SHORT).show();
				case R.id.modifyCity:

					Intent it = new Intent();
					it.putExtra("province", mProvince);
					it.putExtra("city", mCity);
					it.putExtra("mCityId", mCityId);
					it.putExtra("mProvinceId", mProvinceId);
					
					setResult(Activity.RESULT_OK, it);
					finish();
					break;
				case R.id.close_dialog:
//					hideLoading();
					Toast.makeText(RegionSellActivity.this, "网络不可用", Toast.LENGTH_SHORT).show();
					break;
				default:
					break;
				}
			}



		};
	}

	private void assemblyProvinceList(List<Province.ContentBean> provinces) {

		mProvinceList = (ArrayList<Province.ContentBean>)provinces;
		list = new ArrayList<SimpleList>();
		for (int i = 1; i <= provinces.size(); i++) {
			int color = getResources().getColor(
					android.R.color.black);
			String name = provinces.get(i - 1).getName();
			SimpleList simpleList = new SimpleList(i, color, name);
			list.add(simpleList);
		}
		simple2Adpter = new Simple2Adapter(getApplicationContext(),
				list);
		mProvinceContent.setAdapter(simple2Adpter);
	}

	private void assemblyCityList(List<City.ContentBean> cities) {

		mCityList = (ArrayList<City.ContentBean>) cities;
		if (!TextUtils.isEmpty(hideAllRegionFlag)) {
			cities.remove(0);
		}
		cityslist = new ArrayList<SimpleList>();
		for (int i = 1; i <= cities.size(); i++) {
			int color = getResources().getColor(
					android.R.color.black);
			String name = cities.get(i - 1).getName();
			SimpleList simpleList = new SimpleList(i, color, name);
			cityslist.add(simpleList);
		}
		cityAdapter = new Simple2Adapter(getApplicationContext(),
				cityslist);
		mCarDetailInfoCityContent.setAdapter(cityAdapter);

	}

	/**
	 * 获取省列表信息 startProvinceThead: <br/>
	 */
	private void startProvinceThead() {
//		showLoading();
		getProvinceList();
	}

	private void startCityThread() {
//		showLoading();
		Province.ContentBean province = mProvinceList.get(mProvinceContentListClickPostion);
		getCityList(province.getId());
		
	}


	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		int viewid = parent.getId();
		switch (viewid) {
		case R.id.province_content:
			modifyTextColor(view, position);
			prepareCityList();
			startCityThread();
			break;
		case R.id.car_detail_info_city_content:
			returnRegion(position);
			break;
		default:
			break;
		}
	}

	private void returnRegion(int position) {

		// 当标记比较相同的时候走筛选设置模块的选择城市业务
		if ("carfilter_region".equals(carFilterRegionFlag)) {
			// 车源定制选择城市
			City.ContentBean city = mCityList.get(position);
			Province.ContentBean province = mProvinceList.get(mProvinceContentListClickPostion);
			Intent intent = new Intent();
			String regionStr = city.getName();
			String regionId = String.valueOf(city.getId());
			intent.putExtra("regionStr", regionStr);
			intent.putExtra("regionId", regionId);
			intent.putExtra("regionProvince", province.getName());
			intent.putExtra("regionProvinceId",
					String.valueOf(province.getId()));
			setResult(Activity.RESULT_OK, intent);
			finish();
		}
		// 注册和个人中心的逻辑
		else {
			mCity = cityslist.get(position).getName();
			returnCityInfo(position);
		}

	}

	/**
	 * 限迁标准查询
	 * 
	 * @param position
	 */
	private void returnCityInfo(final int position) {
		// 城市Id
		mCityId = mCityList.get(position).getId();
		// 省Id
		mProvinceId = mProvinceList.get(mProvinceContentListClickPostion).getId();
		// 城市名字
		mCity = mCityList.get(position).getName();
		mProvince = mProvinceList.get(mProvinceContentListClickPostion).getName();
		Intent it = new Intent();
		it.putExtra("province", mProvince);
		it.putExtra("city", mCity);
		it.putExtra("mCityId", mCityId);
		it.putExtra("mProvinceId", mProvinceId);
		setResult(Activity.RESULT_OK, it);
		finish();

	}


	private void prepareCityList() {
		// 如果城市选择列表没有开启则打开列表
		if (!mCarDetailInfoCityDrawer.isOpened())
			mCarDetailInfoCityDrawer.animateOpen();
		// 清空市列表数据原始数据
		if (cityList != null) {
			cityList.clear();
			cityAdapter.notifyDataSetChanged();
		}
	}

	private void modifyTextColor(View view, int position) {
		isProvinceClick = true;
		mProvinceContentListClickPostion = position;

		TextView name = (TextView) view
				.findViewById(R.id.car_list_content_name);
		// 赋值当前选择省份
		mProvince = name.getText().toString();
		// 修改当前选中项字体颜色
		list.get(mProvinceContentListClickPostion).setColor(
				getResources().getColor(R.color.common_blue_normal));
		if (oldPoc != -1 && oldPoc != position) {
			list.get(oldPoc).setColor(
					getResources().getColor(R.color.black_font));
		}
		simple2Adpter.notifyDataSetChanged();
		oldPoc = mProvinceContentListClickPostion;
	}

	@Override
	public void onDrawerClosed() {

	}

	@Override
	public void onDrawerOpened() {

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

	}


	/**
	 * 获取省数据 getProvinceList: <br/>
	 *
	 * @return
	 * @throws Exception
	 * @since JDK 1.6
	 */
	private void getProvinceList()
	{
		mAreaProvincePresenter = new AreaProvincePresenter(this);
		mAreaProvincePresenter.getProvince(jzgGuyeHttp,signForPost());

	}

	/**
	 * 获取市数据 getCityList: <br/>
	 *
	 * @author wang
	 * @param provinceid
	 * @return
	 * @throws Exception
	 * @since JDK 1.6
	 */
	private void getCityList(int provinceid)
	{
		mAreaCityPresenter = new AreaCityPresenter(this);
		mAreaCityPresenter.getCity(jzgGuyeHttp,String.valueOf(provinceid),signForCityList(String.valueOf(provinceid)));
	}

	private static final String privateNum = "2CB3147B-D93C-964B-47AE-EEE448C84E3C";
	public static String signForPost()
	{
		StringBuffer buffer = new StringBuffer();
		buffer.append("?");
		buffer.append(privateNum);

		return MD5Utils.newMD5(buffer.toString());
	}

	public static String signForCityList(String procinceid)
	{
		StringBuffer buffer = new StringBuffer();
		buffer.append("?ProvId=");
		buffer.append(procinceid);
		buffer.append(privateNum);

		return MD5Utils.newMD5(buffer.toString());
	}

	// 选择地区线上
	private static final String jzgGuyeHttp = "http://api.jingzhengu.com";

	@Override
	public void succeed(List<Province.ContentBean> data) {
		assemblyProvinceList(data);
	}

	@Override
	public void showError(String error) {
		MyToast.showShort("列表无返回数据，请检查网络是否链接！！！");
	}

	@Override
	public void succeedCity(List<City.ContentBean> data) {
		assemblyCityList(data);
	}
}
