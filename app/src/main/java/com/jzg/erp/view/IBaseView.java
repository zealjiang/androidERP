package com.jzg.erp.view;

/**
 * @author: voiceofnet
 * email: pengkun@jingzhengu.com
 * phone:18101032717
 * @time: 2016/6/7 15:41
 * @desc:
 */
public interface IBaseView {
    /**
     * 显示错误信息
     *
     * @param error 错误信息
     */
    void showError(String error);

    /**
     * 显示加载
     */
    void showDialog();

    /**
     * 关闭加载
     */
    void dismissDialog();

}
