package com.ocrlib.zdsb.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * SharePreferences
 */
public class SharePrefUtil {
	private static String TAG = "SharePrefUtil";
	private final static String SP_NAME = "xdja_ocr_lib"; //
	private static SharedPreferences sp;

	/**
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void saveBoolean(Context context, String key, boolean value) {
		if (sp == null)
			sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
		sp.edit().putBoolean(key, value).commit();
	}

	/**
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void saveString(Context context, String key, String value) {
		if (sp == null) sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
		sp.edit().putString(key, value).commit();
	}

	public static void clear(Context context) {
		if (sp == null) sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
		sp.edit().clear().commit();
	}

	public static void removeItem(Context context, String key) {
		if (sp == null)
			sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
		sp.edit().remove(key).commit();
	}

	/**
	 *  
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void saveLong(Context context, String key, long value) {
		if (sp == null) sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
		sp.edit().putLong(key, value).commit();
	}

	/**
	 * 
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void saveInt(Context context, String key, int value) {
		if (sp == null)
			sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
		sp.edit().putInt(key, value).commit();
	}

	/**
	 * 
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void saveFloat(Context context, String key, float value) {
		if (sp == null)
			sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
		sp.edit().putFloat(key, value).commit();
	}

	/**
	 * 
	 * @param context
	 * @param key
	 * @param defValue
	 * @return
	 */
	public static String getString(Context context, String key, String defValue) {
		if (sp == null)
			sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
		return sp.getString(key, defValue);
	}

	/**
	 * 
	 * @param context
	 * @param key
	 * @param defValue
	 * @return
	 */
	public static int getInt(Context context, String key, int defValue) {
		if (sp == null)
			sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
		return sp.getInt(key, defValue);
	}

	/**
	 * 
	 * @param context
	 * @param key
	 * @param defValue
	 * @return
	 */
	public static long getLong(Context context, String key, long defValue) {
		if (sp == null)
			sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
		return sp.getLong(key, defValue);
	}

	/**
	 * 
	 * @param context
	 * @param key
	 * @param defValue
	 * @return
	 */
	public static float getFloat(Context context, String key, float defValue) {
		if (sp == null)
			sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
		return sp.getFloat(key, defValue);
	}

	/**
	 * @param context
	 * @param key
	 * @param defValue
	 * @return
	 */
	public static boolean getBoolean(Context context, String key, boolean defValue) {
		if (sp == null)
			sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
		return sp.getBoolean(key, defValue);
	}
}
