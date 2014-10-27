package com.sensorberg.sdk.demo;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.widget.TextView;

import com.sensorberg.near.R;
import com.sensorberg.sdk.internal.AndroidPlattform;
import com.sensorberg.sdk.internal.Plattform;
import com.sensorberg.sdk.settings.Settings;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class ShowSettingsActivity extends Activity {

    @InjectView(R.id.settings_textView)
    TextView settingsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_settings);

        ButterKnife.inject(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateView();
    }

    @OnClick(R.id.settings_updateView_Button)
    void updateView() {
        Crouton.showText(this, "Settings should be displayed now.", Style.CONFIRM);
        Plattform platform = new AndroidPlattform(this);
        Settings settings = new Settings(platform, platform.getSettingsSharedPrefs());

        Date date = new Date(System.currentTimeMillis());
        DateFormat dateFormat = android.text.format.DateFormat.getTimeFormat(getApplicationContext());


        StringBuilder builder = new StringBuilder("last update at: " + dateFormat.format(date)).append('\n');
        builder.append("getBackgroundScanTime: ").append(settings.getBackgroundScanTime()).append('\n');
        builder.append("getBackgroundWaitTime: ").append(settings.getBackgroundWaitTime()).append('\n');
        builder.append("getForeGroundScanTime: ").append(settings.getForeGroundScanTime()).append('\n');
        builder.append("getForeGroundWaitTime: ").append(settings.getForeGroundWaitTime()).append('\n');
        builder.append("getExitTimeout: ").append(settings.getExitTimeout()).append('\n');
        builder.append("getCleanBeaconMapRestartTimeout: ").append(settings.getCleanBeaconMapRestartTimeout()).append('\n');
        builder.append("getUpdateInterval:").append(settings.getUpdateInterval()).append('\n');

        settingsTextView.setText(builder.toString());
    }
}
