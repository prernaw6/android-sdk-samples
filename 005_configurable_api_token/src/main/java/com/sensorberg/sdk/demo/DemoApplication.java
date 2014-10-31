package com.sensorberg.sdk.demo;

import android.app.Application;
import android.graphics.Color;
import android.util.Log;

import com.sensorberg.near.R;
import com.sensorberg.sdk.Logger;
import com.sensorberg.sdk.bootstrapper.BackgroundDetector;
import com.sensorberg.sdk.near.SharedPreferencesHelper;
import com.sensorberg.sdk.presenter.NotificationLightsConfiguration;
import com.sensorberg.sdk.presenter.PresenterConfiguration;

@SuppressWarnings("javadoc")
public class DemoApplication extends Application
{
    private static final String TAG = "DemoApplication";
    public static final long[] VIBRATION_PATTERN = {0, 120,120, 120,120, 120,3*120, 3*120,120 ,120,120, 120,120, 120};
    public static final NotificationLightsConfiguration LIGHTS_PATTERN = new NotificationLightsConfiguration(Color.MAGENTA, 500, 500);;

    public NearApplicationBootstrapper boot;
    private BackgroundDetector callback;
    public SharedPreferencesHelper helper;
    private static DemoApplication instance;

    static {
        Logger.enableVerboseLogging();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Log.d(TAG, "onCreate application");

        helper = new SharedPreferencesHelper(getSharedPreferences("com.sensorberg.near", MODE_PRIVATE));

        boolean foreGroundNotifications = helper.foreGroundNotificationsEnabled();
        PresenterConfiguration presenterConfiguration = new PresenterConfiguration(R.drawable.ic_launcher);

        if (helper.vibrationOnNotificationsEnabled()){
            presenterConfiguration.setVibrationPattern(VIBRATION_PATTERN);
        }
        if(helper.ledOnNotificationsEnabled()){
            presenterConfiguration.setNotificationLightsConfiguration(LIGHTS_PATTERN);
        }

        boot = new NearApplicationBootstrapper(this, helper.getAPIKey(), foreGroundNotifications, presenterConfiguration);

        callback = new BackgroundDetector(boot);
        registerActivityLifecycleCallbacks(callback);
    }

    static DemoApplication getInstance(){
        return instance;
    }

    public void startSensorbergServiceForFirstTime(){
        boot.connectToService();
    }

}
