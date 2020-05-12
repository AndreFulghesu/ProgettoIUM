package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;

import java.text.Normalizer;

public class UserSession {
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private String SESSION_USER = "session_user";
    private String PREF_NAME = "session";
    private String IS_LOGGED ="isLogged";
    private String DARK_THEME = "dark_theme";
    private String CALLING_ACTIVITY = "calling_activity";
    private String BOOK_ID = "bookId";
    private String CHAPTER_ID = "chapId";

    public UserSession(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    public void saveUserSession(String username) {
        editor.putString(SESSION_USER, username).commit();
        editor.putBoolean(IS_LOGGED, true).commit();
        if (username.equals("Faber123")) {
            editor.putBoolean(DARK_THEME, true).commit();
            setTheme(false);
        } else {
            editor.putBoolean(DARK_THEME, false).commit();
            setTheme(true);
        }
    }
    public Boolean isLogged() {
        return prefs.getBoolean(IS_LOGGED, false);
    }

    public void invalidateSession() {
        editor.remove(SESSION_USER).commit();
        editor.remove(DARK_THEME).commit();
        editor.remove(CALLING_ACTIVITY).commit();
        editor.putBoolean(IS_LOGGED, false).commit();
    }

    public void setBookId (int bookId) {
        editor.putInt(BOOK_ID, bookId).commit();
    }
    public int getBookId() {
        return prefs.getInt(BOOK_ID, -1);
    }
    public void setChapId (int chapId) {
        editor.putInt(CHAPTER_ID, chapId).commit();
    }
    public int getChapId() {
        return prefs.getInt(CHAPTER_ID, -1);
    }

    public void setTheme(Boolean switchTheme) {
        editor.putBoolean(DARK_THEME, switchTheme).commit();
    }

    public Boolean getTheme() {return prefs.getBoolean(DARK_THEME, false);}

    public String getUserSession() {
        return prefs.getString(SESSION_USER," ");
    }

    public int getCallingActivityValue() { return prefs.getInt(CALLING_ACTIVITY, -1);}

    public void setCallingActivity(int classValue) {
        editor.putInt(CALLING_ACTIVITY, classValue).commit();
    }

    public Class getActivityFromValue(int enumValue){
        ActivitiesEnum actEnum = getEnum(enumValue);
        System.out.println(enumValue);
        switch (actEnum){
            case HOME:
                return Home.class;
            case CATALOGO:
                return Catalogo.class;
            case CHAPTERLIST:
                return ChapterList.class;
            case LEGGILIBRO:
                return LeggiLibro.class;
            case COMMENTI:
                return CommentList.class;
            case FORMCOMMENTO:
                return FormCommento.class;
            case MYPROFILE:
                return MyProfile.class;
            default:
                return null;
        }
    }

    public ActivitiesEnum getEnum (int v) {
        switch(v) {
            case 1:
                return ActivitiesEnum.HOME;
            case 2:
                return ActivitiesEnum.CATALOGO;
            case 3:
                return ActivitiesEnum.CHAPTERLIST;
            case 4:
                return ActivitiesEnum.LEGGILIBRO;
            case 5:
                return ActivitiesEnum.COMMENTI;
            case 6:
                return ActivitiesEnum.FORMCOMMENTO;
            case 7:
                return ActivitiesEnum.MYPROFILE;
            default:
                return null;
        }
    }
}
