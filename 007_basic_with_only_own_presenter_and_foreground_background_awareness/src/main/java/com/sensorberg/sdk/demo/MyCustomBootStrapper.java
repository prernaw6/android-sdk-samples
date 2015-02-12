package com.sensorberg.sdk.demo;

import android.app.Application;
import android.media.MediaPlayer;
import android.util.Log;

import com.sensorberg.sdk.Logger;
import com.sensorberg.sdk.SensorbergService;
import com.sensorberg.sdk.action.InAppAction;
import com.sensorberg.sdk.action.UriMessageAction;
import com.sensorberg.sdk.action.VisitWebsiteAction;
import com.sensorberg.sdk.bootstrapper.SensorbergApplicationBootstrapper;
import com.sensorberg.sdk.demo.demoSeven.R;
import com.sensorberg.sdk.presenter.PresenterConfiguration;
import com.sensorberg.sdk.resolver.BeaconEvent;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class MyCustomBootStrapper extends SensorbergApplicationBootstrapper {

    private static final PresenterConfiguration IRRELEVANT = new PresenterConfiguration(1);
    private static final boolean DELEGATE_EVERYTHING = true;
    private static final String TAG = MyCustomBootStrapper.class.getSimpleName();
    private final String apiToken;

    //flag used to keep track of the state of the app
    private boolean isInForeground = false;

    public MyCustomBootStrapper(Application application, String apiToken) {
        //the presenterconfiguration is irrelevant since we want to delegate all calls to this instance
        super(application, DELEGATE_EVERYTHING);
        this.apiToken = apiToken;
    }

    @Override
    public void presentBeaconEvent(BeaconEvent beaconEvent) {
        if (beaconEvent.getAction() != null){
            if (beaconEvent.getAction().getPayload() != null) {
                try {
                    JSONObject payload = beaconEvent.getAction().getPayloadJSONObject();
                    //example showing custom code based on your payload:
                    if (payload.optBoolean("playSound")) {
                        MediaPlayer player = MediaPlayer.create(context, R.raw.beeping);
                        player.prepare();
                        player.start();
                    }
                } catch (JSONException e) {
                    //the payload was not a JSONObject
                } catch (IOException e) {
                    //the Mediaplayer failed :(
                }
            }
            switch (beaconEvent.getAction().getType()){
                case MESSAGE_URI:
                    UriMessageAction uriAction = (UriMessageAction) beaconEvent.getAction();
                    Log.d("DEMO", "I´m seeing an action for beacon " + beaconEvent.getBeaconId().toTraditionalString()  + "\n"
                            + " the action has the following title: " + uriAction.getTitle() + "\n"
                            + " the action has the following content: " + uriAction.getContent()  + "\n"
                            + " the action has the following uri: " + uriAction.getUri()  + "\n"
                            + " the app is in the " + (isInForeground ? "foreground" : "background") + "so you might want react. If the app is in the background, a android.app.Notification might be good idea."  + "\n");
                    break;
                case MESSAGE_WEBSITE:
                    VisitWebsiteAction visitWebsiteAction = (VisitWebsiteAction) beaconEvent.getAction();
                    Log.d("DEMO", "I´m seeing an action for beacon " + beaconEvent.getBeaconId().toTraditionalString()  + "\n"
                            + " the action has the following subject: " + visitWebsiteAction.getSubject() + "\n"
                            + " the action has the following body: " + visitWebsiteAction.getBody()  + "\n"
                            + " the action has the following uri: " + visitWebsiteAction.getUri()  + "\n"
                            + " the app is in the " + (isInForeground ? "foreground" : "background") + "so you might want react. If the app is in the background, a android.app.Notification might be good idea."  + "\n");
                    break;
                case MESSAGE_IN_APP:
                    InAppAction inAppAction = (InAppAction) beaconEvent.getAction();
                    Log.d("DEMO", "I´m seeing an action for beacon " + beaconEvent.getBeaconId().toTraditionalString()  + "\n"
                            + " the action has the following subject: " + inAppAction.getSubject() + "\n"
                            + " the action has the following body: " + inAppAction.getBody()  + "\n"
                            + " the action has the following uri: " + inAppAction.getUri()  + "\n"
                            + " the app is in the " + (isInForeground ? "foreground" : "background") + "so you might want react. If the app is in the background, a android.app.Notification might be good idea."  + "\n");
                    break;
            }

        }
    }


    //we don´t want to unsubsubscribe from the presentation delegation, so we override this method
    @Override
    public void hostApplicationInBackground() {
        Log.d(TAG, "hostApplicationInBackground");
        isInForeground = false;
        Logger.log.applicationStateChanged("hostApplicationInBackground");
        sendEmptyMessage(SensorbergService.MSG_APPLICATION_IN_BACKGROUND);
    }

    //we only want to tell the SDK scanner to change the scanning cycles
    @Override
    public void hostApplicationInForeground() {
        this.isInForeground = true;
        Log.d(TAG, "hostApplicationInForeground sending to SDK");
        sendEmptyMessage(SensorbergService.MSG_APPLICATION_IN_FOREGROUND);
    }

    //we want to be extra sure and only allow this value to be set to false
    @Override
    public void setPresentationDelegationEnabled(boolean value) {
        if (!value){
            return;
        }
        super.setPresentationDelegationEnabled(value);
    }

    public void connectToService() {
        super.connectToService(apiToken, IRRELEVANT);
    }
}
