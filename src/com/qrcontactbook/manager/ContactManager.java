package com.qrcontactbook.manager;

import java.sql.SQLException;
import java.util.List;

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
	
}
