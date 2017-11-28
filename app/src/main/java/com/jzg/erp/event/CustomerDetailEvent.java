package com.jzg.erp.event;

import com.jzg.erp.model.CustomerDetail;

/**
 * @author: voiceofnet
 * email: pengkun@jingzhengu.com
 * phone:18101032717
 * @time: 2016/6/30 14:18
 * @desc:
 */
public class CustomerDetailEvent {
    private int id;
    private CustomerDetail.DataBean detail;

    public CustomerDetailEvent(int id, CustomerDetail.DataBean detail) {
        this.id = id;
        this.detail = detail;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CustomerDetail.DataBean getDetail() {
        return detail;
    }

    public void setDetail(CustomerDetail.DataBean detail) {
        this.detail = detail;
    }
}
