package com.qrcontactbook.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.qrcontactbook.R;
import com.qrcontactbook.db.Contact;

public class ContactAdapter extends BaseAdapter {
	
	private List<Contact> contacts;
	private LayoutInflater inflater;
	
	public ContactAdapter(Context context) {
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	public void setData(List<Contact> list) {
		this.contacts = list;
	}
	
	@Override
	public int getCount() {
		return contacts.size();
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public Contact getItem(int position) {
		return contacts.get(position);
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		view = inflater.inflate(R.layout.simple_list_item, null);
        Contact contact = getItem(position);
        TextView.class.cast(view.findViewById(R.id.txt_text)).setText(""+contact.getId());
        TextView.class.cast(view.findViewById(R.id.txt_title)).setText(contact.getName());
        return view;
	}
	
}