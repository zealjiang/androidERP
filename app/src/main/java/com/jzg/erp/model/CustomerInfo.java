package com.jzg.erp.model;

import java.util.List;

/**
 * Created by JZG on 2016/6/23.
 */
public class CustomerInfo extends BaseObject{


    /**
     * countBooking : 1
     * countDeal : 1
     * countFailure : 0
     * countIntend : 2
     * customerList : [{"Address":"天津捷通达","AllotDate":"1900-01-01T00:00:00","AllotUser":"","BrowserCar":"","BuyCarType":61,"CityAreaID":20101,"CityAreaName":"东城区","CityID":201,"CityName":"北京","CreateTime":"2016-06-15T11:41:43.517","CreateUser":"裴俊辉","CustomerID":35,"CustomerLevel":50,"CustomerName":"李山美","CustomerSource":45,"CustomerStatus":39,"CustomerType":62,"FollowUpStatus":0,"FollowUpTime":"1900-01-01T00:00:00","Identitinfy":"111111111111111111","IntendCar":"2_4477,3_2040,26_2406,14_1582,82_2045","IntendCarName":"奔驰 奔驰GLA级,宝马 宝马6系(进口),本田 雅阁,北汽制造 陆霸,保时捷 保时捷911","IntendMoney":"34W","IntendRemark":"有钱任性","LinkMan":"李山美","NextFlowUp":"","OrderId":2,"PreCharDate":"50","ProvinceID":2,"ProvinceName":"北京","SalesId":41,"SerialNumber":"C16061500002","Sex":"女","Status":0,"Telphone":"15678084032","Transmission":2,"UpdateTime":"1900-01-01T00:00:00","UpdateUser":""},{"Address":"运通行集团总部","AllotDate":"2016-06-13T10:59:37.407","AllotUser":"","BrowserCar":"","BuyCarType":60,"CityAreaID":340802,"CityAreaName":"迎江区","CityID":102,"CityName":"安庆","CreateTime":"2016-06-08T16:35:44.35","CreateUser":"李天","CustomerID":6,"CustomerLevel":51,"CustomerName":"大爷","CustomerSource":46,"CustomerStatus":39,"CustomerType":58,"FollowUpStatus":0,"FollowUpTime":"1900-01-01T00:00:00","Identitinfy":"420821333333","IntendCar":"2348,3528,2_3528","IntendCarName":"宝马 宝马6系(进口),宝马 宝马1系(进口),奔驰 奔驰GLK级","IntendMoney":"8W","IntendRemark":"奥迪，宝马都便宜的也行","LinkMan":"捷通达","NextFlowUp":"","OrderId":0,"PreCharDate":"51","ProvinceID":1,"ProvinceName":"安徽","SalesId":41,"SerialNumber":"C2016060800001","Sex":"女","Status":0,"Telphone":"13434324324","Transmission":2,"UpdateTime":"1900-01-01T00:00:00","UpdateUser":""}]
     */

    private int countBooking;
    private int countDeal;
    private int countFailure;
    private int countIntend;
    /**
     * Address : 天津捷通达
     * AllotDate : 1900-01-01T00:00:00
     * AllotUser :
     * BrowserCar :
     * BuyCarType : 61
     * CityAreaID : 20101
     * CityAreaName : 东城区
     * CityID : 201
     * CityName : 北京
     * CreateTime : 2016-06-15T11:41:43.517
     * CreateUser : 裴俊辉
     * CustomerID : 35
     * CustomerLevel : 50
     * CustomerName : 李山美
     * CustomerSource : 45
     * CustomerStatus : 39
     * CustomerType : 62
     * FollowUpStatus : 0
     * FollowUpTime : 1900-01-01T00:00:00
     * Identitinfy : 111111111111111111
     * IntendCar : 2_4477,3_2040,26_2406,14_1582,82_2045
     * IntendCarName : 奔驰 奔驰GLA级,宝马 宝马6系(进口),本田 雅阁,北汽制造 陆霸,保时捷 保时捷911
     * IntendMoney : 34W
     * IntendRemark : 有钱任性
     * LinkMan : 李山美
     * NextFlowUp :
     * OrderId : 2
     * PreCharDate : 50
     * ProvinceID : 2
     * ProvinceName : 北京
     * SalesId : 41
     * SerialNumber : C16061500002
     * Sex : 女
     * Status : 0
     * Telphone : 15678084032
     * Transmission : 2
     * UpdateTime : 1900-01-01T00:00:00
     * UpdateUser :
     */

    private List<CustomerListEntity> customerList;

    public int getCountBooking() {
        return countBooking;
    }

    public void setCountBooking(int countBooking) {
        this.countBooking = countBooking;
    }

    public int getCountDeal() {
        return countDeal;
    }

    public void setCountDeal(int countDeal) {
        this.countDeal = countDeal;
    }

    public int getCountFailure() {
        return countFailure;
    }

    public void setCountFailure(int countFailure) {
        this.countFailure = countFailure;
    }

    public int getCountIntend() {
        return countIntend;
    }

    public void setCountIntend(int countIntend) {
        this.countIntend = countIntend;
    }

    public List<CustomerListEntity> getCustomerList() {
        return customerList;
    }

    public void setCustomerList(List<CustomerListEntity> customerList) {
        this.customerList = customerList;
    }

    public static class CustomerListEntity {
        private String Address;
        private String AllotDate;
        private String AllotUser;
        private String BrowserCar;
        private int BuyCarType;
        private int CityAreaID;
        private String CityAreaName;
        private int CityID;
        private String CityName;
        private String CreateTime;
        private String CreateUser;
        private int CustomerID;
        private int CustomerLevel;
        private String CustomerName;
        private int CustomerSource;
        private int CustomerStatus;
        private int CustomerType;
        private int FollowUpStatus;
        private String FollowUpTime;
        private String Identitinfy;
        private String IntendCar;
        private String IntendCarName;
        private String IntendMoney;
        private String IntendRemark;
        private String LinkMan;
        private String NextFlowUp;
        private int OrderId;
        private String PreCharDate;
        private int ProvinceID;
        private String ProvinceName;
        private int SalesId;
        private String SerialNumber;
        private String Sex;
        private int Status;
        private String Telphone;
        private int Transmission;
        private String UpdateTime;
        private String UpdateUser;

        public String getAddress() {
            return Address;
        }

        public void setAddress(String Address) {
            this.Address = Address;
        }

        public String getAllotDate() {
            return AllotDate;
        }

        public void setAllotDate(String AllotDate) {
            this.AllotDate = AllotDate;
        }

        public String getAllotUser() {
            return AllotUser;
        }

        public void setAllotUser(String AllotUser) {
            this.AllotUser = AllotUser;
        }

        public String getBrowserCar() {
            return BrowserCar;
        }

        public void setBrowserCar(String BrowserCar) {
            this.BrowserCar = BrowserCar;
        }

        public int getBuyCarType() {
            return BuyCarType;
        }

        public void setBuyCarType(int BuyCarType) {
            this.BuyCarType = BuyCarType;
        }

        public int getCityAreaID() {
            return CityAreaID;
        }

        public void setCityAreaID(int CityAreaID) {
            this.CityAreaID = CityAreaID;
        }

        public String getCityAreaName() {
            return CityAreaName;
        }

        public void setCityAreaName(String CityAreaName) {
            this.CityAreaName = CityAreaName;
        }

        public int getCityID() {
            return CityID;
        }

        public void setCityID(int CityID) {
            this.CityID = CityID;
        }

        public String getCityName() {
            return CityName;
        }

        public void setCityName(String CityName) {
            this.CityName = CityName;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String CreateTime) {
            this.CreateTime = CreateTime;
        }

        public String getCreateUser() {
            return CreateUser;
        }

        public void setCreateUser(String CreateUser) {
            this.CreateUser = CreateUser;
        }

        public int getCustomerID() {
            return CustomerID;
        }

        public void setCustomerID(int CustomerID) {
            this.CustomerID = CustomerID;
        }

        public int getCustomerLevel() {
            return CustomerLevel;
        }

        public void setCustomerLevel(int CustomerLevel) {
            this.CustomerLevel = CustomerLevel;
        }

        public String getCustomerName() {
            return CustomerName;
        }

        public void setCustomerName(String CustomerName) {
            this.CustomerName = CustomerName;
        }

        public int getCustomerSource() {
            return CustomerSource;
        }

        public void setCustomerSource(int CustomerSource) {
            this.CustomerSource = CustomerSource;
        }

        public int getCustomerStatus() {
            return CustomerStatus;
        }

        public void setCustomerStatus(int CustomerStatus) {
            this.CustomerStatus = CustomerStatus;
        }

        public int getCustomerType() {
            return CustomerType;
        }

        public void setCustomerType(int CustomerType) {
            this.CustomerType = CustomerType;
        }

        public int getFollowUpStatus() {
            return FollowUpStatus;
        }

        public void setFollowUpStatus(int FollowUpStatus) {
            this.FollowUpStatus = FollowUpStatus;
        }

        public String getFollowUpTime() {
            return FollowUpTime;
        }

        public void setFollowUpTime(String FollowUpTime) {
            this.FollowUpTime = FollowUpTime;
        }

        public String getIdentitinfy() {
            return Identitinfy;
        }

        public void setIdentitinfy(String Identitinfy) {
            this.Identitinfy = Identitinfy;
        }

        public String getIntendCar() {
            return IntendCar;
        }

        public void setIntendCar(String IntendCar) {
            this.IntendCar = IntendCar;
        }

        public String getIntendCarName() {
            return IntendCarName;
        }

        public void setIntendCarName(String IntendCarName) {
            this.IntendCarName = IntendCarName;
        }

        public String getIntendMoney() {
            return IntendMoney;
        }

        public void setIntendMoney(String IntendMoney) {
            this.IntendMoney = IntendMoney;
        }

        public String getIntendRemark() {
            return IntendRemark;
        }

        public void setIntendRemark(String IntendRemark) {
            this.IntendRemark = IntendRemark;
        }

        public String getLinkMan() {
            return LinkMan;
        }

        public void setLinkMan(String LinkMan) {
            this.LinkMan = LinkMan;
        }

        public String getNextFlowUp() {
            return NextFlowUp;
        }

        public void setNextFlowUp(String NextFlowUp) {
            this.NextFlowUp = NextFlowUp;
        }

        public int getOrderId() {
            return OrderId;
        }

        public void setOrderId(int OrderId) {
            this.OrderId = OrderId;
        }

        public String getPreCharDate() {
            return PreCharDate;
        }

        public void setPreCharDate(String PreCharDate) {
            this.PreCharDate = PreCharDate;
        }

        public int getProvinceID() {
            return ProvinceID;
        }

        public void setProvinceID(int ProvinceID) {
            this.ProvinceID = ProvinceID;
        }

        public String getProvinceName() {
            return ProvinceName;
        }

        public void setProvinceName(String ProvinceName) {
            this.ProvinceName = ProvinceName;
        }

        public int getSalesId() {
            return SalesId;
        }

        public void setSalesId(int SalesId) {
            this.SalesId = SalesId;
        }

        public String getSerialNumber() {
            return SerialNumber;
        }

        public void setSerialNumber(String SerialNumber) {
            this.SerialNumber = SerialNumber;
        }

        public String getSex() {
            return Sex;
        }

        public void setSex(String Sex) {
            this.Sex = Sex;
        }

        public int getStatus() {
            return Status;
        }

        public void setStatus(int Status) {
            this.Status = Status;
        }

        public String getTelphone() {
            return Telphone;
        }

        public void setTelphone(String Telphone) {
            this.Telphone = Telphone;
        }

        public int getTransmission() {
            return Transmission;
        }

        public void setTransmission(int Transmission) {
            this.Transmission = Transmission;
        }

        public String getUpdateTime() {
            return UpdateTime;
        }

        public void setUpdateTime(String UpdateTime) {
            this.UpdateTime = UpdateTime;
        }

        public String getUpdateUser() {
            return UpdateUser;
        }

        public void setUpdateUser(String UpdateUser) {
            this.UpdateUser = UpdateUser;
        }
    }
}
