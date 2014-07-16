package com.codepath.healthpact.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.healthpact.R;
import com.codepath.healthpact.activity.AddActionDialog.AddActionDialogListener;
import com.codepath.healthpact.activity.SetDurationDialog.SetDurationDialogListener;
import com.codepath.healthpact.fragments.UserActionsFragment;
import com.codepath.healthpact.models.Action;
import com.codepath.healthpact.models.Plan;
import com.codepath.healthpact.models.UserPlan;
import com.codepath.healthpact.parseUtils.ParseUtils;
import com.parse.ParseUser;

public class CreatePlanActivity extends FragmentActivity implements AddActionDialogListener, SetDurationDialogListener{
	EditText etPlanName; 
	Button btnSave;
	TextView tvduration;
	List<Action> actionsList = new ArrayList<Action>();
	UserActionsFragment fragmentDemo;
	int duration;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_plan);
		
		fragmentDemo = (UserActionsFragment) 
                getSupportFragmentManager().findFragmentById(R.id.cpActionViewFragment);
            
		etPlanName = (EditText) findViewById(R.id.lblcPPlanNameEditText);
		btnSave = (Button) findViewById(R.id.cpBSavePlan);
		tvduration = (TextView)findViewById(R.id.lblcPDuration);
		
		btnSave.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				PlanSave();				
			}
		});
	}
	
	private void PlanSave() {
		UserPlan userPlan = new UserPlan();
		userPlan.setUser_id(ParseUser.getCurrentUser().getObjectId());
		
		String planName = etPlanName.getText().toString();
		List<Action> actions = fragmentDemo.getActions();
		
		Plan plan = new Plan();
		plan.setPlanName(planName);
		plan.setPlanDuration(duration);
		plan.setPlanDesc(planName+ " descrip");
		ParseUtils.createPlan(plan, actions);
		Toast.makeText(this, "Plan create", Toast.LENGTH_SHORT).show();
	}
	
	public void onAddAction(View v) {
		FragmentManager fm = getSupportFragmentManager();		
		AddActionDialog compose = new AddActionDialog();
		compose.show(fm, "");
	}
	
	public void onSetDuration(View v) {
		FragmentManager fm = getSupportFragmentManager();		
		SetDurationDialog compose = new SetDurationDialog();
		compose.show(fm, "");
	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_plan, menu);
		return true;
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
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void finishToActivity(Action result) {
		fragmentDemo.populateAction(result);
	}

	@Override
	public void finishToActivityFromDuration(int result) {
		duration = result;
		tvduration.setText("Duration: "+result);
	}
}
