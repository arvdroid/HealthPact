package com.codepath.healthpact.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.codepath.healthpact.R;
import com.codepath.healthpact.dialogs.AddProfileDialog;

public class NewUserProfileActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_profile);
		Button bAddProfile =(Button)findViewById(R.id.paBAddProfile);
		
		bAddProfile.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//Toast.makeText(FindPlansActivity.this, "test", Toast.LENGTH_SHORT).show();
				FragmentManager fm = getSupportFragmentManager();		
				AddProfileDialog compose = new AddProfileDialog();
				compose.show(fm, "");
			}
		});
	}	
}
