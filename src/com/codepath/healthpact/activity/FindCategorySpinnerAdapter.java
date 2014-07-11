package com.codepath.healthpact.activity;

import com.codepath.healthpact.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class FindCategorySpinnerAdapter extends ArrayAdapter<String> {
	TextView prompt;
	public FindCategorySpinnerAdapter(Context context, String[] objects) {
		super(context, R.layout.spinner_layout, objects);
		prompt = new TextView(context);
		prompt.setText("select category");
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		CheckBox view;
		/*if(position == 0)
			return prompt;
		else{			
			String text = this.getItem(position);
			if(convertView instanceof  TextView){
				LayoutInflater inflator = LayoutInflater.from(getContext());
				view = (CheckBox) inflator.inflate(R.layout.spinner_layout, parent, false);
			}else{
				view = (CheckBox)convertView;
			}
			//view.setSelected(false);
			
			view.setText(text);
			view.setChecked(true);
			return view;
		}*/
		
		String text = this.getItem(position);
		if(convertView ==  null){
			LayoutInflater inflator = LayoutInflater.from(getContext());
			view = (CheckBox) inflator.inflate(R.layout.spinner_layout, parent, false);
		}else{
			view = (CheckBox)convertView;
		}
				
		view.setText(text);
		
		return view;
		
	}
	
	

}
