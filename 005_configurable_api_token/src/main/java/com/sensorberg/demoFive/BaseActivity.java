package com.sensorberg.demoFive;

import android.app.Activity;

import net.hockeyapp.android.Tracking;

public class BaseActivity extends Activity{

    @Override
    protected void onResume() {
        super.onResume();
        if(!BuildConfig.DEBUG) {
            Tracking.startUsage(this);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(!BuildConfig.DEBUG) {
            Tracking.stopUsage(this);
        }
    }
}
