package com.qrcontactbook.db;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class Contact {
	
	private int id;
	private String name;
	private String email;
	private Map<String, Integer> numbers;
	
	public Contact(String name) {
		this.name = name;
		this.email = null;
		numbers = new HashMap<String, Integer>();
	}
	
	public Contact() {
		this("");
	}
	
	public void setId(int id) {this.id = id;}
	public int getId() {return this.id;}
	
	public void setName(String name) { this.name = name; }
	public String getName() {return this.name;}
	
	public void setEmail(String email) {this.email = email;}
	public String getEmail() {return this.email;}
	
	
	public void addNumber(String type, int number) {
		numbers.put(type, number);
	}
	public int getNumber(String type) {
		return numbers.get(type);
	}
	public Set<Entry<String,Integer>> getNumbers() {
		return numbers.entrySet();
	}
	public boolean hasNumber(String type) {
		return numbers.keySet().contains(type);
	}

}
