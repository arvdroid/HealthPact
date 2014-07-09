package com.codepath.healthpact.activity;

import com.codepath.healthpact.R;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.NumberPicker;

public class SetDurationDialog extends DialogFragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		Dialog dialog = getDialog();
		Window window = dialog.getWindow();
		View view = inflater.inflate(R.layout.set_plan_duration_view, container);
		window.requestFeature(Window.FEATURE_NO_TITLE);
		
		NumberPicker np = (NumberPicker) view.findViewById(R.id.numberPicker1);
		
		np.setMinValue(1);
	    np.setMaxValue(52);
	    np.setValue(10);
	    np.setWrapSelectorWheel(false);
	    
		return view;
	}

}
