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

    @Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
        TextView textView = new TextView(this);
        textView.setText("follow the code intetration of the current release, this sample only highlights that you \n" +
                "can actually unzip the aar, copy the jar file, manually merge the manifest, manually \n" +
                "transitively add all dependencies and run the SDK.\n" +
                "\n" +
                "BUT PLEASE DON`T ACTUALLY DO THIS!!! USE GRADLE!");
		setContentView(textView);
	}
}
