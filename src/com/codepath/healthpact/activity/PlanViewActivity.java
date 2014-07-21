package com.codepath.healthpact.activity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.healthpact.R;
import com.codepath.healthpact.dialogs.ShareUserDialog;
import com.codepath.healthpact.fragments.DatePickerFragment;
import com.codepath.healthpact.fragments.UserActionsFragment;
import com.codepath.healthpact.models.AppPlan;
import com.codepath.healthpact.parseUtils.ParseUtils;

public class PlanViewActivity extends FragmentActivity {
	Calendar calender = Calendar.getInstance();
	OnDateSetListener ondate;
	AppPlan result;
	Menu menu;
	String pattern = "MM/dd/yyyy";
	SimpleDateFormat format = new SimpleDateFormat(pattern);
	UserActionsFragment actionsFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_plan_view);
		
		TextView pName = (TextView)findViewById(R.id.pvtvPlanName);
		TextView pDesc = (TextView)findViewById(R.id.pvtvDescription);
		TextView pDuration = (TextView)findViewById(R.id.pvtvDuration);
		TextView pCreatedAt = (TextView)findViewById(R.id.pvtvCreatedDate);
		TextView pToday = (TextView)findViewById(R.id.pvtvCurrentDate);
		TextView pCreatedBy = (TextView)findViewById(R.id.pvtvCreatedBy);
		
		result = (AppPlan) getIntent().getSerializableExtra("userplan");
		pName.setText(result.getName());
		pDesc.setText(result.getDesc());
		pDuration.setText("Duration:"+ result.getDuration() + " weeks");
		pCreatedBy.setText("Created By: " + result.getUsrName());
		
		if(result.getFollowed()){
			pCreatedAt.setText("Started On: "+format.format(result.getStartDate()));
			pToday.setVisibility(View.VISIBLE);
			pToday.setText("Today: "+format.format(new Date()));
		}
		else
			pCreatedAt.setText("Created On: "+format.format(result.getCreatedDate()));
				
		ondate = new OnDateSetListener() {
			boolean OndateSet;
			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				calender.set(year, monthOfYear, dayOfMonth, 0, 0);
				if(!OndateSet){
					ParseUtils.updatePlanFollowedByUser(result.getId(), calender.getTime(), result.getDuration(), actionsFragment.getActions());
					Toast.makeText(PlanViewActivity.this, "Plan followed" ,Toast.LENGTH_LONG).show();
				}
				OndateSet = true;
			}
		};
		
		actionsFragment = (UserActionsFragment) 
                getSupportFragmentManager().findFragmentById(R.id.actionViewFragment);
		
		actionsFragment.populateActions(result);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		this.menu = menu;
		getMenuInflater().inflate(R.menu.plan_view, menu);		
		return true;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		if(result.getFollowed()) {
		    menu.findItem(R.id.action_follow).setVisible(false);
		}
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_home) {
			Intent i = new Intent(this, HomeViewActivity.class);
			startActivity(i);
			return true;
		} if(id == R.id.action_share) {
			FragmentManager fm = getSupportFragmentManager();		
			ShareUserDialog shareDialog = new ShareUserDialog();
			Bundle args = new Bundle();
			args.putString("planid", result.getId());
			args.putString("usrplanid", result.getUsrPlanid());
			shareDialog.setArguments(args);
			shareDialog.show(fm, "");
		} if(id == R.id.action_follow) {
			DatePickerFragment date = new DatePickerFragment();
			/**
			 * Set Up Current Date Into dialog
			 */
			Bundle args = new Bundle();
			args.putInt("year", calender.get(Calendar.YEAR));
			args.putInt("month", calender.get(Calendar.MONTH));
			args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
			date.setArguments(args);
			/**
			 * Set Call back to capture selected date
			 */
			date.setCallBack(ondate);
			date.show(getSupportFragmentManager(), "Date Picker");
		}
		return super.onOptionsItemSelected(item);
	}
}
