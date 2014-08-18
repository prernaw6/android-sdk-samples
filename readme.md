[![Build Status](https://travis-ci.org/sensorberg-dev/android-sdk.svg?branch=master)](https://travis-ci.org/sensorberg-dev/android-sdk)

#Welcome to the samples of the Sensorberg Android SDK

#howto integrate the SDK

We recommend gradle builds and will only document the gradle setup. If you rely on legacy build systems, check the samples, there might be a project that shows how to integrate it with any IDE/build system.

##Integration:

1. your manifest file:

	```xml
	<uses-permission android:name="android.permission.INTERNET" />
	<uses-permission android:name="android.permission.BLUETOOTH" />
	<uses-permission android:name="android.permission.BLUETOOTH_ADMIN" />
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
        compile 'com.sensorberg.sdk:sensorberg-sdk:+'
        compile ('com.sensorberg.sdk.bootstrapper:sensorberg-sdk-bootstrapper:+')/*{
            exclude group: 'com.sensorberg.sdk', module: 'sensorberg-sdk'
        }*/
    }
	```

3. Set up the SDK in your Application class:

	```java
	public class DemoApplication extends Application
	{ 
	    private SensorbergApplicationBootstrapper boot;
	
	    @Override
	    public void onCreate() {
	        super.onCreate();        
	
	
	        ScannerConfiguration scannerConfiguration = new ScannerConfiguration(this);
	        scannerConfiguration.setExitEventDelay(15000);
	        ResolverConfiguration resolverConfiguration = new ResolverConfiguration(this, "f257de3b91d141aa93b6a9b39c97b83df257de3b91d141aa93b6a9b39c97b83d");
	        PresenterConfiguration presenterConfiguration = new PresenterConfiguration(this);
	
	
	        //bootstrap the complete integration, keep a local reference to the bootstrapper
	        boot =  new SensorbergApplicationBootstrapper()
	                .bootstrapApplication(scannerConfiguration, resolverConfiguration, presenterConfiguration)
	                .bootstrapBackgroundScanning();
		}
	}
	```

#samples

browse the samples folder to see basic integrations of the SDK
	
	── 001_basic
	── 002_basic_with_local_dependency
	── 003_local_dependency
	── 004_basic_with_jar_dependency

##001_basic sample

The recommended way to integrate our SDK

* build with gradle
* reference SDK artifact from our github maven repository

##002_basic_with_local_dependency sample

A reference for a local reference of the aar reference of the SDK. It used the module **003_local_dependency** which statically declares the aar as an artifact. This sample follows the 

This is not recommended, since you need to **manually** add the sdk dependencies as well. Currently we depend on **com.loopj.android:android-async-http**.

##004_basic_with_jar_dependency

This project shows that you can also unpack the aar and only reference the included jar file. We are currently not including any resourse files in our SDK, so there is no disadvantage with this method, still, you will propably run into problems in the future. This sample also relies on you to manually add the SDK dependencies as jar files. This method is also **not** recommended