package com.qrcontactbook;

import java.util.List;

import com.qrcontactbook.db.ContactData;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class QRCoderActivity extends Activity {

	private static final String TAG = "com.qrcontactbook.QRCoderActivity";
	private long contact_id = 0;
	private String contact_name = "";
	private String contact_type = "";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Intent intent = this.getIntent();
		contact_id = intent.getLongExtra("contact_id", 0);
		contact_name = intent.getStringExtra("contact_name");
		contact_type = intent.getStringExtra("contact_type");

		setContentView();
	}
	
	@SuppressWarnings("deprecation")
	private void setContentView() {
		LinearLayout ll = new LinearLayout(this);
		ll.setOrientation(LinearLayout.VERTICAL);

		LinearLayout top = new LinearLayout(this);

		TextView info = new TextView(this);
		info.setText("QR Code for contact: " + contact_name);
		info.setLayoutParams(new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, 1f));
		top.addView(info);

		Button gen = new Button(this);
		gen.setText("Generate");
		gen.setLayoutParams(new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, 2.5f));
		gen.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				new QRContactCoder(QRCoderActivity.this).execute(
						(contact_type.equals("phone_contact"))?
								getPhoneData():getBaseData());
			}
		});
		top.addView(gen);

		ll.addView(top, LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		setContentView(ll);
	}
	
	private String getPhoneData() {
		String data = "No data";
		
		return data;
	}
	
	private String getBaseData() {
		String data = "No data";
		
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("QRCodeContact.type;");
			sb.append("name=").append(contact_name).append(";");
			
			List<ContactData> list = ((ContactApp)this.getApplication())
					.getContactDataManager().getBaseContactData(contact_id);
			for(ContactData d : list) {
				sb.append(d.getType()).append("=")
					.append(d.getValue()).append(";");
			}
			sb.append("end");
			
			data = sb.toString();
			Log.d("QR CODE OUTPUT", data);
		} catch(java.sql.SQLException ex) {
			Log.e(TAG, ex.getMessage());
		}
		
		return data;
	}
}
