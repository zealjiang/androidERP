/**
 * Project Name:JingZhenGu
 * File Name:ModelList.java
 * Package Name:com.gc.jingzhengu.vo
 * Date:2014-6-13上午11:33:32
 * Copyright (c) 2014, wangyd523@gmail.com All Rights Reserved.
 *
 */

package com.jzg.erp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class ModelList implements Parcelable
{
	private int status;
	private String msg;
	private String success;

	private ArrayList<ModelCategory> ManufacturerList;

	public ArrayList<ModelCategory> getManufacturerList() {
		return ManufacturerList;
	}

	public void setManufacturerList(ArrayList<ModelCategory> manufacturerList) {
		ManufacturerList = manufacturerList;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}

	@Override
	public String toString() {
		return "ModelList{" +
				"ManufacturerList=" + ManufacturerList +
				'}';
	}


	public ModelList() {
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(this.status);
		dest.writeString(this.msg);
		dest.writeTypedList(this.ManufacturerList);
	}

	protected ModelList(Parcel in) {
		this.status = in.readInt();
		this.msg = in.readString();
		this.ManufacturerList = in.createTypedArrayList(ModelCategory.CREATOR);
	}

	public static final Creator<ModelList> CREATOR = new Creator<ModelList>() {
		@Override
		public ModelList createFromParcel(Parcel source) {
			return new ModelList(source);
		}

		@Override
		public ModelList[] newArray(int size) {
			return new ModelList[size];
		}
	};
}
