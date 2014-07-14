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
import android.widget.ToggleButton;

import com.codepath.healthpact.R;
import com.codepath.healthpact.activity.ActionDetailActivity;
import com.codepath.healthpact.models.Action;

public class ActionArrayAdapter extends ArrayAdapter<Action> {
	boolean followed;
	
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
					} else {
						((ToggleButton) v).setBackground(offD);
					}				

				}
			});
		}else{
			tbActionDone.setVisibility(View.INVISIBLE);
		}

        view.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {
			launchDetailsActivity(useraction);
		}
	});

		return view;
	}
	
	public void setFollowed(boolean followed){
		this.followed = followed;
	}
	
	protected void launchDetailsActivity(Action useraction) {
		Intent showplan = new Intent(getContext(), ActionDetailActivity.class);
		showplan.putExtra("useraction", useraction);
		getContext().startActivity(showplan);
	}
}

