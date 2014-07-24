package com.codepath.healthpact.activity;

import java.text.SimpleDateFormat;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.codepath.healthpact.R;
import com.codepath.healthpact.dialogs.AddProfileDialog;
import com.codepath.healthpact.fragments.PlanListFragment;
import com.codepath.healthpact.parseUtils.ParseUtils;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.parse.ParseUser;

import java.io.IOException;
import java.util.List;

import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class UserProfileActivity extends FragmentActivity implements OnMapClickListener, OnMapLongClickListener {
	PlanListFragment planFragment;
	String pattern = "MM/dd/yyyy";
	SimpleDateFormat format = new SimpleDateFormat(pattern);
	TextView usrPlansCnt;
	TextView usrPlansFollowingCnt;
	TextView usrFollowersCnt;
	String zipcode = null;

    // Google Map
    private GoogleMap googleMap;
	LocationManager locationManager;
	String locationProvider;
	MarkerOptions markerOptions;
	LatLng latLng;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_profile);
		
		TextView userName = (TextView)findViewById(R.id.uptvCreatedBy);
		TextView createdDate = (TextView)findViewById(R.id.uptvCreatedDate);
		TextView updatedDate = (TextView)findViewById(R.id.uptvUpdatedDate);
		TextView location = (TextView)findViewById(R.id.uptvLocation);
		TextView description = (TextView)findViewById(R.id.uptvDescription);

		usrPlansCnt = (TextView)findViewById(R.id.uptvPlansCnt);
		usrPlansFollowingCnt = (TextView)findViewById(R.id.uptvFollowingCnt);
		usrFollowersCnt = (TextView)findViewById(R.id.uptvFollowersCnt);
		
		ParseUser user = ParseUser.getCurrentUser();
		zipcode = user.getString("location");
		createdDate.setText("Posted: " + format.format(user.getCreatedAt()));
		updatedDate.setText("Updated: "+format.format(user.getUpdatedAt()));		
		userName.setText("Name: "+user.getUsername());
		description.setText(user.getString("desc"));
		location.setText("Location: "+zipcode);

		populateUserProfileCounts();
		
		try {
            // Loading map
            initilizeMap();

        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	private void populateUserProfileCounts(){
		int plansC = ParseUtils.getPlansCreatedByCurrentUser().size();
		int plansF = ParseUtils.getUserFollowedPlans(null).size();
		int plansFs = ParseUtils.getUserFollowerPlansCount();
		usrPlansCnt.setText(String.valueOf(plansC));
		usrPlansFollowingCnt.setText(String.valueOf(plansF));
		usrFollowersCnt.setText(String.valueOf(plansFs));
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.user_profile, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.userprofile_edit) {
			FragmentManager fm = getSupportFragmentManager();		
			AddProfileDialog compose = new AddProfileDialog();
			compose.show(fm, "");
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	//Map render codeline
	private void initilizeMap() {
		// Do a null check to confirm that we have not already instantiated the map.
	    if (googleMap == null) {
	    	SupportMapFragment sfm = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
	    	googleMap = sfm.getMap();
	        // Check if we were successful in obtaining the map.
	        if (googleMap == null) {
	           // The Map is verified. It is now safe to manipulate the map.
                Toast.makeText(getApplicationContext(), "Sorry! unable to create maps", Toast.LENGTH_SHORT).show();
	        }
	        else {
	            // Initialize map options. For example:
	            googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
	            
	          //display zoom map 
	            ///googleMap.moveCamera( CameraUpdateFactory.zoomTo(13.2f)); 
	            // Enabling MyLocation Layer of Google Map googleMap.setMyLocationEnabled(true); 
	            ///googleMap.setOnMapClickListener(this); 
	            ///googleMap.setOnMapLongClickListener(this);
	            
	            if ((zipcode != null) && (!zipcode.isEmpty())) {
	            	new GeocoderTask().execute(zipcode);
	            }
	        }
	    }		   
	}

	// An AsyncTask class for accessing the GeoCoding Web Service
	private class GeocoderTask extends AsyncTask<String, Void, List<Address>> {

		@Override
		protected List<Address> doInBackground(String... locationName) {
			// Creating an instance of Geocoder class
			Geocoder geocoder = new Geocoder(getBaseContext());
			List<Address> addresses = null;

			try {
				// Getting a maximum of 3 Address that matches the input text
				addresses = geocoder.getFromLocationName(locationName[0], 3);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return addresses;
		}

		@Override
		protected void onPostExecute(List<Address> addresses) {

			if (addresses == null || addresses.size() == 0) {
				Toast.makeText(getBaseContext(), "No Location found",
						Toast.LENGTH_SHORT).show();
			}

			// Clears all the existing markers on the map
			googleMap.clear();

			// Adding Markers on Google Map for each matching address
			for (int i = 0; i < addresses.size(); i++) {

				Address address = (Address) addresses.get(i);

				// Creating an instance of GeoPoint, to display in Google Map
				latLng = new LatLng(address.getLatitude(),
						address.getLongitude());

				String addressText = String.format(
						"%s, %s",
						address.getMaxAddressLineIndex() > 0 ? address
								.getAddressLine(0) : "", address
								.getCountryName());

				markerOptions = new MarkerOptions();
				markerOptions.position(latLng);
				markerOptions.title(addressText);

				googleMap.addMarker(markerOptions);

				// Locate the first location
				if (i == 0)
					googleMap.animateCamera(CameraUpdateFactory
							.newLatLng(latLng));
			}
		}
	}

	@Override
    protected void onResume() {
        super.onResume();
        initilizeMap();
    }
	
	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override public void onMapClick(LatLng point) { 
		//add marker 
		MarkerOptions marker=new MarkerOptions(); 
		marker.position(point); googleMap.addMarker(marker); 
		//add circle 
		CircleOptions circle=new CircleOptions(); 
		circle.center(point).fillColor(Color.LTGRAY).radius(1000); 
		googleMap.addCircle(circle); 
	} 
	
	@Override public void onMapLongClick(LatLng point) { 
		googleMap.clear(); 
	}
	
}
