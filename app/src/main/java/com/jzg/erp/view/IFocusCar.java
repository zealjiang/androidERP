package com.jzg.erp.view;

import com.jzg.erp.model.CarSourceData;
import com.jzg.erp.model.CarSourceTagData;

/**
 * Created by wangyd on 16/6/29.
 * 关注车列表
 */
public interface IFocusCar extends IBaseView {
    /**
     * 显示被关注列表
     *
     * @param carSourceData 关注车列表
     */
    void showFocusCar(CarSourceData carSourceData, int pageIdex);
    /**
     * 显示被关注列表
     *
     * @param carSourceData 提交关注车接口
     */
    void showFocusCarWithTag(CarSourceTagData carSourceData);
}
