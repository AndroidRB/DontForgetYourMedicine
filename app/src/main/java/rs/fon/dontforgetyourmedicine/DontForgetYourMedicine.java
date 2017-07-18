package rs.fon.dontforgetyourmedicine;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

import java.util.Locale;

import rs.fon.dontforgetyourmedicine.util.SharedPrefs;

/**
 * Created by radovan.bogdanic on 3/9/2017.
 */

public class DontForgetYourMedicine extends Application {

    private static DontForgetYourMedicine instance;

    public static DontForgetYourMedicine getApplication() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        setDefaultLanguage();
    }

    private void setDefaultLanguage() {
        String language = SharedPrefs.getPreferences().getLanguage();
        if(!"".equals(language)) {
            setLocale(this,language);
        } else {
            SharedPrefs.getPreferences().setLanguageDefaultPreference(Locale.getDefault().getISO3Language());
            setLocale(this, Locale.getDefault().getISO3Language());
        }
    }

    public static void setLocale(Context context, String language){
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        context.getApplicationContext().getResources().updateConfiguration(config, null);
    }
}
