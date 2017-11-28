package com.jzg.erp.model;

/**
 * @author: voiceofnet
 * email: pengkun@jingzhengu.com
 * phone:18101032717
 * @time: 2016/6/22 19:04
 * @desc:
 */
public class Task {
    private int id;
    private String customerName;
    private int customerType;
    private int state;
    private String taskName;

    public Task(int id, String customerName, int customerType, int state, String taskName) {
        this.id = id;
        this.customerName = customerName;
        this.customerType = customerType;
        this.state = state;
        this.taskName = taskName;
    }

    public Task() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getCustomerType() {
        return customerType;
    }

    public void setCustomerType(int customerType) {
        this.customerType = customerType;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
}
