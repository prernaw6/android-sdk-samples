package com.sensorberg.sdk.demo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.sensorberg.near.BaseActivity;
import com.sensorberg.near.BuildConfig;
import com.sensorberg.near.R;
import com.sensorberg.sdk.Logger;
import com.sensorberg.sdk.action.UriMessageAction;
import com.sensorberg.sdk.bootstrapper.ActionActivity;
import com.sensorberg.sdk.near.SharedPreferencesHelper;
import com.sensorberg.sdk.presenter.PresenterConfiguration;
import com.sensorberg.sdk.resolver.BeaconEvent;

import net.hockeyapp.android.CrashManager;
import net.hockeyapp.android.UpdateManager;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class DemoActivity extends BaseActivity {
    private static final String TAG = DemoActivity.class.getSimpleName();

    @InjectView(R.id.apiKey_editText)
    EditText apiKeyEditText;

    @InjectView(R.id.version_textField)
    TextView versionTextView;

    @InjectView(R.id.sdk_version_textField)
    TextView sdkVersionTextView;

    @InjectView(R.id.enableforegroundNotificationsCheckBox)
    CheckBox enableforegroundNotificationsCheckBox;

    @InjectView(R.id.enableVibratonOnNotificationsCheckBox)
    CheckBox enableVibrationOnNotificationsCheckBox;

    @InjectView(R.id.enableLEDOnNotificationsCheckBox)
    CheckBox enableLEDOnNotificationsCheckBox;


    SharedPreferencesHelper sharedPreferencesHelper;

    @InjectView(R.id.disableServiceSwitch)
    Switch disableServiceSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_demo);

        ButterKnife.inject(this);
        sharedPreferencesHelper = DemoApplication.getInstance().helper;


        apiKeyEditText.setText(sharedPreferencesHelper.getAPIKey());

        if (!BuildConfig.BUILD_TYPE.equalsIgnoreCase("playStore")) {
            versionTextView.setVisibility(View.VISIBLE);
            versionTextView.setText("app:" + BuildConfig.PACKAGE_NAME + ":" + BuildConfig.VERSION_CODE + ":" + BuildConfig.VERSION_NAME);
        }
        sdkVersionTextView.setText("sdk: " + com.sensorberg.sdk.BuildConfig.VERSION_NAME + " bootstrapper:" + com.sensorberg.sdk.bootstrapper.BuildConfig.VERSION_NAME);

        enableforegroundNotificationsCheckBox.setChecked(sharedPreferencesHelper.foreGroundNotificationsEnabled());
        enableVibrationOnNotificationsCheckBox.setChecked(sharedPreferencesHelper.vibrationOnNotificationsEnabled());
        enableLEDOnNotificationsCheckBox.setChecked(sharedPreferencesHelper.ledOnNotificationsEnabled());

        disableServiceSwitch.setChecked(!sharedPreferencesHelper.isServiceDisabled());
        disableServiceSwitchCheckedChanged(disableServiceSwitch.isChecked());
    }

    @OnCheckedChanged(R.id.enableforegroundNotificationsCheckBox)
    void setEnableforegroundNotificationsCheckBoxChecked(boolean value){
        sharedPreferencesHelper.setForegroundNotificationEnabled(value);
        DemoApplication.getInstance().boot.setPresentationDelegationEnabled(value);
    }

    @OnCheckedChanged(R.id.enableLEDOnNotificationsCheckBox)
    void enableLEDOnNotificationsCheckBoxChecked(boolean value){
        PresenterConfiguration presenterConfiguration = DemoApplication.getInstance().boot.presenterConfiguration;
        if (value) {
            presenterConfiguration.setNotificationLightsConfiguration(DemoApplication.LIGHTS_PATTERN);
        }
        else{
            presenterConfiguration.setNotificationLightsConfiguration(null);
        }
        DemoApplication.getInstance().boot.updatePresenterConfiguration(presenterConfiguration);
        sharedPreferencesHelper.setLedOnNotificationsEnabled(value);
    }

    @OnCheckedChanged(R.id.enableVibratonOnNotificationsCheckBox)
    void setEnableVibrateOnNotificationsCheckBoxChecked(boolean value){
        sharedPreferencesHelper.setEnableVibrationOnNotifications(value);
        PresenterConfiguration presenterConfiguration = DemoApplication.getInstance().boot.presenterConfiguration;
        if (value) {
            presenterConfiguration.setVibrationPattern(DemoApplication.VIBRATION_PATTERN);
        }
        else{
            presenterConfiguration.setVibrationPattern(new long[]{});
        }
        DemoApplication.getInstance().boot.updatePresenterConfiguration(presenterConfiguration);
    }

    @OnClick(R.id.startServiceForTheFirstTime)
    void startServiceForTheFirstTime(){
        DemoApplication.getInstance().startSensorbergServiceForFirstTime();
        disableServiceSwitch.setChecked(true);
    }

    @OnCheckedChanged(R.id.verboseADBLogging)
    void verboseADBLoggingEnabled(boolean value){
        if (value) {
            Logger.enableVerboseLogging();
        } else {
            Logger.log = Logger.QUIET_LOG;
        }
    }

    @OnCheckedChanged(R.id.disableServiceSwitch)
    void disableServiceSwitchCheckedChanged(boolean checked) {
        if (checked) {
            enableService();
        } else {
            disableService();
        }
        View[] views = new View[]{apiKeyEditText, versionTextView, sdkVersionTextView, enableforegroundNotificationsCheckBox, enableVibrationOnNotificationsCheckBox,
                findViewById(R.id.apply_api_token_button),
                findViewById(R.id.apply_demo_token_button),
                findViewById(R.id.scan_qr_code_button),
                findViewById(R.id.testNotificatinButton)};

        for (View view : views) {
            view.setEnabled(checked);
        }
    }

    @OnClick(R.id.apply_demo_token_button)
    void applyDemoToken(){
        setApiToken(SharedPreferencesHelper.DEMO_ACCOUNT_API_KEY);
        applyApiToken();
    }

    @OnClick(R.id.apply_api_token_button)
    void applyApiToken() {
        String newApiToken = apiKeyEditText.getText().toString();
        sharedPreferencesHelper.setAPIKey(newApiToken);

        DemoApplication.getInstance().boot.changeAPIToken(newApiToken);
        Crouton.showText(this, "api token applied", Style.INFO);
    }

    @OnClick(R.id.testNotificatinButton)
    void testnotification(){
        BeaconEvent beaconEvent = new BeaconEvent.Builder()
                .withAction(new UriMessageAction("hello", "world", "http://hel.lo", 0L))
                .build();
        startActivity(ActionActivity.intentFor(this, beaconEvent));
    }

    @OnClick(R.id.scan_qr_code_button)
    void scanQRCode() {
        startActivityForResult(new Intent(this, ScannerActivity.class), ScannerActivity.SCAN_QR);
    }

    @OnClick(R.id.enableServiceButton)
    void enableService(){
        sharedPreferencesHelper.setServiceDisabled(false);
        DemoApplication.getInstance().boot.enableService(getApplicationContext());
    }

    @OnClick(R.id.disableServiceButton)
    void disableService(){
        sharedPreferencesHelper.setServiceDisabled(true);
        DemoApplication.getInstance().boot.disableServiceCompletely(getApplicationContext());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case ScannerActivity.SCAN_QR: {
                if (resultCode == RESULT_OK) {
                    String text = data.getStringExtra(ScannerActivity.TEXT);
                    if (text != null && text.length() == 64) {
                        setApiToken(text);
                        Crouton.showText(this, "Scanned sucessfully. Now apply the token", Style.INFO);
                    } else {
                        Crouton.showText(this, "QR code was not an API token", Style.ALERT);
                    }
                }
                break;
            }
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }

    private void setApiToken(String text) {
        sharedPreferencesHelper.setAPIKey(text);
        apiKeyEditText.setText(text);
    }

    @Override
    protected void onResume() {
        super.onResume();

        checkForCrashes();
        checkForUpdates();
    }

    private void checkForCrashes() {
        if (!BuildConfig.DEBUG) {
            Log.d(TAG, "registering the Hockeyapp CrashManager");
            CrashManager.register(this, BuildConfig.HOCKEYAPP_APP_TOKEN);

        } else {
            Log.d(TAG, "CrashManager not active");
        }
    }

    private void checkForUpdates() {
        if (!BuildConfig.DEBUG) {
            Log.d(TAG, "registering the UpdateManager");
            UpdateManager.register(this, BuildConfig.HOCKEYAPP_APP_TOKEN);
        } else {
            Log.d(TAG, "UpdateManager not active");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_show_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_settings: {
                startActivity(new Intent(this, ShowSettingsActivity.class));
                return true;
            }
            case R.id.action_show_more: {
                findViewById(R.id.serviceButtons).setVisibility(View.VISIBLE);
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

}
