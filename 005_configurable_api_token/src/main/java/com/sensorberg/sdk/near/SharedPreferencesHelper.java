package com.sensorberg.sdk.near;

import android.content.SharedPreferences;

public class SharedPreferencesHelper {

    private static final String DEFAULT_API_KEY = "f257de3b91d141aa93b6a9b39c97b83df257de3b91d141aa93b6a9b39c97b83d";
    public static final String API_KEY = "com.sensorberg.apiKey";
    private SharedPreferences preferences;

    public SharedPreferencesHelper(SharedPreferences preferences){
        this.preferences = preferences;
    }

    public void setAPIKey(String APIKey) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(API_KEY, APIKey);
        editor.commit();
    }

    public String getAPIKey() {
        return preferences.getString(API_KEY, DEFAULT_API_KEY);
    }
}
