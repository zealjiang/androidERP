package com.jzg.erp.view;/**
 * Created by voiceofnet on 2016/6/30.
 */

import com.jzg.erp.model.TaskItemGroupWrapper;

import java.util.List;

/**
 * @author: voiceofnet
 * email: pengkun@jingzhengu.com
 * phone:18101032717
 * @time: 2016/6/30 17:41
 * @desc:
 */
public interface IGetFollowUp {
    void onSucceed(List<TaskItemGroupWrapper.TaskGroup> resultList);
    void onError(String msg);
}
