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
import android.widget.Toast;

import com.codepath.healthpact.R;
import com.codepath.healthpact.models.Action;

public class AddActionDialog extends DialogFragment {
	
	Spinner actionCategorySpinner;
	EditText perDay;
	EditText aDesc;
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
		aDesc = (EditText)view.findViewById((R.id.tv_aATvDescEdit));
		
        ArrayAdapter<String> adapters = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, imgSizeVals);
        actionCategorySpinner.setAdapter(adapters);
        
        Button bDone = (Button)view.findViewById((R.id.aABDone));
        bDone.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {				
				String aP = perDay.getText().toString();
				if(!aP.isEmpty()){
					String aC = (String)actionCategorySpinner.getSelectedItem();
					String sDesc = aDesc.getText().toString();
					Action nA = new Action();
					nA.setActionName(aC);
					nA.setActionServing(aP);
					nA.setActionDesc(sDesc);
					AddActionDialogListener activity = (AddActionDialogListener)getActivity();
					activity.finishToActivity(nA);
					dismiss();
				}else{
					Toast.makeText(getActivity(),"Please Enter Per Day", Toast.LENGTH_LONG).show();
				}
			}
		});
		
		return view;
	}
	
	public interface AddActionDialogListener {
    	void finishToActivity(Action result);
    }

}
