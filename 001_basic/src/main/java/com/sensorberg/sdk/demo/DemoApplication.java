package com.sensorberg.sdk.demo;

import android.app.Application;
import android.util.Log;


import com.sensorberg.sdk.Logger;
import com.sensorberg.sdk.bootstrapper.BackgroundDetector;
import com.sensorberg.sdk.bootstrapper.SensorbergApplicationBootstrapper;
import com.sensorberg.sdk.demo.demoOne.BuildConfig;
import com.sensorberg.sdk.demo.demoOne.R;
import com.sensorberg.sdk.presenter.PresenterConfiguration;

@SuppressWarnings("javadoc")
public class DemoApplication extends Application
{
    private static final String TAG = "DemoApplication";
    private SensorbergApplicationBootstrapper boot;
    private BackgroundDetector detector;

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

        PresenterConfiguration presenterConfiguration = new PresenterConfiguration(R.drawable.ic_launcher);
        //bootstrap the complete integration, keep a local reference to the bootstrapper
        boot =  new SensorbergApplicationBootstrapper(this);
        boot.connectToService("69954b55cdb77846d1f8b844bfc4004e722c910afdee638012a104f7f9842c33", presenterConfiguration);

        detector = new BackgroundDetector(boot);
        registerActivityLifecycleCallbacks(detector);

	}
}
