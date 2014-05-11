package com.qrcontactbook.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.qrcontactbook.R;
import com.qrcontactbook.db.ContactData;

public class ContactDataAdapter extends BaseAdapter {
	
	private List<ContactData> data;
	private LayoutInflater inflater;
	
	public ContactDataAdapter(Context context) {
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	public void setData(List<ContactData> list) {
		this.data = list;
	}
	
	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public ContactData getItem(int position) {
		return data.get(position);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = null;
		
		if(convertView == null) {
			view = inflater.inflate(R.layout.contact_data_list_item, null);
			ViewHolder holder = new ViewHolder();
			holder.image = (ImageView) view.findViewById(R.id.contactDataIcon);
			holder.title = (TextView) view.findViewById(R.id.contactDataTitle);
			holder.text = (TextView) view.findViewById(R.id.contactDataText);
			view.setTag(holder);
		} else 
			view = convertView;
        
		ContactData data = this.getItem(position);
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.image.setImageResource(R.drawable.ic_launcher);
        holder.title.setText(data.getVisibleType());
        holder.text.setText(data.getValue());
        
        return view;
	}
	
	static class ViewHolder {
		protected ImageView image;
		protected TextView title;
		protected TextView text;
	}
}
