package com.codepath.healthpact.dialogs;

import com.codepath.healthpact.R;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

public class AddProfileDialog extends DialogFragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Dialog dialog = getDialog();
		Window window = dialog.getWindow();
		View view = inflater.inflate(R.layout.add_profile_layout, container);
		window.requestFeature(Window.FEATURE_NO_TITLE);
		
		return view;
	}
}
