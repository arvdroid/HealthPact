package com.codepath.healthpact.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.codepath.healthpact.R;

public class HomeViewActivity extends Activity {
	Button pv;
	Button av;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_view);
		
		pv = (Button)findViewById(R.id.button11);
		av = (Button)findViewById(R.id.button22);
		
		pv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(HomeViewActivity.this, PlanViewActivity.class);
				startActivity(i);
				
			}
		});
		
		av.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(HomeViewActivity.this, ActionDetailActivity.class);
				startActivity(i);
				
			}
		});
	}
	
	public void onCreatePlan(){
		Intent i = new Intent(this, CreatePlanActivity.class);
		startActivity(i);
	}
	
	public void onFindPlan(){
		Intent i = new Intent(this, FindPlansActivity.class);
		startActivity(i);
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
		}
		return super.onOptionsItemSelected(item);
	}
}
