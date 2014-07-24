package com.codepath.healthpact.fragments;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.codepath.healthpact.R;
import com.codepath.healthpact.adapters.ActionArrayAdapter;
import com.codepath.healthpact.models.Action;
import com.codepath.healthpact.models.AppPlan;
import com.codepath.healthpact.models.PlanAction;
import com.codepath.healthpact.parseUtils.ParseUtils;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

public class UserActionsFragment extends Fragment{
	
	private ArrayList<Action> planActions;
	private ArrayAdapter<Action> actionarrayadapter;
	private ListView lvPlanActions;
	private ProgressBar pb;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      //populateActions();
     }
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(com.codepath.healthpact.R.layout.fragments_action_list, container, false);
		lvPlanActions = (ListView) v.findViewById(R.id.lvPlanActions);
		pb = (ProgressBar)v.findViewById(R.id.pvPbLoading);
		planActions = new ArrayList<Action>();
		actionarrayadapter = new ActionArrayAdapter(getActivity(),planActions);
		lvPlanActions.setAdapter(actionarrayadapter);
		return v;
	}
	
	private void updateActionAdapter(AppPlan plan){
		if(plan.getFollowed()){
			Date startD = plan.getStartDate();
			Date today = new Date(); 
			if(today.compareTo(startD)>=0)
				((ActionArrayAdapter)actionarrayadapter).setStartedToFollow(true);
		}
		((ActionArrayAdapter)actionarrayadapter).setFollowed(plan.getFollowed());
		((ActionArrayAdapter)actionarrayadapter).setUsrPlanid(plan.getUsrPlanid());
	}
	
	public void populateActions(final AppPlan plan) {		
		updateActionAdapter(plan);
		actionarrayadapter.clear();
		final String planId = plan.getPlanid();
		showProgressBar();
		ParseQuery<PlanAction> query = ParseUtils.getActionForPlanQuery(planId);		
		query.findInBackground(new FindCallback<PlanAction>() {

			@Override
			public void done(List<PlanAction> planActions, ParseException parseEx) {
				if (parseEx == null) {
					List<Action> actions = ParseUtils.getActionsForPlan(planActions);
					for (Action action : actions) {
						boolean updated = ParseUtils.getIndividualActionPerPlan(plan.getUsrPlanid(), action.getObjectId());
						int progress = (int)ParseUtils.getPlanActionCompleted(planId, action.getObjectId());
						action.setUpdated(updated);
						action.setProgress(progress);
					}
					
					clearProgressBar();
					actionarrayadapter.addAll(actions);
				}else{
					clearProgressBar();
					Toast.makeText(getActivity(), "Failed to get data", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
	
	public void populateAction(Action action){		
		actionarrayadapter.add(action);
	}
	
	public List<Action> getActions(){
		List<Action> actions = new ArrayList<Action>();
		for(int i=0; i<actionarrayadapter.getCount(); i++){
			actions.add(actionarrayadapter.getItem(i));
		}
		return actions;
	}
	
	public void removeAction(){
		int position = lvPlanActions.getSelectedItemPosition();
		Action a = actionarrayadapter.getItem(position);
		actionarrayadapter.remove(a);
	}
	
	public void test(){
		lvPlanActions.getSelectedItemPosition();
	}
	
	public void showProgressBar(){
		pb.setVisibility(ProgressBar.VISIBLE);
	}
	
	public void clearProgressBar(){
		pb.setVisibility(ProgressBar.INVISIBLE);
	}
	
}
