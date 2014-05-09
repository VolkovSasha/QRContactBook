package com.qrcontactbook.db;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "contact_data")
public class ContactData {
	
	@DatabaseField(generatedId = true)
	private long id;
	@DatabaseField(columnName = "contact_id", canBeNull = false)
	private long contactId;
	@DatabaseField(columnName = "type", canBeNull = false, index = true, indexName = "type_index")
	private String type;
	@DatabaseField(columnName = "value", canBeNull = false)
	private String value;
	
	private String vType = null;
	
	public ContactData(long conId, String type, String value) {
		this.contactId = conId;
		this.type = type;
		this.value = value;
	}
	
	public ContactData() {
		this(0, "", "");
	}
	
	public void setId(long id) {this.id = id;}
	public long getId() {return this.id;}
	
	public void setContactId(long id) {this.contactId = id;}
	public long getContactId() {return this.contactId;}
	
	public void setType(String type) {this.type = type;}
	public String getType() {return this.type;}
	
	public void setValue(String value) {this.value = value;}
	public String getValue() {return this.value;}

	public void setVisibleType(String type) {this.vType = type;}
	public String getVisibleType() {
		if(type.contains("number:"))
			vType = type.substring(type.indexOf("number:") + 7);
		return (this.vType==null)?type:vType;
	}
	
}
