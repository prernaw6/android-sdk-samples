[![Build Status](https://travis-ci.org/sensorberg-dev/android-sdk.svg?branch=master)](https://travis-ci.org/sensorberg-dev/android-sdk)

#Welcome to the samples of the Sensorberg Android SDK

#howto integrate the SDK

We recommend gradle builds and will only document the gradle setup. If you rely on legacy build systems, check the samples, there might be a project that shows how to integrate it with any IDE/build system.

##Integration:

1. your manifest file:<br/> 
do nothing (our aar has all the neccesary declarations, and gradle simply merges them)
If this seems to easy, and you want to vibrate when a beacon is nearby, add the vibrate permission:
	```xml
		<uses-permission android:name="android.permission.VIBRATE"/>
	```

2. your gradle file:

	```groovy
	repositories {
	    mavenCentral()
	    maven {
	        url "https://raw.github.com/sensorberg-dev/android-sdk/mvn-repo";
	    }
	}
	
	dependencies {
        compile ('com.sensorberg.sdk.bootstrapper:sensorberg-sdk-bootstrapper:+')
    }
	```

3. Set up the SDK in your Application class:

	```java
	public class DemoApplication extends Application
	{ 
	    private SensorbergApplicationBootstrapper boot;
	    private BackgroundDetector callback;
	
	    @Override
	    public void onCreate() {
	        super.onCreate();        
	
	
	        PresenterConfiguration presenterConfiguration = new PresenterConfiguration(R.drawable.ic_launcher);
	        boot = new SensorbergApplicationBootstrapper(this, "your-api-key", foreGroundNotifications, presenterConfiguration);

	        BackgroundDetector callback = new BackgroundDetector(boot);
	        registerActivityLifecycleCallbacks(callback);
		}
	}
	```

#samples

browse the samples folder to see basic integrations of the SDK
	
	── 001_basic
	── 002_basic_with_local_dependency
	── 003_local_dependency
	── 004_basic_with_jar_dependency
	── 005_configurable_api_token
	── 006_basic_with_only_own_presenter

##001_basic sample

The recommended way to integrate our SDK

* build with gradle
* reference SDK artifact from our github maven repository

##002_basic_with_local_dependency sample

A reference for a local reference of the aar reference of the SDK. It used the module **003_local_dependency** which statically declares the aar as an artifact. This sample follows the 

This is not recommended, since you need to **manually** add the sdk dependencies as well. Currently we depend on **com.loopj.android:android-async-http**.

##004_basic_with_jar_dependency

This project shows that you can also unpack the aar and only reference the included jar file. We are currently not including any resourse files in our SDK, so there is no disadvantage with this method, still, you will propably run into problems in the future. This sample also relies on you to manually add the SDK dependencies as jar files. This method is also **not** recommended. You must also merge the manifest manually and all our declarations. Basically you need to manually merge all the entries from the aar file. Again, please don´t try this at home.

##005_configurable_api_token

This is our internal dogfooding app. We´re extending it with all the API features we are exposing to you. Feel free to use it as a sample for all kinds of integrations. It also incorporates a QR scanner so you can scan your API token directly from the website if you want to get started really quick.

This sample also highlights the implementation of a custom interface in your application for the content associated with a beacon.

Check this if you want to see how to turn logging on and off for debugging purposes.

###006_basic_with_only_own_presenter

Sample that shows how to handle all Beacon Actions in your application.


