package com.qrcontactbook;


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
		    
		    final Spinner spinner = (Spinner)findViewById(R.id.group_spinner);
		    ArrayAdapter<?> adapter = 
		    		ArrayAdapter.createFromResource(this, R.array.group_spinner, 
		    				android.R.layout.simple_spinner_item);
		    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		    spinner.setAdapter(adapter);
		    
		    final ListView lve = (ListView)findViewById(R.id.listViewEditBtnClick);
		    ArrayAdapter<String> adapterLV = new ArrayAdapter<String>(this,
		    		android.R.layout.simple_list_item_1, namee);
		    lve.setAdapter(adapterLV);
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
