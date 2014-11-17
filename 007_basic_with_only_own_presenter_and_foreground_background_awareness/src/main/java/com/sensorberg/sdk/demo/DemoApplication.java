package com.sensorberg.sdk.demo;

import android.app.Application;
import android.util.Log;

import com.sensorberg.sdk.BuildConfig;
import com.sensorberg.sdk.Logger;
import com.sensorberg.sdk.bootstrapper.BackgroundDetector;
import com.sensorberg.sdk.bootstrapper.SensorbergApplicationBootstrapper;
import com.sensorberg.sdk.presenter.PresenterConfiguration;
import com.sensorberg.sdk.resolver.ResolverConfiguration;
import com.sensorberg.sdk.scanner.ScannerConfiguration;

@SuppressWarnings("javadoc")
public class DemoApplication extends Application
{
    private static final String TAG = "DemoApplication";
    private MyCustomBootStrapper boot;

    //show all internal logging in debug mode
    static {
        if (BuildConfig.DEBUG) {
            Logger.enableVerboseLogging();
        }
    }

    private BackgroundDetector detector;

    @Override
	public void onCreate() {
		super.onCreate();
        Log.d(TAG, "onCreate application");

        //bootstrap the complete integration, keep a local reference to the bootstrapper
        boot =  new MyCustomBootStrapper(this, "36e8adb02ead475a856d36326850e9e8c772c613de6fe5a76c9b4c78ac16d40d");
        boot.connectToService();


        //we still want a background detector, that tells the SDK to save power when the app is in the background
        detector = new BackgroundDetector(boot);
        registerActivityLifecycleCallbacks(detector);
	}
}
