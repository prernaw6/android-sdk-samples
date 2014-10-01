package com.sensorberg.sdk.demo;

import android.app.Application;
import android.util.Log;

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

    @Override
	public void onCreate() {
		super.onCreate();
        Log.d(TAG, "onCreate application");
        //bootstrap the complete integration, keep a local reference to the bootstrapper
        boot =  new MyCustomBootStrapper(this, "f257de3b91d141aa93b6a9b39c97b83df257de3b91d141aa93b6a9b39c97b83d");
	}
}
