package com.jzg.erp.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jzg.erp.R;
import com.jzg.erp.adapter.BrandListAdapter;
import com.jzg.erp.adapter.CarSourceAdapter;
import com.jzg.erp.adapter.CarSourcePopAdapter;
import com.jzg.erp.adapter.ModelCategoryAdapter;
import com.jzg.erp.app.JzgApp;
import com.jzg.erp.base.NewBaseFragment;
import com.jzg.erp.global.Constant;
import com.jzg.erp.model.CarSourceData;
import com.jzg.erp.model.Make;
import com.jzg.erp.model.MakeList;
import com.jzg.erp.model.Model;
import com.jzg.erp.presenter.BrandPresenter;
import com.jzg.erp.ui.activity.CarDetailActivity;
import com.jzg.erp.ui.activity.SearchActivity;
import com.jzg.erp.utils.ACache;
import com.jzg.erp.utils.LogUtil;
import com.jzg.erp.utils.MD5Utils;
import com.jzg.erp.utils.MyToast;
import com.jzg.erp.utils.UIUtils;
import com.jzg.erp.view.IBrandInterface;
import com.jzg.erp.view.MyLetterListView;
import com.jzg.erp.widget.EmptyView;
import com.jzg.erp.widget.ErrorView;
import com.jzg.erp.widget.RecycleViewDivider;
import com.jzg.erp.widget.XRecyclerView;
import com.yyydjk.library.DropDownMenu;
import com.zhy.base.adapter.recyclerview.OnItemClickListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * author: guochen
 * date: 2016/6/23 15:09
 * email:
 * 车源Fragment
 */
public class CarSourceFragment extends NewBaseFragment implements IBrandInterface, DrawerLayout.DrawerListener, AdapterView.OnItemClickListener, XRecyclerView.LoadingListener {
    @Bind(R.id.dropDownMenu)
    DropDownMenu dropDownMenu;
    @Bind(R.id.ivRight)
    ImageView ivRight;
    private String condtionStrs[] = {"默认排序", "品牌", "状态"};
    private String defaultDatas[] = {"默认排序", "价格最高", "价格最低", "车龄最短", "里程最少"};
    private String statusDatas[] = {"全部状态", "在售", "已预订", "已售出"};
    private List<View> viewList = new ArrayList<>();
    private ListView defaultListview;
    private ListView statusListview;
    private View brandListview;
    //abcd索引需要的数据
    private Map<String, Integer> indexData;
    private ListView brand_list;
    private DrawerLayout drawer_layout;
    private MyLetterListView indexListView;
    //品牌列表分组数据
    private ArrayList<Map<String, Object>> brandItems;
    //车系列表数据
    private ArrayList<Model> models;
    //车系列表分组数据
    private ArrayList<String> modelsGroupNames;
    //当前选中的品牌
    private Make curMake;
    /**当前选中的品牌下的某款车系 by zj*/
    private Model curModel;
    //查询条件
    private String searchName;
    //品牌列表数据
    private ArrayList<Make> makes;

    private ModelCategoryAdapter modelCategoryAdapter;
    private ListView model_list;
    private LinearLayout modelHearderLayout;
    private LinearLayout makeHeaderLayout;
    private BrandPresenter carSourcePresenter;
    private String json;
    private View view;
    private CarSourceAdapter carSourceAdapter;
    private String Sort = "0"; //排序方式
    private String carStatus = "0"; //车辆状态
    private String makeName = ""; //品牌名字
    private String condition = ""; //模糊条件查询
    private List<CarSourceData.DataBean> carsourceDatas = new ArrayList<>();
    private XRecyclerView contentView;
    private ErrorView errorView;
    private EmptyView emptyView;
    private int pageIndex = 1;
    private CarSourcePopAdapter defaultAdapter;
    private CarSourcePopAdapter statusAdapter;

    @Override
    protected void setView() {
        showBack(false);
        setTitle(R.string.carsouce);
        setImageRight(R.mipmap.sousuo003);
    }

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_carsource, container, false);
        ButterKnife.bind(this, rootView);
        modelHearderLayout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.model_header_view, null);
        makeHeaderLayout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.make_header_view, null);
        //品牌
        brandListview = View.inflate(context, R.layout.brand_layout, null);
        brand_list = (ListView) brandListview.findViewById(R.id.brand_list);
        drawer_layout = (DrawerLayout) brandListview.findViewById(R.id.drawer_layout);
        indexListView = (MyLetterListView) brandListview.findViewById(R.id.index_list);
        model_list = (ListView) brandListview.findViewById(R.id.model_list);
        brand_list.addHeaderView(makeHeaderLayout);
        view = View.inflate(context, R.layout.carsource_recycler, null);
        contentView = (XRecyclerView) view.findViewById(R.id.recycler);
        errorView = (ErrorView) view.findViewById(R.id.err_layout);
        initCarSourceData();
        init();
        initListener();
        return rootView;
    }

    private void initCarSourceData() {
        carSourceAdapter = new CarSourceAdapter(context,   R.layout.carsource_item, carsourceDatas, contentView);
    }

    @Override
    public void setImageRight(int res) {
        super.setImageRight(res);
    }

    private void init() {
        carSourcePresenter = new BrandPresenter(this);
        carSourcePresenter.getBrand(getBrandParams());//请求车型车系
        if (!TextUtils.isEmpty(json)) {
            MakeList list = new Gson().fromJson(json, MakeList.class);//从本地缓存中度数据
            carSourcePresenter.showMake(list.getMakeList());
            dismissDialog();
        }
        viewList.clear();
        //内容显示区域
        contentView.setLayoutManager(new LinearLayoutManager(context));
        contentView.setRefreshProgressStyle(ProgressStyle.CubeTransition);
        contentView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        contentView.addItemDecoration(new RecycleViewDivider(context, LinearLayoutManager.VERTICAL, 1, R.color.common_gray_line));
        contentView.setLoadingListener(this);
        emptyView = new EmptyView(context, "暂无车源");
        ((ViewGroup) contentView.getParent()).addView(emptyView);
        contentView.setEmptyView(emptyView);
        errorView.setOnErrorListener(new ErrorView.OnErrorListener() {
            @Override
            public void onError() {
                pageIndex = 1;
                carSourcePresenter.loadCarSourceData(getCarSourceParams());
            }
        });
        contentView.setAdapter(carSourceAdapter);
        //默认排序
        defaultListview = new ListView(getActivity());
        defaultListview.setDividerHeight(0);
        defaultAdapter = new CarSourcePopAdapter(context, Arrays.asList(defaultDatas));
        defaultListview.setAdapter(defaultAdapter);
        //状态
        statusListview = new ListView(getActivity());
        statusListview.setDividerHeight(0);
        statusAdapter = new CarSourcePopAdapter(context, Arrays.asList(statusDatas));
        statusListview.setAdapter(statusAdapter);


        viewList.add(defaultListview);
        viewList.add(brandListview);
        viewList.add(statusListview);
        dropDownMenu.setDropDownMenu(Arrays.asList(condtionStrs), viewList, view);

        loadAllSource();
    }


    private void initListener() {
        carSourceAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View view, Object o, int position) {
                Intent intent = new Intent(context, CarDetailActivity.class);
                intent.putExtra(Constant.MAKE_NAME, carsourceDatas.get(position).getMakeName());
                intent.putExtra(Constant.MODE_NAME, carsourceDatas.get(position).getModelName());
                intent.putExtra(Constant.CARSTATUS, carsourceDatas.get(position).getCarStatus2()+"");
                intent.putExtra(Constant.CARSOURCEID, carsourceDatas.get(position).getCarSourceID()+"");
                intent.putExtra(Constant.CARID, carsourceDatas.get(position).getID()+"");
                intent.putExtra(Constant.CARDETAILPATH, carsourceDatas.get(position).getCarDetailPath());
                //分享
                intent.putExtra(Constant.SHARE_PIC, carsourceDatas.get(position).getPicPath());
                intent.putExtra(Constant.SHARE_TEXT, carsourceDatas.get(position).getFullName());
                startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(ViewGroup parent, View view, Object o, int position) {
                return false;
            }
        });

        defaultListview.setOnItemClickListener(new AdapterView.OnItemClickListener() { //默认排序
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                defaultAdapter.setCheckItem(position);
                dropDownMenu.setTabText(defaultDatas[position]);
                Sort = position + "";
                dropDownMenu.closeMenu();
                showDialog();
                carSourcePresenter.loadCarSourceData(getCarSourceParams());

            }
        });

        statusListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {//状态
                statusAdapter.setCheckItem(position);
                dropDownMenu.setTabText(statusDatas[position]);
                if (position == 0) {
                    carStatus = "";
                    pageIndex = 1;

                } else {
                    carStatus = position + "";
                    pageIndex = 1;
                }

                dropDownMenu.closeMenu();
                showDialog();
                carSourcePresenter.loadCarSourceData(getCarSourceParams());

            }
        });

        indexListView.setOnTouchingLetterChangedListener(new LetterListViewListener());
        drawer_layout.setDrawerListener(this);
        brand_list.setOnItemClickListener(this);
        model_list.setOnItemClickListener(this);
    }

    @Override
    protected void initData() {
        //从缓存得到数据
        json = ACache.get(context).getAsString("makeinfo");
        carsourceDatas = new ArrayList<>();

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        //by zj
        if (drawer_layout.isDrawerOpen(model_list)) {
            drawer_layout.closeDrawer(model_list);
        }
    }

    @Override
    public void showBrand(ArrayList<Map<String, Object>> items) {
        brandItems = items;
        BrandListAdapter brandListAdapter = new BrandListAdapter(getActivity(), brandItems);
        brand_list.setAdapter(brandListAdapter);
    }

    @Override
    public void showModel() {
        if (!drawer_layout.isDrawerOpen(model_list))
            drawer_layout.openDrawer(model_list);
        modelCategoryAdapter = new ModelCategoryAdapter(context, models, modelsGroupNames);
        setHeader();
        model_list.addHeaderView(modelHearderLayout);
        model_list.setAdapter(modelCategoryAdapter);
    }

    @Override
    public int getDefaultFontColor() {
        return R.color.grey3;
    }

    /**
     * 设置ListView头部信息
     */
    private void setHeader() {
        SimpleDraweeView img = (SimpleDraweeView) modelHearderLayout.findViewById(R.id.header_img);
        TextView text = (TextView) modelHearderLayout.findViewById(R.id.header_text);
        Uri uri = Uri.parse(curMake.getMakeLogo());
        img.setImageURI(uri);
        text.setText(curMake.getMakeName());
    }

    @Override
    public void setIndexData(Map<String, Integer> indexData) {
        this.indexData = indexData;
    }

    @Override
    public void setBrands(ArrayList<Make> makes) {
        this.makes = makes;
    }

    @Override
    public void setModels(ArrayList<Model> mModels) {
        this.models = mModels;
    }

    @Override
    public void setModelsGroupNames(ArrayList<String> mModelsGroupNames) {
        this.modelsGroupNames = mModelsGroupNames;
    }
    @Override
    public Map<String, String> getBrandParams() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("op", "GetMake");
        params.put("InSale", "1");
        params.put("userId", JzgApp.getUser().getUserID()+"");
        Map<String, Object> signMap = new HashMap<>();
        signMap.putAll(params);
        params.put("sign", MD5Utils.getMD5Sign(signMap));
        return params;
    }

    public Map<String, String> getCarSourceParams() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("op", "getCarList");
        CheckParams(params, "Sort", Sort, "");
        CheckParams(params, "carStatus", carStatus, "");
        CheckParams(params, "makeName", searchName, "");
        CheckParams(params, "condition", condition, "");
        params.put("pageIndex",pageIndex+"");
        params.put("pageCount", String.valueOf(Constant.PAGECOUNT));
        Map<String, Object> signMap = new HashMap<>();
        signMap.putAll(params);
        params.put("sign", MD5Utils.getMD5Sign(signMap));
        LogUtil.e(TAG, UIUtils.getUrl(params));
        return params;
    }

    @Override
    public void showError(String error) {
        super.showError(error);
        contentView.setVisibility(View.GONE);
        errorView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {

    }

    @Override
    public void onDrawerOpened(View drawerView) {
        indexListView.setVisibility(View.GONE);
    }

    @Override
    public void onDrawerClosed(View drawerView) {
        indexListView.setVisibility(View.VISIBLE);
        model_list.removeHeaderView(modelHearderLayout);
    }

    @Override
    public void onDrawerStateChanged(int newState) {

    }

    //刷新
    @Override
    public void onRefresh() {
        pageIndex = 1;
        carSourcePresenter.loadCarSourceData(getCarSourceParams());

    }

    //加载
    @Override
    public void onLoadMore() {
        if(carsourceDatas.size()%Constant.PAGECOUNT!=0){
            contentView.loadMoreComplete();
            return;
        }
        pageIndex++;
        carSourcePresenter.loadMoreCarSourceData(getCarSourceParams());
    }

    @Override
    public void onResume() {
        super.onResume();
        //loadAllSource();
    }

    private void loadAllSource() {
        Sort = "0";
        carStatus = "";
        searchName = "";
        condition = "";
        pageIndex = 1;
        showDialog();
        carSourcePresenter.loadCarSourceData(getCarSourceParams());
    }

    @OnClick(R.id.ivRight)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivRight:
                Intent intent = new Intent(context, SearchActivity.class);
                context.startActivity(intent);
                break;
            default:

                break;
        }
    }

    class LetterListViewListener implements MyLetterListView.OnTouchingLetterChangedListener {

        @Override
        public void onTouchingLetterChanged(final String s) {
            if (indexData != null && indexData.get(s) != null) {
                int position = indexData.get(s);
                brand_list.setSelection(position);
            }
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.brand_list:
                if (position == 0) {
                    //带全部选项并且点击全部所走流程 sendMakeAllBroadcast(); 处理点击全部的逻辑
                    dropDownMenu.setTabText("全部品牌");
                    drawer_layout.closeDrawer(model_list);
                    dropDownMenu.closeMenu();
                    makeName = "";
                    searchName = makeName;
                    showDialog();
                    carSourcePresenter.loadCarSourceData(getCarSourceParams());
                } else if (position != 0) {

                    //带全部选项并且点击其他position所走流程
                    curMake = makes.get(position - 1);
                    //设置是否带有全部品牌选项标记，用于广播接受的时候判断
                    makeName = curMake.getMakeName();
                    searchName = makeName;
                    carSourcePresenter.getModel(getModelParams());
                } else {
                    //不带全部选项的流程
                    curMake = makes.get(position);
                    //设置是否带有全部品牌选项标记，用于广播接受的时候判断
                    makeName = curMake.getMakeName();
                    searchName = makeName;
                    carSourcePresenter.getModel(getModelParams());
                }
                break;
            case R.id.model_list://车系
                //如果点击位置不是车系的头部位置则进行跳转，并把当前的品牌数据和车系数据发送到车型界面去
                if (position != Constant.MODEL_HEADER) {
                    curModel = models.get(position - 1);
//                    dropDownMenu.setTabText(curMake.getMakeName() + "-" +  curModel.getName());
                    dropDownMenu.setTabText( curModel.getName());
                    searchName = curModel.getName();
                    drawer_layout.closeDrawer(model_list);
                    dropDownMenu.closeMenu();
                    showDialog();
                    carSourcePresenter.loadCarSourceData(getCarSourceParams());
                }else{
                    //点击的是全部
                    searchName = makeName;
                    dropDownMenu.setTabText(searchName);
                    drawer_layout.closeDrawer(model_list);
                    dropDownMenu.closeMenu();
                    showDialog();
                    carSourcePresenter.loadCarSourceData(getCarSourceParams());
                }
                break;
        }
    }

    /**
     * 获取车系查询参数
     *
     * @return
     */
    @Override
    public Map<String, String> getModelParams() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("makeId", curMake.getMakeId() + "");
        params.put("op", "GetModel");
        params.put("InSale", "1");
        params.put("userId", JzgApp.getUser().getUserID()+"");
        Map<String, Object> signMaps = new HashMap<>();
        signMaps.putAll(params);
        params.put("sign", MD5Utils.getMD5Sign(signMaps, "2CB3147B-D93C-964B-47AE-EEE448C84E3C"));

        return params;
    }

    public void CheckParams(Map map, String key, String value, String message) {
        if (!TextUtils.isEmpty(value) && !TextUtils.isEmpty(value.trim())) {
            map.put(key, value);
        } else if (!TextUtils.isEmpty(message.trim())) {
            MyToast.showShort(message);
        }
    }

    /**
     * 缓存车型车系
     *
     * @param makeList
     * @return
     */
    @Override
    public boolean readFromCache(MakeList makeList) {
        Gson gson = new Gson();
        String json = ACache.get(context).getAsString("makeinfo");//从缓存得到数据
        String json1 = gson.toJson(makeList);//网路数据

        if (!TextUtils.isEmpty(json)) {//如果缓存不为空
            if (json.equals(json1)) {  //如果缓存和网路数据一致
                return true;
            } else {
                ACache.get(context).put("makeinfo", json1);
                return false;
            }
        } else {
            ACache.get(context).put("makeinfo", json1);
            return false;
        }
    }

    @Override
    public void ShowCarSourceData(CarSourceData carSourceData) {
        errorView.setVisibility(View.GONE);
        contentView.loadMoreComplete();//关闭加载
        contentView.refreshComplete();//关闭刷新
        carsourceDatas.clear();
        if (carSourceData.getStatus() == Constant.SUCCESS) {
            if (carSourceData.getData() != null) {
                carsourceDatas.addAll(carSourceData.getData());
            }
        } else {
            MyToast.showShort(carSourceData.getMessage());
            contentView.setEmptyView(emptyView);
        }

        carSourceAdapter.notifyDataSetChanged();
    }

    /**
     * 加载更多回调
     *
     * @param carSourceData
     */
    @Override
    public void ShowMoreCarSourceData(CarSourceData carSourceData) {
        contentView.loadMoreComplete();//关闭加载
        contentView.refreshComplete();//关闭刷新
        if (carSourceData.getStatus() == Constant.SUCCESS) {
            if (carSourceData.getData() != null) {
                carsourceDatas.addAll(carSourceData.getData());
            }
        } else {
            MyToast.showShort(carSourceData.getMessage());
        }
        carSourceAdapter.notifyDataSetChanged();

    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_BACK) {
            if (dropDownMenu.isShowing()) {
                if (drawer_layout.isDrawerOpen(model_list)) {
                    drawer_layout.closeDrawer(model_list);
                }
                dropDownMenu.closeMenu();
                return true;
            }else{
                if (drawer_layout.isDrawerOpen(model_list)) {
                    drawer_layout.closeDrawer(model_list);
                }
            }
        }
        return false;
    }


}
