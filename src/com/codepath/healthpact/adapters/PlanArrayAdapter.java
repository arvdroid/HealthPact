package com.codepath.healthpact.adapters;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.codepath.healthpact.activity.PlanViewActivity;
import com.codepath.healthpact.models.Plan;


public class PlanArrayAdapter extends ArrayAdapter<Plan>{
	
	public PlanArrayAdapter(Context context,List<Plan> userplans) {
		super(context, com.codepath.healthpact.R.layout.plan_item, userplans);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	    final Plan userPlan = getItem(position);
		View view;
		if(convertView == null) {
			LayoutInflater inflator = LayoutInflater.from(getContext());
			view = inflator.inflate(com.codepath.healthpact.R.layout.plan_item, parent, false);
		} else {
			view = convertView;
		}
		TextView tvUserPlanName = (TextView) view.findViewById(com.codepath.healthpact.R.id.tvUserPlanName);
		TextView tvUserPlanStartDate = (TextView) view.findViewById(com.codepath.healthpact.R.id.tvUserPlanStartDate);
		TextView tvUserPlanDurationLeft = (TextView) view.findViewById(com.codepath.healthpact.R.id.tvUserPlanDurationLeft);
		ProgressBar pbUserPlanProgress = (ProgressBar) view.findViewById(com.codepath.healthpact.R.id.pbUserPlanProgress);
		
		//To-do get plan details
      
		tvUserPlanName.setText(userPlan.getPlanName());
		//tvUserPlanStartDate.setText(userPlan.getPlanDuration());
		//tvUserPlanDurationLeft.setText(userPlan.getPlanDuration());
		pbUserPlanProgress.setProgress(10);


        view.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			launchDetailsActivity(userPlan);
		}
	});

		return view;
	}
	
	protected void launchDetailsActivity(Plan userPlan) {
		Intent showplan = new Intent(getContext(), PlanViewActivity.class);
		//showplan.putExtra("userplan", userPlan)
		getContext().startActivity(showplan);
	}
}
