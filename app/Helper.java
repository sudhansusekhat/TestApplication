package com.dbs.easyhomeloan;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.io.UnsupportedEncodingException;

public class Helper {

    private static final String SHARED_PREF_NAME = "EASY";
    private static final String IS_LOGGED_IN = "isLoggedIn";
    private static final String IS_ELIGIBLE = "IS_ELIGIBLE";
    public static final String MyPREFERENCES = "MyPrefs", Security_PREFERENCES = "SecurityPrefs";
    public static SharedPreferences sharedpreferences, security_sharedpreferences;


    public static boolean isStringNotEmpty(String str) {
        return str != null && !str.isEmpty();
    }

    public static void saveBool(Context context, boolean value) {
        SharedPreferences sharedPref = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(IS_LOGGED_IN, value);
        editor.apply();
    }

    public static boolean getBool(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPref.getBoolean(IS_LOGGED_IN,false);
    }
    public static void saveEligible(Context context, boolean value) {
        SharedPreferences sharedPref = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(IS_ELIGIBLE, value);
        editor.apply();
    }

    public static boolean isEligible(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPref.getBoolean(IS_ELIGIBLE,false);
    }

    public static void getHideKeyBoard(Context ctx, View view){
        InputMethodManager imm = (InputMethodManager)ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static String changeBase64String(String text) {
        byte[] data = new byte[0];
        try {
            data = text.getBytes("UTF-8");
            String base64 = Base64.encodeToString(data, Base64.DEFAULT);
            return base64;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static void setPreference(Context context, String key, String value) {
        sharedpreferences = context.getSharedPreferences(MyPREFERENCES,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();

        editor.putString(key, value);
        editor.commit();
    }

    public static String getPreference(Context context, String key) {
        sharedpreferences = context.getSharedPreferences(MyPREFERENCES,
                Context.MODE_PRIVATE);
        return sharedpreferences.getString(key, "");
    }
}
