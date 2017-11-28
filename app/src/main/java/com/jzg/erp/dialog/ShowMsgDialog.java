package com.jzg.erp.dialog;

import android.app.Dialog;
import android.content.Context;

/**
 * 自定义弹出框显示
 * @author jzg
 * @Date 2015-05-12
 */
public class ShowMsgDialog extends Dialog {

	private ShowMsgDialog(Context context, boolean cancelable,OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
	}
	/**
	 * 定义的Dialog.   theme 调用R.style.dialog
	 * @param context
	 * @param theme
	 */
	public ShowMsgDialog(Context context, int theme) {
		super(context, theme);
	}

	private ShowMsgDialog(Context context) {
		super(context);
	}


	
}
