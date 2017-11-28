package com.jzg.erp.receiver;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.jzg.erp.SplashActivity;
import com.jzg.erp.app.JzgApp;
import com.jzg.erp.ui.activity.HomeActivity;

import cn.jpush.android.api.JPushInterface;

/**
 * 自定义接收器
 * 
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class MyReceiver extends BroadcastReceiver {
	private static final String TAG = "JPush";

	@Override
	public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
		
        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {

                        
        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
        	//Log.d(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
        
        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
        	
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            //Log.d(TAG, "[MyReceiver] 用户点击打开了通知");
			Activity activity = JzgApp.getAppContext().getCurActivity();
			if(activity!=null){
				//打开自定义的Activity
				Intent i = new Intent(context, HomeActivity.class);
				i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				i.putExtra("from","daiban");
				context.startActivity(i);
			}else{
				//打开自定义的Activity
				Intent i = new Intent(context, SplashActivity.class);
				i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(i);
			}
			int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
			//清楚通知
			JPushInterface.clearNotificationById(JzgApp.getAppContext(),notifactionId);



		} else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {

        	
        } else if(JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {

        } else {

        }
	}
}
