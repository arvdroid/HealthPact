package com.codepath.healthpact.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.healthpact.R;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUpActivity extends Activity {
	ParseUser user = new ParseUser();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up);
	}
	
	public void onHomeView(View v) {

		ParseUser.logOut();
		
		TextView tvUserName = (TextView) findViewById(R.id.etUserName);
		TextView tvPassword = (TextView) findViewById(R.id.etPass);
		TextView tvPasswordVerify = (TextView) findViewById(R.id.etPassVerify);
		TextView tvEmail = (TextView) findViewById(R.id.etEmail);
		
		if (!tvPassword.getText().toString().equals(tvPasswordVerify.getText().toString())) {
			Toast.makeText(getApplicationContext(), "Password Mismatch", Toast.LENGTH_SHORT).show();
			return;
		}
		
		user.setUsername(tvUserName.getText().toString());
		user.setPassword(tvPassword.getText().toString());
		user.setEmail(tvEmail.getText().toString());
		 
		// other fields can be set just like with ParseObject
		user.put("phone", "650-253-0000");
		 
		user.signUpInBackground(new SignUpCallback() {
		  public void done(ParseException e) {
		    if (e == null) {
		      // Hooray! Let them use the app now.
				Intent i = new Intent(SignUpActivity.this, HomeViewActivity.class);
				startActivity(i);
		    } else {
		      // Sign up didn't succeed. Look at the ParseException
		      // to figure out what went wrong
				Toast.makeText(getApplicationContext(), ParseUser.getCurrentUser().getUsername() + " is invalid, try different user.", Toast.LENGTH_SHORT).show();
		    }
		  }
		});		
	}

}
