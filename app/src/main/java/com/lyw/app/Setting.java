package com.lyw.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.v4.content.SharedPreferencesCompat;
import android.text.TextUtils;

import com.lyw.app.utils.VersionUtil;


/**
 * Created by lyw on 2017/7/23.
 */

public final class Setting {
    private static final String KEY_SEVER_URL = "serverUrl";
    private static final String KEY_VERSION_CODE = "versionCode";
    public static final String KEY_APP_UNIQUE_ID = "appUniqueID";
    private static final String KEY_SYSTEM_CONFIG_TIMESTAMP = "systemConfigTimeStamp";
    private static final String KEY_LOCATION_INFO = "locationInfo";
    private static final String KEY_LOCATION_PERMISSION = "locationPermission";
    private static final String KEY_LOCATION_APP_CODE = "locationAppCode";
    private static final String KEY_SOFT_KEYBOARD_HEIGHT = "softKeyboardHeight";


    public static final String KEY_TOKEN = "token";
    public static final String KEY_UID = "uid";
    public static final String KEY_PHONE_MD5 = "phone_md5";
    public static final String BACK_STATUS = "code";
    public static final boolean RESULT_STATUS_SUCCESS = true;
    public static final boolean RESULT_STATUS_FAIL = false;

    public static final String APP_ID = "包名";
    public static final String CHARSET = "utf-8";
    // Fields from default config.
    public static final String API_SERVER_URL = "";

    public static String getCachedToken(Context context){
        return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).getString(KEY_TOKEN, null);
    }

    public static void cacheToken(Context context,String token){
        Editor e = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
        e.putString(KEY_TOKEN, token);
        e.commit();
    }

    public static String getCachedUID(Context context){
        return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).getString(KEY_UID, null);
    }

    public static void cacheUID(Context context,String phoneNum){
        Editor e = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
        e.putString(KEY_UID, phoneNum);
        e.commit();
    }


    public static SharedPreferences getSettingPreferences(Context context) {
        return context.getSharedPreferences(Setting.class.getName(), Context.MODE_PRIVATE);
    }

    public static boolean checkIsNewVersion(Context context) {
        int saveVersionCode = getSaveVersionCode(context);
        int currentVersionCode = VersionUtil.getVersionCode();
        if (saveVersionCode < currentVersionCode) {
            updateSaveVersionCode(context, currentVersionCode);
            return true;
        }
        return false;
    }

    public static int getSaveVersionCode(Context context) {
        SharedPreferences sp = getSettingPreferences(context);
        return sp.getInt(KEY_VERSION_CODE, 0);
    }

    private static int updateSaveVersionCode(Context context, int version) {
        SharedPreferences sp = getSettingPreferences(context);
        SharedPreferences.Editor editor = sp.edit().putInt(KEY_VERSION_CODE, version);
        SharedPreferencesCompat.EditorCompat.getInstance().apply(editor);
        return version;
    }

    public static String getServerUrl(Context context) {
        SharedPreferences sp = getSettingPreferences(context);
        String url = sp.getString(KEY_SEVER_URL, null);
        if (TextUtils.isEmpty(url)) {
            String[] urls = API_SERVER_URL.split(";");
            if (urls.length > 0) {
                url = urls[0];
            } else {
                url = "https://www.oschina.net/";
            }
            updateServerUrl(context, url);
        }
        return url;
    }

    public static void updateServerUrl(Context context, String url) {
        SharedPreferences sp = getSettingPreferences(context);
        SharedPreferences.Editor editor = sp.edit().putString(KEY_SEVER_URL, url);
        SharedPreferencesCompat.EditorCompat.getInstance().apply(editor);
    }

    public static void updateSystemConfigTimeStamp(Context context) {
        SharedPreferences sp = getSettingPreferences(context);
        SharedPreferences.Editor editor = sp.edit().putLong(KEY_SYSTEM_CONFIG_TIMESTAMP,
                System.currentTimeMillis());
        SharedPreferencesCompat.EditorCompat.getInstance().apply(editor);
    }

    public static long getSystemConfigTimeStamp(Context context) {
        SharedPreferences sp = getSettingPreferences(context);
        return sp.getLong(KEY_SYSTEM_CONFIG_TIMESTAMP, 0);
    }

    public static void updateLocationInfo(Context context, boolean hasLocation) {
        SharedPreferences sp = getSettingPreferences(context);
        SharedPreferences.Editor editor = sp.edit().putBoolean(KEY_LOCATION_INFO, hasLocation);
        SharedPreferencesCompat.EditorCompat.getInstance().apply(editor);
    }

    public static boolean hasLocation(Context context) {
        SharedPreferences sp = getSettingPreferences(context);
        return sp.getBoolean(KEY_LOCATION_INFO, false);
    }

    public static void updateLocationPermission(Context context, boolean hasPermission) {
        SharedPreferences sp = getSettingPreferences(context);
        SharedPreferences.Editor editor = sp.edit().putBoolean(KEY_LOCATION_PERMISSION, hasPermission);
        SharedPreferencesCompat.EditorCompat.getInstance().apply(editor);
    }

    public static boolean hasLocationPermission(Context context) {
        SharedPreferences sp = getSettingPreferences(context);
        return sp.getBoolean(KEY_LOCATION_PERMISSION, false);
    }

    public static void updateLocationAppCode(Context context, int appCode) {
        SharedPreferences sp = getSettingPreferences(context);
        SharedPreferences.Editor editor = sp.edit().putInt(KEY_LOCATION_APP_CODE, appCode);
        SharedPreferencesCompat.EditorCompat.getInstance().apply(editor);
    }

    public static int hasLocationAppCode(Context context) {
        SharedPreferences sp = getSettingPreferences(context);
        return sp.getInt(KEY_LOCATION_APP_CODE, 0);
    }

    public static int getSoftKeyboardHeight(Context context) {
        SharedPreferences sp = getSettingPreferences(context);
        return sp.getInt(KEY_SOFT_KEYBOARD_HEIGHT, 0);
    }

    public static void updateSoftKeyboardHeight(Context context, int height) {
        SharedPreferences sp = getSettingPreferences(context);
        SharedPreferences.Editor editor = sp.edit().putInt(KEY_SOFT_KEYBOARD_HEIGHT, height);
        SharedPreferencesCompat.EditorCompat.getInstance().apply(editor);
    }
}

