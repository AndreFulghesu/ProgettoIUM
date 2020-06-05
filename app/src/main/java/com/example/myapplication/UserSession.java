package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;

/**Classe per il salvataggio della sessione, nella quale verranno salvati utente loggato ed
 * altri eventuali dati importanti per il sistema
 */
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
    private String ORDINAMENTO = "ordinamento";
    private String USER_AUTHOR ="userName_Author";

    /**Richiesta sessione*/
    public UserSession(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    /**Salvataggio sessione al login*/
    void saveUserSession(String username) {
        editor.putString(SESSION_USER, username).commit();
        editor.putBoolean(IS_LOGGED, true).commit();
        editor.putBoolean(DARK_THEME, false).commit();
        setTheme(true);
    }
    /**Controllo status sessione dell'utente*/
    Boolean isLogged() {
        return prefs.getBoolean(IS_LOGGED, false);
    }

    /**Metodo che invalida la sessione in caso di logout*/
    public void invalidateSession() {
        editor.remove(SESSION_USER).commit();
        editor.remove(DARK_THEME).commit();
        editor.remove(CALLING_ACTIVITY).commit();
        editor.putBoolean(IS_LOGGED, false).commit();
    }

    /**Metodi getter e setter per l'accesso e l'eventuale modifica
     * degli elementi salvati in sessione
     */
    public void setBookId (int bookId) {
        editor.putInt(BOOK_ID, bookId).commit();
    }

    public int getBookId() {
        return prefs.getInt(BOOK_ID, -1);
    }

    void setChapId(int chapId) {
        editor.putInt(CHAPTER_ID, chapId).commit();
    }

    int getChapId() {
        return prefs.getInt(CHAPTER_ID, -1);
    }

    void setUserAuthor(String username) {editor.putString(USER_AUTHOR,username).commit();}

    String getUsernameAuthor() { return prefs.getString(USER_AUTHOR,"");}

    public void setTheme(Boolean switchTheme) {
        editor.putBoolean(DARK_THEME, switchTheme).commit();
    }

    public Boolean getTheme() {return prefs.getBoolean(DARK_THEME, false);}

    public String getUserSession() {
        return prefs.getString(SESSION_USER," ");
    }

    void setOrdinamento(int ordinamento) {
        editor.putInt(ORDINAMENTO, ordinamento).commit();
    }

    int getOrdinamento(){
        return prefs.getInt(ORDINAMENTO, -1);
    }

    public int getCallingActivityValue() { return prefs.getInt(CALLING_ACTIVITY, -1);}

    void setCallingActivity(int classValue) {
        editor.putInt(CALLING_ACTIVITY, classValue).commit();
    }

    Class getActivityFromValue(int enumValue){
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

    private ActivitiesEnum getEnum(int v) {
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
