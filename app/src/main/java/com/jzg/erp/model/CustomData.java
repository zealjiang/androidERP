package com.jzg.erp.model;

/**
 * This is just a simple class for holding data that is used to render our
 * custom view
 */
public class CustomData
{
	private int mBackgroundColor;
	private String mText;
	private String mProvinceId;
	private String mCityId;

	public CustomData(int backgroundColor, String text, String provinceId,
					  String cityId)
	{
		mBackgroundColor = backgroundColor;
		mText = text;
		mProvinceId = provinceId;
		mCityId = cityId;
	}

	public CustomData(int backgroundColor, String text)
	{
		mBackgroundColor = backgroundColor;
		mText = text;
	}

	/**
	 * @return the backgroundColor
	 */
	public int getBackgroundColor()
	{
		return mBackgroundColor;
	}

	/**
	 * @return the text
	 */
	public String getText()
	{
		return mText;
	}

	public String getmProvinceId()
	{
		return mProvinceId;
	}

	public void setmProvinceId(String mProvinceId)
	{
		this.mProvinceId = mProvinceId;
	}

	public String getmCityId()
	{
		return mCityId;
	}

	public void setmCityId(String mCityId)
	{
		this.mCityId = mCityId;
	}
}
