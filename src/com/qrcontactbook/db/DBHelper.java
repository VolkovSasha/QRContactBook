package com.qrcontactbook.db;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class DBHelper extends OrmLiteSqliteOpenHelper {

	private static final String TAG = "com.qrcontactbook.DBHelper";
    private static final String DB_NAME = "qrcontacts.db";
    private static final int DB_VERSION = 1;
    private Context context;

	public DBHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
		this.context = context;
	}
	
	private void testfunction() {
		try {
			Dao<Contact, Integer> dao =
					DaoManager.createDao(this.getConnectionSource(), 
							Contact.class);
			dao.create(new Contact("contact1"));
			dao.create(new Contact("contact2"));
			dao.create(new Contact("contact3"));
			dao.create(new Contact("Ololosh"));
			dao.create(new Contact("Trololosh"));
			dao.create(new Contact("Olen"));
		} catch(SQLException ex) {
			Log.e(TAG, ex.getMessage());
		}
	}
	
	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource sourse) {
		try {
			TableUtils.createTableIfNotExists(sourse, Contact.class);
			TableUtils.createTableIfNotExists(sourse, ContactData.class);
			
			testfunction();
		} catch(java.sql.SQLException ex) {
			Log.e(TAG, ex.getMessage());
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource sourse, 
			int oldVersion, int newVersion) {}
	
	public Context getContext() {
		return this.context;
	}

}
