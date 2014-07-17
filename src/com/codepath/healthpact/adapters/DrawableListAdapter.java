package com.codepath.healthpact.adapters;

import java.util.ArrayList;

import com.codepath.healthpact.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DrawableListAdapter extends ArrayAdapter<String>{
	public DrawableListAdapter(Context context, ArrayList<String> objects) {
		super(context, R.layout.nav_drawer_list_view, objects);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v;
		String s = getItem(position);
		if(convertView ==  null){
			LayoutInflater inflator = LayoutInflater.from(getContext());
			v = inflator.inflate(R.layout.nav_drawer_list_view, parent, false);
		}else{
			v = convertView;
		}
		ImageView imgView = (ImageView) v.findViewById(R.id.nvDlvImageView);
		if(position == 0){
			imgView.setImageResource(R.drawable.ic_follow);
		}else if(position == 1){
			imgView.setImageResource(R.drawable.ic_share);
		}else if(position == 2){
			imgView.setImageResource(R.drawable.ic_add);
		}		
		
		TextView tv = (TextView) v.findViewById(R.id.nvDlvTv);
		tv.setText(s);
		
		return v;
	}
}
