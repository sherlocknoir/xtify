package com.ibm.gcm;

import java.util.ArrayList;
import java.util.List;
import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;


public class ChatHeadService extends Service {
private WindowManager windowManager;
private List<View> chatHeads;
private LayoutInflater inflater;
private ImageView chatHead;

@Override
public IBinder onBind(Intent intent) {
return null;
}
@Override
public void onCreate() {
	super.onCreate();
	windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
	inflater = LayoutInflater.from(this);
	chatHeads = new ArrayList<View>();
	
}
@Override
public int onStartCommand(Intent intent, int flags, int startId) {
	chatHead = new ImageView(this);
	
	String image = intent.getStringExtra("image");
    if(image.equals("watson"))
    {
    	chatHead.setImageResource(R.drawable.watson);
    }
    else if(image.equals("bluemix"))
    {
    	chatHead.setImageResource(R.drawable.bluemix);
    }
	
final WindowManager.LayoutParams params = new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT,
WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.TYPE_PHONE, 0, PixelFormat.TRANSLUCENT);
params.gravity = Gravity.CENTER;

chatHead.setOnClickListener(new OnClickListener() {
    @Override
    	public void onClick(View v) {
    		windowManager.removeView(chatHead);
    	}
    });

addChatHead(chatHead, params);
return super.onStartCommand(intent, flags, startId);
}

public void addChatHead(View chatHead, LayoutParams params) {
	chatHeads.add(chatHead);
	windowManager.addView(chatHead, params);
}
public void removeChatHead(View chatHead) {
	chatHeads.remove(chatHead);
	windowManager.removeView(chatHead);
}
@Override
public void onDestroy() {
	super.onDestroy();
	for (View chatHead : chatHeads) {
	removeChatHead(chatHead);
}
}
}