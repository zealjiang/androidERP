package com.jzg.erp.event;

/**
 * @author: voiceofnet
 * email: pengkun@jingzhengu.com
 * phone:18101032717
 * @time: 2016/7/5 11:05
 * @desc:
 */
public class TaskCountEvent {
    private int id;
    private int allSum;
    private int todaySum;

    public TaskCountEvent() {
    }

    public TaskCountEvent(int id, int allSum, int todaySum) {
        this.id = id;
        this.allSum = allSum;
        this.todaySum = todaySum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAllSum() {
        return allSum;
    }

    public void setAllSum(int allSum) {
        this.allSum = allSum;
    }

    public int getTodaySum() {
        return todaySum;
    }

    public void setTodaySum(int todaySum) {
        this.todaySum = todaySum;
    }
}
