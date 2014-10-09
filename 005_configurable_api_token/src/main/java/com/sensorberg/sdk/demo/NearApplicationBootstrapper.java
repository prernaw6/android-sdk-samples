package com.sensorberg.sdk.demo;

import android.app.Application;
import android.content.Context;

import com.sensorberg.sdk.bootstrapper.SensorbergApplicationBootstrapper;
import com.sensorberg.sdk.presenter.PresenterConfiguration;

public class NearApplicationBootstrapper extends SensorbergApplicationBootstrapper {

    private final PresenterConfiguration presenterConfiguration;
    private final String apiKey;

    public NearApplicationBootstrapper(Application application, String apiKey, boolean foreGroundNotifications, PresenterConfiguration presenterConfiguration) {
        super(application, foreGroundNotifications);
        this.presenterConfiguration = presenterConfiguration;
        this.apiKey = apiKey;
    }

    public void enableService(Context context){
        enableService(context, apiKey, presenterConfiguration);
    }

    @Override
    public void disableServiceCompletely(Context context) {
        super.disableServiceCompletely(context);
    }

    public void connectToService() {
        connectToService(apiKey, presenterConfiguration);
    }
}
