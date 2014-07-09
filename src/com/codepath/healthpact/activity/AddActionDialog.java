package com.codepath.healthpact.activity;

import com.codepath.healthpact.R;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class AddActionDialog extends DialogFragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		Dialog dialog = getDialog();
		Window window = dialog.getWindow();
		View view = inflater.inflate(R.layout.add_action_view, container);
		window.requestFeature(Window.FEATURE_NO_TITLE);
		
		String[] imgSizeVals = new String[]{"Running", "Walking", "Yoga", "Eat Fruits", "Hot Therapy"};
		Spinner imgSizeSpinner = (Spinner)view.findViewById((R.id.spinner1));
		
        ArrayAdapter<String> adapters = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, imgSizeVals);
        imgSizeSpinner.setAdapter(adapters);
		
		return view;
	}
	
	
	public interface AddActionDialogListener {
    	void finishToActivity();
    }

}
