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
    private SensorbergApplicationBootstrapper boot;

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
        //bootstrap the complete integration, keep a local reference to the bootstrapper
        boot =  new MyCustomBootStrapper(this, "36e8adb02ead475a856d36326850e9e8c772c613de6fe5a76c9b4c78ac16d40d");
        //you are in charge of making the app scan with the foreground or background configuration,
        //we´e assuming you want the allways foreground (fast but more battery intense) mode
        boot.hostApplicationInForeground();
	}
}
