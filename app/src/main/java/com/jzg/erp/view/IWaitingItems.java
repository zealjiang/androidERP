package com.jzg.erp.view;/**
 * Created by voiceofnet on 2016/7/2.
 */

import com.jzg.erp.model.WaitingItemWrapper;

/**
 * @author: voiceofnet
 * email: pengkun@jingzhengu.com
 * phone:18101032717
 * @time: 2016/7/2 19:52
 * @desc:
 */
public interface IWaitingItems extends IBaseView {
    void onSucceed(WaitingItemWrapper wrapper);

}
