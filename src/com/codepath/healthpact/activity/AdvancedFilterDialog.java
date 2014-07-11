package com.codepath.healthpact.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;

import com.codepath.healthpact.R;

public class AdvancedFilterDialog extends DialogFragment {
	ListView  lvC;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		Dialog dialog = getDialog();
		Window window = dialog.getWindow();
		View view = inflater.inflate(R.layout.advanced_filter_view, container);
		window.requestFeature(Window.FEATURE_NO_TITLE);
		
		String[] imgSizeVals = new String[]{"Running", "Walking", "Yoga", "Eat Fruits", "Hot Therapy"};
		//Spinner imgSizeSpinner = (Spinner)view.findViewById((R.id.fdCategories));
		lvC = (ListView)view.findViewById((R.id.fdLvCategories));
		
        //ArrayAdapter<String> adapters = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_multiple_choice, imgSizeVals);
		FindCategorySpinnerAdapter adapters  = new FindCategorySpinnerAdapter(this.getActivity(), imgSizeVals);
		lvC.setAdapter(adapters);
		
		TextView textView =(TextView)view.findViewById(R.id.fdTvSelectCategories);
		/*String text = "<u>Select Categories</u>";
		textView.setText(Html.fromHtml(text));
		textView.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(lvC.getVisibility() == View.VISIBLE)
					lvC.setVisibility(View.GONE);
				else
					lvC.setVisibility(View.VISIBLE);
			}
		});*/
        		
		return view;
	}
	
	
	private TextView findViewById(int fdtvselectcategories) {
		// TODO Auto-generated method stub
		return null;
	}


	public interface AddActionDialogListener {
    	void finishToActivity();
    }

}
