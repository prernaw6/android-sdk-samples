package com.sensorberg.sdk.demo;

import android.app.Application;
import android.util.Log;

import com.sensorberg.sdk.bootstrapper.BackgroundDetector;
import com.sensorberg.sdk.bootstrapper.SensorbergApplicationBootstrapper;
import com.sensorberg.sdk.demo.demoOne.R;
import com.sensorberg.sdk.presenter.PresenterConfiguration;
import com.sensorberg.sdk.resolver.ResolverConfiguration;
import com.sensorberg.sdk.scanner.ScannerConfiguration;

@SuppressWarnings("javadoc")
public class DemoApplication extends Application
{
    private static final String TAG = "DemoApplication";
    private SensorbergApplicationBootstrapper boot;
    private BackgroundDetector detector;

    @Override
	public void onCreate() {
		super.onCreate();
        Log.d(TAG, "onCreate application");

        PresenterConfiguration presenterConfiguration = new PresenterConfiguration(R.drawable.ic_launcher);
        //bootstrap the complete integration, keep a local reference to the bootstrapper
        boot =  new SensorbergApplicationBootstrapper(this, presenterConfiguration, "f257de3b91d141aa93b6a9b39c97b83df257de3b91d141aa93b6a9b39c97b83d");

        detector = new BackgroundDetector(boot);
        registerActivityLifecycleCallbacks(detector);

	}
}
