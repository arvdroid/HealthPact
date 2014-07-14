package com.codepath.healthpact.dialogs;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.healthpact.R;
import com.codepath.healthpact.activity.UserProfileActivity;
import com.codepath.healthpact.models.AppUser;
import com.codepath.healthpact.parseUtils.ParseUtils;

public class AddProfileDialog extends DialogFragment {

	private EditText ProfileExpertise;
	private EditText ProfileLocation;
	private EditText ProfileDescription;
	private TextView UserName;
	AppUser appUser;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Dialog dialog = getDialog();
		Window window = dialog.getWindow();
		View view = inflater.inflate(R.layout.add_profile_layout, container);
		window.requestFeature(Window.FEATURE_NO_TITLE);

		UserName = (TextView) view.findViewById(R.id.apEtName);
		ProfileExpertise = (EditText) view.findViewById(R.id.apEtExpertise);
		ProfileLocation = (EditText) view.findViewById(R.id.apEtLocation);
		ProfileDescription = (EditText) view.findViewById(R.id.apEtDescription);

		UserName.setText(ParseUtils.getUserName());
		ProfileExpertise.setText(ParseUtils.getExpertise());
		ProfileLocation.setText(ParseUtils.getLocation());
		ProfileDescription.setText(ParseUtils.getDescription());

		final Button doneButton = (Button) view.findViewById(R.id.aABDone);
		final Button cancelButton = (Button) view.findViewById(R.id.aABCancel);

		doneButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if(ProfileExpertise.getText().toString() != null && !ProfileExpertise.getText().toString().equals("")){
					ParseUtils.updateProfile(ProfileExpertise.getText().toString(),ProfileLocation.getText().toString(),ProfileDescription.getText().toString());
					Intent i = new Intent(getActivity(), UserProfileActivity.class);
					startActivity(i);
					AddProfileDialog.this.dismiss();
				} else {
					Toast.makeText(getActivity(),"Please Enter Expertise", Toast.LENGTH_LONG).show();
				}
			}
		});

		cancelButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				dismiss();
			}
		});
		return view;
	}
}
