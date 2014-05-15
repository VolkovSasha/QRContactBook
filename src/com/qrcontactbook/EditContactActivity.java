package com.qrcontactbook;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.j256.ormlite.stmt.DeleteBuilder;
import com.qrcontactbook.adapter.ContactDataAdapter;
import com.qrcontactbook.db.Contact;
import com.qrcontactbook.db.ContactData;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

public class EditContactActivity extends Activity {
	
	private static final String TAG = "com.qrcontactbook.EditContactActivity";
	private long contact_id = 0;
	private String contact_name = "";
	private String contact_type = "";
	
	private EditText name;
	private EditText mail;
	private Spinner groupSpinner;
	private ListView numbersLV;
	private ContactDataAdapter numberAdapter;
	
	private Map<String, List<Long>> groupList;
	private Map<String, Long> baseGroups;
	
	private View dlg = null;
	private ArrayAdapter<?> numberTypeAdapter;
		
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_contact_activity);
		setTitle("Edit Profile");
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
		
		name = (EditText)findViewById(R.id.edit_text_name);
		name.setText(contact_name);
		
		try {
			baseGroups = ((ContactApp)this.getApplication()).getContactDataManager()
					.getBaseGroupList();
			List<String> names = new ArrayList<String>();
			names.addAll(baseGroups.keySet());
			
			groupSpinner = (Spinner)findViewById(R.id.group_spinner);
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_spinner_item,
					baseGroups.keySet().toArray(new String[0]));
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			groupSpinner.setAdapter(adapter);
			groupSpinner.setSelection(names.indexOf(((ContactApp)
					this.getApplication()).getContactDataManager()
					.getBaseContactGroup(contact_id)));
			
			mail = (EditText)findViewById(R.id.edit_text_mail);
			mail.setText(((ContactApp)this.getApplication()).getContactDataManager()
					.getBaseContactFirstMail(contact_id));
			
			numbersLV = (ListView)findViewById(R.id.listViewEditBtnClick);
			numberAdapter = new ContactDataAdapter(this);
			numberAdapter.setData(((ContactApp)this.getApplication())
					.getContactDataManager().getBaseContactNumbers(contact_id));
			numbersLV.setAdapter(numberAdapter);
			numbersLV.setOnItemLongClickListener(new OnItemLongClickListener() {
				@Override
				public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2,
						long arg3) {
					ContactData data = numberAdapter.getItem(arg2);
					try {
						((ContactApp) getApplication()).getContactDataManager()
								.delete(data);
						updateBaseNumbers();
					} catch(SQLException ex) {
						Log.e(TAG, ex.getMessage());
					}
					return true;
				}
			});
		} catch(SQLException ex) {
			Log.e(TAG, ex.getMessage());
		}
	}
	
	private void updateBaseNumbers() {
		try {
		numberAdapter.setData(((ContactApp)this.getApplication())
				.getContactDataManager().getBaseContactNumbers(contact_id));
		numberAdapter.notifyDataSetChanged();
		} catch(SQLException ex) {
			Log.e(TAG, ex.getMessage());
		}
	}
	 
	
	private void setDataFromPhone() {

		name = (EditText)findViewById(R.id.edit_text_name);
		name.setText(contact_name);
		
		groupList = ((ContactApp)this.getApplication()).getContactDataManager()
				.getPhoneGroupList(this);
		List<String> names = new ArrayList<String>();
		names.addAll(groupList.keySet());
		
		groupSpinner = (Spinner)findViewById(R.id.group_spinner);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item,
				groupList.keySet().toArray(new String[0]));
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		groupSpinner.setAdapter(adapter);
		groupSpinner.setSelection(names.indexOf(((ContactApp)
				this.getApplication()).getContactDataManager()
				.getPhoneContactGroup(contact_id, this)));
		
		mail = (EditText)findViewById(R.id.edit_text_mail);
		mail.setText(((ContactApp)this.getApplication()).getContactDataManager()
				.getPhoneContactFirstMail(contact_id, this));
		
		numbersLV = (ListView)findViewById(R.id.listViewEditBtnClick);
		numberAdapter = new ContactDataAdapter(this);
		numberAdapter.setData(((ContactApp)this.getApplication())
				.getContactDataManager().getPhoneContactNumbers(contact_id, this));
		numbersLV.setAdapter(numberAdapter);
	}
	
	 public void onSaveClick(View view){
		 String name = ((TextView) this.findViewById(R.id.edit_text_name))
				 .getText().toString();
		 if(name.equals(""))
			 return;
		 String mail = ((TextView) this.findViewById(R.id.edit_text_mail))
				 .getText().toString();
		 try {
			 if(!contact_name.equals(name)) {
				 Contact contact = new Contact();
				 contact.setId(contact_id);
				 contact.setName(name);
				 ((ContactApp) this.getApplication()).getContactManager()
				 		.update(contact);
			 }

			 DeleteBuilder<ContactData, Integer> db = 
					 ((ContactApp)this.getApplication()).getContactDataManager()
			 		.getContactDataDao().deleteBuilder();
			 db.where().eq("type", "group").and().eq("contact_id", contact_id);
			 db.delete();
			 if(!"No group".equals(groupSpinner.getSelectedItem().toString())) {
				 ((ContactApp)this.getApplication()).getContactDataManager()
				 		.create(new ContactData(contact_id, "group",
				 				baseGroups.get(groupSpinner.getSelectedItem()).toString()));
			 }

			 db = ((ContactApp)this.getApplication()).getContactDataManager()
				 		.getContactDataDao().deleteBuilder();
					 db.where().eq("type", "E-mail").and().eq("contact_id", contact_id);
					 db.delete();
			 if(!(mail.equals("") || mail.equals("No e-mail"))) {
				 ((ContactApp)this.getApplication()).getContactDataManager()
			 		.create(new ContactData(contact_id, "E-mail", mail));
			 }
			 Intent intent = new Intent(this, ContactActivity.class);
				intent.putExtra("contact_id", contact_id);
				intent.putExtra("contact_name", name);
				intent.putExtra("contact_type", contact_type);
			 this.startActivity(intent);
			 finish();
		 } catch(SQLException ex) {
			 Log.e(TAG, ex.getMessage());
		 }
		 
	 }
	 
	 public void onCancelClick(View view){
		 Intent intent = new Intent(this, ContactActivity.class);
			intent.putExtra("contact_id", contact_id);
			intent.putExtra("contact_name", contact_name);
			intent.putExtra("contact_type", contact_type);
		 this.startActivity(intent);
		 finish();
	 }
	 
	 public void onAddPhoneClick(View view){
		 //final String[] groups = getResources().getStringArray(R.array.group_spinner);
		 LayoutInflater inflater = this.getLayoutInflater();
		 dlg = inflater.inflate(R.layout.dialog_add_tel, null);
		 final Spinner spinner = (Spinner) dlg.findViewById(R.id.spinnerDialog);
		 numberTypeAdapter = 
		    		ArrayAdapter.createFromResource(this, R.array.group_spinner, 
		    				android.R.layout.simple_spinner_item);
		 spinner.setAdapter(numberTypeAdapter);
		 
		 final EditText et = (EditText) dlg.findViewById(R.id.edit_text_input_dialog);
		 
		 final Dialog dialog = new Dialog(EditContactActivity.this);
		 dialog.setContentView(dlg);
		 dialog.setTitle("Add Phone Number");
		 Button btnOk = (Button) dialog.findViewById(R.id.btnOk);
		 Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);
		 
		 btnOk.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ContactData data = new ContactData();
				data.setContactId(contact_id);
				data.setType("number:" + numberTypeAdapter
						.getItem(spinner.getSelectedItemPosition()).toString());
				data.setValue(et.getText().toString());
				try {
					((ContactApp)getApplication()).getContactDataManager()
							.getContactDataDao().create(data);
					updateBaseNumbers();
				} catch(SQLException ex) {
					Log.e(TAG, ex.getMessage());
				}
				dialog.dismiss();
			}
		});
		 
		 btnCancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		 
		 dialog.show();
		 
	 }
}
