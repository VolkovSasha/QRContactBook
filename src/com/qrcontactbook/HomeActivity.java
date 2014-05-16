package com.qrcontactbook;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
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
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.qrcontactbook.adapter.ContactAdapter;
import com.qrcontactbook.db.Contact;
import com.qrcontactbook.db.ContactData;

public class HomeActivity extends ActionBarActivity {
	
	private static final String TAG = "com.qrcontactbook.HomeActivity";
    private int mState = 0;
    private int state =0;
    private ViewPager viewPager = null;
    
    private ContactAdapter phoneAdapter = null;
    private ContactAdapter baseAdapter = null;
	
    @Override
	public void onCreate(Bundle savedInstanceState) {
	
    	super.onCreate(savedInstanceState);
	    setContentView(R.layout.contact_base_tel_page);
	    setTitle("Phone Contact");
	    setContactViews();
	    getOverflowMenu();
	}
    
    @Override
    public void onResume() {
    	super.onResume();
    	updateData();
    }
    
    private void updateData() {
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
	    lv.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> adap, View view,
					int position, long id) {
				final int pos = position;
				final String[] name ={"Generate QR Code", "Import contact"};
				AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
				builder.setTitle("Contact Menu");
				builder.setItems(name, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int item) {
						if(name[item].equals("Generate QR Code")) {
							Intent intent = new Intent(HomeActivity.this, QRCoderActivity.class);
							intent.putExtra("contact_id", phoneAdapter.getItem(pos).getId());
							intent.putExtra("contact_name", phoneAdapter.getItem(pos).getName());
							intent.putExtra("contact_type", "phone_contact");
							startActivity(intent);
						}
						if(name[item].equals("Import contact"))
							importContact(pos, true);
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
				final String[] name ={"Generate QR Code"};
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
	    	public void onPageSelected(int arg0) {menuChange(); titleChange();	}
	    });
	    
	    setContentView(viewPager);
    }
    
    private void importContact(int pos, boolean refresh) {
    	Contact contact = new Contact(phoneAdapter.getItem(pos).getName());
    	try {
    		((ContactApp)this.getApplication()).getContactManager()
    				.create(contact);
    		contact = ((ContactApp)this.getApplication()).getContactManager()
    				.getLast();
    		
    		List<ContactData> list = ((ContactApp)this.getApplication())
    				.getContactDataManager().getPhoneContactData(
							phoneAdapter.getItem(pos).getId(), this);
    		for(ContactData d : list) {
    			String type = formatType(d);
				if(type.equals(""))
					continue;
				ContactData data = new ContactData();
				data.setContactId(contact.getId());
				data.setType(type);
				data.setValue(d.getValue());
				((ContactApp)this.getApplication()).getContactDataManager()
						.create(data);
    		}
    		
    		Toast.makeText(this, "Contact " + contact.getName() + 
    				" was imported", Toast.LENGTH_SHORT).show();
    		if(refresh)
    			updateData();
    	} catch(SQLException ex) {
    		Log.e(TAG, ex.getMessage(), ex);
    	}
    }
    
	private String formatType(ContactData data) {
		String type = "";
		
		if("E-Mail".equals(data.getType()))
			type = "E-mail";
		if("1".equals(data.getType()))
			type = "number:Home";
		if("2".equals(data.getType()))
			type = "number:Mobile";
		if("3".equals(data.getType()))
			type = "number:Work";
		
		return type;
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
	private void titleChange(){
		state = viewPager.getCurrentItem();
		if(state ==0)
			setTitle("Phone Contact");
		else 
			setTitle("Base Contact");
	}

	private void getOverflowMenu() {
		 
	    try {
	 
	       ViewConfiguration config = ViewConfiguration.get(this);
	       java.lang.reflect.Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
	       if(menuKeyField != null) {
	           menuKeyField.setAccessible(true);
	           menuKeyField.setBoolean(config, false);
	       }
	   } catch (Exception e) {
	       e.printStackTrace();
	   }
	 
	}
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	if(mState == 0)
    		getMenuInflater().inflate(R.menu.menu_one, menu);
    	
    	else
    		getMenuInflater().inflate(R.menu.menu_two, menu);
        return true;
    }

	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {

		case R.id.menu_one_qrcode:
		case R.id.menu_two_qrcode:
			Intent intent = new Intent(HomeActivity.this, QRDecoderActivity.class);
			startActivity(intent);
			break;

		case R.id.menu_one_find:
			break;
		case R.id.menu_one_import_all:
			
			AlertDialog.Builder build = new AlertDialog.Builder(this);
			build.setTitle("Inport All");
			build.setPositiveButton("Inport", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {

					for(int i = 0; i < phoneAdapter.getCount(); i++)
						importContact(i, false);
					updateData();
				}
			});
			build.setNegativeButton("Cansel", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
				}
			});
			build.show();
			break;
		case R.id.menu_one_settings:
			break;
		case R.id.menu_two_delete_all:
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Delete All ?");
			builder.setPositiveButton("Delete All", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					try {
						for(int i = 0; i < baseAdapter.getCount(); i++)
							((ContactApp)HomeActivity.this.getApplication()).getContactManager()
							.delete(baseAdapter.getItem(i));
						updateData();
					} catch(SQLException ex) {
						Log.e(TAG, ex.getMessage(), ex);
					}
					
				}
			});

			builder.setNegativeButton("Cansel", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
				}
			});
			builder.show();
			
			break;
		case R.id.menu_two_export_all:
			break;
		case R.id.menu_two_new_contact:
			newContact();
			break;
		case R.id.menu_two_find:
			break;
		case R.id.menu_two_settings:
			break;
		}
		
		return true;
	}
	
	private void newContact(){
		final EditText contact = new EditText(this);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("New Contact");
		builder.setView(contact);
		builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
			}
		});
		builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
			} });
		builder.show();
	}

}
