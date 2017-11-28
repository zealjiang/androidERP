package com.jzg.erp.presenter;

import com.jzg.erp.view.IBaseView;

/**
 * @author: voiceofnet
 * email: pengkun@jingzhengu.com
 * phone:18101032717
 * @time: 2016/6/7 15:43
 * @desc:
 */
public interface IPresenter<T extends IBaseView> {
    void attachView(T v);
    void detachView();
}
