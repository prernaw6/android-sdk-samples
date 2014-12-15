package com.sensorberg.sdk.demo;

import android.app.Application;
import android.util.Log;

import com.sensorberg.sdk.presenter.PresenterConfiguration;
import com.sensorberg.sdk.resolver.ResolverConfiguration;
import com.sensorberg.sdk.scanner.ScannerConfiguration;

@SuppressWarnings("javadoc")
public class DemoApplication extends Application
{
    @Override
	public void onCreate() {
		//follow the code intetration of the current release, this sample only highlights that you
		// can actually unzip the aar, copy the jar file, manually merge the manifest, manually
		// transitively add all dependencies and run the SDK.

        //BUT PLEASE DON`T ACTUALLY DO THIS!!! USE GRADLE!

	}
}
