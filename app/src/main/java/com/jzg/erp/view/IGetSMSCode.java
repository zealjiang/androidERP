package com.jzg.erp.view;/**
 * Created by voiceofnet on 2016/6/22.
 */

import com.jzg.erp.model.SMS;

/**
 * @author: voiceofnet
 * email: pengkun@jingzhengu.com
 * phone:18101032717
 * @time: 2016/6/22 16:20
 * @desc:
 */
public interface IGetSMSCode extends IBaseView {
    void getSMSCode(SMS sms);
    void validateOK();
}
