[![Build Status](https://travis-ci.org/sensorberg-dev/android-sdk-samples.svg)](https://travis-ci.org/sensorberg-dev/android-sdk-samples)

#Welcome to the samples of the Sensorberg Android SDK

Before continuing please consider answering this [questionaire](https://docs.google.com/a/sensorberg.com/forms/d/1EvQFPimFyLQBzoWtGNr83Iovx0T6UlnLJqTur5Tz8Kw/viewform)

#Warning
Head over to http://developer.sensorberg.com/android/ for instructions.

#samples

browse the samples folder to see basic integrations of the SDK
	
	── 001_basic
	── 002_basic_with_local_dependency
	── 003_local_dependency
	── 004_basic_with_jar_dependency
	── 005_configurable_api_token
	── 006_basic_with_only_own_presenter [removed]
	-- 007_basic_with_only_own_presenter_and_foreground_background_awareness
	-- 008_basic_api_level_14
	-- 009_basic_api_level_9

**Please read the notes of each sample carefully!**

##001_basic sample

The recommended way to integrate our SDK

* build with gradle
* reference SDK artifact from our github maven repository

##002_basic_with_local_dependency sample

A reference for a local reference of the aar reference of the SDK. It used the module **003_local_dependency** which statically declares the aar as an artifact. This sample follows a sample by Google.

This is not recommended, since you need to **manually** add the sdk dependencies as well. Currently we depend on **com.loopj.android:android-async-http**.

If you want to proceeed using the jar dependenceny:

* **YOU** must download the latest aar artifact
* **YOU** must check if all dependencies are fullfilled by checking the dependencies in the pom file
* Do this **ALSO** for the bootstrapper!!!
 
*This sample only shows, that using the local aar dependency is technically possible. If you know what you are doing please proceed, otherwise* **pretty please** *use the aar dependency.* Therefore this project is **not maintained** to the latest SDK and bootstrapper version!!!


##004_basic_with_jar_dependency

This project shows that you can also unpack the aar and only reference the included jar file. We are currently not including any resourse files in our SDK, so there is no disadvantage with this method, still, you will propably run into problems in the future. This sample also relies on you to manually add the SDK dependencies as jar files. This method is also **not** recommended. You must also merge the manifest manually and all our declarations. Basically you need to manually merge all the entries from the aar file. Again, please don´t try this at home.

If you want to proceeed using the jar dependenceny:

* **YOU must** download the latest aar artifact **REGULARLY**
* **YOU must** unpack the aar file
* **YOU must** extract the jar file
* **YOU must** check if all dependencies are fullfilled by checking the dependencies in the pom file
* **YOU must** merge the manifest from the aar
* **YOU must** merge all other possible resources from the aar (please check the [aar reference](http://tools.android.com/tech-docs/new-build-system/aar-format))
 
*This sample only shows, that using the jar dependency is technically possible. If you know what you are doing please proceed, otherwise* **pretty please** *use the aar dependency.* Therefore this project is **not maintained** to the latest SDK and bootstrapper version!!!

##005_configurable_api_token

This is our internal dogfooding app. We´re extending it with all the API features we are exposing to you. Feel free to use it as a sample for all kinds of integrations. It also incorporates a QR scanner so you can scan your API token directly from the website if you want to get started really quick.

This sample also highlights the implementation of a custom interface in your application for the content associated with a beacon.

Check this if you want to see how to turn logging on and off for debugging purposes.

This sample also highlights how to completely disable a the SDK when it is running. **If you wish to disable the service permanently, store and persist this flag in your app.** Instanciating a bootstrapper does not longer start the SDK, but the ```connectToService()```method does. Use the ```disableServiceCompletely```method accordingly to disable the Scanning, Auto-awakening...

###~~006_basic_with_only_own_presenter~~

~~Sample that shows how to handle all Beacon Actions in your application.~~

This sample was removed since it did not respect if the app is in the foreground or background. 

###007_basic_with_only_own_presenter_and_foreground_background_awareness

This sample leaves the presentation to the host app, but foreground and background changes are resprected by the SDK to save power.

###008_basic_api_level_14

This samples shows, what you should do, if your app is minSdkVersion 14

**YOU SHOULD PROPABLY FOLLOW THIS EXAMPLE ON TOP OF THE OTHER SAMPLES IN ALL YOUR PROJECTS IF YOU ARE minSdkVersion 14**

###009_basic_api_level_9

This samples shows, what you should do, if your app is MinApiLevel 9

**YOU SHOULD PROPABLY FOLLOW THIS EXAMPLE ON TOP OF THE OTHER SAMPLES IN ALL YOUR PROJECTS IF YOU ARE minSdkVersion 9**


