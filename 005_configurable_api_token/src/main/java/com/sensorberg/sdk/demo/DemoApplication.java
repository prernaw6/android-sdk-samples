package com.sensorberg.sdk.demo;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.sensorberg.near.R;
import com.sensorberg.sdk.bootstrapper.BackgroundDetector;
import com.sensorberg.sdk.bootstrapper.SensorbergApplicationBootstrapper;
import com.sensorberg.sdk.internal.Plattform;
import com.sensorberg.sdk.near.SharedPreferencesHelper;
import com.sensorberg.sdk.presenter.PresenterConfiguration;

@SuppressWarnings("javadoc")
public class DemoApplication extends Application
{
    private static final String TAG = "DemoApplication";

    public SensorbergApplicationBootstrapper boot;


    public SharedPreferencesHelper helper;


    private static DemoApplication instance;

    @Override
	public void onCreate() {
		super.onCreate();

        Log.d(TAG, "onCreate application");

        helper = new SharedPreferencesHelper(getSharedPreferences("com.sensorberg.near", MODE_PRIVATE));

        boolean foreGroundNotifications = getSharedPreferences("me", MODE_PRIVATE).getBoolean("foreground_notifications", false);

        PresenterConfiguration presenterConfiguration = new PresenterConfiguration(R.drawable.ic_launcher);
        boot = new SensorbergApplicationBootstrapper(this, helper.getAPIKey(), foreGroundNotifications, presenterConfiguration);

        instance = this;

        BackgroundDetector callback = new BackgroundDetector(boot);
        registerActivityLifecycleCallbacks(callback);
	}

    static DemoApplication getInstance(){
        return instance;
    }


}
