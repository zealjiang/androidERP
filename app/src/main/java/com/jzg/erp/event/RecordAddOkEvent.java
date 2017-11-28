package com.jzg.erp.event;

/**
 * @author: voiceofnet
 * email: pengkun@jingzhengu.com
 * phone:18101032717
 * @time: 2016/7/7 11:41
 * @desc:
 */
public class RecordAddOkEvent {
    public static final int EVENT_TYPE_CALL=0;
    public static final int EVENT_TYPE_REC=1;
    public static final int EVENT_TYPE_ITEM=2;

    private int type;
    private String msg;

    public RecordAddOkEvent(int type, String msg) {
        this.type = type;
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
