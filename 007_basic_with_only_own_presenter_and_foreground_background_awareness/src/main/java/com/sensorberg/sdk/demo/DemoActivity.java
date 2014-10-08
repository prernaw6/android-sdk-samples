package com.sensorberg.sdk.demo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.sensorberg.sdk.exception.SdkException;
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
        textView.setText("all events are sent to the logcat using code ourside of the SDK, the scan times are also changed if the app is in the background...");
	}
}
