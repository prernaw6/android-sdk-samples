package com.sensorberg.sdk.demo;

import android.app.Application;
import android.os.Build;
import android.util.Log;

import com.sensorberg.sdk.BuildConfig;
import com.sensorberg.sdk.Logger;
import com.sensorberg.sdk.bootstrapper.BackgroundDetector;
import com.sensorberg.sdk.bootstrapper.SensorbergApplicationBootstrapper;
import com.sensorberg.sdk.demo.demoSeven.R;
import com.sensorberg.sdk.internal.AndroidPlattform;
import com.sensorberg.sdk.presenter.PresenterConfiguration;

@SuppressWarnings("javadoc")
public class DemoApplication extends Application
{
    private static final String TAG = "DemoApplication";
    public static DemoApplication instance;
    private SensorbergApplicationBootstrapper boot;
    private BackgroundDetector detector;

    public AndroidPlattform plattform;

    //show all internal logging in debug mode
    static {
        if (BuildConfig.DEBUG) {
            Logger.enableVerboseLogging();
        }
    }



    @Override
	public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate application");
        instance = this;

        plattform = new AndroidPlattform(getApplicationContext());
        if (plattform.hasMinimumAndroidRequirements() && plattform.isBluetoothLowEnergySupported()) {
            //continue as in sample 001
        }
	}
}
