package com.qrcontactbook;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ContactActivity extends Activity {
	
	private long contact_id = 0;
	private String contact_name = "";
	
	 @Override
		public void onCreate(Bundle savedInstanceState) {
		
	    	super.onCreate(savedInstanceState);
		    setContentView(R.layout.contact_activity);
		    
		    Intent intent = this.getIntent();
		    contact_id = intent.getLongExtra("contact_id", 0);
		    contact_name = intent.getStringExtra("contact_name");
		    
		    setData();
		   
		 }
	 
	 private void setData() {
		    
		 TextView nameView = (TextView)findViewById(R.id.nameTextView);
		 nameView.setText(contact_name);
		 
		 Cursor cur = this.getContentResolver().query(
				 ContactsContract.CommonDataKinds.Phone.CONTENT_URI, 
				 null, 
				 ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + contact_id, 
				 null, null);
				 
		 if(cur.getCount() > 0) {
			 
		 } else {
			 Log.e("ContactActivity", "Sasay lalka");
		 }
		 
		 
		 
		 
		 
		 ListView lv = (ListView)findViewById(R.id.listViewEditContact);
		    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,	
					 R.layout.contact_activity_list_item, R.id.label, new String[] {});
		   lv.setAdapter(adapter);
		 
	 }
	 
	 public void onEditContactClick(View view){
		 Intent intent = new Intent(this, EditContactActivity.class);
		 this.startActivity(intent);
	 }
	 
}