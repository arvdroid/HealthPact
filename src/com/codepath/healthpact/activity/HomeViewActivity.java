package com.codepath.healthpact.activity;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;

import com.codepath.healthpact.R;
import com.codepath.healthpact.dialogs.AddProfileDialog;
import com.codepath.healthpact.fragments.PlansFollowedFragment;
import com.codepath.healthpact.fragments.PlansSharedFragment;
import com.codepath.healthpact.listeners.FragmentTabListener;
import com.codepath.healthpact.parseUtils.ParseUtils;

public class HomeViewActivity extends FragmentActivity {
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_view);
		setupTabs();
	}
	
	private void setupTabs() {
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(true);

		Tab tab1 = actionBar
		    .newTab()
		    .setText("Followed")
		    .setTag("PlansFollowedFragment")
		    .setTabListener(new FragmentTabListener<PlansFollowedFragment>(R.id.flContainer, this,
                        "PlansFollowed", PlansFollowedFragment.class));

		actionBar.addTab(tab1);
		actionBar.selectTab(tab1);

		Tab tab2 = actionBar
		    .newTab()
		    .setText("Shared")
		    .setTag("PlansSharedFragment")
		    .setTabListener(new FragmentTabListener<PlansSharedFragment>(R.id.flContainer, this,
                        "PlansShared", PlansSharedFragment.class));
		actionBar.addTab(tab2);
	}
	
	public void onCreatePlan(){
		Intent i = new Intent(this, CreatePlanActivity.class);
		startActivity(i);
	}
	
	public void onFindPlan(){
		Intent i = new Intent(this, FindPlansActivity.class);
		startActivity(i);
	}
	
	public void onShowProdile(){
		String expertise = ParseUtils.getExpertise();
		if(expertise == null || expertise.equals("")) {
			Intent i = new Intent(this, NewUserProfileActivity.class);
			startActivity(i);
		} else {
			Intent i = new Intent(this, UserProfileActivity.class);
			startActivity(i);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home_view, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_add) {
			onCreatePlan();
			return true;
		}else if(id == R.id.action_find){
			onFindPlan();
			return true;
		}else if(id == R.id.action_profile){
			onShowProdile();
			return true;
		}
		
		return super.onOptionsItemSelected(item);
	}
}
