package com.jzg.erp.view;/**
 * author: gcc
 * date: 2016/6/30 17:45
 * email:
 */

import com.jzg.erp.model.CustomerDetail;
import com.jzg.erp.model.MatchCustomerData;

/**
 * author: guochen
 * date: 2016/6/30 17:45
 * email: 
 */
public interface IMatchCustomer extends IBaseView{
    void matchCustomerSuccess(MatchCustomerData matchCustomerData);
    void loadClientDetailSuccess(CustomerDetail customerDetail);
    void customerRedistribution(String msg);
}
