package com.codepath.healthpact.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.codepath.healthpact.R;

public class FindPlansActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_find_plans);
		setContentView(R.layout.activity_find_plans);
		TextView textView =(TextView)findViewById(R.id.tvFilter);
		//textView.setClickable(true);
		textView.setMovementMethod(LinkMovementMethod.getInstance());
		String text = "<u>Filter</u>";
		textView.setText(Html.fromHtml(text));
		textView.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				//Toast.makeText(FindPlansActivity.this, "test", Toast.LENGTH_SHORT).show();
				FragmentManager fm = getSupportFragmentManager();		
				AdvancedFilterDialog compose = new AdvancedFilterDialog();
				compose.show(fm, "");
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
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
