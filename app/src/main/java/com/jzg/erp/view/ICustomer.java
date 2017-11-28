package com.jzg.erp.view;

import com.jzg.erp.model.CustomerDetail;
import com.jzg.erp.model.CustomerInfo;

/**
 * Created by JZG on 2016/6/24.
 */
public interface ICustomer extends IBaseView{
    void succeedResult(CustomerInfo customerInfos,int pageIndex);
    /**
     * 显示客户信息详情
     *
     * @param customerDetail
     */
    void showCustomerDetail(CustomerDetail customerDetail);
}
