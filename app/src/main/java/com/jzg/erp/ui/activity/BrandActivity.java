package com.jzg.erp.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.jzg.erp.R;
import com.jzg.erp.adapter.BrandListAdapter;
import com.jzg.erp.adapter.ModelCategoryAdapter;
import com.jzg.erp.base.BaseActivity;
import com.jzg.erp.global.Constant;
import com.jzg.erp.model.CarSourceData;
import com.jzg.erp.model.Make;
import com.jzg.erp.model.MakeList;
import com.jzg.erp.model.Model;
import com.jzg.erp.presenter.BrandPresenter;
import com.jzg.erp.utils.ACache;
import com.jzg.erp.utils.LogUtil;
import com.jzg.erp.utils.MD5Utils;
import com.jzg.erp.utils.UIUtils;
import com.jzg.erp.view.IBrandInterface;
import com.jzg.erp.view.MyLetterListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

/**
 * Created by wangyd on 15/12/22.
 * 品牌列表界面
 */
public class BrandActivity extends BaseActivity implements IBrandInterface, View.OnClickListener, AdapterView.OnItemClickListener, DrawerLayout.DrawerListener {

    @Bind(R.id.brand_list)
    ListView brand_list;
    @Bind(R.id.index_list)
    MyLetterListView indexListView;
    @Bind(R.id.model_list)
    ListView model_list;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawer_layout;

    LinearLayout modelHearderLayout;

    LinearLayout makeHeaderLayout;

    //品牌列表业务逻辑
    private BrandPresenter brandPresenter;

    //品牌列表适配器
    private BrandListAdapter brandListAdapter;

    //车系列表适配器
    private ModelCategoryAdapter modelCategoryAdapter;

    //品牌列表分组数据
    private ArrayList<Map<String, Object>> brandItems;

    //abcd索引需要的数据
    private Map<String, Integer> indexData;

    //品牌列表数据
    private ArrayList<Make> makes;

    //车系列表数据
    private ArrayList<Model> models;

    //车系列表分组数据
    private ArrayList<String> modelsGroupNames;

    //当前选中的品牌
    private Make curMake;

    //判断品牌列表是否需要显示全部选项
    private boolean isMakeAll;



    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.brand_modle_layout);
        ButterKnife.bind(this);
        brandPresenter = new BrandPresenter(this);

    }

    @Override
    protected void setData() {
        isMakeAll = false;
        setTitle("添加意向车型");

        modelHearderLayout = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.model_header_view, null);

        if (isMakeAll) {
            makeHeaderLayout = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.make_header_view, null);
            brand_list.addHeaderView(makeHeaderLayout);
        }


//        loading.setVisibility(View.VISIBLE);
//        showDialog();
        indexListView
                .setOnTouchingLetterChangedListener(new LetterListViewListener());

        drawer_layout.setDrawerListener(this);
        String json = ACache.get(this).getAsString("makeinfo");//从缓存得到数据
        brandPresenter.getBrand(getBrandParams());
        if (!TextUtils.isEmpty(json)) {
            MakeList list = new Gson().fromJson(json, MakeList.class);
            brandPresenter.showMake(list.getMakeList());
        } else {
            showDialog();

        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }



    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        super.onClick(v);
    }


    /**
     * 显示品牌列表
     *
     * @param items
     */
    @Override
    public void showBrand(ArrayList<Map<String, Object>> items) {

        brandItems = items;
        BrandListAdapter brandListAdapter = new BrandListAdapter(this, brandItems);
        brand_list.setAdapter(brandListAdapter);
    }

    /**
     * 显示车系列表
     */
    @Override
    public void showModel() {
        if (!drawer_layout.isDrawerOpen(model_list))
            drawer_layout.openDrawer(model_list);
        modelCategoryAdapter = new ModelCategoryAdapter(this, models, modelsGroupNames);
        setHeader();
        model_list.addHeaderView(modelHearderLayout);
        model_list.setAdapter(modelCategoryAdapter);

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

    /**
     * 获取字体默认颜色
     *
     * @return
     */
    @Override
    public int getDefaultFontColor() {
        return getResources().getColor(R.color.grey3);
    }

    /**
     * 设置右侧index列表需要的数据
     *
     * @param indexData
     */
    @Override
    public void setIndexData(Map<String, Integer> indexData) {
        this.indexData = indexData;
    }

    /**
     * 设置品牌数据
     *
     * @param makes
     */
    @Override
    public void setBrands(ArrayList<Make> makes) {
        this.makes = makes;
    }

    /**
     * 设置车系数据
     *
     * @param models
     */
    @Override
    public void setModels(ArrayList<Model> models) {
        this.models = models;
    }

    /**
     * 设置车系分组数据
     *
     * @param modelsGroupNames
     */
    @Override
    public void setModelsGroupNames(ArrayList<String> modelsGroupNames) {
        this.modelsGroupNames = modelsGroupNames;
    }

    /**
     * 获取品牌查询参数
     *
     * @return
     */
    @Override
    public Map<String, String> getBrandParams() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("op", "GetMake");
        params.put("InSale", "1");
        Map<String, Object> signMap = new HashMap<>();
        signMap.putAll(params);
        params.put("sign", MD5Utils.getMD5Sign(signMap, "2CB3147B-D93C-964B-47AE-EEE448C84E3C"));
        LogUtil.e(TAG,UIUtils.getUrl(params));
        return params;
    }

    /**
     * 获取车系查询参数
     *
     * @return
     */
    @Override
    public Map<String, String> getModelParams() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("makeId", String.valueOf(curMake.getMakeId()));
        params.put("op", "GetModel");
        params.put("InSale", "1");
        Map<String, Object> signMaps = new HashMap<>();
        signMaps.putAll(params);
        params.put("sign", MD5Utils.getMD5Sign(signMaps, "2CB3147B-D93C-964B-47AE-EEE448C84E3C"));
        LogUtil.e(TAG,UIUtils.getUrl(params));
        return params;
    }

    @Override
    public boolean readFromCache(MakeList makeList) {
        Gson gson = new Gson();
        String json = ACache.get(this).getAsString("makeinfo");//从缓存得到数据
        String json1 = gson.toJson(makeList);//网路数据
        if (!TextUtils.isEmpty(json)) {//如果缓存不为空
            if (json.equals(json1)) {//如果缓存和网路数据一致
                return true;
            } else {
                ACache.get(this).put("makeinfo", json1);
                return false;
            }
        } else {
            ACache.get(this).put("makeinfo", json1);
            return false;
        }
    }

    @Override
    public void ShowCarSourceData(CarSourceData carSourceData) {

    }

    @Override
    public void ShowMoreCarSourceData(CarSourceData carSourceData) {

    }

    /**
     * 显示错误信息
     *
     * @param error
     */

    /**
     * Callback method to be invoked when an item in this AdapterView has
     * been clicked.
     * <p>
     * Implementers can call getItemAtPosition(position) if they need
     * to access the data associated with the selected item.
     *
     * @param parent   The AdapterView where the click happened.
     * @param view     The view within the AdapterView that was clicked (this
     *                 will be a view provided by the adapter)
     * @param position The position of the view in the adapter.
     * @param id       The row id of the item that was clicked.
     */
    @Override
    @OnItemClick({R.id.brand_list, R.id.model_list})
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.brand_list:
                if (isMakeAll && position == 0) {
                    finish();
                } else if (isMakeAll && position != 0) {
                    //带全部选项并且点击其他position所走流程
                    showDialog();
                    curMake = makes.get(position - 1);
                    //设置是否带有全部品牌选项标记，用于广播接受的时候判断
                    curMake.setIsMakeAll(isMakeAll);

                    brandPresenter.getModel(getModelParams());
                } else {
                    //不带全部选项的流程
                    showDialog();
                    curMake = makes.get(position);
                    //设置是否带有全部品牌选项标记，用于广播接受的时候判断
                    curMake.setIsMakeAll(isMakeAll);

                    brandPresenter.getModel(getModelParams());
                }
                break;
            case R.id.model_list:
                //如果点击位置不是车系的头部位置则进行跳转，并把当前的品牌数据和车系数据发送到车型界面去
                if (position != Constant.MODEL_HEADER) {
                    Intent intent = new Intent();
                    intent.putExtra("make",curMake);
                    intent.putExtra("model",models.get(position- 1));
                    setResult(100,intent);
                    finish();
                }
                break;
        }
    }


    /**
     * Called when a drawer's position changes.
     *
     * @param drawerView  The child view that was moved
     * @param slideOffset The new offset of this drawer within its range, from 0-1
     */
    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {
    }

    /**
     * Called when a drawer has settled in a completely open state.
     * The drawer is interactive at this point.
     *
     * @param drawerView Drawer view that is now open
     */
    @Override
    public void onDrawerOpened(View drawerView) {
        indexListView.setVisibility(View.GONE);
    }

    /**
     * Called when a drawer has settled in a completely closed state.
     *
     * @param drawerView Drawer view that is now closed
     */
    @Override
    public void onDrawerClosed(View drawerView) {
        indexListView.setVisibility(View.VISIBLE);
        model_list.removeHeaderView(modelHearderLayout);
    }

    /**
     * Called when the drawer motion state changes. The new state will
     *
     * @param newState The new drawer motion state
     */
    @Override
    public void onDrawerStateChanged(int newState) {
    }


    /**
     * a-z索引监听 ClassName: LetterListViewListener <br/>
     * Function: TODO ADD FUNCTION. <br/>
     * Reason: TODO ADD REASON. <br/>
     * date: 2014-6-2 下午3:44:10 <br/>
     *
     * @author wang
     * @version IndexCarActivity
     * @since JDK 1.6
     */
    class LetterListViewListener implements MyLetterListView.OnTouchingLetterChangedListener {

        @Override
        public void onTouchingLetterChanged(final String s) {
            if (indexData != null && indexData.get(s) != null) {
                int position = indexData.get(s);
                brand_list.setSelection(position);
            }
        }

    }
}
