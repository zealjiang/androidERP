package com.jzg.erp.model;

import java.util.List;

/**
 * @author: voiceofnet
 * email: pengkun@jingzhengu.com
 * phone:18101032717
 * @time: 2016/7/3 19:03
 * @desc:
 */
public class TaskItemGroupWrapper extends com.jzg.erp.base.BaseObject {

    /**
     * Key : 2016年06月14日
     * DataList : [{"CreateDateStrYMD":"2016年06月14日","CreateDateStrHM":"14:10","LinkMan":null,"CustomerName":null,"FollowupId":17,"CustomerId":20,"FollowUpStatus":"","FollowingRecord":"aaaaaaaaaaaaaaa","ReturnVisit":1,"ReturnVisitDate":"2016-06-15T00:00:00","ReturnVisitTime":"下午","SpecialInstruction":"aaaaaaaaaaaaaaaa","FaliureReason":"","OnShopDate":"2016-06-14T14:10:48.737","OnShopTime":"","FollowType":"2","CreateDate":"2016-06-14T14:10:48.737","CreateUser":"aaaaa","NextShopDate":"2016-06-14T14:10:48.737","NextShopTime":"","CreateUserID":null,"CustomerLevel":null,"FollowUpItemState":0},{"CreateDateStrYMD":"2016年06月14日","CreateDateStrHM":"14:10","LinkMan":null,"CustomerName":null,"FollowupId":16,"CustomerId":20,"FollowUpStatus":"","FollowingRecord":"aaaaaaaaaaaaaaa","ReturnVisit":1,"ReturnVisitDate":"2016-06-15T00:00:00","ReturnVisitTime":"下午","SpecialInstruction":"aaaaaaaaaaaaaaaa","FaliureReason":"","OnShopDate":"2016-06-14T14:10:45.63","OnShopTime":"","FollowType":"2","CreateDate":"2016-06-14T14:10:45.63","CreateUser":"aaaaa","NextShopDate":"2016-06-14T14:10:45.63","NextShopTime":"","CreateUserID":null,"CustomerLevel":null,"FollowUpItemState":0},{"CreateDateStrYMD":"2016年06月14日","CreateDateStrHM":"14:10","LinkMan":null,"CustomerName":null,"FollowupId":15,"CustomerId":20,"FollowUpStatus":"","FollowingRecord":"aaaaaaaaaaaaaaa","ReturnVisit":1,"ReturnVisitDate":"2016-06-15T00:00:00","ReturnVisitTime":"下午","SpecialInstruction":"aaaaaaaaaaaaaaaa","FaliureReason":"","OnShopDate":"2016-06-14T14:10:42.087","OnShopTime":"","FollowType":"2","CreateDate":"2016-06-14T14:10:42.087","CreateUser":"aaaaa","NextShopDate":"2016-06-14T14:10:42.087","NextShopTime":"","CreateUserID":null,"CustomerLevel":null,"FollowUpItemState":0},{"CreateDateStrYMD":"2016年06月14日","CreateDateStrHM":"14:10","LinkMan":null,"CustomerName":null,"FollowupId":14,"CustomerId":20,"FollowUpStatus":"","FollowingRecord":"aaaaaaaaaaaaaaa","ReturnVisit":1,"ReturnVisitDate":"2016-06-15T00:00:00","ReturnVisitTime":"下午","SpecialInstruction":"aaaaaaaaaaaaaaaa","FaliureReason":"","OnShopDate":"2016-06-14T14:10:36.213","OnShopTime":"","FollowType":"2","CreateDate":"2016-06-14T14:10:36.213","CreateUser":"aaaaa","NextShopDate":"2016-06-14T14:10:36.213","NextShopTime":"","CreateUserID":null,"CustomerLevel":null,"FollowUpItemState":0}]
     */

    private List<TaskGroup> Data;

    public List<TaskGroup> getData() {
        return Data;
    }

    public void setData(List<TaskGroup> Data) {
        this.Data = Data;
    }

    public static class TaskGroup {
        private String Key;
        /**
         * CreateDateStrYMD : 2016年06月14日
         * CreateDateStrHM : 14:10
         * LinkMan : null
         * CustomerName : null
         * FollowupId : 17
         * CustomerId : 20
         * FollowUpStatus :
         * FollowingRecord : aaaaaaaaaaaaaaa
         * ReturnVisit : 1
         * ReturnVisitDate : 2016-06-15T00:00:00
         * ReturnVisitTime : 下午
         * SpecialInstruction : aaaaaaaaaaaaaaaa
         * FaliureReason :
         * OnShopDate : 2016-06-14T14:10:48.737
         * OnShopTime :
         * FollowType : 2
         * CreateDate : 2016-06-14T14:10:48.737
         * CreateUser : aaaaa
         * NextShopDate : 2016-06-14T14:10:48.737
         * NextShopTime :
         * CreateUserID : null
         * CustomerLevel : null
         * FollowUpItemState : 0
         */

        private List<TaskItem> DataList;

        public String getKey() {
            return Key;
        }

        public void setKey(String Key) {
            this.Key = Key;
        }

        public List<TaskItem> getDataList() {
            return DataList;
        }

        public void setDataList(List<TaskItem> DataList) {
            this.DataList = DataList;
        }

        public static class TaskItem {
            private String CreateDateStrYMD;
            private String CreateDateStrHM;
            private String LinkMan;
            private String CustomerName;
            private int FollowupId;
            private int CustomerId;
            private String FollowUpStatus;
            private String FollowingRecord;
            private String SpecialInstruction;
            private String FaliureReason;
            private String OnShopDate;
            private String OnShopTime;
            private String FollowType;
            private String CreateDate;
            private String CreateUser;
            private String NextShopDate;
            private String NextShopTime;
            private String CreateUserID;
            private String CustomerLevel;
            private int FollowUpItemState;
            private String tag;
            public String getTag() {
                return tag;
            }

            public void setTag(String tag) {
                this.tag = tag;
            }

            public String getCreateDateStrYMD() {
                return CreateDateStrYMD;
            }

            public void setCreateDateStrYMD(String CreateDateStrYMD) {
                this.CreateDateStrYMD = CreateDateStrYMD;
            }

            public String getCreateDateStrHM() {
                return CreateDateStrHM;
            }

            public void setCreateDateStrHM(String CreateDateStrHM) {
                this.CreateDateStrHM = CreateDateStrHM;
            }

            public String getLinkMan() {
                return LinkMan;
            }

            public void setLinkMan(String LinkMan) {
                this.LinkMan = LinkMan;
            }

            public String getCustomerName() {
                return CustomerName;
            }

            public void setCustomerName(String CustomerName) {
                this.CustomerName = CustomerName;
            }

            public int getFollowupId() {
                return FollowupId;
            }

            public void setFollowupId(int FollowupId) {
                this.FollowupId = FollowupId;
            }

            public int getCustomerId() {
                return CustomerId;
            }

            public void setCustomerId(int CustomerId) {
                this.CustomerId = CustomerId;
            }

            public String getFollowUpStatus() {
                return FollowUpStatus;
            }

            public void setFollowUpStatus(String FollowUpStatus) {
                this.FollowUpStatus = FollowUpStatus;
            }

            public String getFollowingRecord() {
                return FollowingRecord;
            }

            public void setFollowingRecord(String FollowingRecord) {
                this.FollowingRecord = FollowingRecord;
            }

            public String getSpecialInstruction() {
                return SpecialInstruction;
            }

            public void setSpecialInstruction(String SpecialInstruction) {
                this.SpecialInstruction = SpecialInstruction;
            }

            public String getFaliureReason() {
                return FaliureReason;
            }

            public void setFaliureReason(String FaliureReason) {
                this.FaliureReason = FaliureReason;
            }

            public String getOnShopDate() {
                return OnShopDate;
            }

            public void setOnShopDate(String OnShopDate) {
                this.OnShopDate = OnShopDate;
            }

            public String getOnShopTime() {
                return OnShopTime;
            }

            public void setOnShopTime(String OnShopTime) {
                this.OnShopTime = OnShopTime;
            }

            public String getFollowType() {
                return FollowType;
            }

            public void setFollowType(String FollowType) {
                this.FollowType = FollowType;
            }

            public String getCreateDate() {
                return CreateDate;
            }

            public void setCreateDate(String CreateDate) {
                this.CreateDate = CreateDate;
            }

            public String getCreateUser() {
                return CreateUser;
            }

            public void setCreateUser(String CreateUser) {
                this.CreateUser = CreateUser;
            }

            public String getNextShopDate() {
                return NextShopDate;
            }

            public void setNextShopDate(String NextShopDate) {
                this.NextShopDate = NextShopDate;
            }

            public String getNextShopTime() {
                return NextShopTime;
            }

            public void setNextShopTime(String NextShopTime) {
                this.NextShopTime = NextShopTime;
            }

            public String getCreateUserID() {
                return CreateUserID;
            }

            public void setCreateUserID(String CreateUserID) {
                this.CreateUserID = CreateUserID;
            }

            public String getCustomerLevel() {
                return CustomerLevel;
            }

            public void setCustomerLevel(String CustomerLevel) {
                this.CustomerLevel = CustomerLevel;
            }

            public int getFollowUpItemState() {
                return FollowUpItemState;
            }

            public void setFollowUpItemState(int FollowUpItemState) {
                this.FollowUpItemState = FollowUpItemState;
            }
        }
    }
}
