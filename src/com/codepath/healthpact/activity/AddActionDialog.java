package com.codepath.healthpact.activity;

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
import android.widget.EditText;
import android.widget.Spinner;

import com.codepath.healthpact.R;
import com.codepath.healthpact.models.Action;

public class AddActionDialog extends DialogFragment {
	
	Spinner actionCategorySpinner;
	EditText perDay;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		Dialog dialog = getDialog();
		Window window = dialog.getWindow();
		View view = inflater.inflate(R.layout.add_action_view, container);
		window.requestFeature(Window.FEATURE_NO_TITLE);
		
		String[] imgSizeVals = new String[]{"Hot Therapy", "Lift Weights", "Running", "Walking", "Yoga"};
		actionCategorySpinner = (Spinner)view.findViewById((R.id.spinner1));
		
		perDay = (EditText)view.findViewById((R.id.tv_aAEditPerDay));
		
        ArrayAdapter<String> adapters = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, imgSizeVals);
        actionCategorySpinner.setAdapter(adapters);
        
        Button bDone = (Button)view.findViewById((R.id.aABDone));
        bDone.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {				
				String aC = (String)actionCategorySpinner.getSelectedItem();
				String aP = perDay.getText().toString();
				Action nA = new Action();
				nA.setActionName(aC);
				AddActionDialogListener activity = (AddActionDialogListener)getActivity();
				activity.finishToActivity(nA);
				dismiss();
			}
		});
		
		return view;
	}
	
	public interface AddActionDialogListener {
    	void finishToActivity(Action result);
    }

}
