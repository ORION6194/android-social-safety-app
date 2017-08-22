package com.sadtech.socialsafety;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class SharedPreference {

    private static String sharedPrefs = "sharedPrefs";
    
    public static void putObject(Context ct,String key,String json){
        SharedPreferences myPrefs = ct.getSharedPreferences(sharedPrefs,Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = myPrefs.edit();
        prefsEditor.putString(key,json);
        prefsEditor.apply();
    }
    
    public static String getObject(Context ct,String key){
        SharedPreferences myPrefs = ct.getSharedPreferences(sharedPrefs, Context.MODE_PRIVATE);
        String prefName = myPrefs.getString(key, "");
        return prefName;
    }
    
	public static void putBoolean(Context ct , String key , boolean value)
	{
		SharedPreferences myPrefs = ct.getSharedPreferences(sharedPrefs, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = myPrefs.edit();
        prefsEditor.putBoolean(key, value);
        prefsEditor.apply();
	}
	public static Boolean getBoolean(Context ct , String key)
	{
		SharedPreferences myPrefs = ct.getSharedPreferences(sharedPrefs, Context.MODE_PRIVATE);
        boolean prefName = myPrefs.getBoolean(key, true);
        return prefName;                          
	}
	public static void putInt(Context ct , String key , int value)
	{
		SharedPreferences myPrefs = ct.getSharedPreferences(sharedPrefs, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = myPrefs.edit();
        prefsEditor.putInt(key, value);
        prefsEditor.apply();
	}
	public static int getInt(Context ct , String key)
	{
		SharedPreferences myPrefs = ct.getSharedPreferences(sharedPrefs, Context.MODE_PRIVATE);
        int prefName = myPrefs.getInt(key, 0);
        return prefName;                          
	}
	public static void putLong(Context ct , String key , long value)
	{
		SharedPreferences myPrefs = ct.getSharedPreferences(sharedPrefs, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = myPrefs.edit();
        prefsEditor.putLong(key, value);
        prefsEditor.apply();
	}
	
	public static long getLong(Context ct , String key)
	{
		SharedPreferences myPrefs = ct.getSharedPreferences(sharedPrefs, Context.MODE_PRIVATE);
        long prefName = myPrefs.getLong(key, 0);
        return prefName;                          
	}
	public static void clear(Context ct) {

		SharedPreferences myPrefs = ct.getSharedPreferences(sharedPrefs,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = myPrefs.edit();
		editor.clear();
		editor.commit();

	}

    public static void setStringArrayPref(Context context, String key, ArrayList<String> values) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        JSONArray a = new JSONArray();
        for (int i = 0; i < values.size(); i++) {
            a.put(values.get(i));
        }
        if (!values.isEmpty()) {
            editor.putString(key, a.toString());
        } else {
            editor.putString(key, null);
        }
        editor.commit();
    }

    public static ArrayList<String> getStringArrayPref(Context context, String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String json = prefs.getString(key, null);
        ArrayList<String> urls = new ArrayList<String>();
        if (json != null) {
            try {
                JSONArray a = new JSONArray(json);
                for (int i = 0; i < a.length(); i++) {
                    String url = a.optString(i);
                    urls.add(url);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return urls;
    }

}
