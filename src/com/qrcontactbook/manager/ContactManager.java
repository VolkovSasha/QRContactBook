package com.qrcontactbook.manager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.widget.Toast;

import com.j256.ormlite.dao.Dao;
import com.qrcontactbook.db.Contact;

public class ContactManager {

	private Dao<Contact, Integer> contactDao = null;

	public Dao<Contact, Integer> getContactDao() {
		return this.contactDao;
	}

	public void setContactDao(Dao<Contact, Integer> dao) {
		this.contactDao = dao;
	}

	public void create(Contact contact) throws SQLException {
		this.contactDao.create(contact);
	}

	public void update(Contact contact) throws SQLException {
		this.contactDao.update(contact);
	}

	public void delete(Contact contact) throws SQLException {
		this.contactDao.delete(contact);
	}

	public List<Contact> getAll() throws SQLException {
		return contactDao.queryForAll();
	}

	public Contact getLast() throws SQLException {
		List<Contact> list = this.getAll();
		return list.get(list.size() - 1);
	}

	public void createPhoneContact(String name, String number, Context context)
			throws Exception {
		ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();

		// ------------ creating contact URI ------------------
		ops.add(ContentProviderOperation
				.newInsert(ContactsContract.RawContacts.CONTENT_URI)
				.withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
				.withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
				.build());

		ops.add(ContentProviderOperation
				.newInsert(ContactsContract.Data.CONTENT_URI)
				.withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
				.withValue(
						ContactsContract.Data.MIMETYPE,
						ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
				.withValue(
						ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
						name).build());
		ops.add(ContentProviderOperation
				.newInsert(ContactsContract.Data.CONTENT_URI)
				.withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
				.withValue(
						ContactsContract.Data.MIMETYPE,
						ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
				.withValue(ContactsContract.CommonDataKinds.Phone.NUMBER,
						number)
				.withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
						ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
				.build());

		ContentProviderResult[] res = context.getContentResolver().applyBatch(
				ContactsContract.AUTHORITY, ops);
		if (res != null && res[0] != null)
			Toast.makeText(context, "Contact " + name + " created",
					Toast.LENGTH_SHORT).show();
		else
			Toast.makeText(context, "Contact not added", Toast.LENGTH_SHORT)
					.show();
	}

	public void createPhoneNumber() {

	}

	public List<Contact> getPhoneContactList(Context context) {
		List<Contact> list = new ArrayList<Contact>();

		Uri uri = ContactsContract.Contacts.CONTENT_URI;
		ContentResolver cr = context.getContentResolver();

		String[] projection = { ContactsContract.Contacts._ID,
				ContactsContract.Contacts.DISPLAY_NAME,
				ContactsContract.Contacts.HAS_PHONE_NUMBER };
		String selection = ContactsContract.Contacts.HAS_PHONE_NUMBER + "=1";
		String sortOrder = ContactsContract.Contacts.DISPLAY_NAME
				+ " COLLATE LOCALIZED ASC";

		Cursor cur = cr.query(uri, projection, selection, null, sortOrder);
		if (cur.getCount() > 0) {
			while (cur.moveToNext()) {
				Contact contact = new Contact();
				contact.setId(Long.parseLong(cur.getString(cur
						.getColumnIndex(ContactsContract.Contacts._ID))));
				contact.setName(cur.getString(cur
						.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)));
				list.add(contact);
			}
		}
		cur.close();

		return list;
	}

	public void deletePhoneContact(String name, Context context) {
		Cursor cur = context.getContentResolver().query(
				ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
				null,
				ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " LIKE '"
						+ name + "'", null, null);
		this.deletePhoneContact(cur, context);
	}

	public void deletePhoneContact(long contact_id, Context context) {
		Cursor cur = context.getContentResolver().query(
				ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
				null,
				ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "="
						+ contact_id, null, null);
		this.deletePhoneContact(cur, context);
	}

	private void deletePhoneContact(Cursor cur, Context context) {
		cur.moveToFirst();
		String lookupKey = cur.getString(cur
				.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY));
		Uri uri = Uri.withAppendedPath(
				ContactsContract.Contacts.CONTENT_LOOKUP_URI, lookupKey);
		
		context.getContentResolver().delete(uri, null, null);
		
		cur.close();
	}

}
