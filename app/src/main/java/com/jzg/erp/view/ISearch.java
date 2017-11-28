package com.jzg.erp.view;

import com.jzg.erp.model.CustomerDetail;
import com.jzg.erp.model.CustomerInfo;

import java.util.List;

/**
 * Created by JZG on 2016/6/23.
 */
public interface ISearch extends IBaseView{
    void searcheSucceed(List<CustomerInfo.CustomerListEntity> customerInfo,boolean isFirst);
    /**
     * 显示客户信息详情
     *
     * @param customerDetail
     */
    void showCustomerDetail(CustomerDetail customerDetail);

}
