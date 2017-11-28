package com.jzg.erp.utils;

import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;

/**
 * 动画工具类
 * @author zealjiang
 * @time 2015年11月9日上午11:32:28
 */
public class AnimationUtil {

	//动画持续时间
	public static final long ANIMATION_TIME = 500;
	

	
	/**
	 * 
	 * @note 获取从底部退出的Animation动画对象
	 * @author zealjiang
	 * @time 2015年11月9日下午2:30:57
	 * @param time 动画持续时间
	 * @return TranslateAnimation 对象
	 */
	public TranslateAnimation getTransOutAnim(long time) {
		//view 布局位置android:layout_alignParentBottom="true"
		TranslateAnimation ta = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT,
				0, Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_SELF,
				1);
		if(time<0){
			ta.setDuration(ANIMATION_TIME);
		}else{
			ta.setDuration(time);
		}
		ta.setInterpolator(new DecelerateInterpolator());
		ta.setFillAfter(true);
		return ta;
	}


	/**
	 * 
	 * @note 获取从底部进入的Animation动画对象
	 * @author zealjiang
	 * @time 2015年11月9日下午2:30:31
	 * @param time 动画持续时间
	 * @return TranslateAnimation 对象
	 */
	public TranslateAnimation getTransInAnim(long time) {
		//view 布局位置android:layout_alignParentBottom="true"
		TranslateAnimation ta = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT,
				0, Animation.RELATIVE_TO_PARENT, 1,
				Animation.RELATIVE_TO_SELF, 0);

		if(time<0){
			ta.setDuration(ANIMATION_TIME);
		}else{
			ta.setDuration(time);
		}
		ta.setInterpolator(new DecelerateInterpolator());
		ta.setFillAfter(true);

		return ta;
	}
	
	/**
	 * 
	 * @note 获取从透明到不透明的AlphaAnimation动画对象
	 * @author zealjiang
	 * @time 2015年11月13日下午4:33:09
	 * @param time
	 * @return AlphaAnimation
	 */
	public AlphaAnimation getAlphaInAnim(long time){
		AlphaAnimation animation = new AlphaAnimation(0, 1); 
		if(time<0){
			animation.setDuration(ANIMATION_TIME);
		}else{
			animation.setDuration(time);
		}
		animation.setInterpolator(new DecelerateInterpolator());
		animation.setFillAfter(true);
		
		return animation;
	}
	
	
	/**
	 * 
	 * @note 获取从不透明到透明的AlphaAnimation动画对象
	 * @author zealjiang
	 * @time 2015年11月13日下午4:33:09
	 * @param time
	 * @return AlphaAnimation
	 */
	public AlphaAnimation getAlphaOutAnim(long time){
		AlphaAnimation animation = new AlphaAnimation(1, 0); 
		if(time<0){
			animation.setDuration(ANIMATION_TIME);
		}else{
			animation.setDuration(time);
		}
		animation.setInterpolator(new DecelerateInterpolator());
		animation.setFillAfter(true);
		
		return animation;
	}
}
