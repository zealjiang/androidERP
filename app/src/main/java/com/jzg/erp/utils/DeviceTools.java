package com.jzg.erp.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 设备工具类
 * 
 * @author forcetech
 * 
 */
public class DeviceTools {
	/**
	 * 获得设备系统
	 * 
	 * @return
	 */
	public static String getDeviceOs() {
		return "android/" + android.os.Build.VERSION.RELEASE;
	}

	/**
	 * 获得设备型号
	 * 
	 * @return
	 */
	public static String getDeviceModel() {
		return android.os.Build.MANUFACTURER + "/" + android.os.Build.MODEL;
	}

	/**
	 * 获得设备系统语言
	 * 
	 * @param context
	 * @return
	 */
	public static String getDeviceLanguage(Context context) {
		return context.getResources().getConfiguration().locale
				.getDisplayName()
				+ "/"
				+ context.getResources().getConfiguration().locale
						.getLanguage();
	}


	/**
	 * 获得屏幕设备信息
	 * 
	 * @param context
	 * @return
	 */
	public static DisplayMetrics getDisplayMetrics(Context context) {
		WindowManager manager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		manager.getDefaultDisplay().getMetrics(outMetrics);
		return outMetrics;
	}

	public static String getCurrentTime(String format) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
		String currentTime = sdf.format(date);
		return currentTime;
	}

	/**
	 * 获取当前时间
	 * 
	 * @return
	 */
	public static String getCurrentTime() {
		return getCurrentTime("yyyy-MM-dd  HH:mm:ss");
	}

	/**
	 * 获取当前时间的时间戳
	 */
	public static long getStringDayDatess() {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
		Date date = new Date();
		try {
			date = sf.parse(getCurrentTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date.getTime();
	}

	/**
	 * 获取当前时间的时间戳
	 */
	public static String getCalendar(Date date) {
		SimpleDateFormat sf = new SimpleDateFormat("MM月dd日");
		String sDate = sf.format(date);

		return sDate;
	}

	/**
	 * 获取当前时间的时间戳
	 */
	public static String getYearMonth(Date date) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy年MM月");
		String sDate = sf.format(date);

		return sDate;
	}

	/**
	 * 获取当前时间 2015-06-16
	 */
	public static String getYearMonthDay() {
		String date = getCurrentTime("yyyy-MM-dd");
		return date;
	}

	/**
	 * 获取以2015-06-16为格式的时间戳
	 */
	public static long getYearMonthDayNum(String time) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		try {
			date = sf.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date.getTime();
	}

	/**
	 * 获取当前日期时间戳
	 */
	public static long getYearMothDayNum() {
		return getYearMonthDayNum(getYearMonthDay());
	}

	/**
	 * 
	 * @note 判断是否全为数字
	 * @author zealjiang
	 * @time 2015年11月7日下午3:27:00
	 * @param str
	 * @return 全为数字返回true
	 */
	public static boolean isNumeric(String str) {

		Pattern pattern = Pattern.compile("[0-9]*");

		Matcher isNum = pattern.matcher(str);

		if (!isNum.matches()) {

			return false;

		}

		return true;

	}


	/**
	 * 判断是否有表情符号
	 */
	public static boolean ifHaveBq(String mobiles) {
		if (TextUtils.isEmpty(mobiles)) {
			return false;
		}
		Pattern p = Pattern.compile("^[A-Za-z0-9\u4e00-\u9fa5]+$");
		Matcher m = p.matcher(mobiles);
		if (!m.matches()) {
			return true;
		}
		return false;
	}



	/**
	 * 控制软件盘弹出
	 * 
	 * @param editText
	 */
	public static void openInputMethod(final EditText editText) {

		Timer timer = new Timer();

		timer.schedule(new TimerTask() {

			public void run() {

				InputMethodManager inputManager = (InputMethodManager) editText

						.getContext().getSystemService(

								Context.INPUT_METHOD_SERVICE);

				inputManager.showSoftInput(editText, 0);

			}

		}, 200);

	}

	/**
	 * 时间转换
	 * 
	 * @param format
	 * @param time
	 * @return
	 */
	public static String formatTimeToFormat(String format, long time) {
		if (TextUtils.isEmpty(format))
			return String.valueOf(time);
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		if (String.valueOf(time).length() < 13) {
			return sdf.format(time * 1000l);
		} else {
			return sdf.format(time);
		}
	}

	/**
	 * 释放图片
	 */
	public static void releaseBitmap(Bitmap bitmap) {
		if (bitmap != null) {
			try {
				if (!bitmap.isRecycled()) {
					bitmap.recycle();
				}
			} catch (Exception e) {
			}

			bitmap = null;
		}
	}

	// 合适的压缩图片，可有效防止OOM
	public static Bitmap getCompressBitmapFromFile(String fullFilename) {
		try {
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(new File(fullFilename)));
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(in, null, options);
			in.close();
			int i = 0;
			Bitmap bitmap = null;
			while (true) {
				if ((options.outWidth >> i <= 1000) && (options.outHeight >> i <= 1000)) {
					in = new BufferedInputStream(new FileInputStream(new File(fullFilename)));
					options.inSampleSize = (int) Math.pow(2.0D, i);
					options.inJustDecodeBounds = false;
					bitmap = BitmapFactory.decodeStream(in, null, options);
					break;
				}
				i += 1;
			}

			// 获取图片的旋转角度，有些系统把拍照的图片旋转了，有的没有旋转
			int degree = AppUtils.readPictureDegree(fullFilename);
			if (degree > 0) {
				bitmap = AppUtils.rotateBitmapByDegree(bitmap, degree);
			}

			return bitmap;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

}
