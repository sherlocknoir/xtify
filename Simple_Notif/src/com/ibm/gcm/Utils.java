package com.ibm.gcm;

import com.ibm.gcm.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class Utils extends Activity {

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.utils);
        TextView title= (TextView) findViewById(R.id.title);
        TextView message= (TextView) findViewById(R.id.message);

        title.setText(getIntent().getExtras().getString("title"));
        message.setText(getIntent().getExtras().getString("message"));


    }
}
