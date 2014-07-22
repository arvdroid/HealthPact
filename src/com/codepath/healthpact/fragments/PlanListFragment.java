package com.codepath.healthpact.fragments;


import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;

import com.codepath.healthpact.R;
import com.codepath.healthpact.adapters.PlanArrayAdapter;
import com.codepath.healthpact.models.AppPlan;

import eu.erikw.PullToRefreshListView;
import eu.erikw.PullToRefreshListView.OnRefreshListener;

public abstract class PlanListFragment extends Fragment{
	
	protected ArrayAdapter<AppPlan> userplanadapter;
	protected PullToRefreshListView lvUserPlans;
	protected ArrayList<AppPlan> appPlans = new ArrayList<AppPlan>();;
	private ProgressBar pb;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		userplanadapter = new PlanArrayAdapter(getActivity(),appPlans);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(com.codepath.healthpact.R.layout.fragments_user_plan_list, container, false);
		lvUserPlans = (PullToRefreshListView) v.findViewById(R.id.lvUserPlans);
		pb = (ProgressBar)v.findViewById(R.id.pbLoading);
		lvUserPlans.setAdapter(userplanadapter);
		showProgressBar();
		getPlans(true);
	    lvUserPlans.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
            	getPlans(false);
            }
        });
		return v;
	}
	
	public abstract void getPlans(boolean loadFromDB);
	
	public void populatePlans(List<AppPlan> plans, boolean fromDB){
		if(fromDB)
			userplanadapter.clear();
		userplanadapter.addAll(plans);
		lvUserPlans.onRefreshComplete();
	}
	
	public void showProgressBar(){
		pb.setVisibility(ProgressBar.VISIBLE);
	}
	
	public void clearProgressBar(){
		pb.setVisibility(ProgressBar.INVISIBLE);
	}
}
