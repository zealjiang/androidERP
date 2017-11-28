package com.jzg.erp.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zealjiang on 2016/6/28 14:14.
 * Email: zealjiang@126.com
 */
public class City implements Serializable{


    /**
     * msg : 成功！
     * status : 100
     * content : [{"Id":0,"Name":"全部地区"},{"Id":601,"Name":"南宁"}]
     */

    private String msg;
    private int status;
    /**
     * Id : 0
     * Name : 全部地区
     */

    private List<ContentBean> content;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<ContentBean> getContent() {
        return content;
    }

    public void setContent(List<ContentBean> content) {
        this.content = content;
    }

    public static class ContentBean {
        private int Id;
        private String Name;

        public int getId() {
            return Id;
        }

        public void setId(int Id) {
            this.Id = Id;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }
    }
}
