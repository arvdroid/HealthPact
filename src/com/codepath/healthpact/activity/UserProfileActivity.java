package com.codepath.healthpact.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.codepath.healthpact.R;
import com.codepath.healthpact.dialogs.AddProfileDialog;
import com.codepath.healthpact.fragments.PlanListFragment;
import com.codepath.healthpact.models.AppPlan;
import com.codepath.healthpact.models.Plan;
import com.codepath.healthpact.parseUtils.ParseUtils;
import com.parse.ParseUser;

public class UserProfileActivity extends FragmentActivity {
	PlanListFragment planFragment;
	String pattern = "MM/dd/yyyy";
	SimpleDateFormat format = new SimpleDateFormat(pattern);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_profile);
		
		TextView userName = (TextView)findViewById(R.id.uptvCreatedBy);
		TextView createdDate = (TextView)findViewById(R.id.uptvCreatedDate);
		TextView updatedDate = (TextView)findViewById(R.id.uptvUpdatedDate);
		
		ParseUser user = ParseUser.getCurrentUser();
		createdDate.setText(format.format(user.getCreatedAt()));
		updatedDate.setText(format.format(user.getUpdatedAt()));		
		userName.setText("Name: "+user.getUsername());
		
		planFragment = (PlanListFragment) 
                getSupportFragmentManager().findFragmentById(R.id.upPlanViewFragment);
		populateUserCreatedPlans();
	}
	
	private void populateUserCreatedPlans()	{
		ArrayList<Plan> plans = ParseUtils.getPlansCreatedByCurrentUser();
		List<AppPlan> appPlans = new ArrayList<AppPlan>();
		Log.d("hp", "user plans: "+ plans.size());
		for(Plan plan: plans){
			AppPlan ap = new AppPlan();
			ap.setName(plan.getPlanName());
			ap.setDuration(plan.getPlanDuration());
			appPlans.add(ap);
		}
		planFragment.populatePlans(appPlans);	
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.user_profile, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.userprofile_edit) {
			FragmentManager fm = getSupportFragmentManager();		
			AddProfileDialog compose = new AddProfileDialog();
			compose.show(fm, "");
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
