package com.qrcontactbook.manager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import com.j256.ormlite.dao.Dao;
import com.qrcontactbook.db.ContactData;

public class ContactDataManager {
	private Dao<ContactData, Integer> contactDataDao = null;
	
	public Dao<ContactData, Integer> getContactDao() {
		return this.contactDataDao;
	}
	public void setContactDataDao(Dao<ContactData, Integer> dao) {
		this.contactDataDao = dao;
	}
	
	public void create(ContactData contact) throws SQLException {
		this.contactDataDao.create(contact);
	}
	
	public long getPhoneGroupId(long contact_id, Context context) {
		long groupId = 0;
		
		Cursor dataCur = context.getContentResolver().query(
				ContactsContract.Data.CONTENT_URI,
				new String[] { ContactsContract.Data.CONTACT_ID,
						ContactsContract.Data.DATA1 },
				ContactsContract.Data.MIMETYPE + "=? AND " +
				ContactsContract.Data.CONTACT_ID + "=" + contact_id,
				new String[] {ContactsContract.CommonDataKinds.GroupMembership.CONTENT_ITEM_TYPE},
				null
				);
		if(dataCur.getCount() > 0) 
			if(dataCur.moveToFirst()) 
				groupId = dataCur.getLong(dataCur.getColumnIndex(
						ContactsContract.Data.DATA1));
		dataCur.close();
		
		return groupId;
	}
	
	public String getPhoneGroupName(long groupId, Context context) {
		String groupName = "no group";
		
		Cursor cur = context.getContentResolver().query(
				ContactsContract.Groups.CONTENT_URI,
				new String[] {ContactsContract.Groups._ID,
						ContactsContract.Groups.TITLE},
				ContactsContract.Groups._ID + "=" + groupId,
				null, null
				);
		if(cur.getCount() > 0)
			if(cur.moveToFirst())
				groupName = cur.getString(cur.getColumnIndex(
						ContactsContract.Groups.TITLE));
		cur.close();
		
		return groupName;
	}
	
	public List<ContactData> getPhoneContactData(long contact_id, Context context) {
		List<ContactData> data = new ArrayList<ContactData>();
		
		data.addAll(this.getPhoneContactNumbers(contact_id, context));
		data.addAll(this.getPhoneContactMails(contact_id, context));
		
		return data;
	}
	
	public List<ContactData> getPhoneContactNumbers(long contact_id, Context context) {
		List<ContactData> numbers = new ArrayList<ContactData>();
		
		Cursor cur = context.getContentResolver().query(
				ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
				null,
				ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" +
				contact_id,
				null, null
				);
		if(cur.getCount() > 0) {
			while(cur.moveToNext()) {
				ContactData data = new ContactData();
				
				data.setId(cur.getLong(cur.getColumnIndex(
						ContactsContract.CommonDataKinds.Phone._ID)));
				data.setContactId(contact_id);
				data.setType(cur.getString(cur.getColumnIndex(
						ContactsContract.CommonDataKinds.Phone.TYPE)));
				data.setValue(cur.getString(cur.getColumnIndex(
						ContactsContract.CommonDataKinds.Phone.NUMBER)));
				data.setVisibleType((String) 
						ContactsContract.CommonDataKinds.Phone
						.getTypeLabel(context.getResources(), 
								Integer.parseInt(data.getType()), ""));
				numbers.add(data);
			}
		}
		cur.close();
		
		return numbers;
	}
	
	public List<ContactData> getPhoneContactMails(long contact_id, Context context) {
		List<ContactData> mails = new ArrayList<ContactData>();
		
		Cursor cur = context.getContentResolver().query(
				ContactsContract.CommonDataKinds.Email.CONTENT_URI,
				null,
				ContactsContract.CommonDataKinds.Email.CONTACT_ID + "=" +
				contact_id,
				null, null
				);
		if(cur.getCount() > 0) {
			while(cur.moveToNext()) {
				ContactData data = new ContactData();
				
				data.setId(cur.getLong(cur.getColumnIndex(
						ContactsContract.CommonDataKinds.Email._ID)));
				data.setContactId(contact_id);
				data.setType("E-Mail");
				data.setValue(cur.getString(cur.getColumnIndex(
						ContactsContract.CommonDataKinds.Email.DATA)));
				mails.add(data);
			}
		}
		cur.close();
		
		return mails;
	}
	
}
