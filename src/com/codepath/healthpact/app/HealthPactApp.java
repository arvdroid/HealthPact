package com.codepath.healthpact.app;

import android.content.Context;
import android.widget.Toast;

import com.codepath.healthpact.activity.ParseStarterProjectActivity;
import com.codepath.healthpact.models.Action;
import com.codepath.healthpact.models.Plan;
import com.codepath.healthpact.models.PlanAction;
import com.codepath.healthpact.models.PlanShared;
import com.codepath.healthpact.models.UserPlan;
import com.codepath.healthpact.models.UserPlanRelation;
import com.codepath.healthpact.restclient.RestClient;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.PushService;

/*
 * This is the Android application itself and is used to configure various settings
 * including the image cache in memory and on disk. This also adds a singleton
 * for accessing the relevant rest client.
 * 
 *     RestClient client = HealthPactApp.getRestClient();
 *     // use client to send requests to API
 *     
 */
public class HealthPactApp extends com.activeandroid.app.Application {
	private static Context context;
	public static ParseUser currentUser;	// to hold the current user
	public static String strUser = "dipankar";
	public static String strPwd = "aravinda";
	public static String strEmailAddress = "dipankar14@gmail.com";


    @Override
    public void onCreate() {
        super.onCreate();
        HealthPactApp.context = this;
        
        // Parse initialize
        parseInit();
        //parseSignUp();
        //parseLogin(strUser, strPwd);
        registerParseAppTables();
        
        // Create global configuration and initialize ImageLoader with this configuration
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder().
        		cacheInMemory().cacheOnDisc().build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
            .defaultDisplayImageOptions(defaultOptions)
            .build();
        ImageLoader.getInstance().init(config);
    }
    
    public static RestClient getRestClient() {
    	return (RestClient) RestClient.getInstance(RestClient.class, HealthPactApp.context);
    }
    
	private void parseInit() {
		// parse connection by using the keys
		Parse.initialize(this, "noOZrPtlAS5V2xWMPMr4LkYZIiNwt2Wkegry8K37",	"dMRniLjifTp83vL6VdxkYefnnapt684Ilu3KGrUS");

		ParseUser.enableAutomaticUser();
		ParseACL defaultACL = new ParseACL();

		// If you would like all objects to be private by default, remove this
		// line.
		defaultACL.setPublicReadAccess(true);
		ParseACL.setDefaultACL(defaultACL, true);

		// default Activity to handle push notifications,
		PushService.setDefaultPushCallback(this, ParseStarterProjectActivity.class);
		ParseInstallation.getCurrentInstallation().saveInBackground();
	}
	
	private void registerParseAppTables() {
	    ParseObject.registerSubclass(Plan.class);
	    ParseObject.registerSubclass(UserPlan.class);
	    ParseObject.registerSubclass(PlanShared.class);
	    ParseObject.registerSubclass(Action.class);
	    ParseObject.registerSubclass(UserPlanRelation.class);
	    ParseObject.registerSubclass(PlanAction.class);
	}
	
	@SuppressWarnings("unused")
	public void logout() {
		currentUser = ParseUser.getCurrentUser();
		if (currentUser != null) {
			ParseUser.logOut();
			currentUser = ParseUser.getCurrentUser();
			if (currentUser != null) {
				Toast.makeText(getApplicationContext(), "Log out failed", Toast.LENGTH_SHORT).show();
			}
			else {
				Toast.makeText(getApplicationContext(), "Log out successfull", Toast.LENGTH_SHORT).show();
			}
		} else {
		  // show the signup or login screen
		}
	}
}