package com.ibm.gcm;

import java.util.Calendar;
import android.app.Activity;
import android.content.Intent;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

public class addCalendar extends BroadcastReceiver {
		 
		private static final Object CALENDAR = "ADD_CALENDAR";
		private static final Object CALL = "CALL";
		
		@Override
		public void onReceive(Context context, Intent intent) {
		   
		   String action = intent.getAction();
		   
		   Log.v("com.ibm.gcm","On Receive");
		   Log.v("com.ibm.gcm","Pressed YES");
		   
		    if(CALENDAR.equals(action)) {
		        
		        Toast.makeText(context, "Calendar Entry created", Toast.LENGTH_LONG).show();
		        
		    }else if(action.startsWith("CALL")) {
		    	
		    	Bundle extras = intent.getExtras();
		    	if (extras == null) {
		    	  return;
		    	}
		    	// get data via the key
		    	String value1 = extras.getString(Intent.EXTRA_TEXT);
		    	if (value1 != null) {
		    		
		    		Toast. makeText(context, "Call created to " + value1, Toast.LENGTH_LONG).show();
			    	Intent call_intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + value1));
			    	call_intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			    	context.startActivity(call_intent);
		    	} 
		    	
		    }else if(action.startsWith("SMS")) {
		    	
		    	Bundle extras = intent.getExtras();
		    	if (extras == null) {
		    	  return;
		    	}
		    	// get data via the key
		    	String value1 = extras.getString(Intent.EXTRA_TEXT);
		    	if (value1 != null) {
		    		
		    		Toast. makeText(context, "SMS created with message: Will call you later", Toast.LENGTH_LONG).show();	
		    		SmsManager smsManager = SmsManager.getDefault();
					smsManager.sendTextMessage(value1, null, "Will call you later", null, null);    		
			    } 	
		   }   
	}
}

