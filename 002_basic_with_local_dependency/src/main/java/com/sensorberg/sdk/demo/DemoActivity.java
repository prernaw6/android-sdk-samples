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
        textView.setText("* This example only highlights the integration or the aar artifact in a separate project\n" +
                "     * please don´t actually do this, as you still have to manually add all transitive dependencies\n" +
                "     * of the sdk.\n" +
                "     * \n" +
                "     * use our github maven repository :)");
		setContentView(textView);
	}


}
