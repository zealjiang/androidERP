package com.jzg.erp.model;

import java.util.List;

/**
 * author: guochen
 * date: 2016/6/24 14:29
 * email:
 * 车源列表，关注车列表实体对象
 */
public class CarSourceData extends BaseObject {
    /**
     * ID : 1106
     * CarSourceID : 1295
     * MakeID : 53
     * ModelID : 1886
     * StyleID : 107638
     * MakeName : 一汽
     * ModelName : 天津一汽夏利A+
     * StyleName : 1.0L 手动 三厢A+
     * FullName : 天津一汽夏利A+ 1.0L 手动 三厢A+
     * Regdate : 2007-08-06T00:00:00
     * Mileage : 71000
     * BuyPrice : 0
     * CarStatus2 : 1
     * IsPreparation : 2
     * IsUpshelf : 2
     * CarStatus2Name : 在售
     * IsPreparationName : 未整备
     * IsUpshelfName : 未上架
     */

    private java.util.List<DataBean> Data;

    public List<DataBean> getData() {
        return Data;
    }

    public void setData(List<DataBean> data) {
        Data = data;
    }

    public static class DataBean {
        private int ID;
        private int CarSourceID;
        private int MakeID;
        private int ModelID;
        private int StyleID;
        private String MakeName;
        private String ModelName;
        private String StyleName;
        private String FullName;
        private String Regdate;
        private String Mileage;
        //        private int BuyPrice;
        private String BuyPrice;
        private int CarStatus2;//1.在售;2.已预订;3.已销售;4转批售
        private int IsPreparation;//1 已整备  2未整备
        private int IsUpshelf;//1.已上架;2未上架 3已下架
        private String CarStatus2Name;
        private String IsPreparationName;
        private String IsUpshelfName;
        private String MileageMKM;//万公里
        private String BuyPriceFormat;//万元
        private String PicPath;
        private boolean ShowState; //true 显示子状态
        private String CreateTimeStr;
        private String CreateTime;
        private String RegdateStr;//车辆上牌时间
        private String IsPreparationColor;
        private String CarDetailPath;
        private String IsUpshelfColor;
        private String CarStatus2Color;
        private String RowNumber;

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

        public String getMileageMKM() {
            return MileageMKM;
        }

        public void setMileageMKM(String mileageMKM) {
            MileageMKM = mileageMKM;
        }

        public String getBuyPriceFormat() {
            return BuyPriceFormat;
        }

        public void setBuyPriceFormat(String buyPriceFormat) {
            BuyPriceFormat = buyPriceFormat;
        }

        public boolean isShowState() {
            return ShowState;
        }

        public void setShowState(boolean showState) {
            ShowState = showState;
        }

        public String getPicPath() {
            return PicPath;
        }

        public void setPicPath(String picPath) {
            PicPath = picPath;
        }

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public int getCarSourceID() {
            return CarSourceID;
        }

        public void setCarSourceID(int CarSourceID) {
            this.CarSourceID = CarSourceID;
        }

        public int getMakeID() {
            return MakeID;
        }

        public void setMakeID(int MakeID) {
            this.MakeID = MakeID;
        }

        public int getModelID() {
            return ModelID;
        }

        public void setModelID(int ModelID) {
            this.ModelID = ModelID;
        }

        public int getStyleID() {
            return StyleID;
        }

        public void setStyleID(int StyleID) {
            this.StyleID = StyleID;
        }

        public String getMakeName() {
            return MakeName;
        }

        public void setMakeName(String MakeName) {
            this.MakeName = MakeName;
        }

        public String getModelName() {
            return ModelName;
        }

        public void setModelName(String ModelName) {
            this.ModelName = ModelName;
        }

        public String getStyleName() {
            return StyleName;
        }

        public void setStyleName(String StyleName) {
            this.StyleName = StyleName;
        }

        public String getFullName() {
            return FullName;
        }

        public void setFullName(String FullName) {
            this.FullName = FullName;
        }

        public String getRegdate() {
            return Regdate;
        }

        public void setRegdate(String Regdate) {
            this.Regdate = Regdate;
        }

        public String getMileage() {
            return Mileage;
        }

        public void setMileage(String Mileage) {
            this.Mileage = Mileage;
        }

        //        public int getBuyPrice() {
//            return BuyPrice;
//        }
//
//        public void setBuyPrice(int BuyPrice) {
//            this.BuyPrice = BuyPrice;
//        }
        public String getBuyPrice() {
            return BuyPrice;
        }

        public void setBuyPrice(String BuyPrice) {
            this.BuyPrice = BuyPrice;
        }

        public int getCarStatus2() {
            return CarStatus2;
        }

        public void setCarStatus2(int CarStatus2) {
            this.CarStatus2 = CarStatus2;
        }

        public int getIsPreparation() {
            return IsPreparation;
        }

        public void setIsPreparation(int IsPreparation) {
            this.IsPreparation = IsPreparation;
        }

        public int getIsUpshelf() {
            return IsUpshelf;
        }

        public void setIsUpshelf(int IsUpshelf) {
            this.IsUpshelf = IsUpshelf;
        }

        public String getCarStatus2Name() {
            return CarStatus2Name;
        }

        public void setCarStatus2Name(String CarStatus2Name) {
            this.CarStatus2Name = CarStatus2Name;
        }

        public String getIsPreparationName() {
            return IsPreparationName;
        }

        public void setIsPreparationName(String IsPreparationName) {
            this.IsPreparationName = IsPreparationName;
        }

        public String getIsUpshelfName() {
            return IsUpshelfName;
        }

        public void setIsUpshelfName(String IsUpshelfName) {
            this.IsUpshelfName = IsUpshelfName;
        }

        public String getRegdateStr() {
            return RegdateStr;
        }

        public void setRegdateStr(String regdateStr) {
            RegdateStr = regdateStr;
        }

        public String getIsUpshelfColor() {
            return IsUpshelfColor;
        }

        public void setIsUpshelfColor(String isUpshelfColor) {
            IsUpshelfColor = isUpshelfColor;
        }

        public String getCarStatus2Color() {
            return CarStatus2Color;
        }

        public void setCarStatus2Color(String carStatus2Color) {
            CarStatus2Color = carStatus2Color;
        }

        public String getRowNumber() {
            return RowNumber;
        }

        public void setRowNumber(String rowNumber) {
            RowNumber = rowNumber;
        }
    }

    @Override
    public String toString() {
        return "CarSourceData{" +
                "Data=" + Data +
                '}';
    }
}
