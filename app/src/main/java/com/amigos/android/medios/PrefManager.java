package com.amigos.android.medios;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Arijit on 07-07-2017.
 */

/**
 * The welcome/intro screen should be shown only once when the app is launched for the very first time.
 * If the user launches the app on second time, he should directly go to main screen.
 * To achieve this, we use SharedPreferences to store a boolean value indicating first time launch.
 */

class PrefManager {
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context _context;

    // shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "medios-welcome";

    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";

    public PrefManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }
}
