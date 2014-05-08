package com.qrcontactbook.db;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "groups")
public class Group {

	@DatabaseField(generatedId = true)
	private long id;
	@DatabaseField(columnName = "name", canBeNull = false, index = true, indexName = "name_index")
	private String name;

	public Group(String name) {
		this.name = name;
	}
	
	public Group() {
		this("");
	}
	
	public void setId(long id) {this.id = id;}
	public long getId() {return this.id;}
	
	public void setName(String name) { this.name = name; }
	public String getName() {return this.name;}
	
}
