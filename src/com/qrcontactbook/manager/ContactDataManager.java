package com.qrcontactbook.manager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.qrcontactbook.db.ContactData;
import com.qrcontactbook.db.Group;

public class ContactDataManager {
	
	private Dao<ContactData, Integer> contactDataDao = null;
	private Dao<Group, Integer> groupDao = null;
	
	public Dao<ContactData, Integer> getContactDataDao() {
		return this.contactDataDao;
	}
	public void setContactDataDao(Dao<ContactData, Integer> dao) {
		this.contactDataDao = dao;
	}
	
	public Dao<Group, Integer> getGroupDao() {
		return this.groupDao;
	}
	public void setGroupDao(Dao<Group, Integer> dao) {
		this.groupDao = dao;
	}
	
	public void create(ContactData contact) throws SQLException {
		this.contactDataDao.create(contact);
	}
	
	public List<ContactData> getBaseContactData(long contact_id) 
				throws SQLException {
		
		List<ContactData> list = new ArrayList<ContactData>();
		
		list.addAll(this.getBaseContactNumbers(contact_id));
		list.addAll(this.getBaseContactMails(contact_id));
		
		return list;
	}
	
	public List<ContactData> getBaseContactNumbers(long contact_id) 
				throws SQLException{

		return contactDataDao.queryBuilder().where()
				.like("type", "%number%")
				.and()
				.eq("contact_id", contact_id).query();
	}
	
	public List<ContactData> getBaseContactMails(long contact_id)
				throws SQLException {
		
		return contactDataDao.queryBuilder().where()
				.eq("type", "E-mail")
				.and()
				.eq("contact_id", contact_id).query();
	}
	
	public String getBaseContactGroup(long contact_id) throws SQLException {
		return this.getBaseGroupName(this.getBaseGroupId(contact_id));
	}
	
	public long getBaseGroupId(long contact_id) throws SQLException {
		long groupId = -1;
		
		List<ContactData> data = this.getContactDataDao().queryBuilder()
				.where().eq("type", "group")
				.and()
				.eq("contact_id", contact_id).query();
		try {
			if(data.size() > 0)
				groupId = Long.parseLong(data.get(0).getValue());
		} catch(NumberFormatException ex) {
			Log.e(ContactDataManager.class.getName(), 
					ex.getClass().getName() + ": " + ex.getMessage());
		}
		
		return groupId;
	}
	
	public String getBaseGroupName(long groupId) throws SQLException {
		String name = "No group";

		List<Group> list = groupDao.queryBuilder().where()
				.eq("id", groupId).query();
		if(list.size() > 0)
			name = list.get(0).getName();
		
		return name;
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
	
	public Map<String, List<Long>> getPhoneGroupList(Context context) {
		Map<String, List<Long>> list = new HashMap<String, List<Long>>();
		
		Cursor cur = context.getContentResolver().query(
				ContactsContract.Groups.CONTENT_URI,
				new String[] { ContactsContract.Groups._ID,
						ContactsContract.Groups.TITLE},
				ContactsContract.Groups.DELETED+"!='1'", null, null
				);
		if(cur.getCount() > 0) {
			while(cur.moveToNext()) {
				long id = cur.getLong(cur.getColumnIndex(
						ContactsContract.Groups._ID));
				String title = cur.getString(cur.getColumnIndex(
						ContactsContract.Groups.TITLE));
				
				if (title.contains("Starred in Android")
		                || title.contains("My Contacts")) {
		            continue;
		        }
				
				title = this.formatPnoneGroupName(title);
				if(list.keySet().contains(title))
					list.get(title).add(id);
				else {
					List<Long> ids = new ArrayList<Long>();
					ids.add(id);
					list.put(title, ids);
				}
			}
		}
		cur.close();
		list.put("No group", null);
		
		return list;
	}
	
	public String formatPnoneGroupName(String group) {
		if (group.contains("Group:")) {
            group = group.substring(group.indexOf("Group:") + 6).trim();
        }
		if (group.contains("Favorite_")) {
            group = "Favorites";
        }
		return group;
	}
	
	public String getPhoneContactGroup(long contact_id, Context context) {
		return this.getPhoneGroupName(
				this.getPhoneGroupId(contact_id, context), context);
	}
	
	public String getPhoneGroupName(long groupId, Context context) {
		String groupName = "No group";
		
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
		groupName = this.formatPnoneGroupName(groupName);
		
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
	
	public String getPhoneContactFirstMail(long contact_id, Context context) {
		String mail = "No E-mail";
		
		List<ContactData> mails = this.getPhoneContactMails(contact_id, context);
		if(mails.size() > 0)
			mail = mails.get(0).getValue();
		
		return mail;
	}
	
}
