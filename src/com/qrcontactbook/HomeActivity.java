package com.qrcontactbook;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

import com.qrcontactbook.adapter.ContactAdapter;
import com.qrcontactbook.db.Contact;

public class HomeActivity extends ActionBarActivity {
	
	private static final String TAG = "com.qrcontactbook.HomeActivity";
    private int mState = 0;
    private ViewPager viewPager = null;
    
    private ContactAdapter phoneAdapter = null;
    private ContactAdapter baseAdapter = null;
	
    @Override
	public void onCreate(Bundle savedInstanceState) {
	
    	super.onCreate(savedInstanceState);
	    setContentView(R.layout.contact_base_tel_page);
	    
	    setContactViews();
	}
    
    @Override
    public void onResume() {
    	super.onResume();
    	try {
    		phoneAdapter.setData(this.getPhoneContactList());
    		phoneAdapter.notifyDataSetChanged();
    		baseAdapter.setData(((ContactApp) this.getApplication())
    				.getContactManager().getAll());
    		baseAdapter.notifyDataSetChanged();
    	} catch(SQLException ex) {
    		Log.e(TAG, ex.getMessage(), ex);
    	}
    }
    
    private void setContactViews() {

    	LayoutInflater inflater = LayoutInflater.from(this);
	    List<View> pages = new ArrayList<View>();

	    View page = inflater.inflate(R.layout.contact_base_tel_page, null);
	    
	    ListView lv = (ListView) page.findViewById(R.id.listViewPageBaseTel);
	    lv.setAdapter(this.getPhoneAdapter());
	    lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position,
					long id) {
				Intent intent = new Intent(HomeActivity.this, ContactActivity.class);
				intent.putExtra("contact_id", phoneAdapter.getItem(position).getId());
				intent.putExtra("contact_name", phoneAdapter.getItem(position).getName());
				intent.putExtra("contact_type", "phone_contact");
				HomeActivity.this.startActivity(intent);
			}
	    });
	    pages.add(lv);
	        
	    View  page1 = inflater.inflate(R.layout.contact_base_page, null);
	    
	    ListView lvOne = (ListView) page1.findViewById(R.id.listViewPageBase);
	    lvOne.setAdapter(this.getBaseAdapter());
	    pages.add(lvOne);
	    lvOne.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position,
					long id) {
				Intent intent = new Intent(HomeActivity.this, ContactActivity.class);
				intent.putExtra("contact_id", baseAdapter.getItem(position).getId());
				intent.putExtra("contact_name", baseAdapter.getItem(position).getName());
				intent.putExtra("contact_type", "base_contact");
				HomeActivity.this.startActivity(intent);
			}
	    });
	    lvOne.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> adap, View view,
					int position, long id) {
				final int pos = position;
				final String[] name ={"Generate QR Code", "Read QR Code"};
				AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
				builder.setTitle("Contact Menu");
				builder.setItems(name, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int item) {
						if(name[item].equals("Generate QR Code")) {
							Intent intent = new Intent(HomeActivity.this, QRCoderActivity.class);
							intent.putExtra("contact_id", baseAdapter.getItem(pos).getId());
							intent.putExtra("contact_name", baseAdapter.getItem(pos).getName());
							intent.putExtra("contact_type", "base_contact");
							startActivity(intent);
						}
						if(name[item].equals("Read QR Code")) {
							Intent intent = new Intent(HomeActivity.this, QRDecoderActivity.class);
							startActivity(intent);
						}
					}
				});
				builder.setNegativeButton("Cancel", 
						new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});
				final Dialog dialog = builder.create();
				dialog.show();
				return false;
			}
		});
	    
	    MyPagerAdapter pagerAdapter = new MyPagerAdapter(pages);
	    viewPager = new ViewPager(this);
	    viewPager.setAdapter(pagerAdapter);
	    viewPager.setCurrentItem(0);    
	    viewPager.setOnPageChangeListener(new OnPageChangeListener() {
	    	@Override
	    	public void onPageScrollStateChanged(int arg0) {}
	    	@Override
	    	public void onPageScrolled(int arg0, float arg1, int arg2) {}
	    	@Override
	    	public void onPageSelected(int arg0) {menuChange();	}
	    });
	    
	    setContentView(viewPager);
    }
    
    private ContactAdapter getPhoneAdapter() {
    	phoneAdapter = new ContactAdapter(this);
    	phoneAdapter.setData(this.getPhoneContactList());
    	return phoneAdapter;
    }
	 
    private ContactAdapter getBaseAdapter() {
    	baseAdapter = new ContactAdapter(this);
    	try {
    		baseAdapter.setData(((ContactApp)this.getApplication())
    			.getContactManager().getAll());
    	} catch(SQLException ex) {
    		Log.e(TAG, ex.getMessage());
    	}
    	
    	return baseAdapter;
    }
    
    private List<Contact> getPhoneContactList() {
    	List<Contact> list = new ArrayList<Contact>();
    	
    	Uri uri = ContactsContract.Contacts.CONTENT_URI;
    	ContentResolver cr = getContentResolver();
    	
    	String[] projection = {ContactsContract.Contacts._ID, 
    			ContactsContract.Contacts.DISPLAY_NAME,
    			ContactsContract.Contacts.HAS_PHONE_NUMBER};
    	String selection = ContactsContract.Contacts.HAS_PHONE_NUMBER + "=1";
    	String sortOrder = ContactsContract.Contacts.DISPLAY_NAME
    			+ " COLLATE LOCALIZED ASC";
    	
    	Cursor cur = cr.query(uri, projection, selection, null, sortOrder);
    	if(cur.getCount() > 0) {
    		while(cur.moveToNext()) {
        		Contact contact = new Contact();
    			contact.setId(Long.parseLong(cur.getString(
    					cur.getColumnIndex(ContactsContract.Contacts._ID))));
    			contact.setName(cur.getString(cur.getColumnIndex(
    					ContactsContract.Contacts.DISPLAY_NAME)));
    			list.add(contact);
    		}
    	}
    	cur.close();
    	
    	return list;
    }
	
	 
	private void menuChange() {
		 mState = viewPager.getCurrentItem();
		 this.supportInvalidateOptionsMenu();
	}


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	if(mState == 0)
    		getMenuInflater().inflate(R.menu.menu_one, menu);
    	else
    		getMenuInflater().inflate(R.menu.menu_two, menu);
        return true;
    }

}
