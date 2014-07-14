package com.codepath.healthpact.activity;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.codepath.healthpact.R;
import com.codepath.healthpact.models.Action;

public class ActionDetailActivity extends Activity {
	
	ToggleButton tbM;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.details_action_view);
		Action result = (Action) getIntent().getSerializableExtra("useraction");
		
		TextView aName = (TextView)findViewById(R.id.datvActionName);
		TextView aDesc = (TextView)findViewById(R.id.aVtvDescription);
		
		aName.setText(result.getActionName());
		
		final Drawable onD = (Drawable)getResources().getDrawable(R.drawable.custom_week_layout_on);
		final Drawable offD = (Drawable)getResources().getDrawable(R.drawable.custom_week_layout);
		
		tbM = (ToggleButton)findViewById(R.id.toggleButtonM);
		tbM.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				boolean on = ((ToggleButton) v).isChecked();
			    if (on) {
			        //Toast.makeText(HealthPactActivity.this, "on", Toast.LENGTH_SHORT).show();			        
			        ((ToggleButton) v).setBackground(onD);
			    } else {
			    	//Toast.makeText(HealthPactActivity.this, "off", Toast.LENGTH_SHORT).show();
			    	 ((ToggleButton) v).setBackground(offD);
			    }				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.action_detail, menu);
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
