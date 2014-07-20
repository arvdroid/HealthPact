package com.codepath.healthpact.activity;

import java.util.ArrayList;

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
import android.widget.TextView;
import android.widget.ToggleButton;

import com.codepath.healthpact.R;
import com.codepath.healthpact.container.PagerContainer;
import com.codepath.healthpact.models.ParseProxyObject;

public class ActionDetailActivity extends Activity {
	
	ToggleButton tbM;
    PagerContainer mContainer;
    View customPageView;
    ArrayList<String> alist;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.details_action_view);
		ParseProxyObject result = (ParseProxyObject) getIntent().getSerializableExtra("useraction");
		boolean followed = getIntent().getBooleanExtra("followed", false);
		TextView aName = (TextView)findViewById(R.id.datvActionName);
		TextView aDesc = (TextView)findViewById(R.id.aVtvDescription);
		TextView aPerDayCnt = (TextView)findViewById(R.id.datvActionPerDayTime);
		
		String actionname = result.getString("action_name");

		aName.setText(actionname);
		aDesc.setText(result.getString("action_desc"));
		aPerDayCnt.setText(result.getString("serving"));
		
        mContainer = (PagerContainer) findViewById(R.id.pager_container);
        loadData();
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
		
		if(!followed){
			//findViewById(R.id.aVlevelTwoLayout).setVisibility(View.INVISIBLE);
			//findViewById(R.id.layoutWeeks).setVisibility(View.INVISIBLE);
			//findViewById(R.id.actionProgressBar).setVisibility(View.INVISIBLE);
		}else{

			final Drawable onD = (Drawable)getResources().getDrawable(R.drawable.custom_week_layout_on);
			final Drawable offD = (Drawable)getResources().getDrawable(R.drawable.custom_week_layout);

			/*tbM = (ToggleButton)findViewById(R.id.toggleButtonM);
			tbM.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					boolean on = ((ToggleButton) v).isChecked();
					if (on) {
						//Toast.makeText(HealthPactActivity.this, "on", Toast.LENGTH_SHORT).show();			        
						((ToggleButton) v).setBackground(onD);
					} else {
						//Toast.makeText(HealthPactActivity.this, "off", Toast.LENGTH_SHORT).show();
						((ToggleButton) v).setBackground(offD);
					}				
				}
			}); */
		}
	}
	
    public void loadData() {
    	alist = new ArrayList<String>();
    	alist.add("week 1");
    	alist.add("week 2");
    	alist.add("week 3");
    	alist.add("week 4");
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
            //TextView view = new TextView(PagerActivity.this);
            //view.setText("sample "+alist.get(position));
        	customPageView = getLayoutInflater().inflate(R.layout.week_layout, null);
        	TextView tv = (TextView) customPageView.findViewById(R.id.tvVWeek);
        	tv.setText(alist.get(position));
            //view.setGravity(Gravity.CENTER);
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
            return alist.size();
        }
 
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return (view == object);
        }
    }
	
}
