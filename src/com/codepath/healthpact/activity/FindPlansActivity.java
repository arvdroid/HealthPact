package com.codepath.healthpact.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.codepath.healthpact.R;
import com.codepath.healthpact.fragments.FindPlansFragment;

public class FindPlansActivity extends FragmentActivity {
	
	FindPlansFragment planFragment;
	EditText searchText;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_find_plans);

		TextView textView =(TextView)findViewById(R.id.tvFilter);
		Button findButton = (Button)findViewById(R.id.fpBSearch);
		searchText = (EditText)findViewById(R.id.fpSearchEditText);
		
		planFragment = (FindPlansFragment) 
                getSupportFragmentManager().findFragmentById(R.id.fpPlanViewFragment);
		planFragment.clearProgressBar();
				
		textView.setMovementMethod(LinkMovementMethod.getInstance());
		String text = "<u>Filter</u>";
		textView.setText(Html.fromHtml(text));
		textView.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				FragmentManager fm = getSupportFragmentManager();		
				AdvancedFilterDialog compose = new AdvancedFilterDialog();
				compose.show(fm, "");
			}
		});
		
		findButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {				
				planFragment.populateFindPlans(searchText.getText().toString());
			}
		});	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.find_plans, menu);
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
}
