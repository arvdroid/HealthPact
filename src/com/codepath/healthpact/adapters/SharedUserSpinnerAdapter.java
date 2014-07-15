package com.codepath.healthpact.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.activeandroid.util.Log;
import com.codepath.healthpact.R;
import com.parse.ParseUser;

public class SharedUserSpinnerAdapter extends ArrayAdapter<ParseUser> {
	private List<ParseUser> items;
	
	public SharedUserSpinnerAdapter(Context context, List<ParseUser> objects) {
		super(context, R.layout.share_user_spinner_layout, objects);
		items = objects;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView view;	
		ParseUser user = getItem(position);
		if(convertView ==  null){
			LayoutInflater inflator = LayoutInflater.from(getContext());
			view = (TextView)inflator.inflate(R.layout.share_user_spinner_layout, parent, false);
		}else{
			view = (TextView)convertView;
		}
		
		view.setText(user.getUsername());
		
		return view;
	}
	
	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		TextView view;
		if(convertView ==  null){
			LayoutInflater inflator = LayoutInflater.from(getContext());
			view = (TextView)inflator.inflate(R.layout.share_user_spinner_layout, parent, false);
		}else{
			view = (TextView)convertView;
		}
		view.setText(items.get(position).getUsername());
	    return view;
	}
}
