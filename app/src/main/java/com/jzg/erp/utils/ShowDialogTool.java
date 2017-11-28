package com.jzg.erp.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jzg.erp.R;
import com.jzg.erp.dialog.ShowMsgDialog;

import java.text.DecimalFormat;

/**
 * 弹出框控制类
 *
 * @author jzg
 * @Date 2015-05-11
 */
public class ShowDialogTool {

    /**
     * 弹出Toast
     *
     * @param context
     * @param msg
     */
    public static void showCenterToast(Context context, String msg) {
        if (TextUtils.isEmpty(msg)) return;
        if (context == null) return;
        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    private static ShowMsgDialog mHintMsgDialog;

    /**
     * 弹出提示框
     *
     * @param context
     */
    public static void showHintDialog(Context context, String msg, final DialogCallBack callback) {
        if (TextUtils.isEmpty(msg)) return;
        if (mHintMsgDialog == null) {
            mHintMsgDialog = new ShowMsgDialog(context, R.style.dialog_hint);
            mHintMsgDialog.setContentView(R.layout.dialog_hint_msg_layout);
            mHintMsgDialog.getWindow().getAttributes().width = ViewGroup.LayoutParams.MATCH_PARENT;
            mHintMsgDialog.getWindow().getAttributes().height = ViewGroup.LayoutParams.MATCH_PARENT;
            mHintMsgDialog.setCanceledOnTouchOutside(false);
            mHintMsgDialog.getWindow().setGravity(Gravity.CENTER);
        }
        TextView phone = (TextView) mHintMsgDialog.findViewById(R.id.tv_dialog_hint_msg_phone);
        phone.setText("+86 " + msg);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_dialog_hint_ok:
                        callback.applyBack(v);
                        break;
                    case R.id.btn_dialog_hint_cancel:
                        callback.cancelBack(v);
                        break;
                }
            }
        };
        mHintMsgDialog.findViewById(R.id.btn_dialog_hint_cancel).setOnClickListener(listener);
        mHintMsgDialog.findViewById(R.id.btn_dialog_hint_ok).setOnClickListener(listener);
        if (!mHintMsgDialog.isShowing())
            mHintMsgDialog.show();
    }

    public static void dismissHintDialog() {
        if (mHintMsgDialog != null && mHintMsgDialog.isShowing()) {
            mHintMsgDialog.dismiss();
        }
        mHintMsgDialog = null;
    }

    private static ShowMsgDialog mLoadingDialog;

    public static void showLoadingDialog(Context context) {
        if (context == null) return;
        if (mLoadingDialog == null) {

            if(android.os.Build.VERSION.SDK_INT>=21){
                mLoadingDialog = new ShowMsgDialog(context, android.R.style.Theme_Material_Light_Dialog);
            }else{
                mLoadingDialog = new ShowMsgDialog(context, android.R.style.Theme_DeviceDefault_Dialog);
            }
            mLoadingDialog.setContentView(R.layout.dialog_loading_layout);
			mLoadingDialog.setCanceledOnTouchOutside(false);
            mLoadingDialog.getWindow().setGravity(Gravity.CENTER);
            mLoadingDialog.setCancelable(true);
			mLoadingDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
				@Override
				public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
					if(keyCode == KeyEvent.KEYCODE_SEARCH){
						return true;
					}
					return false;
				}
			});
        }
        if (!mLoadingDialog.isShowing())
            mLoadingDialog.show();
    }

    public static void dismissLoadingDialog() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
        mLoadingDialog = null;
    }

    private static ShowMsgDialog mCancelLoadingDialog;

    public static void showCancelLoadingDialog(Context context) {
        if (mCancelLoadingDialog == null) {
            mCancelLoadingDialog = new ShowMsgDialog(context, R.style.dialog_hint);
            mCancelLoadingDialog.setContentView(R.layout.dialog_loading_layout);
            mCancelLoadingDialog.setCanceledOnTouchOutside(false);
            mCancelLoadingDialog.getWindow().setGravity(Gravity.CENTER);
        }
        if (!mCancelLoadingDialog.isShowing())
            mCancelLoadingDialog.show();
    }

    public static void dismissCancelLoadingDialog() {
        if (mCancelLoadingDialog != null && mCancelLoadingDialog.isShowing()) {
            mCancelLoadingDialog.dismiss();
        }
        mCancelLoadingDialog = null;
    }

    public interface DialogCallBack {
        public void cancelBack(View v);

        public void applyBack(View v);
    }


    private static ShowMsgDialog mHintViewDialog;

    /**
     * 弹出提示框
     *
     * @param context
     */
    public static void showViewDialog(Context context, String title, String msg, final DialogCallBack callback, DialogInterface.OnDismissListener dismiss) {

        if (mHintViewDialog != null) {
            if (mHintViewDialog.isShowing()) {
                mHintViewDialog.dismiss();
            }
        }

        mHintViewDialog = new ShowMsgDialog(context, R.style.dialog_hint);
        mHintViewDialog.setContentView(R.layout.dialog_hint_view_layout);
        mHintViewDialog.getWindow().getAttributes().width = ViewGroup.LayoutParams.MATCH_PARENT;
        mHintViewDialog.getWindow().getAttributes().height = ViewGroup.LayoutParams.MATCH_PARENT;
        mHintViewDialog.setCanceledOnTouchOutside(false);
        mHintViewDialog.getWindow().setGravity(Gravity.CENTER);
        TextView tvTitle = (TextView) mHintViewDialog.findViewById(R.id.tv_dialog_hint_view_title);
        if (TextUtils.isEmpty(title)) {
            tvTitle.setVisibility(View.GONE);
        } else {
            if (title instanceof String) {
                if (!TextUtils.isEmpty(title)) {
                    tvTitle.setText(title);
                }
            }
        }
        TextView tvMsg = (TextView) mHintViewDialog.findViewById(R.id.tv_dialog_hint_view_msg);
        if (TextUtils.isEmpty(msg)) {
            tvMsg.setVisibility(View.GONE);
        } else {
            if (msg instanceof String) {
                if (!TextUtils.isEmpty(msg)) {
                    tvMsg.setText(msg);
                }
            }
        }
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_dialog_hint_ok:
                        callback.applyBack(v);
                        break;
                }
                dismissViewDialog();
            }
        };
        mHintViewDialog.setOnDismissListener(dismiss);
        mHintViewDialog.findViewById(R.id.btn_dialog_hint_ok).setOnClickListener(listener);
        if (!mHintViewDialog.isShowing())
            mHintViewDialog.show();
    }

    public static void dismissViewDialog() {
        if (mHintViewDialog != null && mHintViewDialog.isShowing()) {
            mHintViewDialog.dismiss();
        }
        mHintViewDialog = null;
    }

    private static ShowMsgDialog mClearMsgViewDialog;

    /**
     * 弹出提示框
     *
     * @param context
     */
    public static void showTitleAndMsgViewDialog(Context context, String title, String msg, final DialogCallBack callback) {

        if (context == null) {
            return;
        }
        if (mClearMsgViewDialog != null) {
            if (mClearMsgViewDialog.isShowing()) {
                mClearMsgViewDialog.dismiss();
            }
        }
        mClearMsgViewDialog = new ShowMsgDialog(context, R.style.dialog_hint);
        mClearMsgViewDialog.setContentView(R.layout.dialog_clear_msg_view_layout);
        mClearMsgViewDialog.getWindow().getAttributes().width = ViewGroup.LayoutParams.MATCH_PARENT;
        mClearMsgViewDialog.getWindow().getAttributes().height = ViewGroup.LayoutParams.MATCH_PARENT;
        mClearMsgViewDialog.setCanceledOnTouchOutside(false);
        mClearMsgViewDialog.getWindow().setGravity(Gravity.CENTER);

        TextView tvMsg = (TextView) mClearMsgViewDialog.findViewById(R.id.tv_dialog_hint_view_msg);
        if (TextUtils.isEmpty(msg)) {
            tvMsg.setVisibility(View.GONE);
        } else {
            if (msg instanceof String) {
                if (!TextUtils.isEmpty(msg)) {
                    tvMsg.setText(msg);
                }
            }
        }
        TextView tvTitle = (TextView) mClearMsgViewDialog.findViewById(R.id.tv_dialog_hint_view_title);
        if (TextUtils.isEmpty(title)) {
            tvTitle.setVisibility(View.GONE);
        } else {
            if (title instanceof String) {
                if (!TextUtils.isEmpty(title)) {
                    tvTitle.setText(title);
                }
            }
        }
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_dialog_hint_ok:
                        if (callback != null)
                            callback.applyBack(v);
                        break;
                    case R.id.btn_dialog_hint_cancel:
                        if (callback != null)
                            callback.cancelBack(v);
                        break;
                }
                dismissTitleAndMsgViewDialog();
            }
        };
        mClearMsgViewDialog.findViewById(R.id.btn_dialog_hint_ok).setOnClickListener(listener);
        mClearMsgViewDialog.findViewById(R.id.btn_dialog_hint_cancel).setOnClickListener(listener);
        if (!mClearMsgViewDialog.isShowing())
            mClearMsgViewDialog.show();
    }

    public static void dismissTitleAndMsgViewDialog() {
        if (mClearMsgViewDialog != null && mClearMsgViewDialog.isShowing()) {
            mClearMsgViewDialog.dismiss();
        }
        mClearMsgViewDialog = null;
    }

    private static ShowMsgDialog mSelfViewDialog;

    public static void showSelfViewDialog(Activity context, View view, boolean outside, final DialogCallBack callback) {
        if (view == null) return;
        if (mSelfViewDialog != null) {
            if (mSelfViewDialog.isShowing()) {
                mSelfViewDialog.dismiss();
            }
        }
        mSelfViewDialog = new ShowMsgDialog(context, R.style.dialog_hint);
        mSelfViewDialog.setContentView(R.layout.dialog_self_view_layout);
        mSelfViewDialog.getWindow().getAttributes().width = ViewGroup.LayoutParams.MATCH_PARENT;
        mSelfViewDialog.getWindow().getAttributes().height = ViewGroup.LayoutParams.MATCH_PARENT;
        mSelfViewDialog.getWindow().setGravity(Gravity.CENTER);
        mSelfViewDialog.setCanceledOnTouchOutside(outside);
        mSelfViewDialog.setCancelable(outside);

        final RelativeLayout rel = (RelativeLayout) mSelfViewDialog.findViewById(R.id.rel_self_view_content);
        if (rel.getChildCount() != 0) {
            rel.removeAllViews();
        }
        if (view != null)
            rel.addView(view);
        if (!mSelfViewDialog.isShowing())
            mSelfViewDialog.show();
    }

    public static void dismissSelfViewDialog() {
        if (mSelfViewDialog != null && mSelfViewDialog.isShowing()) {
            mSelfViewDialog.dismiss();
        }
        mSelfViewDialog = null;
    }

    public static String getNumberFromDouble(String value) {
        if (TextUtils.isEmpty(value)) return "--";
        Double d = Double.valueOf(value);
        if (d == 0d) {
            return "--";
        }
        DecimalFormat df = new DecimalFormat("#,##0.00");
        return df.format(d);
    }

    public static String getValueFromDouble(double d) {
        if (d == 0d) {
            return "0.0";
        }
        DecimalFormat df = new DecimalFormat("#,##0.0");
        return df.format(d);
    }
}
