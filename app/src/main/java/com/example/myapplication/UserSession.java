package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;

public class UserSession {

    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private String SESSION_USER = "session_user";
    private String PREF_NAME = "session";
    private String IS_LOGGED ="isLogged";
    public UserSession(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    public void saveUserSession(String username) {
        editor.putString(SESSION_USER, username).commit();
        editor.putBoolean(IS_LOGGED, true).commit();
    }
    public Boolean isLogged() {
        return prefs.getBoolean(IS_LOGGED, false);
    }
    public String getUserSession() {
        return prefs.getString(SESSION_USER," ");
    }

    public void invalidateSession() {
        editor.remove(SESSION_USER).commit();
        editor.putBoolean(IS_LOGGED, false).commit();
    }

}
