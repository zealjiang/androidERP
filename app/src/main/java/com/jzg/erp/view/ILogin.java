package com.jzg.erp.view;/**
 * Created by voiceofnet on 2016/6/22.
 */

import com.jzg.erp.model.UserWrapper;

/**
 * @author: voiceofnet
 * email: pengkun@jingzhengu.com
 * phone:18101032717
 * @time: 2016/6/22 16:04
 * @desc:
 */
public interface ILogin extends IBaseView {
    void loginSucceed(UserWrapper.User user);
}
