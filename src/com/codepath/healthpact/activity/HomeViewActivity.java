package com.codepath.healthpact.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.codepath.healthpact.R;
import com.codepath.healthpact.drawer.HomeViewNavigationDrawer;
import com.codepath.healthpact.fragments.PlansCreatedFragment;
import com.codepath.healthpact.fragments.PlansFollowedFragment;
import com.codepath.healthpact.fragments.PlansSharedFragment;
import com.codepath.healthpact.parseUtils.ParseUtils;

public class HomeViewActivity extends FragmentActivity {
	
	private HomeViewNavigationDrawer dlDrawer;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_view);
		dlDrawer = (HomeViewNavigationDrawer) findViewById(R.id.drawer_layout);
		// Setup drawer view
		dlDrawer.setupDrawerConfiguration((ListView) findViewById(R.id.lvDrawer), 
                     R.layout.drawer_nav_item, R.id.flContent);
		// Add nav items
				
		dlDrawer.addNavItem("Plans Followed", "Plans Followed", PlansFollowedFragment.class);
		dlDrawer.addNavItem("Plans Shared By Expert", "Plans Shared By Expert", PlansSharedFragment.class);
		dlDrawer.addNavItem("Plans Created", "Plans Created", PlansCreatedFragment.class);
		// Select default
		if (savedInstanceState == null) {
			dlDrawer.selectDrawerItem(0);
		}
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
	public boolean onPrepareOptionsMenu(Menu menu) {
		// If the nav drawer is open, hide action items related to the content
		if (dlDrawer.isDrawerOpen()) {
			//menu.findItem(R.id.mi_test).setVisible(false);
		}
		return super.onPrepareOptionsMenu(menu);
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
		if (dlDrawer.getDrawerToggle().onOptionsItemSelected(item)) {
			return true;
		}else if (id == R.id.action_add) {
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
	
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		dlDrawer.getDrawerToggle().syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggles
		dlDrawer.getDrawerToggle().onConfigurationChanged(newConfig);
	}
}
