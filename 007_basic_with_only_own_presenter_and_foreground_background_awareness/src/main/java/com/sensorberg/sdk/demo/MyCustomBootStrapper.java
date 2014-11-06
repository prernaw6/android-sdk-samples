package com.sensorberg.sdk.demo;

import android.app.Application;
import android.util.Log;

import com.sensorberg.sdk.Logger;
import com.sensorberg.sdk.SensorbergService;
import com.sensorberg.sdk.action.UriMessageAction;
import com.sensorberg.sdk.bootstrapper.SensorbergApplicationBootstrapper;
import com.sensorberg.sdk.presenter.PresenterConfiguration;
import com.sensorberg.sdk.resolver.BeaconEvent;

public class MyCustomBootStrapper extends SensorbergApplicationBootstrapper {

    private static final boolean DELEGATE_EVERYTHING = true;
    private static final PresenterConfiguration IRRELEVANT = new PresenterConfiguration(1);
    private final String apiToken;

    //flag used to keep track of the state of the app
    private boolean isInForeground = false;

    public MyCustomBootStrapper(Application application, String apiToken) {
        //the presenterconfiguration is irrelevant since we want to delegate all calls to this instance
        super(application, DELEGATE_EVERYTHING);
        this.apiToken = apiToken;
    }

    public void connectToService() {
        super.connectToService(apiToken, IRRELEVANT);
    }

    @Override
    public void presentBeaconEvent(BeaconEvent beaconEvent) {
        if (beaconEvent.getAction() != null){
            switch (beaconEvent.getAction().getType()){
                case MESSAGE_URI:
                    UriMessageAction uriAction = (UriMessageAction) beaconEvent.getAction();
                    Log.d("DEMO", "I´m seeing an action for beacon " + beaconEvent.getBeaconId().toTraditionalString()  + "\n"
                            + " the action has the following title: " + uriAction.getTitle() + "\n"
                            + " the action has the following content: " + uriAction.getContent()  + "\n"
                            + " the action has the following uri: " + uriAction.getUri()  + "\n"
                            + " the app is in the " + (isInForeground ? "foreground" : "background") + "so you might want react. If the app is in the background, a android.app.Notification might be good idea."  + "\n");
                    break;
            }

        }
    }


    //we don´t want to unsubsubscribe from the presentation delegation, so we override this method
    @Override
    public void hostApplicationInBackground() {
        isInForeground = false;
        Logger.log.applicationStateChanged("hostApplicationInBackground");
        if (serviceMessenger != null){
            sendEmptyMessage(SensorbergService.MSG_APPLICATION_IN_BACKGROUND);
        }
    }

    @Override
    public void hostApplicationInForeground() {
        this.isInForeground = true;
        super.hostApplicationInForeground();
    }
}
