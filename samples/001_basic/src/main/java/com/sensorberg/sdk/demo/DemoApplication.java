package com.sensorberg.sdk.demo;

import android.app.Application;
import android.util.Log;

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


		ScannerConfiguration scannerConfiguration = new ScannerConfiguration(this);
        ResolverConfiguration resolverConfiguration = new ResolverConfiguration(this, "f257de3b91d141aa93b6a9b39c97b83df257de3b91d141aa93b6a9b39c97b83d");
        PresenterConfiguration presenterConfiguration = new PresenterConfiguration(this);


        //bootstrap the complete integration, keep a local reference to the bootstrapper
        boot =  new SensorbergApplicationBootstrapper()
                .bootstrapApplication(scannerConfiguration, resolverConfiguration, presenterConfiguration)
                .bootstrapBackgroundScanning();
	}
}
