package com.jzg.erp.presenter;

/**
 * @author: voiceofnet
 * email: pengkun@jingzhengu.com
 * phone:18101032717
 * @time: 2016/6/7 15:50
 * @desc:
 */
public abstract class BasePresenter<T> {
    protected String TAG = getClass().getName();
    protected T baseView;

    public BasePresenter(T from) {
        this.baseView = from;
    }
}
