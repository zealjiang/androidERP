package com.jzg.erp.view;

import com.jzg.erp.model.CustomerData;

/**
 * Created by JZG on 2016/6/29.
 */
public interface IChooseCustomer  extends IBaseView{
    void showCustomerResult(CustomerData customerData);
    void showMoreCustomerResult(CustomerData customerData);
    void succeedResult(CustomerData customerData);
}
