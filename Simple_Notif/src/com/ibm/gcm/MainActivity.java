package com.ibm.gcm;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import com.ibm.rn.RichNotifInboxActivity;
import com.ibm.gcm.R;
import com.xtify.sdk.api.NotificationsPreference;
import com.xtify.sdk.api.XtifyLocation;
import com.xtify.sdk.api.XtifyLocation.LocationUpdateListener;
import com.xtify.sdk.api.XtifySDK;

public class MainActivity extends Activity { 
	//Update Application Key, API Key, Google Sender Id and Welcome Text and Title here.  
	//Update preferences and other strings in the strings.xml file.  
	//Update example push notification copy and HTML and bottom of MainActivity page.
	//Update drawables / logos.
	private static final String XTIFY_APP_KEY = "e9356276-8d91-4dac-8ec6-85f16d0961b7";
	private static final String XTIFY_API_KEY = "9a4db126-6ae4-4f7b-9dc4-4db3e87b9993";
	private static final String SENDER_ID = "338592195819";
	private static final String welcome_text_title = "Welcome to IBM Mobile App";
	private static final String welcome_text_body = "Thank you for downloading the Xtify demonstration application.\n\n"+"This application will demonstrate simple and rich push notifications.\n\n";
	private XtifyLocation xtifyLocation;

	@Override
	protected void onStart() {
		super.onStart(); 
		XtifySDK.start(getApplicationContext(), XTIFY_APP_KEY, SENDER_ID);
		XtifySDK.enableDefaultNotification(getApplicationContext(), false);
		
	} 
 
	public static String getDeviceId(Context context) {
		return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
	}

	@Override 
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);
		//TextView verTextView = (TextView) findViewById(R.id.version_tv);
		//verTextView.setText("SDK version: " + XtifySDK.getSdkVerNumber());
		xtifyLocation = new XtifyLocation(getApplicationContext());
		/*
		 * Customizing the default SDK notification
		 */
		XtifySDK.enableDefaultNotification(getApplicationContext(), false);
		NotificationsPreference.setSoundEnabled(getApplicationContext(), true);
		NotificationsPreference.setSound(getApplicationContext(), R.raw.notification_sound);
		NotificationsPreference.setVibrateEnabled(getApplicationContext(), true);
		long[] vibrate = { 0, 100, 200, 300 };
		NotificationsPreference.setVibrationPattern(getApplicationContext(), vibrate);
		NotificationsPreference.setIcon(getApplicationContext(), R.drawable.ibm_notif_xtify);
		NotificationsPreference.setLightsEnabled(getApplicationContext(), true);
		int ledARGB = 0x00a2ff;
		int ledOnMS = 300;
		int ledOffMS = 1000;
		NotificationsPreference.setLights(getApplicationContext(), new int[] { ledARGB, ledOnMS, ledOffMS }); 
		

	}

	public void displayRNInbox(View view) {
		Intent intent = new Intent(this, RichNotifInboxActivity.class);
		this.startActivity(intent);
	}


	
	public void enableRepetitiveLocUpdate(View view) {
		Toast.makeText(MainActivity.this, "Updating location every 15 min ...", Toast.LENGTH_SHORT).show();
		// Location is disabled by default. Call the following static method to
		// enable repetitive location update.
		XtifyLocation.enableRepetitiveLocUpdate(getApplicationContext());
	}

	public void disableRepetitiveLocUpdate(View view) {
		Toast.makeText(MainActivity.this, "Stopping location update...", Toast.LENGTH_SHORT).show();
		XtifyLocation.disableRepetitiveLocUpdate(getApplicationContext());
	}

	public void disableNotification(View view) {
		Toast.makeText(MainActivity.this, "Notifications Disabled", Toast.LENGTH_SHORT).show();
		XtifySDK.disableNotification(getApplicationContext());
	}

	public void enableNotification(View view) {
		Toast.makeText(MainActivity.this, "Notifications Enabled", Toast.LENGTH_SHORT).show();
		XtifySDK.enableNotification(getApplicationContext());
	}

	public void displayTagScreen(View view) {
		Intent intent = new Intent(getApplicationContext(), TagSettingActivity.class);
		startActivity(intent);
	}

	public void showAppKey(View view) {
		showDialog(XtifySDK.getAppKey(getApplicationContext()));
	}

	public void showXid(View view) {
		String xid = XtifySDK.getXidKey(getApplicationContext());
		String dialogMsg;
		/*
		 * The XID is not available until the device is registered. If you want
		 * to obtain the XID and send it to your server you should do it in
		 * XtifyNotifier onRegistered method.
		 */
		if (xid != null) {
			dialogMsg = xid;
		} else {
			dialogMsg = "Device is not yet registered with Xtify";
		}
		showDialog(dialogMsg);
	}

	public void showDialog(String msg) {
		LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.custom_dialog, (ViewGroup) findViewById(R.id.ll));
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setView(layout);
		builder.setMessage(msg).setCancelable(false)
				.setPositiveButton("Close", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});
		AlertDialog alert = builder.create();
		
		alert.show();
	}
}