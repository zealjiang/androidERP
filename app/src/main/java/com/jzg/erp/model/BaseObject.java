/**
 * Project Name:JingZhenGu
 * File Name:BaseObject.java
 * Package Name:com.gc.jingzhengu.vo
 * Date:2014-6-12下午12:22:05
 * Copyright (c) 2014, wangyd523@gmail.com All Rights Reserved.
 *
 */

package com.jzg.erp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * ClassName:BaseObject <br/>
 * Function: 服务器返回对象基类. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2014-6-12 下午12:22:05 <br/>
 * 
 * @author 汪渝栋
 * @version
 * @since JDK 1.6
 * @see
 */
public class BaseObject implements Serializable, Parcelable {
	private int Status;

	private String Message;
	
	private int flag;
	
	private int carsourceid;

	public String getMessage() {
		return Message;
	}

	public void setMessage(String message) {
		Message = message;
	}

	public int getStatus() {
		return Status;
	}

	public void setStatus(int status) {
		Status = status;
	}

	public int getFlag()
	{
		return flag;
	}

	public void setFlag(int flag)
	{
		this.flag = flag;
	}

	public int getCarsourceid()
	{
		return carsourceid;
	}

	public void setCarsourceid(int carsourceid)
	{
		this.carsourceid = carsourceid;
	}

	@Override
	public String toString() {
		return "BaseObject{" +
				"carsourceid=" + carsourceid +
				", Status=" + Status +
				", Message='" + Message + '\'' +
				", flag=" + flag +
				'}';
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(this.Status);
		dest.writeString(this.Message);
		dest.writeInt(this.flag);
		dest.writeInt(this.carsourceid);
	}

	public BaseObject() {
	}

	protected BaseObject(Parcel in) {
		this.Status = in.readInt();
		this.Message = in.readString();
		this.flag = in.readInt();
		this.carsourceid = in.readInt();
	}

}
