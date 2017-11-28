/**
 * Project Name:JingZhenGu
 * File Name:SimpleList.java
 * Package Name:com.gc.jingzhengu.vo
 * Date:2014-6-17下午5:30:38
 * Copyright (c) 2014, wangyd523@gmail.com All Rights Reserved.
 *
 */

package com.jzg.erp.model;

import java.io.Serializable;

/**
 * ClassName:SimpleList <br/>
 * Function: TODO Simplist适配器填充实体. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2014-6-17 下午5:30:38 <br/>
 * 
 * @author 汪渝栋
 * @version
 * @since JDK 1.6
 * @see
 */
public class SimpleList implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 当前位置
	private int position;

	// 字体颜色值
	private int color;

	// 列表item内容
	private String name;

	public SimpleList(int position, int color, String name)
	{
		super();
		this.position = position;
		this.color = color;
		this.name = name;
	}

	public int getPosition()
	{
		return position;
	}

	public void setPosition(int position)
	{
		this.position = position;
	}

	public int getColor()
	{
		return color;
	}

	public void setColor(int color)
	{
		this.color = color;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

}
