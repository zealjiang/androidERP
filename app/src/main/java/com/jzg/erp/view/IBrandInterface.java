package com.jzg.erp.view;


import com.jzg.erp.model.CarSourceData;
import com.jzg.erp.model.Make;
import com.jzg.erp.model.MakeList;
import com.jzg.erp.model.Model;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by wangyd on 15/12/23.
 */
public interface IBrandInterface extends IBaseView {
    /**
     * 显示错误信息
     *
     * @param error
     */
    void showError(String error);

    /**
     * 显示品牌列表
     *
     * @param items
     */
    void showBrand(ArrayList<Map<String, Object>> items);

    /**
     * 显示车系列表
     */
    void showModel();

    /**
     * 获取字体默认颜色
     *
     * @return
     */
    int getDefaultFontColor();

    /**
     * 设置右侧index列表需要的数据
     *
     * @param indexData
     */
    void setIndexData(Map<String, Integer> indexData);

    /**
     * 设置品牌数据
     *
     * @param makes
     */
    void setBrands(ArrayList<Make> makes);

    /**
     * 设置车系数据
     *
     * @param mModels
     */
    void setModels(ArrayList<Model> mModels);

    /**
     * 设置车系分组数据
     *
     * @param mModelsGroupNames
     */
    void setModelsGroupNames(ArrayList<String> mModelsGroupNames);


    /**
     * 获取品牌查询参数
     *
     * @return
     */
    Map<String, String> getBrandParams();

    /**
     * 获取车系查询参数
     *
     * @return
     */
    Map<String, String> getModelParams();
    /**
     *
     */
    boolean readFromCache(MakeList makeList);

    void ShowCarSourceData(CarSourceData carSourceData);
    void ShowMoreCarSourceData(CarSourceData carSourceData);
}
