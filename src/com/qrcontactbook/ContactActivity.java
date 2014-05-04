package com.qrcontactbook;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ContactActivity extends Activity {
	final String[] name = new String[] {
		    "trolo", "trololo", "rt", "ertwe", "dgds",
		    "ololo", "rety", "sdfgfh", "sdfggfhas", "sdgfdfgdg",
		    "sdfgdfrt", "fhfgh", "gjhghj"		    
		  };
	 @Override
		public void onCreate(Bundle savedInstanceState) {
		
	    	super.onCreate(savedInstanceState);
		    setContentView(R.layout.contact_activity);
		    ListView lv = (ListView)findViewById(R.id.listViewEditContact);
		    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,	
					 R.layout.contact_activity_list_item, R.id.label, name);
		   lv.setAdapter(adapter);
		   
		   
		   
		 }
	 
}