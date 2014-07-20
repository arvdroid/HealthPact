package com.codepath.healthpact.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.codepath.healthpact.R;
import com.codepath.healthpact.container.PagerContainer;
import com.codepath.healthpact.models.ActionPerPeriod;
import com.codepath.healthpact.models.ActionPerPeriod.ActionPerDay;
import com.codepath.healthpact.models.ParseProxyObject;
import com.codepath.healthpact.parseUtils.ParseUtils;

public class ActionDetailActivity extends Activity {
	
	Drawable onD;
	
    PagerContainer mContainer;
    View customPageView;
    Map<Date, ActionPerDay> mapOfWeeks;
    int currentWeekIndex;
    List<ActionPerDay> listOfWeeks;
    
    String pattern = "MM/dd/yyyy";
	SimpleDateFormat format = new SimpleDateFormat(pattern);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.details_action_view);
		ParseProxyObject result = (ParseProxyObject) getIntent().getSerializableExtra("useraction");
		//boolean followed = getIntent().getBooleanExtra("followed", false);
		String usrPlanId = getIntent().getStringExtra("usrPlanId");
		String actionid = getIntent().getStringExtra("actionid");
		int progress = getIntent().getIntExtra("actionProgress", 0);
		
		TextView aName = (TextView)findViewById(R.id.datvActionName);
		TextView aDesc = (TextView)findViewById(R.id.aVtvDescription);
		TextView aPerDayCnt = (TextView)findViewById(R.id.datvActionPerDayTime);
		ProgressBar planActionProgress = (ProgressBar)findViewById(R.id.actionProgressBar);
		
		String actionname = result.getString("action_name");

		aName.setText(actionname);
		aDesc.setText(result.getString("action_desc"));
		aPerDayCnt.setText(result.getString("serving"));
		planActionProgress.setProgress(progress);
		
        mContainer = (PagerContainer) findViewById(R.id.pager_container);
        loadData(usrPlanId, actionid);
        ViewPager pager = mContainer.getViewPager();
        
        PagerAdapter adapter = new MyPagerAdapter();
        pager.setAdapter(adapter);
        //Necessary or the pager will only have one extra page to show
        // make this at least however many pages you can see
        pager.setOffscreenPageLimit(adapter.getCount());
        //A little space between pages
        pager.setPageMargin(10);
 
        //If hardware acceleration is enabled, you should also remove
        // clipping on the pager for its children.
        pager.setClipChildren(false);
        
		onD = (Drawable)getResources().getDrawable(R.drawable.custom_week_layout_on);
	}
	
    public void loadData(String usrPlanId, String actionId) {
    	    	
    	ActionPerPeriod app = ParseUtils.getPlanRelationPerDuration(usrPlanId, actionId);
    	ParseUtils.setCurrentWeekStartEndDate(app.getCurrentWeek());
    	listOfWeeks = new ArrayList<ActionPerPeriod.ActionPerDay>();
				
		for (Entry<Date, ActionPerDay> entry: app.getResultSet().entrySet())
		{
			ActionPerDay apd = entry.getValue();
			listOfWeeks.add(apd);
		}
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.action_detail, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
    //Nothing special about this adapter, just throwing up colored views for demo
    private class MyPagerAdapter extends PagerAdapter {
 
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            
        	customPageView = getLayoutInflater().inflate(R.layout.week_layout, null);
        	TextView tv = (TextView) customPageView.findViewById(R.id.tvVWeek);
        	TextView weekStatus = (TextView) customPageView.findViewById(R.id.tvActionStatus);
        	ToggleButton tbM = (ToggleButton)customPageView.findViewById(R.id.toggleButtonM);
        	ToggleButton tbT = (ToggleButton)customPageView.findViewById(R.id.toggleButtonT);
        	ToggleButton tbW = (ToggleButton)customPageView.findViewById(R.id.toggleButtonW);
        	ToggleButton tbTh = (ToggleButton)customPageView.findViewById(R.id.toggleButtonTh);
        	ToggleButton tbF = (ToggleButton)customPageView.findViewById(R.id.toggleButtonF);
        	ToggleButton tbSa = (ToggleButton)customPageView.findViewById(R.id.toggleButtonS);
        	ToggleButton tbS = (ToggleButton)customPageView.findViewById(R.id.toggleButtonSu);
        	
        	ActionPerDay perWeek = listOfWeeks.get(position);
        	
        	tv.setText(format.format(perWeek.getWeek().getStartDate()) + " - "+ format.format(perWeek.getWeek().getEndDate()));
			
        	boolean[] actionPerDayStatus = perWeek.getDones();
        	int cntOfTrue = 0;
        	for(int i =0;i<actionPerDayStatus.length;i++){
        		boolean update = actionPerDayStatus[i];
        		if(update)
        			cntOfTrue++;
        		
        		switch (i) {
        			case 0 :
        				if(update)
        					tbS.setBackground(onD);
        				break;
        			case 1:
        				if(update)
        					tbM.setBackground(onD);
        				break;
        			case 2:
        				if(update)
        					tbT.setBackground(onD);
        				break;
        			case 3:
        				if(update)
        					tbW.setBackground(onD);
        				break;        				
        			case 4:
        				if(update)
        					tbTh.setBackground(onD);
        				break;
        			case 5:
        				if(update)
        					tbF.setBackground(onD);
        				break;
        			case 6:
        				if(update)
        					tbSa.setBackground(onD);
        				break;
        		}
        	}
        	weekStatus.setText(cntOfTrue+"/7");
        	
        	//
            customPageView.setBackgroundColor(Color.argb(255, 255, 255, 255));
            container.addView(customPageView);
            return customPageView;
        }
 
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View)object);
        }
 
        @Override
        public int getCount() {
            return listOfWeeks.size();
        }
 
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return (view == object);
        }
    }
	
}
