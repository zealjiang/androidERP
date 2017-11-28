package com.jzg.erp.view;

/***
 * 所有访问网络后没有data返回的界面都用这个
 */
public interface ISucceedWithoutNoResult extends IBaseView {

    void onSucceed();
}
