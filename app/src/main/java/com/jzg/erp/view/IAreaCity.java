package com.jzg.erp.view;/**
 * Created by voiceofnet on 2016/6/22.
 */

import com.jzg.erp.model.City;

import java.util.List;

/**
 * 市数据
 * @author zealjiang
 * @time 2016/6/28 14:04
 */
public interface IAreaCity extends IBaseView {
    void succeedCity(List<City.ContentBean> data);
}
