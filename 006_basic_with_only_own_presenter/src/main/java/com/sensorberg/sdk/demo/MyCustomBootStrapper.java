package com.sensorberg.sdk.demo;

import android.app.Application;
import android.util.Log;

import com.sensorberg.sdk.action.UriMessageAction;
import com.sensorberg.sdk.bootstrapper.SensorbergApplicationBootstrapper;
import com.sensorberg.sdk.presenter.PresenterConfiguration;
import com.sensorberg.sdk.resolver.BeaconEvent;

public class MyCustomBootStrapper extends SensorbergApplicationBootstrapper {

    public MyCustomBootStrapper(Application application, String apiToken) {
        //the presenterconfiguration is irrelevant since we want to delegate all calls to this instance
        super(application, apiToken, true, new PresenterConfiguration(1));
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
                            + " the action has the following uri: " + uriAction.getUri()  + "\n");
                    break;
            }

        }
    }
}
