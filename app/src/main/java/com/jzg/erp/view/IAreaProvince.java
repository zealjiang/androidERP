package com.jzg.erp.view;/**
 * Created by voiceofnet on 2016/6/22.
 */

import com.jzg.erp.model.Province;

import java.util.List;

/**
 * 省数据
 * @author zealjiang
 * @time 2016/6/28 14:04
 */
public interface IAreaProvince extends IBaseView {
    void succeed(List<Province.ContentBean> data);
}
