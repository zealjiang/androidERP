package com.jzg.erp.view;

import com.jzg.erp.model.BuyCarIntent;

/**
 * Created by wangyd on 16/6/28.
 * 修改购车意向
 */
public interface IBuyCarIntent extends IBaseView {
    /**
     * 修改购车意向
     *
     * @param params 修改购车意向返回结果
     */
    void modifyBuyCarIntent(BuyCarIntent buyCarIntent);
}
