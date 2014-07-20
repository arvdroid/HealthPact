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
	private boolean disableDetail;
	private String usrPlanId;
	
	public ActionArrayAdapter(Context context,List<Action> userplans) {
		super(context, com.codepath.healthpact.R.layout.action_item, userplans);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	    final Action useraction = getItem(position);
		View view;
		if(convertView == null) {
			LayoutInflater inflator = LayoutInflater.from(getContext());
			view = inflator.inflate(com.codepath.healthpact.R.layout.action_item, parent, false);
		} else {
			view = convertView;
		}
		TextView tvUserActionName = (TextView) view.findViewById(com.codepath.healthpact.R.id.tvUserActionName);		
		ToggleButton tbActionDone = (ToggleButton) view.findViewById(R.id.tbactionDone);
		
		
		tvUserActionName.setText(useraction.getActionName());
		if(followed){
			final Drawable onD = (Drawable) view.getResources().getDrawable(R.drawable.custom_action_done_pressed);
			final Drawable offD = (Drawable)view.getResources().getDrawable(R.drawable.custom_action_done);
			tbActionDone.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					boolean on = ((ToggleButton) v).isChecked();
					if (on) {
						((ToggleButton) v).setBackground(onD);						
						ParseUtils.updateIndividualActionPerPlan(usrPlanId, useraction.getObjectId(), true);
						Toast.makeText(getContext(), "Action updated", Toast.LENGTH_SHORT).show();
					} else {
						((ToggleButton) v).setBackground(offD);
					}
				}
			});
		}else{
			tbActionDone.setVisibility(View.INVISIBLE);
		}

		if(!disableDetail)
			view.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					launchDetailsActivity(useraction);
				}
			});

		return view;
	}
	
	public void setFollowed(boolean followed){this.followed = followed;	}
	
	public void setDisableDetails(boolean disableDetail){this.disableDetail = disableDetail;}
		
	public String getUsrPlanid() {return usrPlanId;}

	public void setUsrPlanid(String usrPlanId) {this.usrPlanId = usrPlanId;}

	protected void launchDetailsActivity(Action useraction) {
		Intent showplan = new Intent(getContext(), ActionDetailActivity.class);
		// using proxy class for serialization and transfer using putExtra
        ParseProxyObject proxyProject = new ParseProxyObject(useraction);
        showplan.putExtra("followed", followed);
		showplan.putExtra("useraction", proxyProject);
		getContext().startActivity(showplan);
	}
}

