package com.jzg.erp.model;

/**
 * Created by zealjiang on 2016/6/27 11:46.
 * Email: zealjiang@126.com
 */
public class AccountInf extends com.jzg.erp.base.BaseObject{

    /**
     * HeadImageUrl : /JietongdaImage/2016/06/04/1ee94d36-1a8a-4b92-92c0-2592a8ff4304_{0}.jpg
     * UserName : xxxxxxxxx
     * Tel : xxxxxxxxx
     * DicSubValue : 测试部
     * DicSubValueZW : 工程师
     * StoreName : 测试门店3
     */

    private DataBean Data;

    public DataBean getData() {
        return Data;
    }

    public void setData(DataBean Data) {
        this.Data = Data;
    }

    public static class DataBean {
        private String HeadImageUrl;
        private String UserName;
        private String TrueName;
        private String Tel;
        private String DicSubValue;
        private String DicSubValueZW;
        private String StoreName;

        public String getHeadImageUrl() {
            return HeadImageUrl;
        }

        public void setHeadImageUrl(String HeadImageUrl) {
            this.HeadImageUrl = HeadImageUrl;
        }

        public String getUserName() {
            return UserName;
        }

        public void setUserName(String UserName) {
            this.UserName = UserName;
        }

        public String getTel() {
            return Tel;
        }

        public void setTel(String Tel) {
            this.Tel = Tel;
        }

        public String getDicSubValue() {
            return DicSubValue;
        }

        public void setDicSubValue(String DicSubValue) {
            this.DicSubValue = DicSubValue;
        }

        public String getDicSubValueZW() {
            return DicSubValueZW;
        }

        public void setDicSubValueZW(String DicSubValueZW) {
            this.DicSubValueZW = DicSubValueZW;
        }

        public String getStoreName() {
            return StoreName;
        }

        public void setStoreName(String StoreName) {
            this.StoreName = StoreName;
        }

        public String getTrueName() {
            return TrueName;
        }

        public void setTrueName(String trueName) {
            TrueName = trueName;
        }
    }



}
