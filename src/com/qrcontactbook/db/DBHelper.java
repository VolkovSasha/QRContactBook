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
			dao.create(new Contact("contact4"));
			dao.create(new Contact("contact5"));
			dao.create(new Contact("contact6"));
			
			Dao<Group, Integer> gdao =
					DaoManager.createDao(this.getConnectionSource(), 
							Group.class);
			gdao.create(new Group("VIP"));
			gdao.create(new Group("Favorites"));
			gdao.create(new Group("Friends"));
			gdao.create(new Group("Family"));
			gdao.create(new Group("Coworkers"));
			
			Dao<ContactData, Integer> cdao = 
					DaoManager.createDao(this.getConnectionSource(), 
							ContactData.class);
			cdao.create(new ContactData(1, "group", "1"));
			cdao.create(new ContactData(2, "group", "2"));
			cdao.create(new ContactData(3, "group", "3"));
			cdao.create(new ContactData(4, "group", "4"));
			cdao.create(new ContactData(1, "number:Mobile", "0951435423"));
			cdao.create(new ContactData(2, "number:Mobile", "0951435423"));
			cdao.create(new ContactData(3, "number:Mobile", "0951435423"));
			cdao.create(new ContactData(4, "number:Mobile", "0951435423"));
			cdao.create(new ContactData(5, "number:Mobile", "0951435423"));
			cdao.create(new ContactData(6, "number:Mobile", "0951435423"));
			cdao.create(new ContactData(1, "number:Work", "0951435423"));
			cdao.create(new ContactData(3, "number:Home", "0951435423"));
			cdao.create(new ContactData(5, "number:Home", "0951435423"));
			cdao.create(new ContactData(1, "E-mail", "somemail@mail.ru"));
			cdao.create(new ContactData(2, "E-mail", "somemail@mail.ru"));
			cdao.create(new ContactData(5, "E-mail", "somemail@mail.ru"));
			cdao.create(new ContactData(6, "E-mail", "somemail@mail.ru"));
		} catch(SQLException ex) {
			Log.e(TAG, ex.getMessage());
		}
	}
	
	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource sourse) {
		try {
			TableUtils.createTableIfNotExists(sourse, Contact.class);
			TableUtils.createTableIfNotExists(sourse, ContactData.class);
			TableUtils.createTableIfNotExists(sourse, Group.class);
			
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
