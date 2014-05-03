package com.qrcontactbook;

import java.sql.SQLException;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.qrcontactbook.db.Contact;
import com.qrcontactbook.db.ContactData;
import com.qrcontactbook.db.DBHelper;
import com.qrcontactbook.manager.ContactDataManager;
import com.qrcontactbook.manager.ContactManager;

import android.app.Application;
import android.util.Log;

public class ContactApp extends Application {

	private static final String TAG = "com.qrcontactbook.ContactApp";
	
	private DBHelper dbHelper = null;
	private ContactManager contactManager = null;
	private ContactDataManager contactDataManager = null;
	
	public ContactApp() {
		super();
		dbHelper = new DBHelper(this);
	}
	
	public ContactManager getContactManager() {
		if(contactManager == null) {
			try {
				contactManager = new ContactManager();
				Dao<Contact, Integer> contactDao =
						DaoManager.createDao(dbHelper.getConnectionSource(), 
								Contact.class);
				contactManager.setContactDao(contactDao);
			} catch(SQLException ex) {
				Log.e(TAG, ex.getMessage());
			}
		}
		return contactManager;
	}
	
	public ContactDataManager getContactDataManager() {
		if(contactDataManager == null) {
			try {
				contactDataManager = new ContactDataManager();
				Dao<ContactData, Integer> contactDataDao =
						DaoManager.createDao(dbHelper.getConnectionSource(), 
								ContactData.class);
				contactDataManager.setContactDataDao(contactDataDao);
			} catch(SQLException ex) {
				Log.e(TAG, ex.getMessage());
			}
		}
		return contactDataManager;
	}
	
}
