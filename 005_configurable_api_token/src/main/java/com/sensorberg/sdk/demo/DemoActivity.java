package com.sensorberg.sdk.demo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
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


    SharedPreferencesHelper sharedPreferencesHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_demo);

        AsyncHttpClient client = new AsyncHttpClient();

        ButterKnife.inject(this);
        sharedPreferencesHelper = DemoApplication.getInstance().helper;


        apiKeyEditText.setText(sharedPreferencesHelper.getAPIKey());

        if (!BuildConfig.BUILD_TYPE.equalsIgnoreCase("playStore")) {
            versionTextView.setVisibility(View.VISIBLE);
            versionTextView.setText("app:" + BuildConfig.PACKAGE_NAME + ":" + BuildConfig.VERSION_CODE + ":" + BuildConfig.VERSION_NAME);
        }
        sdkVersionTextView.setText("sdk: " + com.sensorberg.sdk.BuildConfig.VERSION_NAME + "-SNAPSHOT");

        enableforegroundNotificationsCheckBox.setChecked(sharedPreferencesHelper.foreGroundNotificationsEnabled());
        enableVibrationOnNotificationsCheckBox.setChecked(sharedPreferencesHelper.vibrationOnNotificationsEnabled());

    }

    @OnCheckedChanged(R.id.enableforegroundNotificationsCheckBox)
    void setEnableforegroundNotificationsCheckBoxChecked(boolean value){
        sharedPreferencesHelper.setForegroundNotificationEnabled(value);
        DemoApplication.getInstance().boot.setPresentationDelegationEnabled(value);
    }

    @OnCheckedChanged(R.id.enableVibratonOnNotificationsCheckBox)
    void setEnableVibrateOnNotificationsCheckBoxChecked(boolean value){
        sharedPreferencesHelper.setEnableVibrationOnNotifications(value);
        PresenterConfiguration presenterConfiguration = new PresenterConfiguration(R.drawable.ic_launcher);
        if (value) {
            presenterConfiguration.setVibrationPattern(DemoApplication.VIBRATION_PATTERN);
        }
        DemoApplication.getInstance().boot.updatePresenterConfiguration(presenterConfiguration);
    }

    @OnCheckedChanged(R.id.verboseADBLogging)
    void verboseADBLoggingEnabled(boolean value){
        if (value) {
            Logger.enableVerboseLogging();
        } else {
            Logger.log = Logger.QUIET_LOG;
        }
    }

    @OnClick(R.id.apply_api_token_button)
    void applyApiToken() {
        String newApiToken = apiKeyEditText.getText().toString();
        sharedPreferencesHelper.setAPIKey(newApiToken);

        DemoApplication.getInstance().boot.changeAPIToken(newApiToken);
        Toast.makeText(this, "api token applied", Toast.LENGTH_SHORT).show();

    }

    @OnClick(R.id.testNotificatinButton)
    void testnotification(){
        BeaconEvent beaconEvent = new BeaconEvent.Builder()
                .withAction(new UriMessageAction("hello", "world", "http://hel.lo"))
                .build();
        startActivity(ActionActivity.intentFor(this, beaconEvent));
    }

    @OnClick(R.id.scan_qr_code_button)
    void scanQRCode() {
        startActivityForResult(new Intent(this, ScannerActivity.class), ScannerActivity.SCAN_QR);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case ScannerActivity.SCAN_QR: {
                if (resultCode == RESULT_OK) {
                    String text = data.getStringExtra(ScannerActivity.TEXT);
                    if (text != null && text.length() == 64) {
                        setApiToken(text);
                    } else {
                        Toast.makeText(this, "QR code was not an API token", Toast.LENGTH_LONG).show();
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
}
