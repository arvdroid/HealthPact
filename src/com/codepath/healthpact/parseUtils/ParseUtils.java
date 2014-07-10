package com.codepath.healthpact.parseUtils;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;

import com.activeandroid.util.Log;
import com.codepath.healthpact.models.AppUser;
import com.codepath.healthpact.models.UserPlan;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class ParseUtils {
	
	public static ParseUser getAppUserDetails(View v, final Context context, String appUserName) {

		ParseQuery<ParseUser> query = ParseQuery.getQuery(ParseUser.class);
		query.whereEqualTo("name", appUserName);
		try {
			return query.getFirst();
		} catch (ParseException parseEx) {
			parseEx.printStackTrace();
			Log.d("getAppUserDetails failed");
		}
		return null;
	}

	public static ArrayList<UserPlan> getUserPlans(View v, final Context context, String appUserName) {
		ParseQuery<AppUser> query = ParseQuery.getQuery(AppUser.class);
		query.whereEqualTo("name", appUserName);
		try {
			AppUser appUserList = query.getFirst();
			
			ParseQuery<UserPlan> userPlanQuery = ParseQuery.getQuery(UserPlan.class);
			userPlanQuery.whereEqualTo("user_id", appUserList.getObjectId());
			return (ArrayList<UserPlan>) userPlanQuery.find();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	// need to implement this msg for common error log
	private void LogMsg(String msg, int errLvl) {
		return;
	}

}
