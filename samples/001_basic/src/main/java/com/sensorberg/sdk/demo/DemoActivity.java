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
	Scanner		scanner		= Scanner.getInstance();
	Resolver	resolver	= Resolver.getInstance();
    private TextView textView;

    @Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
        textView = new TextView(this);
		setContentView(textView);
	}

	@Override
	protected void onResume()
	{
		super.onResume();


        //You might want to inform the user, that btle is not supported on this device and all cool iBeacon features will not be accessible.
		if(!scanner.isBluetoothSupported()) {
            textView.setText("BTLE not supported");
            Toast.makeText(this, "BTLE not supported.", Toast.LENGTH_LONG).show();
		}
        else if(!scanner.isBluetoothActive()){
            textView.setText("bluetooth is not active on this device");
            try {
                scanner.requestBluetoothActivation(this);
            } catch (SdkException e) {
                //we ignore that btle might not be supported by this device
            }
        }
	}
}
