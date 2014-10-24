package com.sensorberg.sdk.demo;

import android.app.Application;
import android.util.Log;

import com.sensorberg.sdk.action.UriMessageAction;
import com.sensorberg.sdk.bootstrapper.SensorbergApplicationBootstrapper;
import com.sensorberg.sdk.presenter.PresenterConfiguration;
import com.sensorberg.sdk.resolver.BeaconEvent;

public class MyCustomBootStrapper extends SensorbergApplicationBootstrapper {

    private static final boolean DELEGATE_EVERYTHING = true;
    private static final PresenterConfiguration IRRELEVANT = new PresenterConfiguration(1);

    public MyCustomBootStrapper(Application application) {
        super(application, true);
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

    public void connectToService(String s) {
        //the presenterconfiguration is irrelevant since we want to delegate all calls to this instance
        super.connectToService(s, IRRELEVANT);
    }
}
