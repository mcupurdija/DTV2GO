package rs.multitelekom.dtv2go.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPreferencesUtils {

    public static final String USER_ID_KEY = "dtv2go_user_id";
    public static final String DEV_ID_KEY = "dev_id";
    public static final String LAST_SYNC_DATE = "dtv2go_last_sync_date";
    public static final String LAST_MOVIES_SYNC_DATE = "dtv2go_last_movies_sync_date";

    public static void savePreferences(Context context, String key, String value) {
        SharedPreferences sp = context.getSharedPreferences(AppConstants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        Editor editor = sp.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String readPreferences(Context context, String key, String defaultValue) {
        SharedPreferences sp = context.getSharedPreferences(AppConstants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        return sp.getString(key, defaultValue);
    }

    public static void saveUserId(Context context, String value) {
        SharedPreferences sp = context.getSharedPreferences(AppConstants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        Editor editor = sp.edit();
        editor.putString(USER_ID_KEY, value);
        editor.apply();
    }

    public static String getUserId(Context context) {
        SharedPreferences sp = context.getSharedPreferences(AppConstants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        return sp.getString(USER_ID_KEY, null);
    }

    public static void saveDevId(Context context, String value) {
        SharedPreferences sp = context.getSharedPreferences(AppConstants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        Editor editor = sp.edit();
        editor.putString(DEV_ID_KEY, value);
        editor.apply();
    }

    public static String getDevId(Context context) {
        SharedPreferences sp = context.getSharedPreferences(AppConstants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        return sp.getString(DEV_ID_KEY, null);
    }

    public static void saveLastSyncDate(Context context, String value) {
        SharedPreferences sp = context.getSharedPreferences(AppConstants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        Editor editor = sp.edit();
        editor.putString(LAST_SYNC_DATE, value);
        editor.apply();
    }

    public static String getLastSyncDate(Context context) {
        SharedPreferences sp = context.getSharedPreferences(AppConstants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        return sp.getString(LAST_SYNC_DATE, null);
    }
}