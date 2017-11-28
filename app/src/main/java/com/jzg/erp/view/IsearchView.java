package com.jzg.erp.view;

import com.jzg.erp.model.CarSourceData;

/**
 * author: guochen
 * date: 2016/6/24 16:58
 * email: 
 */
public interface IsearchView extends IBaseView {
    void SearchSuccess(CarSourceData data);
    void searchMoreSuccess(CarSourceData data);

}
