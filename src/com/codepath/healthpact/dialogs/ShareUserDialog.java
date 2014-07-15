package com.codepath.healthpact.dialogs;

import java.util.List;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.activeandroid.util.Log;
import com.codepath.healthpact.R;
import com.codepath.healthpact.adapters.SharedUserSpinnerAdapter;
import com.codepath.healthpact.parseUtils.ParseUtils;
import com.parse.ParseUser;

public class ShareUserDialog extends DialogFragment{
	Spinner userSpinner;
	String planid;
	String usrPlanid;
	
	public ShareUserDialog(){}

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		Dialog dialog = getDialog();
		Window window = dialog.getWindow();
		View view = inflater.inflate(R.layout.share_user_view, container);
		window.requestFeature(Window.FEATURE_NO_TITLE);
		
		userSpinner = (Spinner)view.findViewById((R.id.sUspinner));
		planid = getArguments().getString("planid");
		usrPlanid = getArguments().getString("usrplanid");
		
		List<ParseUser> users = ParseUtils.getAllUsers(false);
		Log.d("healthpact", "user spinner: "+ users.size());
		ArrayAdapter<ParseUser> adapters = new SharedUserSpinnerAdapter(this.getActivity(), users);
		
		userSpinner.setAdapter(adapters);

		Button bDone = (Button)view.findViewById((R.id.sUBDone));
		bDone.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ParseUser selectedUser = (ParseUser)userSpinner.getSelectedItem();
				ParseUtils.convertPlanToShared(selectedUser.getObjectId(), planid);
				dismiss();
			}
		});

		return view;
	}
}
