package com.jzg.erp.model;

import java.io.Serializable;

/**
 * @author: voiceofnet
 * email: pengkun@jingzhengu.com
 * phone:18101032717
 * @time: 2016/6/22 16:05
 * @desc:
 */
public class UserWrapper extends com.jzg.erp.base.BaseObject implements Serializable{

    /**
     * UserID : 52
     * OrganizationID : 0
     * UserName : xxxxxxxxx
     * UserPass : d775b79f7d3d35f30a5c2450205dddb6
     * TrueName : 裴俊辉
     * Gender : 男
     * Tel : 15600623984
     * Status : 1
     * CreateTime : 2016-06-07T15:13:11.947
     * StoreId : 4
     * DepartMentId : 6
     * DutyId : 28
     * HeadImageUrl : /JietongdaImage/2016/06/07/fe4c6cf8-ab38-4380-83e7-5ee8c48ae8f9_{0}.jpg
     * DepName : null
     * DutyName : null
     * GroupID : 1
     */

    private User Data;

    public User getData() {
        return Data;
    }

    public void setData(User Data) {
        this.Data = Data;
    }

    public static class User implements Serializable{
        private int UserID;
        private int OrganizationID;
        private String UserName;
        private String UserPass;
        private String TrueName;
        private String Gender;
        private String Tel;
        private int Status;
        private String CreateTime;
        private int StoreId;
        private int DepartMentId;
        private int DutyId;
        private String HeadImageUrl;
        private Object DepName;
        private Object DutyName;
        private int GroupID;

        public int getUserID() {
            return UserID;
        }

        public void setUserID(int UserID) {
            this.UserID = UserID;
        }

        public int getOrganizationID() {
            return OrganizationID;
        }

        public void setOrganizationID(int OrganizationID) {
            this.OrganizationID = OrganizationID;
        }

        public String getUserName() {
            return UserName;
        }

        public void setUserName(String UserName) {
            this.UserName = UserName;
        }

        public String getUserPass() {
            return UserPass;
        }

        public void setUserPass(String UserPass) {
            this.UserPass = UserPass;
        }

        public String getTrueName() {
            return TrueName;
        }

        public void setTrueName(String TrueName) {
            this.TrueName = TrueName;
        }

        public String getGender() {
            return Gender;
        }

        public void setGender(String Gender) {
            this.Gender = Gender;
        }

        public String getTel() {
            return Tel;
        }

        public void setTel(String Tel) {
            this.Tel = Tel;
        }

        public int getStatus() {
            return Status;
        }

        public void setStatus(int Status) {
            this.Status = Status;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String CreateTime) {
            this.CreateTime = CreateTime;
        }

        public int getStoreId() {
            return StoreId;
        }

        public void setStoreId(int StoreId) {
            this.StoreId = StoreId;
        }

        public int getDepartMentId() {
            return DepartMentId;
        }

        public void setDepartMentId(int DepartMentId) {
            this.DepartMentId = DepartMentId;
        }

        public int getDutyId() {
            return DutyId;
        }

        public void setDutyId(int DutyId) {
            this.DutyId = DutyId;
        }

        public String getHeadImageUrl() {
            return HeadImageUrl;
        }

        public void setHeadImageUrl(String HeadImageUrl) {
            this.HeadImageUrl = HeadImageUrl;
        }

        public Object getDepName() {
            return DepName;
        }

        public void setDepName(Object DepName) {
            this.DepName = DepName;
        }

        public Object getDutyName() {
            return DutyName;
        }

        public void setDutyName(Object DutyName) {
            this.DutyName = DutyName;
        }

        public int getGroupID() {
            return GroupID;
        }

        public void setGroupID(int GroupID) {
            this.GroupID = GroupID;
        }
    }
}
