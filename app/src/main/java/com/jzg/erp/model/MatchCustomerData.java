package com.jzg.erp.model;/**
 * author: gcc
 * date: 2016/7/2 18:11
 * email:
 */

import android.os.Parcel;

import java.util.ArrayList;
import java.util.List;

/**
 * author: guochen
 * date: 2016/7/2 18:11
 * email: 
 */
public class MatchCustomerData extends BaseObject {

    /**
     * CustomerID : 12
     * CarSourceID : 174
     * CustomerName : 李白
     * CustomerStatus : 2
     * CustomerDep : 市场部
     * CustomerLevel : A
     */

    private List<DataBean> Data;

    public List<DataBean> getData() {
        return Data;
    }

    public void setData(List<DataBean> Data) {
        this.Data = Data;
    }

    public static class DataBean implements android.os.Parcelable {
        private int CustomerID;
        private int CarSourceID;
        private String CustomerName;
        private int CustomerStatus;
        private String CustomerDep;
        private String CustomerLevel;
        private String LinkMan;

        public String getLinkMan() {
            return LinkMan;
        }

        public void setLinkMan(String linkMan) {
            LinkMan = linkMan;
        }

        public int getCustomerID() {
            return CustomerID;
        }

        public void setCustomerID(int CustomerID) {
            this.CustomerID = CustomerID;
        }

        public int getCarSourceID() {
            return CarSourceID;
        }

        public void setCarSourceID(int CarSourceID) {
            this.CarSourceID = CarSourceID;
        }

        public String getCustomerName() {
            return CustomerName;
        }

        public void setCustomerName(String CustomerName) {
            this.CustomerName = CustomerName;
        }

        public int getCustomerStatus() {
            return CustomerStatus;
        }

        public void setCustomerStatus(int CustomerStatus) {
            this.CustomerStatus = CustomerStatus;
        }

        public String getCustomerDep() {
            return CustomerDep;
        }

        public void setCustomerDep(String CustomerDep) {
            this.CustomerDep = CustomerDep;
        }

        public String getCustomerLevel() {
            return CustomerLevel;
        }

        public void setCustomerLevel(String CustomerLevel) {
            this.CustomerLevel = CustomerLevel;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.CustomerID);
            dest.writeInt(this.CarSourceID);
            dest.writeString(this.CustomerName);
            dest.writeInt(this.CustomerStatus);
            dest.writeString(this.CustomerDep);
            dest.writeString(this.CustomerLevel);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.CustomerID = in.readInt();
            this.CarSourceID = in.readInt();
            this.CustomerName = in.readString();
            this.CustomerStatus = in.readInt();
            this.CustomerDep = in.readString();
            this.CustomerLevel = in.readString();
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

    public MatchCustomerData() {
    }

    protected MatchCustomerData(Parcel in) {
        super(in);
        this.Data = new ArrayList<DataBean>();
        in.readList(this.Data, DataBean.class.getClassLoader());
    }

    public static final Creator<MatchCustomerData> CREATOR = new Creator<MatchCustomerData>() {
        @Override
        public MatchCustomerData createFromParcel(Parcel source) {
            return new MatchCustomerData(source);
        }

        @Override
        public MatchCustomerData[] newArray(int size) {
            return new MatchCustomerData[size];
        }
    };
}
