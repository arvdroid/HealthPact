package com.codepath.healthpact.activity;

import java.util.Date;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.codepath.healthpact.R;
import com.codepath.healthpact.models.Plan;
import com.codepath.healthpact.models.UserPlan;

public class CreatePlanActivity extends FragmentActivity {
	EditText etPlanName; 
	Button btnSave;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_plan);
		
		etPlanName = (EditText) findViewById(R.id.lblcPPlanNameEditText);
		btnSave = (Button) findViewById(R.id.cpBSavePlan);

		PlanTextChange();
		PlanSave();
	}
	
	private void PlanSave() {
		btnSave.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				UserPlan userPlan = new UserPlan();
				Plan plan = new Plan();
				plan.setPlanDesc(etPlanName.getText().toString());
				plan.saveEventually();
			}
		});
	}

	private void PlanTextChange() {
		etPlanName.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				searchPlan();
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}
	
	private void searchPlan() {
		
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
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
