package com.jzg.erp.model;

/**
 * Created by zealjiang on 2016/6/28 17:05.
 * Email: zealjiang@126.com
 */
public class XianqianCity {


    /**
     * status : 100
     * msg :
     * hasStandard : 1
     * cityStandar : 国V
     */

    private String status;
    private String msg;
    private int hasStandard;
    private String cityStandar;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getHasStandard() {
        return hasStandard;
    }

    public void setHasStandard(int hasStandard) {
        this.hasStandard = hasStandard;
    }

    public String getCityStandar() {
        return cityStandar;
    }

    public void setCityStandar(String cityStandar) {
        this.cityStandar = cityStandar;
    }
}
