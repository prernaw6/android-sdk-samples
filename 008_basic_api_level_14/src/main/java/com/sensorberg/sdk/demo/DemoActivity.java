package com.sensorberg.sdk.demo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.sensorberg.sdk.exception.SdkException;
import com.sensorberg.sdk.internal.AndroidPlattform;
import com.sensorberg.sdk.resolver.Resolver;
import com.sensorberg.sdk.scanner.Scanner;

@SuppressWarnings("javadoc")
public class DemoActivity extends Activity
{
	private TextView textView;

    @Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
        textView = new TextView(this);
		setContentView(textView);

        if (!DemoApplication.instance.plattform.isBluetoothLowEnergySupported()){
            textView.setText("BTLE is not supported on this device");
        }
	}
}
