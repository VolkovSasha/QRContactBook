package com.qrcontactbook;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class QRShareActivity extends Activity {
	 
	 @Override
	 public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	 
	  LinearLayout ll = new LinearLayout(this);
	  ll.setOrientation(LinearLayout.VERTICAL);
	 
	  LinearLayout top = new LinearLayout(this);
	  final String shareapp = "market://details?id=com.droidbrew.travelcheap";
	 
	  Button gen = new Button(this);
	  gen.setText("Generate Share App");
	  gen.setLayoutParams(new LinearLayout.LayoutParams(
	    LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, 2.5f));
	  gen.setOnClickListener(new View.OnClickListener() {
	 
	   @Override
	   public void onClick(View v) {
	    new QRShareCoder(QRShareActivity.this).execute(shareapp);
	 
	   }
	  });
	  top.addView(gen);
	 
	  ll.addView(top, LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
	 
	  setContentView(ll);
	 }
	}