package rs.fon.dontforgetyourmedicine.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import rs.fon.dontforgetyourmedicine.DontForgetYourMedicine;

/**
 * Created by radovan.bogdanic on 3/9/2017.
 */

public class SharedPrefs {

    private static final String PREFERENCES_NAME = "MedicinePreferences";
    private static final String USER_ID = "user_id";
    private static final String USER_MODE_ACTIVE = "user_mode";

    private static SharedPrefs prefs;
    private SharedPreferences sharedPreferences;

    private SharedPrefs(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public static SharedPrefs getPreferences() {
        if(prefs == null) {
            prefs = new SharedPrefs(DontForgetYourMedicine.getApplication().getApplicationContext());
        }
        return prefs;
    }

    public String getLanguage() {
        return sharedPreferences.getString(MedicineConstants.SELECTED_LANGUAGE, "");
    }

    public void setLanguage(String language) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(MedicineConstants.SELECTED_LANGUAGE, language);
        editor.commit();
    }

    public void setLanguageDefaultPreference(String language) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(
                DontForgetYourMedicine.getApplication().getApplicationContext()
        );
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(MedicineConstants.SELECTED_LANGUAGE, language); // value to store
        editor.commit();
    }

    public boolean isVibrationOn() {
        return sharedPreferences.getBoolean(MedicineConstants.SELECTED_VIBRATION,false);
    }

    public void setVibration(boolean isON) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(MedicineConstants.SELECTED_VIBRATION,isON);
        editor.commit();
    }

    public boolean isNotificationnOn() {
        return sharedPreferences.getBoolean(MedicineConstants.SELECTED_NOTIFICATION,false);
    }

    public void setNotification(boolean isON) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(MedicineConstants.SELECTED_NOTIFICATION,isON);
        editor.commit();
    }
    public int getLoggedUserId() {
        return sharedPreferences.getInt(USER_ID, -1);
    }

    public void setLoggedUserId(int id){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(USER_ID, id);

        editor.commit();
    }

    public boolean isVisitor() {
        return sharedPreferences.getBoolean(USER_MODE_ACTIVE,false);
    }
    public void setVisitor(boolean isLogged) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(USER_MODE_ACTIVE,isLogged);
        editor.commit();
    }
}
