package com.jzg.erp.view;

import com.jzg.erp.model.CarSourceData;

/**
*
* 用户列表数据接口
 * IAddFoucsCar
* @author sunbl
* created at 2016/6/29 10:48
*/
public interface IAddFoucsCar extends IBaseView {
    /**
     * 显示被关注列表
     *
     * @param carSourceData 关注车列表
     */
    void showFocusCar(CarSourceData carSourceData, int pageIndex);
    /**
     * 提交被关注车
     *
     * @param carSourceData 提交关注车接口
     */
    void submitFocusCar(CarSourceData carSourceData);
}
