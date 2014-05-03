package com.qrcontactbook.db;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "contacts")
public class Contact {
	
	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField(columnName = "name", canBeNull = false, index = true, indexName = "name_index")
	private String name;
	
	public Contact(String name) {
		this.name = name;
	}
	
	public Contact() {
		this("");
	}
	
	public void setId(int id) {this.id = id;}
	public int getId() {return this.id;}
	
	public void setName(String name) { this.name = name; }
	public String getName() {return this.name;}
	
}
