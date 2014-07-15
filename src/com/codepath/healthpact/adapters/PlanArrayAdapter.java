package com.codepath.healthpact.adapters;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.codepath.healthpact.activity.PlanViewActivity;
import com.codepath.healthpact.models.AppPlan;


public class PlanArrayAdapter extends ArrayAdapter<AppPlan>{
		
	public PlanArrayAdapter(Context context,List<AppPlan> userplans) {
		super(context, com.codepath.healthpact.R.layout.plan_item, userplans);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	    final AppPlan userPlan = getItem(position);
		View view;
		if(convertView == null) {
			LayoutInflater inflator = LayoutInflater.from(getContext());
			view = inflator.inflate(com.codepath.healthpact.R.layout.plan_item, parent, false);
		} else {
			view = convertView;
		}
		TextView tvUserPlanName = (TextView) view.findViewById(com.codepath.healthpact.R.id.pitvUserPlanName);
		TextView tvUserPlanDuration = (TextView) view.findViewById(com.codepath.healthpact.R.id.pitvUserPlanDuration);
		
		
		//To-do get plan details
      
		tvUserPlanName.setText(userPlan.getName());
		tvUserPlanDuration.setText("Duration: "+ userPlan.getDuration()+ " week(s)");


        view.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {
			launchDetailsActivity(userPlan);
		}
	});

		return view;
	}
	
	protected void launchDetailsActivity(AppPlan userPlan) {
		Intent showplan = new Intent(getContext(), PlanViewActivity.class);
		showplan.putExtra("userplan", userPlan);
		getContext().startActivity(showplan);
	}
}
