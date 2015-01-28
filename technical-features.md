#Techical list of features:

* SDK can be made aware if the host application is in the foreground or in the background
* turn off the SDK scanner completely when Bluetooth is turned off
* start the SDK scanner when bluetooth is turned back on
* start the SDK scanner when the device boots (android.intent.action.USER_PRESENT)
* turn verbose logging on and off using a secret code ([release notes](https://github.com/sensorberg-dev/android-sdk/releases/tag/v0.8.4))
* detect entry events from beacons
* detect exit events from beacons
* resolve actions associated with beacons
* present actions in the notification area
* associate an URL with a notification that will be opened when a notification is tapped
* present an action with delay after an event (entry or exit)
* restart the SDK Scanner after the app was [removed from the Android Multitasking Drawer](http://lifehacker.com/what-happens-when-you-remove-an-app-from-androids-mult-1179868228)
* stop the SDK completely "opt-out"
* change the API token at runtime
* update the configuration for presenting actions at runtime
* Do retries

#implicit list of features (implemented server side)
* filter dupplicates (only show x notifications per time unit)


#Tasks of the host application
##Mandatory
* set Api token (at least once)
* configure presentation of messages in the [notification area](http://developer.android.com/guide/topics/ui/notifiers/notifications.html)
	* configure the icon of the notification



##Optional
* do additional configurations	
    * configure the vibration pattern of all notifications
	* configure the icon of the notification
	* configure the [notification lights](http://developer.android.com/reference/android/app/Notification.Builder.html#setLights(int,%20int,%20int))
* present an action in the host UI of your application. Anything except an [notification](http://developer.android.com/guide/topics/ui/notifiers/notifications.html) in the notification area is considered host UI.


##Advanced features:

###Change Settings in the SDK
####1.Aquire an authToken
**POST** https://connect.sensorberg.com/api/user/login
**Headers:**
Content-Type: application/x-www-form-urlencoded
**Body:**
email=<email>&password=<password>

responseBody.response.authToken -> copy this value

####2. Set your values
**POST** https://connect.sensorberg.com/api/applications/<API-TOKEN>/settings/android/
**Headers:**
Content-Type: application/json
Authorization: <Auth-Token-FromLogin>
**Body:**
{
	"scanner.exitTimeoutMillies" 		:  4001,
	"scanner.foreGroundWaitTime" 	: 1000,
	"scanner.foreGroundScanTime" 	: 10001,
	"scanner.backgroundScanTime" 	: 10001,
	"scanner.backgroundWaitTime" 	: 1000,
	"settings.updateTime" 			: 86400000, 
}

**3. check if it worked**
GET https://connect.sensorberg.com/api/applications/<API-TOKEN>/settings/android/
