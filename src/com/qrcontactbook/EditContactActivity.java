package com.qrcontactbook;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.qrcontactbook.adapter.ContactDataAdapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

public class EditContactActivity extends Activity {
	
	private long contact_id = 0;
	private String contact_name = "";
	private String contact_type = "";
	
	private EditText name;
	private EditText mail;
	private Spinner groupSpinner;
	private ListView numbersLV;
	private ContactDataAdapter numberAdapter;
	
	private Map<String, List<Long>> groupList;
	
	final String[] namee = new String[] {
		    "trolo", "trololo", "rt", "ertwe", "dgds",
		    "ololo", "rety", "sdfgfh", "sdfggfhas", "sdgfdfgdg",
		    "sdfgdfrt", "fhfgh", "gjhghj"		    
		  };
	View dlg = null;
		
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_contact_activity);

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
		 
	 }
	 
	 public void onCancelClick(View view){
		 Intent cancel = new Intent(this, ContactActivity.class);
		 this.startActivity(cancel);
	 }
	 
	 public void onAddPhoneClick(View view){
		 //final String[] groups = getResources().getStringArray(R.array.group_spinner);
		 LayoutInflater inflater = this.getLayoutInflater();
		 dlg = inflater.inflate(R.layout.dialog_add_tel, null);
		 final Spinner spinner = (Spinner) dlg.findViewById(R.id.spinnerDialog);
		 ArrayAdapter<?> adapterDl = 
		    		ArrayAdapter.createFromResource(this, R.array.group_spinner, 
		    				android.R.layout.simple_spinner_item);
		 spinner.setAdapter(adapterDl);
		 
		 final EditText et = (EditText) dlg.findViewById(R.id.edit_text_input_dialog);
		 
		 final Dialog dialog = new Dialog(EditContactActivity.this);
		 dialog.setContentView(dlg);
		 
		 Button btnOk = (Button) dialog.findViewById(R.id.btnOk);
		 Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);
		 
		 btnOk.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
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
