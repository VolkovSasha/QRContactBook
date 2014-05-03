package com.qrcontactbook.manager;

import java.sql.SQLException;

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
	
}
