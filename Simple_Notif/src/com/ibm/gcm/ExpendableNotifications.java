package com.ibm.gcm;

import android.app.DialogFragment;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;

import android.content.Intent;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.app.NotificationManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.xtify.sdk.Constants;
import com.xtify.sdk.NotifActionReceiver;
import com.xtify.sdk.api.NotificationsPreference;

import java.io.IOException;
import java.net.URL;
import java.util.UUID;

/**
 * User: feras
 * Date: 6/4/14
 * Time: 6:40 PM
 */
public class ExpendableNotifications {
    private static final String NOTIFICATION_TITLE = "com.xtify.sdk.NOTIFICATION_TITLE";
    private static final String NOTIFICATION_CONTENT = "com.xtify.sdk.NOTIFICATION_CONTENT";

    private static final String NOTIFICATION_STYLE = "data.com.xtify.notification.STYLE";
    private enum NotificationStyle {BIG_PICTURE, BIG_TEXT, LinkedIN, RDV, Call,ChatHead,Missile}
    private static final String NOTIFICATION_BIG_PICTURE = "data.com.xtify.notification.BIG_PICTURE";
    private static final String NOTIFICATION_LARGE_ICON = "data.com.xtify.notification.LARGE_ICON";
    private static final String NOTIFICATION_BIG_TEXT = "data.com.xtify.notification.BIG_TEXT";
    private static final String NOTIFICATION_LinkedIN = "data.com.xtify.notification.LinkedIN";
    private static final String NOTIFICATION_RDV = "data.com.xtify.notification.RDV";
    private static final String NOTIFICATION_Call = "data.com.xtify.notification.Call";
    private static final String NOTIFICATION_ACCOUNT = "data.com.xtify.notification.ACCOUNT";
    private static final String NOTIFICATION_CALLNUMBER = "data.com.xtify.notification.CALLNUMBER";
    private static final String NOTIFICATION_IMAGE = "data.com.xtify.notification.IMAGE";
	private static String ADD_ACTION = "";

    public static void processExtras(final Context context, final Bundle msgExtras){
        new Thread(new Runnable() {
            @Override
            public void run() {
            	String notificationStyle = "";
         notificationStyle = msgExtras.getString(NOTIFICATION_STYLE);
        String TAG = XtifyNotifier.class.getName();
        Log.i(TAG, "-- Notification STYLE: " + notificationStyle);
        
        
        Notification notification = null;
        if(!notificationStyle.equals("")){
        if(notificationStyle.equals(NotificationStyle.BIG_PICTURE.name())){
        	Log.i(TAG, "--IF ENTERED BIG_PICTURE ");
            notification =  createBigPictureNotification(context, msgExtras);
        }else if(notificationStyle.equals(NotificationStyle.BIG_TEXT.name())){
        	Log.i(TAG, "--IF ENTERED BIG_TEXT ");
           notification =  createBigTextNotification(context, msgExtras);
        }else if(notificationStyle.equals(NotificationStyle.LinkedIN.name())){
        	Log.i(TAG, "--IF ENTERED LinkedIN ");
        	notification = createLinkedInNotification(context, msgExtras);
        	
        }else if(notificationStyle.equals(NotificationStyle.RDV.name())){
        	Log.i(TAG, "--IF ENTERED RDV ");
        	notification = createRDVNotification(context, msgExtras);
        	
        }else if(notificationStyle.equals(NotificationStyle.Call.name())){
        	Log.i(TAG, "--IF ENTERED Call ");
        	notification = createCallNotification(context, msgExtras);
        	
        }else if(notificationStyle.equals(NotificationStyle.ChatHead.name())){
        	Log.i(TAG, "--IF ENTERED ChatHead ");
        	createCHNotification(context, msgExtras);
        	
        }
        else if(notificationStyle.equals(NotificationStyle.Missile.name())){
        	Log.i(TAG, "--IF ENTERED Missile ");
        	createMissileNotification(context, msgExtras);
        	
        }
        }
        if(notification == null){
            return;
        }


        NotificationManager NotificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationManager.notify(0, notification);
            }
        }).start();
        
    }


private static void createMissileNotification(Context context, Bundle msgExtras){
    	
    	String TAG = XtifyNotifier.class.getName();
    	Log.i(TAG, "-- CREATE NOTIF Missile : ");    	
        
    	
    }
    
private static void createCHNotification(Context context, Bundle msgExtras){
    	
    	String TAG = XtifyNotifier.class.getName();
    	Log.i(TAG, "-- CREATE NOTIF ChatHead : ");    	
        int icon3 = R.drawable.ofn;

        Intent CH_intent = new Intent(context, ChatHeadService.class);
		CH_intent.putExtra("image", msgExtras.getString(NOTIFICATION_IMAGE));
		context.startService(CH_intent);   
    }
    
    private static Notification createCallNotification(Context context, Bundle msgExtras){
    	
    	String TAG = XtifyNotifier.class.getName();
    	Log.i(TAG, "-- CREATE NOTIF CALL : " + msgExtras.getString(NOTIFICATION_CALLNUMBER));
    	
        int icon3 = R.drawable.ofn;
 
      
        Intent call_intent = new Intent("CALL");
        call_intent.putExtra(android.content.Intent.EXTRA_TEXT, msgExtras.getString(NOTIFICATION_CALLNUMBER));
        PendingIntent pendingIntentCall = PendingIntent.getBroadcast(context, 0, call_intent, 0);
        
        Intent sms_intent = new Intent("SMS");
        sms_intent.putExtra(android.content.Intent.EXTRA_TEXT, msgExtras.getString(NOTIFICATION_CALLNUMBER));
        PendingIntent pendingIntentSMS = PendingIntent.getBroadcast(context, 0, sms_intent, 0);
       
        return  new NotificationCompat.Builder(context)
                        .setContentTitle(msgExtras.getString(NOTIFICATION_TITLE))
                        .setContentText(msgExtras.getString(NOTIFICATION_ACCOUNT) + " " + msgExtras.getString(NOTIFICATION_CALLNUMBER))
                        .setSmallIcon(NotificationsPreference.getIcon(context))
                        .setAutoCancel(true)
                        .addAction(R.drawable.phone, "Call", pendingIntentCall)
                        .addAction(R.drawable.sms, "SMS", pendingIntentSMS)
                        .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                        .setSmallIcon(icon3)
                        .build();   
    }
    
    private static Notification createRDVNotification(Context context, Bundle msgExtras){
    	
    	String TAG = XtifyNotifier.class.getName();
    	Log.i(TAG, "-- CREATE NOTIF RDV");
    	
        int icon3 = R.drawable.ofn;
 
      //Yes intent
        ADD_ACTION = "ADD_CALENDAR";
        PendingIntent pendingIntentYes = PendingIntent.getBroadcast(context, 0, new Intent(ADD_ACTION), 0);
       
        return  new NotificationCompat.Builder(context)
                        .setContentTitle(msgExtras.getString(NOTIFICATION_TITLE))
                        .setContentText(msgExtras.getString(NOTIFICATION_ACCOUNT))
                        .setSmallIcon(NotificationsPreference.getIcon(context))
                        .setAutoCancel(true)
                        .addAction(R.drawable.calendar_v, "Add to your calendar", pendingIntentYes)
                        .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                        .setSmallIcon(icon3)
                        .build();   
    }
    
    private static Notification createLinkedInNotification(Context context, Bundle msgExtras){
    	
    	String TAG = XtifyNotifier.class.getName();
    	Log.i(TAG, "-- CREATE NOTIF ");
    	
        int icon = R.drawable.accept;
        int icon2 = R.drawable.reject;
        int icon3 = R.drawable.ofn;
 
        
        return  new NotificationCompat.Builder(context)
                        .setContentTitle(msgExtras.getString(NOTIFICATION_TITLE))
                        .setContentText(msgExtras.getString(NOTIFICATION_ACCOUNT))
                        .setSmallIcon(NotificationsPreference.getIcon(context))
                        .setAutoCancel(true)
                        .addAction(icon, "Accept", getPendingIntent(context, msgExtras))
                        .addAction(icon2, "Reject", getPendingIntent(context, msgExtras))
                        .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                        .setSmallIcon(icon3)
                        .build();
        
        
    }
    
    
    private static Notification createBigPictureNotification(Context context, Bundle msgExtras){

        if(!msgExtras.containsKey(NOTIFICATION_BIG_PICTURE)){
            return null;
        }

        Bitmap largeIcon = null;
        Bitmap bigPicture = null;
        try {
            largeIcon = BitmapFactory.decodeStream(new URL(msgExtras.getString(NOTIFICATION_LARGE_ICON)).openConnection().getInputStream());
            bigPicture = BitmapFactory.decodeStream(new URL(msgExtras.getString(NOTIFICATION_BIG_PICTURE)).openConnection().getInputStream());
        } catch (IOException e) {
            // no-op
        }
        if(largeIcon == null || bigPicture == null){
            return null;
        }

        return new NotificationCompat.BigPictureStyle(
                new NotificationCompat.Builder(context)
                        .setContentTitle(msgExtras.getString(NOTIFICATION_TITLE))
                        .setContentText(msgExtras.getString(NOTIFICATION_CONTENT))
                        .setSmallIcon(NotificationsPreference.getIcon(context))
                        .setAutoCancel(true)
                        .setContentIntent(getPendingIntent(context, msgExtras))
                        .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                        .setLargeIcon(largeIcon))
                .bigPicture(bigPicture)
                .build();
    }

    private static Notification createBigTextNotification(Context context, Bundle msgExtras){

        if(!msgExtras.containsKey(NOTIFICATION_BIG_TEXT)){
            return null;
        }

        Bitmap largeIcon = null;
        try {
            largeIcon = BitmapFactory.decodeStream(new URL(msgExtras.getString(NOTIFICATION_LARGE_ICON)).openConnection().getInputStream());
        } catch (IOException e) {
            // no-op
        }
        if(largeIcon == null){
            return null;
        }

        return new NotificationCompat.BigTextStyle(
                new NotificationCompat.Builder(context)
                        .setContentTitle(msgExtras.getString(NOTIFICATION_TITLE))
                        .setContentText(msgExtras.getString(NOTIFICATION_CONTENT))
                        .setSmallIcon(NotificationsPreference.getIcon(context))
                        .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                        .setAutoCancel(true)
                        .setContentIntent(getPendingIntent(context, msgExtras))
                        .setLargeIcon(largeIcon))
                .bigText(msgExtras.getString(NOTIFICATION_BIG_TEXT))
                .build();
    }

    private static PendingIntent getPendingIntent(Context context, Bundle msgExtras){
        Intent actionIntent = new Intent(context, NotifActionReceiver.class);
        actionIntent.putExtras(msgExtras);
        actionIntent.putExtra(Constants.Extra.NOTIF_ACTION_FLAGS, 0);
//        actionIntent.putExtra(Constants.Extra.NOTIF_ACTION_CLASS, (Class) null);
        UUID notifUUID = UUID.randomUUID();
        return PendingIntent.getBroadcast(context, notifUUID.hashCode(), actionIntent, 0);
    }
    
    
      

}
