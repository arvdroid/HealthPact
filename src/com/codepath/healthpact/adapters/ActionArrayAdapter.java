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
import com.codepath.healthpact.models.Action;
import com.codepath.healthpact.models.ParseProxyObject;
import com.codepath.healthpact.parseUtils.ParseUtils;

public class ActionArrayAdapter extends ArrayAdapter<Action> {
	private boolean followed;
	private String usrPlanId;
	
	public ActionArrayAdapter(Context context,List<Action> userplans) {
		super(context, R.layout.action_item, userplans);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	    final Action useraction = getItem(position);
		View view;
		if(convertView == null) {
			LayoutInflater inflator = LayoutInflater.from(getContext());
			view = inflator.inflate(R.layout.action_item, parent, false);
		} else {
			view = convertView;
		}
		
		TextView tvUserActionName = (TextView) view.findViewById(R.id.tvUserActionName);
		TextView tvPerDay = (TextView) view.findViewById(R.id.tvAiUserActionPerDay);
		ToggleButton tbActionDone = (ToggleButton) view.findViewById(R.id.tbactionDone);
		
		tvUserActionName.setText(useraction.getActionName());
		tvPerDay.setText("PerDay: "+ useraction.getActionServing() + " min");
				
		if(followed){
			final Drawable onD = (Drawable) view.getResources().getDrawable(R.drawable.custom_action_done_pressed);
			if(useraction.getUpdated())
				tbActionDone.setBackground(onD);
			
			tbActionDone.setOnClickListener(new OnClickListener() {
				boolean updated =  useraction.getUpdated();
				@Override
				public void onClick(View v) {
					boolean on = ((ToggleButton) v).isChecked();
					if (on && !updated) {
						((ToggleButton) v).setBackground(onD);
						((ToggleButton) v).setChecked(true);
						ParseUtils.updateIndividualActionPerPlan(usrPlanId, useraction.getObjectId(), true);
						Toast.makeText(getContext(), "Action updated", Toast.LENGTH_SHORT).show();
						updated = true;
					} else {						
						Toast.makeText(getContext(), "Action is already updated", Toast.LENGTH_SHORT).show();
					}
				}
			});
		}else{
			tbActionDone.setVisibility(View.INVISIBLE);
		}

		if(followed)
			view.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					launchDetailsActivity(useraction);
				}
			});

		return view;
	}
	
	public void setFollowed(boolean followed){this.followed = followed;	}
	
	public String getUsrPlanid() {return usrPlanId;}

	public void setUsrPlanid(String usrPlanId) {this.usrPlanId = usrPlanId;}

	protected void launchDetailsActivity(Action useraction) {
		Intent showAction = new Intent(getContext(), ActionDetailActivity.class);
		// using proxy class for serialization and transfer using putExtra
        ParseProxyObject proxyProject = new ParseProxyObject(useraction);
        showAction.putExtra("actionid", useraction.getObjectId());
        showAction.putExtra("followed", followed);
        showAction.putExtra("usrPlanId", usrPlanId);
        showAction.putExtra("actionProgress", useraction.getProgress());
		showAction.putExtra("useraction", proxyProject);
		getContext().startActivity(showAction);
	}
}

