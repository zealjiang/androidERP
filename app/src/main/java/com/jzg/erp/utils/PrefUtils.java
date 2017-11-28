package com.jzg.erp.utils;


import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.jzg.erp.app.JzgApp;


/**
 * @author: voiceofnet
 * email: pengkun@jingzhengu.com
 * phone:18101032717
 * @time: 2016/5/23 15:41
 * @desc:
 */
public class PrefUtils {

    //是否完成导航页浏览
    public static final String ISCOMPLETEGUIDE = "isCompleteGuide";

    //最近登录的用户名及密码(保存规则：用户名+"_"+密码)
    public static final String LAST_ACCOUNT = "lastAccount";

    private static final String GESTURE_LOCK_PASSWORD_KEY = "gesture_password_key";
    private static final String SHOW_LOCK_PASSWORD_KEY = "show_lock_password_key";
    private static final String GESTURE_OPEN_KEY = "gesture_open_key";
    private static final String PUSH_MESSAGE_KEY = "push_message_key";
    private static final String USER_NAME_KEY = "user_name_key";
    private static String pre_key = "shibei";
    private static final String LOGIN_USER_INFO_KEY = "login_user_info_key";
    private static final String USER_SETTING_KEY = "user_setting_key";
    private static final String MAIN_DATA_CACHE_KEY = "main_data_cache_key";
    private static final String USER_ID = "user_id";
    private static final String USER_TOKEN = "token";
    private static final String USER_AVATAR = "avatar";
    private static final String USER_NICKNAME = "nickname";
    private static final String USER_SEX = "sex";

    private static final String KE_WEN_AFTER_TIME = "aftertime";
    private static final String FOLLOW_IS_LANDER = "followIslander";

    private static SharedPreferences sp;

    static {
        if (sp == null) {
            sp = PreferenceManager.getDefaultSharedPreferences(JzgApp.getAppContext());
        }
    }

    public static void remove(String key) {
        sp.edit().remove(pre_key + "_" + key).commit();
    }

    public static void putInt(String key, int value) {
        sp.edit().putInt(pre_key + "_" + key, value).commit();
    }

    public static void putLong(String key, long value) {
        sp.edit().putLong(pre_key + "_" + key, value).commit();
    }

    public static void putString(String key, String value) {
        sp.edit().putString(pre_key + "_" + key, value).commit();
    }

    public static void putBoolean(String key, boolean value) {
        sp.edit().putBoolean(pre_key + "_" + key, value).commit();
    }

    public static void putFloat(String key, float value) {
        sp.edit().putFloat(pre_key + "_" + key, value).commit();
    }

    public static int getInt(String key) {
        return sp.getInt(pre_key + "_" + key, 0);
    }

    public static long getLong(String key) {
        return sp.getLong(pre_key + "_" + key, 0);
    }

    public static String getString(String key) {
        return sp.getString(pre_key + "_" + key, "");
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        return sp.getBoolean(pre_key + "_" + key, defaultValue);
    }

    public static float getFloat(String key) {
        return sp.getFloat(pre_key + "_" + key, 0);
    }

    public static void clear() {
        sp.edit().clear();
    }


    public static void setUserId(int userId) {
        putInt(USER_ID, userId);
    }

    public static void setUserSex(String sex){
        putString(USER_SEX, sex);
    }

    public static String getUserSex(){
        return getString(pre_key + "_" + USER_SEX);
    }

    public static int getUserId() {
        return sp.getInt(pre_key + "_" + USER_ID, 0);
    }

    public static void setUserToken(String token) {
        putString(USER_TOKEN, token);
    }

    public static String getUserToken() {
        return getString(USER_TOKEN);
    }

    public static void setUserAvatarUrl(String avatar) {
        putString(USER_AVATAR, avatar);
    }

    public static String getUserAvatarUrl() {
        return getString(USER_AVATAR);
    }

    public static String getUserNickName() {
        return getString(USER_NICKNAME);
    }

    public static void setUserNickName(String nickName) {
        putString(USER_NICKNAME, nickName);
    }

    public static void clearUserLoginInfo() {
        PrefUtils.setUserAvatarUrl(null);
        PrefUtils.setUserId(0);
        PrefUtils.setUserNickName(null);
        PrefUtils.setUserToken(null);
        PrefUtils.setUserSex(null);
        PrefUtils.setFollowIsLander(0);
    }


    public static void setFollowed(int follow){
        putInt("followed", follow);
    }

    public static int getFollowed(){
        return getInt("followed");
    }

    public static void setAfterTime(long afterTime){
        putLong(KE_WEN_AFTER_TIME, afterTime);
    }

    public static long getAfterTime(){
        return getLong(KE_WEN_AFTER_TIME);
    }

    public static void setFollowIsLander(int followed){
        putInt(FOLLOW_IS_LANDER,followed);
    }

    public static int getFollowIsLander(){
        return getInt(FOLLOW_IS_LANDER);
    }
}
