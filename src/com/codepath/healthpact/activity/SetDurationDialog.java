package com.codepath.healthpact.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.NumberPicker;

import com.codepath.healthpact.R;
import com.codepath.healthpact.activity.AddActionDialog.AddActionDialogListener;
import com.codepath.healthpact.models.Action;

public class SetDurationDialog extends DialogFragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		Dialog dialog = getDialog();
		Window window = dialog.getWindow();
		View view = inflater.inflate(R.layout.set_plan_duration_view, container);
		window.requestFeature(Window.FEATURE_NO_TITLE);
		
		final NumberPicker np = (NumberPicker) view.findViewById(R.id.pdnumberPicker);
		
		np.setMinValue(1);
	    np.setMaxValue(52);
	    np.setValue(10);
	    np.setWrapSelectorWheel(false);
	    
	    Button bDone = (Button)view.findViewById((R.id.pdDonebutton));
        bDone.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {				
				
				SetDurationDialogListener activity = (SetDurationDialogListener)getActivity();
				activity.finishToActivityFromDuration(np.getValue());
				dismiss();
			}
		});
	    
		return view;
	}
	
	public interface SetDurationDialogListener {
    	void finishToActivityFromDuration(int result);
    }

}
