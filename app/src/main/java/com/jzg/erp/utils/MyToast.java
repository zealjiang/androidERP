package com.jzg.erp.utils;

import android.widget.Toast;
/**
 * @author: voiceofnet
 * email: pengkun@jingzhengu.com
 * phone:18101032717
 * @time: 2016/5/23 15:41
 * @desc:
 */
public class MyToast {
	public static void showLong(String content){
		Toast.makeText(UIUtils.getContext(), content, Toast.LENGTH_LONG).show();
	}
	public static void showShort(int resId){
		Toast.makeText(UIUtils.getContext(), resId, Toast.LENGTH_SHORT).show();
	}

	public static void showShort(String content){
		Toast.makeText(UIUtils.getContext(), content, Toast.LENGTH_SHORT).show();
	}
	public static void showLong(int resId){
		Toast.makeText(UIUtils.getContext(), resId, Toast.LENGTH_LONG).show();
	}
}
