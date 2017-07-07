package com.amigos.android.medios;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Arijit on 07-07-2017.
 */

public class SplashActivity extends AppCompatActivity {
    PrefManager prefManager;

    @Override
    protected void onStart() {
        super.onStart();

        prefManager = new PrefManager(this);
        // Checking for first time launch
        if (prefManager.isFirstTimeLaunch()) {
            // If 'First Time Launch'
            prefManager.setFirstTimeLaunch(false);

            Intent intent = new Intent(this, IntroActivity.class);
            startActivity(intent);
            finish();
        }
        else {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
