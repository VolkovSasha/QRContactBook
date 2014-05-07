package com.qrcontactbook;

import com.qrcontactbook.adapter.ContactDataAdapter;

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
	
	private static final String TAG = "com.qrcontactbook.ContactActivity";
	private long contact_id = 0;
	private String contact_name = "";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contact_activity);
		
		Intent intent = this.getIntent();
		contact_id = intent.getLongExtra("contact_id", 0);
		contact_name = intent.getStringExtra("contact_name");
		
		if(intent.getStringExtra("contact_type").equals("phone_contact"))
			setDataFromPhone();
		else
			setDataFromBase();
	}
	
	private void setDataFromBase() {
		
	}
	 
	private void setDataFromPhone() {

		((TextView)findViewById(R.id.nameTextView)).setText(contact_name);
		((TextView)findViewById(R.id.grouptextView)).setText(
				((ContactApp)this.getApplication()).getContactDataManager()
				.getPhoneGroupName(((ContactApp)this.getApplication())
						.getContactDataManager().getPhoneGroupId(contact_id, this), this));
		
		ListView lv = (ListView)findViewById(R.id.listViewEditContact);
		ContactDataAdapter adapter = new ContactDataAdapter(this);
		adapter.setData(((ContactApp)this.getApplication())
				.getContactDataManager().getPhoneContactData(contact_id, this));
		lv.setAdapter(adapter);
	}
	 
	
	public void onEditContactClick(View view){
		Intent intent = new Intent(this, EditContactActivity.class);
		this.startActivity(intent);
	}
	 
}