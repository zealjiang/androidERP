package com.jzg.erp.model;

import android.os.Parcel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JZG on 2016/7/4.
 */
public class CarSourceTagData extends BaseObject{



    /**
     * Key : 2016年07月13日
     * DataList : [{"CreateTime":"2016-07-13 22:22:12","CreateTimeStr":"2016年07月13日","RowNumber":1,"ID":0,"CarSourceID":0,"MakeID":0,"ModelID":0,"StyleID":0,"MakeName":null,"ModelName":null,"StyleName":null,"FullName":null,"Path":null,"PicPath":"","Regdate":"0001-01-01 00:00:00","RegdateStr":"0001年01月","Mileage":0,"MileageMKM":"0","BuyPrice":0,"BuyPriceFormat":0,"CarStatus2":0,"IsPreparation":0,"IsUpshelf":0,"CarStatus2Name":"未知","IsPreparationName":"未知","IsUpshelfName":"已下架","CarStatus2Color":"","ShowState":false,"CarDetailPath":"http://jtd.erpweb.guchewang.com/CarDetail/M/0","IsPreparationColor":"","IsUpshelfColor":""}]
     */

    private List<DataBean> Data;

    public List<DataBean> getData() {
        return Data;
    }

    public void setData(List<DataBean> Data) {
        this.Data = Data;
    }

    public static class DataBean {
        private String Key;
        /**
         * CreateTime : 2016-07-13 22:22:12
         * CreateTimeStr : 2016年07月13日
         * RowNumber : 1
         * ID : 0
         * CarSourceID : 0
         * MakeID : 0
         * ModelID : 0
         * StyleID : 0
         * MakeName : null
         * ModelName : null
         * StyleName : null
         * FullName : null
         * Path : null
         * PicPath :
         * Regdate : 0001-01-01 00:00:00
         * RegdateStr : 0001年01月
         * Mileage : 0
         * MileageMKM : 0
         * BuyPrice : 0
         * BuyPriceFormat : 0
         * CarStatus2 : 0
         * IsPreparation : 0
         * IsUpshelf : 0
         * CarStatus2Name : 未知
         * IsPreparationName : 未知
         * IsUpshelfName : 已下架
         * CarStatus2Color :
         * ShowState : false
         * CarDetailPath : http://jtd.erpweb.guchewang.com/CarDetail/M/0
         * IsPreparationColor :
         * IsUpshelfColor :
         */

        private List<DataListBean> DataList;

        public String getKey() {
            return Key;
        }

        public void setKey(String Key) {
            this.Key = Key;
        }

        public List<DataListBean> getDataList() {
            return DataList;
        }

        public void setDataList(List<DataListBean> DataList) {
            this.DataList = DataList;
        }

        public static class DataListBean {
            private String Tag;
            private String CreateTime;
            private String CreateTimeStr;
            private String RowNumber;
            private String ID;
            private String CarSourceID;
            private String MakeID;
            private String ModelID;
            private String StyleID;
            private String MakeName;
            private String ModelName;
            private String StyleName;
            private String FullName;
            private String Path;
            private String PicPath;
            private String Regdate;
            private String RegdateStr;
            private String Mileage;
            private String MileageMKM;
            private String BuyPrice;
            private String BuyPriceFormat;
            /**
             *  二期车辆状态 1.在售;2.已预订;3.已销售;4转批售;
             */
            private int CarStatus2;
            /**
             *  是否整备  1.已整备;2未整备
             */
            private int IsPreparation;
            /**
             *  是否上架:1.已经上架;2未上架;3.已下架
             */
            private int IsUpshelf;
            private String CarStatus2Name;
            private String IsPreparationName;
            private String IsUpshelfName;
            private String CarStatus2Color;
            private boolean ShowState;
            private String CarDetailPath;
            private String IsPreparationColor;
            private String IsUpshelfColor;

            public String getTag() {
                return Tag;
            }

            public void setTag(String tag) {
                Tag = tag;
            }

            public String getCreateTime() {
                return CreateTime;
            }

            public void setCreateTime(String createTime) {
                CreateTime = createTime;
            }

            public String getCreateTimeStr() {
                return CreateTimeStr;
            }

            public void setCreateTimeStr(String createTimeStr) {
                CreateTimeStr = createTimeStr;
            }

            public String getRowNumber() {
                return RowNumber;
            }

            public void setRowNumber(String rowNumber) {
                RowNumber = rowNumber;
            }

            public String getID() {
                return ID;
            }

            public void setID(String ID) {
                this.ID = ID;
            }

            public String getCarSourceID() {
                return CarSourceID;
            }

            public void setCarSourceID(String carSourceID) {
                CarSourceID = carSourceID;
            }

            public String getMakeID() {
                return MakeID;
            }

            public void setMakeID(String makeID) {
                MakeID = makeID;
            }

            public String getModelID() {
                return ModelID;
            }

            public void setModelID(String modelID) {
                ModelID = modelID;
            }

            public String getStyleID() {
                return StyleID;
            }

            public void setStyleID(String styleID) {
                StyleID = styleID;
            }

            public String getMakeName() {
                return MakeName;
            }

            public void setMakeName(String makeName) {
                MakeName = makeName;
            }

            public String getModelName() {
                return ModelName;
            }

            public void setModelName(String modelName) {
                ModelName = modelName;
            }

            public String getStyleName() {
                return StyleName;
            }

            public void setStyleName(String styleName) {
                StyleName = styleName;
            }

            public String getFullName() {
                return FullName;
            }

            public void setFullName(String fullName) {
                FullName = fullName;
            }

            public String getPath() {
                return Path;
            }

            public void setPath(String path) {
                Path = path;
            }

            public String getPicPath() {
                return PicPath;
            }

            public void setPicPath(String picPath) {
                PicPath = picPath;
            }

            public String getRegdate() {
                return Regdate;
            }

            public void setRegdate(String regdate) {
                Regdate = regdate;
            }

            public String getRegdateStr() {
                return RegdateStr;
            }

            public void setRegdateStr(String regdateStr) {
                RegdateStr = regdateStr;
            }

            public String getMileage() {
                return Mileage;
            }

            public void setMileage(String mileage) {
                Mileage = mileage;
            }

            public String getMileageMKM() {
                return MileageMKM;
            }

            public void setMileageMKM(String mileageMKM) {
                MileageMKM = mileageMKM;
            }

            public String getBuyPrice() {
                return BuyPrice;
            }

            public void setBuyPrice(String buyPrice) {
                BuyPrice = buyPrice;
            }

            public String getBuyPriceFormat() {
                return BuyPriceFormat;
            }

            public void setBuyPriceFormat(String buyPriceFormat) {
                BuyPriceFormat = buyPriceFormat;
            }

            public int getCarStatus2() {
                return CarStatus2;
            }

            public void setCarStatus2(int carStatus2) {
                CarStatus2 = carStatus2;
            }

            public int getIsPreparation() {
                return IsPreparation;
            }

            public void setIsPreparation(int isPreparation) {
                IsPreparation = isPreparation;
            }

            public int getIsUpshelf() {
                return IsUpshelf;
            }

            public void setIsUpshelf(int isUpshelf) {
                IsUpshelf = isUpshelf;
            }

            public String getCarStatus2Name() {
                return CarStatus2Name;
            }

            public void setCarStatus2Name(String carStatus2Name) {
                CarStatus2Name = carStatus2Name;
            }

            public String getIsPreparationName() {
                return IsPreparationName;
            }

            public void setIsPreparationName(String isPreparationName) {
                IsPreparationName = isPreparationName;
            }

            public String getIsUpshelfName() {
                return IsUpshelfName;
            }

            public void setIsUpshelfName(String isUpshelfName) {
                IsUpshelfName = isUpshelfName;
            }

            public String getCarStatus2Color() {
                return CarStatus2Color;
            }

            public void setCarStatus2Color(String carStatus2Color) {
                CarStatus2Color = carStatus2Color;
            }

            public boolean isShowState() {
                return ShowState;
            }

            public void setShowState(boolean showState) {
                ShowState = showState;
            }

            public String getCarDetailPath() {
                return CarDetailPath;
            }

            public void setCarDetailPath(String carDetailPath) {
                CarDetailPath = carDetailPath;
            }

            public String getIsPreparationColor() {
                return IsPreparationColor;
            }

            public void setIsPreparationColor(String isPreparationColor) {
                IsPreparationColor = isPreparationColor;
            }

            public String getIsUpshelfColor() {
                return IsUpshelfColor;
            }

            public void setIsUpshelfColor(String isUpshelfColor) {
                IsUpshelfColor = isUpshelfColor;
            }
        }
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

    public CarSourceTagData() {
    }

    protected CarSourceTagData(Parcel in) {
        super(in);
        this.Data = new ArrayList<DataBean>();
        in.readList(this.Data, DataBean.class.getClassLoader());
    }

    public static final Creator<CarSourceTagData> CREATOR = new Creator<CarSourceTagData>() {
        @Override
        public CarSourceTagData createFromParcel(Parcel source) {
            return new CarSourceTagData(source);
        }

        @Override
        public CarSourceTagData[] newArray(int size) {
            return new CarSourceTagData[size];
        }
    };
}
