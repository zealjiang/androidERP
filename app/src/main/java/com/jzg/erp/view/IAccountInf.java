package com.jzg.erp.view;

import com.jzg.erp.model.AccountInf;

/**
 *
 * @author zealjiang
 * @time 2016/6/27 12:01
 */
public interface IAccountInf extends IBaseView{
    void succeed(AccountInf.DataBean accountInf);
    void succeedHead(AccountInf.DataBean accountInf);
    void errorHead(String error);
}
