package com.codepath.healthpact.adapters;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.codepath.healthpact.R;
import com.codepath.healthpact.activity.ActionDetailActivity;
import com.codepath.healthpact.models.Plan;

public class ActionArrayAdapter extends ArrayAdapter<Plan> {
	
	public ActionArrayAdapter(Context context,List<Plan> userplans) {
		super(context, com.codepath.healthpact.R.layout.action_item, userplans);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	    final Plan useraction = getItem(position);
		View view;
		if(convertView == null) {
			LayoutInflater inflator = LayoutInflater.from(getContext());
			view = inflator.inflate(com.codepath.healthpact.R.layout.action_item, parent, false);
		} else {
			view = convertView;
		}

		TextView tvUserActionName = (TextView) view.findViewById(com.codepath.healthpact.R.id.tvUserActionName);
		ToggleButton tbActionDone = (ToggleButton) view.findViewById(R.id.tbactionDone);
		final Drawable onD = (Drawable) view.getResources().getDrawable(R.drawable.custom_action_done_pressed);
		final Drawable offD = (Drawable)view.getResources().getDrawable(R.drawable.custom_action_done);
      
		tvUserActionName.setText("Action A");
		
		tbActionDone.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				boolean on = ((ToggleButton) v).isChecked();
			    if (on) {
			        ((ToggleButton) v).setBackground(onD);
			    } else {
			    	((ToggleButton) v).setBackground(offD);
			    }				
				
			}
		});

        view.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			launchDetailsActivity(useraction);
		}
	});

		return view;
	}
	
	protected void launchDetailsActivity(Plan useraction) {
		Intent showplan = new Intent(getContext(), ActionDetailActivity.class);
		//showplan.putExtra("userplan", userPlan)
		getContext().startActivity(showplan);
	}
}

