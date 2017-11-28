package com.jzg.erp.view;

import com.jzg.erp.model.Zhihu;

import java.util.List;

/**
 * @author: voiceofnet
 * email: pengkun@jingzhengu.com
 * phone:18101032717
 * @time: 2016/6/7 15:54
 * @desc:
 */
public interface IZhihu extends IBaseView{
    void success(List<Zhihu.OthersBean> resultList);
    void empty();
}
