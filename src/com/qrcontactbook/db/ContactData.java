package com.qrcontactbook.db;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "contact_data")
public class ContactData {
	
	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField(columnName = "contact_id", canBeNull = false)
	private int contactId;
	@DatabaseField(columnName = "type", canBeNull = false, index = true, indexName = "type_index")
	private String type;
	@DatabaseField(columnName = "value", canBeNull = false)
	private String value;
	
	public ContactData(int conId, String type, String value) {
		this.contactId = conId;
		this.type = type;
		this.value = value;
	}
	
	public ContactData() {
		this(0, "", "");
	}
	
	public void setId(int id) {this.id = id;}
	public int getId() {return this.id;}
	
	public void setContactId(int id) {this.contactId = id;}
	public int getContactId() {return this.contactId;}
	
	public void setType(String type) {this.type = type;}
	public String getType() {return this.type;}
	
	public void setValue(String value) {this.value = value;}
	public String getValue() {return this.value;}

}
