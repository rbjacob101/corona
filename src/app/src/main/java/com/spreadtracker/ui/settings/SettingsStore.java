package com.spreadtracker.ui.settings;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Class used to store settings values across the app.
 */
public class SettingsStore {
    private static final String PREFERENCES_KEY = "com.spreadtracker.preferences";

    private static SettingsStore _instance;
    public static SettingsStore getInstance (@NonNull Context mCtx){
        if (_instance != null) return _instance;
        _instance = new SettingsStore(mCtx);
        return _instance;
    }

    private Context mCtx;
    private SharedPreferences mPreferences;

    private SettingsStore (@NonNull Context ctx) {
        mCtx = ctx;
        mPreferences = mCtx.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE);
    }

    /**
     * Writes the given key-value pair to the preferences map.
     * @param key The key to identify the written value..
     * @param o The object to write. This must be a {@link Boolean}, {@link Float}, {@link Integer}, {@link Long}, {@link String}, or {@link Set<String>},
     *          otherwise an exception will be thrown.
     */
    public void writeValue (String key, Object o) {
        SharedPreferences.Editor editor = mPreferences.edit();

        if (o instanceof Boolean)
            editor.putBoolean(key, (Boolean) o);
        else if (o instanceof Float)
            editor.putFloat(key, (Float) o);
        else if (o instanceof Integer)
            editor.putInt(key, (Integer) o);
        else if (o instanceof Long)
            editor.putLong(key, (Long) o);
        else if (o instanceof String)
            editor.putString(key, (String) o);
        else if (o instanceof Set)
            editor.putStringSet(key, (Set<String>) o);
        else throw new IllegalArgumentException("Tried to write an invalid backing type to the SettingsStore");

        editor.apply();
    }

    public boolean readBool (String key, boolean defaultValue) { return mPreferences.getBoolean(key, defaultValue); }
    public float readFloat (String key, float defaultValue) { return mPreferences.getFloat(key, defaultValue); }
    public int readInt (String key, int defaultValue) { return mPreferences.getInt(key, defaultValue); }
    public long readLong (String key, long defaultValue) { return mPreferences.getLong(key, defaultValue); }
    public String readString (String key, String defaultValue) { return mPreferences.getString(key, defaultValue); }
    public Set<String> readStringSet (String key, Set<String> defaultValue) { return mPreferences.getStringSet(key, defaultValue); }

    public <T> T readValue (String key, T defaultValue) {
        Map<String, ?> pMap = mPreferences.getAll();
        if (pMap.containsKey(key)) return (T) pMap.get(key);
        else return defaultValue;
    }
}