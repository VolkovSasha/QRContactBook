package com.qrcontactbook;

import java.sql.SQLException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.qrcontactbook.adapter.ContactDataAdapter;

public class ContactActivity extends Activity {
	
	private static final String TAG = "com.qrcontactbook.ContactActivity";
	private long contact_id = 0;
	private String contact_name = "";
	private String contact_type = "";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contact_activity);
		
		Intent intent = this.getIntent();
		contact_id = intent.getLongExtra("contact_id", 0);
		contact_name = intent.getStringExtra("contact_name");
		contact_type = intent.getStringExtra("contact_type");
		
		if(contact_type.equals("phone_contact"))
			setDataFromPhone();
		else
			setDataFromBase();
	}
	
	private void setDataFromBase() {
		
		((TextView)findViewById(R.id.nameTextView)).setText(contact_name);
		try {
			((TextView)findViewById(R.id.grouptextView)).setText(
					((ContactApp)this.getApplication()).getContactDataManager()
					.getBaseContactGroup(contact_id));
			
			ListView lv = (ListView)findViewById(R.id.listViewEditContact);
			ContactDataAdapter adapter = new ContactDataAdapter(this);
			adapter.setData(((ContactApp)this.getApplication())
					.getContactDataManager().getBaseContactData(contact_id));
			lv.setAdapter(adapter);
			
		} catch(SQLException ex) {
			Log.e(TAG, ex.getMessage());
		}
	}
	 
	private void setDataFromPhone() {

		((TextView)findViewById(R.id.nameTextView)).setText(contact_name);
		((TextView)findViewById(R.id.grouptextView)).setText(
				((ContactApp)this.getApplication()).getContactDataManager()
				.getPhoneContactGroup(contact_id, this));
		
		ListView lv = (ListView)findViewById(R.id.listViewEditContact);
		ContactDataAdapter adapter = new ContactDataAdapter(this);
		adapter.setData(((ContactApp)this.getApplication())
				.getContactDataManager().getPhoneContactData(contact_id, this));
		lv.setAdapter(adapter);
		
		Button but = (Button)findViewById(R.id.btn_edit);
		but.setEnabled(false);
	}
	 
	
	public void onEditContactClick(View view){
		Intent intent = new Intent(this, EditContactActivity.class);
		intent.putExtra("contact_id", contact_id);
		intent.putExtra("contact_name", contact_name);
		intent.putExtra("contact_type", contact_type);
		this.startActivity(intent);
	}
	
	@Override
	public void onBackPressed() {
		Intent intent = new Intent(this, HomeActivity.class);
		this.startActivity(intent);
		finish();
	}
	 
}