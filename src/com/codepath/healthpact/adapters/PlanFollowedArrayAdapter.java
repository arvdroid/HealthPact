package com.codepath.healthpact.adapters;

import java.text.SimpleDateFormat;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.codepath.healthpact.activity.PlanViewActivity;
import com.codepath.healthpact.models.AppPlan;


public class PlanFollowedArrayAdapter extends ArrayAdapter<AppPlan>{
	String pattern = "MM/dd/yyyy";
	SimpleDateFormat format = new SimpleDateFormat(pattern);
	
	public PlanFollowedArrayAdapter(Context context,List<AppPlan> userplans) {
		super(context, com.codepath.healthpact.R.layout.plan_followed_item, userplans);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	    final AppPlan userPlan = getItem(position);
		View view;
		if(convertView == null) {
			LayoutInflater inflator = LayoutInflater.from(getContext());
			view = inflator.inflate(com.codepath.healthpact.R.layout.plan_followed_item, parent, false);
		} else {
			view = convertView;
		}
		TextView tvUserPlanName = (TextView) view.findViewById(com.codepath.healthpact.R.id.pftvUserPlanName);
		TextView tvUserPlanStartDate = (TextView) view.findViewById(com.codepath.healthpact.R.id.pftvUserPlanStartDate);
		TextView tvUserPlanDurationLeft = (TextView) view.findViewById(com.codepath.healthpact.R.id.pftvUserPlanDurationLeft);
		ProgressBar pbUserPlanProgress = (ProgressBar) view.findViewById(com.codepath.healthpact.R.id.pfpbUserPlanProgress);
		
		//To-do get plan details
      
		tvUserPlanName.setText(userPlan.getName());
		tvUserPlanStartDate.setText("Start date: "+ String.valueOf(format.format(userPlan.getStartDate())));
		
		long today_date = System.currentTimeMillis();		

		long end_date = userPlan.getEndDate().getTime();
		long start_date = userPlan.getStartDate().getTime();
		
		Log.d("hp", "st_date: "+ String.valueOf(format.format(userPlan.getEndDate())));
		
		double timediff = end_date - today_date;
		double total = end_date - start_date;		

		String daysleft = String.valueOf(((int)(timediff/(1000*3600*24))));
		
		tvUserPlanDurationLeft.setText("Days Left: "+ daysleft);
		
		int progressdone = (int) (((total - timediff)/total)*100);
		pbUserPlanProgress.setProgress(progressdone);


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
