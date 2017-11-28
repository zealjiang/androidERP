package com.jzg.erp.model;/**
 * author: gcc
 * date: 2016/7/2 15:36
 * email:
 */

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * author: guochen
 * date: 2016/7/2 15:36
 * email: 
 */
public class CustomerData extends BaseObject implements Parcelable {
    /**
     * CustomerID : 40
     * SerialNumber : C16062800005
     * CustomerStatus : 2
     * CustomerSource : 45
     * CustomerLevel : 50
     * LinkMan : 哈市的佛山接电费的收费水电费水电费是飞是
     * CustomerName : 哈市的佛山接电费的收费水电费水电费是飞是
     * Telphone : 13521512789
     * IntendCarName : 奥迪 奥迪A4
     * IntendCar : 9_1906
     * IntendMoney : 555
     * IntendRemark : dfgdh
     * SalesId : 55
     * Sex :
     * Identitinfy :
     * Address : sdf
     * CreateUser : 123
     * CreateTime : 2016-06-28T04:59:13
     * AllotUser :
     * AllotDate : 1900-01-01T12:00:00
     * UpdateUser :
     * UpdateTime : 1900-01-01T12:00:00
     * FollowUpTime : 1900-01-01T12:00:00
     * NextFlowUp :
     * BrowserCar :
     * OrderId : 30
     * CustomerType : 58
     * BuyCarType : 60
     * PreCharDate : 50
     * ProvinceID : 2
     * ProvinceName : 北京
     * CityID : 201
     * CityName : 北京
     * CityAreaID : 1536
     * CityAreaName : 通州区
     * Transmission : 2
     * FollowUpStatus : 0
     */

    private List<DataBean> Data;

    public List<DataBean> getData() {
        return Data;
    }

    public void setData(List<DataBean> Data) {
        this.Data = Data;
    }

    public static class DataBean implements Parcelable {
        private int CustomerID;
        private String SerialNumber;
        private int CustomerStatus;
        private int CustomerSource;
        private int CustomerLevel;
        private String LinkMan;
        private String CustomerName;
        private String Telphone;
        private String IntendCarName;
        private String IntendCar;
        private String IntendMoney;
        private String IntendRemark;
        private int SalesId;
        private String Sex;
        private String Identitinfy;
        private String Address;
        private String CreateUser;
        private String CreateTime;
        private String AllotUser;
        private String AllotDate;
        private String UpdateUser;
        private String UpdateTime;
        private String FollowUpTime;
        private String NextFlowUp;
        private String BrowserCar;
        private int OrderId;
        private int CustomerType;
        private int BuyCarType;
        private String PreCharDate;
        private int ProvinceID;
        private String ProvinceName;
        private int CityID;
        private String CityName;
        private int CityAreaID;
        private String CityAreaName;
        private int Transmission;
        private int FollowUpStatus;

        public int getCustomerID() {
            return CustomerID;
        }

        public void setCustomerID(int CustomerID) {
            this.CustomerID = CustomerID;
        }

        public String getSerialNumber() {
            return SerialNumber;
        }

        public void setSerialNumber(String SerialNumber) {
            this.SerialNumber = SerialNumber;
        }

        public int getCustomerStatus() {
            return CustomerStatus;
        }

        public void setCustomerStatus(int CustomerStatus) {
            this.CustomerStatus = CustomerStatus;
        }

        public int getCustomerSource() {
            return CustomerSource;
        }

        public void setCustomerSource(int CustomerSource) {
            this.CustomerSource = CustomerSource;
        }

        public int getCustomerLevel() {
            return CustomerLevel;
        }

        public void setCustomerLevel(int CustomerLevel) {
            this.CustomerLevel = CustomerLevel;
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

        public String getTelphone() {
            return Telphone;
        }

        public void setTelphone(String Telphone) {
            this.Telphone = Telphone;
        }

        public String getIntendCarName() {
            return IntendCarName;
        }

        public void setIntendCarName(String IntendCarName) {
            this.IntendCarName = IntendCarName;
        }

        public String getIntendCar() {
            return IntendCar;
        }

        public void setIntendCar(String IntendCar) {
            this.IntendCar = IntendCar;
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

        public int getSalesId() {
            return SalesId;
        }

        public void setSalesId(int SalesId) {
            this.SalesId = SalesId;
        }

        public String getSex() {
            return Sex;
        }

        public void setSex(String Sex) {
            this.Sex = Sex;
        }

        public String getIdentitinfy() {
            return Identitinfy;
        }

        public void setIdentitinfy(String Identitinfy) {
            this.Identitinfy = Identitinfy;
        }

        public String getAddress() {
            return Address;
        }

        public void setAddress(String Address) {
            this.Address = Address;
        }

        public String getCreateUser() {
            return CreateUser;
        }

        public void setCreateUser(String CreateUser) {
            this.CreateUser = CreateUser;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String CreateTime) {
            this.CreateTime = CreateTime;
        }

        public String getAllotUser() {
            return AllotUser;
        }

        public void setAllotUser(String AllotUser) {
            this.AllotUser = AllotUser;
        }

        public String getAllotDate() {
            return AllotDate;
        }

        public void setAllotDate(String AllotDate) {
            this.AllotDate = AllotDate;
        }

        public String getUpdateUser() {
            return UpdateUser;
        }

        public void setUpdateUser(String UpdateUser) {
            this.UpdateUser = UpdateUser;
        }

        public String getUpdateTime() {
            return UpdateTime;
        }

        public void setUpdateTime(String UpdateTime) {
            this.UpdateTime = UpdateTime;
        }

        public String getFollowUpTime() {
            return FollowUpTime;
        }

        public void setFollowUpTime(String FollowUpTime) {
            this.FollowUpTime = FollowUpTime;
        }

        public String getNextFlowUp() {
            return NextFlowUp;
        }

        public void setNextFlowUp(String NextFlowUp) {
            this.NextFlowUp = NextFlowUp;
        }

        public String getBrowserCar() {
            return BrowserCar;
        }

        public void setBrowserCar(String BrowserCar) {
            this.BrowserCar = BrowserCar;
        }

        public int getOrderId() {
            return OrderId;
        }

        public void setOrderId(int OrderId) {
            this.OrderId = OrderId;
        }

        public int getCustomerType() {
            return CustomerType;
        }

        public void setCustomerType(int CustomerType) {
            this.CustomerType = CustomerType;
        }

        public int getBuyCarType() {
            return BuyCarType;
        }

        public void setBuyCarType(int BuyCarType) {
            this.BuyCarType = BuyCarType;
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

        public int getTransmission() {
            return Transmission;
        }

        public void setTransmission(int Transmission) {
            this.Transmission = Transmission;
        }

        public int getFollowUpStatus() {
            return FollowUpStatus;
        }

        public void setFollowUpStatus(int FollowUpStatus) {
            this.FollowUpStatus = FollowUpStatus;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.CustomerID);
            dest.writeString(this.SerialNumber);
            dest.writeInt(this.CustomerStatus);
            dest.writeInt(this.CustomerSource);
            dest.writeInt(this.CustomerLevel);
            dest.writeString(this.LinkMan);
            dest.writeString(this.CustomerName);
            dest.writeString(this.Telphone);
            dest.writeString(this.IntendCarName);
            dest.writeString(this.IntendCar);
            dest.writeString(this.IntendMoney);
            dest.writeString(this.IntendRemark);
            dest.writeInt(this.SalesId);
            dest.writeString(this.Sex);
            dest.writeString(this.Identitinfy);
            dest.writeString(this.Address);
            dest.writeString(this.CreateUser);
            dest.writeString(this.CreateTime);
            dest.writeString(this.AllotUser);
            dest.writeString(this.AllotDate);
            dest.writeString(this.UpdateUser);
            dest.writeString(this.UpdateTime);
            dest.writeString(this.FollowUpTime);
            dest.writeString(this.NextFlowUp);
            dest.writeString(this.BrowserCar);
            dest.writeInt(this.OrderId);
            dest.writeInt(this.CustomerType);
            dest.writeInt(this.BuyCarType);
            dest.writeString(this.PreCharDate);
            dest.writeInt(this.ProvinceID);
            dest.writeString(this.ProvinceName);
            dest.writeInt(this.CityID);
            dest.writeString(this.CityName);
            dest.writeInt(this.CityAreaID);
            dest.writeString(this.CityAreaName);
            dest.writeInt(this.Transmission);
            dest.writeInt(this.FollowUpStatus);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.CustomerID = in.readInt();
            this.SerialNumber = in.readString();
            this.CustomerStatus = in.readInt();
            this.CustomerSource = in.readInt();
            this.CustomerLevel = in.readInt();
            this.LinkMan = in.readString();
            this.CustomerName = in.readString();
            this.Telphone = in.readString();
            this.IntendCarName = in.readString();
            this.IntendCar = in.readString();
            this.IntendMoney = in.readString();
            this.IntendRemark = in.readString();
            this.SalesId = in.readInt();
            this.Sex = in.readString();
            this.Identitinfy = in.readString();
            this.Address = in.readString();
            this.CreateUser = in.readString();
            this.CreateTime = in.readString();
            this.AllotUser = in.readString();
            this.AllotDate = in.readString();
            this.UpdateUser = in.readString();
            this.UpdateTime = in.readString();
            this.FollowUpTime = in.readString();
            this.NextFlowUp = in.readString();
            this.BrowserCar = in.readString();
            this.OrderId = in.readInt();
            this.CustomerType = in.readInt();
            this.BuyCarType = in.readInt();
            this.PreCharDate = in.readString();
            this.ProvinceID = in.readInt();
            this.ProvinceName = in.readString();
            this.CityID = in.readInt();
            this.CityName = in.readString();
            this.CityAreaID = in.readInt();
            this.CityAreaName = in.readString();
            this.Transmission = in.readInt();
            this.FollowUpStatus = in.readInt();
        }

        public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel source) {
                return new DataBean(source);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeList(this.Data);
    }

    public CustomerData() {
    }

    protected CustomerData(Parcel in) {
        super(in);
        this.Data = new ArrayList<DataBean>();
        in.readList(this.Data, DataBean.class.getClassLoader());
    }

    public static final Creator<CustomerData> CREATOR = new Creator<CustomerData>() {
        @Override
        public CustomerData createFromParcel(Parcel source) {
            return new CustomerData(source);
        }

        @Override
        public CustomerData[] newArray(int size) {
            return new CustomerData[size];
        }
    };
}
