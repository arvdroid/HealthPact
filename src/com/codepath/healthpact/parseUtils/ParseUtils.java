package com.codepath.healthpact.parseUtils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.util.Log;
import com.codepath.healthpact.R;
import com.codepath.healthpact.activity.LoginActivity;
import com.codepath.healthpact.app.HealthPactApp;
import com.codepath.healthpact.models.Plan;
import com.codepath.healthpact.models.PlanShared;
import com.codepath.healthpact.models.UserPlan;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class ParseUtils {
	public static ArrayList<UserPlan> userPlansList;
	
	public static void parseLoginForTesting() {
		ParseUser.logInInBackground("dipankar", "dipankar", new LogInCallback() {
			  public void done(ParseUser user, ParseException e) {
			    if (user != null) {
			      // Hooray! The user is logged in.
				} else {
					// Signup failed. Look at the ParseException to see what happened.
				}
			  }
			});
	}
	
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

	/**
	 * Get plan detail for the provided user from UserPlan table
	 * @param v View
	 * @return a list of user plans
	 */
	public static ArrayList<UserPlan> getUserPlansByGivenUser(View v, String user_id) {
		ArrayList<UserPlan> userPlans = null;
		ParseQuery<ParseUser> query = ParseQuery.getQuery(ParseUser.class);
		query.whereEqualTo("objectId", user_id);
		
		try {
			ParseUser user = query.getFirst();
			
			ParseQuery<UserPlan> userPlanQuery = ParseQuery.getQuery(UserPlan.class);
			userPlanQuery.whereEqualTo("user_id", user.getObjectId());
			userPlans = (ArrayList<UserPlan>) userPlanQuery.find();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return userPlans;
	}
	
	/**
	 * Get plan detail with matching plan name for the current user from UserPlan table
	 * @param v View
	 * @return a list of user plans
	 */
	public static ArrayList<Plan> getUserPlans(View v, String partialPlanName) {
		ArrayList<Plan> plans = null;

		ParseQuery<ParseObject> innerUserPlanQuery = new ParseQuery<ParseObject>("UserPlan");
		innerUserPlanQuery.whereEqualTo("user_id", ParseUser.getCurrentUser().getObjectId());

		ParseQuery<Plan> query = ParseQuery.getQuery(Plan.class);
		query.whereStartsWith("plan_desc", partialPlanName);
		query.whereMatchesQuery("plan_id", innerUserPlanQuery);

		
		try {
			plans = (ArrayList<Plan>) query.find();
		} catch (ParseException parseEx) {
			LogMsg(parseEx, 1);
		}
		return plans;
	}

	/**
	 * Get plan desc from Plan for user plans and for the current user from UserPlan table
	 * @param v View
	 * @return a list of user plans
	 */
	public static ArrayList<UserPlan> getUserPlansWithDetails(View v) {
		return getUserPlansWithDetails();
	}

	public static ArrayList<UserPlan> getUserPlansWithDetails() {
		ArrayList<UserPlan> userPlans = null;
		ParseQuery<UserPlan> userPlanQuery = ParseQuery
				.getQuery(UserPlan.class);
		userPlanQuery.whereEqualTo("user_id", ParseUser.getCurrentUser().getObjectId());
		
		try {
			userPlans = (ArrayList<UserPlan>) userPlanQuery.find();
			for (UserPlan up : userPlans) {
				Plan p = getPlanDetail(null, up.getPlanId());
				up.setPlanDescFromPlan(p.getPlanDesc());
			}
		} catch (ParseException parseEx) {
			LogMsg(parseEx, 1);
		}
		
		userPlansList = userPlans;
		return userPlans;
	}
	
	/**
	 * Get plan detail for the current user from UserPlan table
	 * @param v View
	 * @return a list of user plans
	 */
	public static ArrayList<UserPlan> getUserPlans(View v) {
		ArrayList<UserPlan> userPlans = null;
		ParseQuery<UserPlan> userPlanQuery = ParseQuery.getQuery(UserPlan.class);
		userPlanQuery.whereEqualTo("user_id", ParseUser.getCurrentUser().getObjectId());
		try {
			userPlans = (ArrayList<UserPlan>) userPlanQuery.find();
		} catch (ParseException parseEx) {
			LogMsg(parseEx, 1);
		}
		userPlansList = userPlans;
		return userPlans;
	}

	/**
	 * Get shared plan detail for the provided user from SharedPlan table
	 * @param v View
	 * @return a list of user shared plans
	 */
	public static ArrayList<PlanShared> getUserSharedPlansByGivenUser(View v, String user_id) {
		ArrayList<PlanShared> plansShared = null;
		ParseQuery<ParseUser> query = ParseQuery.getQuery(ParseUser.class);
		query.whereEqualTo("objectId", user_id);
		
		try {
			ParseUser user = query.getFirst();

			ParseQuery<PlanShared> userSharedPlanQuery = ParseQuery.getQuery(PlanShared.class);
			userSharedPlanQuery.whereEqualTo("user_id", user.getObjectId());
			plansShared = (ArrayList<PlanShared>) userSharedPlanQuery.find();
		} catch (ParseException parseEx) {
			LogMsg(parseEx, 1);
		}
		return plansShared;
	}

	/**
	 * Get shared plan detail for the given user from SharedPlan table
	 * @param v View
	 * @return a list of user shared plans
	 */
	public static ArrayList<PlanShared> getUserSharedPlans(View v) {
		ArrayList<PlanShared> plansShared = null;
		ParseQuery<PlanShared> userSharedPlanQuery = ParseQuery.getQuery(PlanShared.class);
		userSharedPlanQuery.whereEqualTo("user_id", ParseUser.getCurrentUser().getObjectId());
		
		try {
			plansShared = (ArrayList<PlanShared>) userSharedPlanQuery.find();
		} catch (ParseException parseEx) {
			LogMsg(parseEx, 1);
		}
		return plansShared;		
	}

	/**
	 * Get plan detail for the provided plan identifier from Plan table
	 * @param v View
	 * @return a list of user plans
	 */
	public static Plan getPlanDetail(View v, String plan_id) {
		Plan plan = new Plan();
		ParseQuery<Plan> planQuery = ParseQuery.getQuery(Plan.class);
		planQuery.whereEqualTo("objectId", plan_id);
		try {
			plan = (Plan) planQuery.getFirst();
		} catch (ParseException parseEx) {
			LogMsg(parseEx, 1);
		}
		return plan;
	}

	/**
	 * Get actions for the current user plan
	 * @param v View
	 * @return a list of user shared plans
	 */
	public static ArrayList<PlanShared> getActions(View v, UserPlan plan) {
		ArrayList<PlanShared> plansShared = null;
		ParseQuery<PlanShared> userSharedPlanQuery = ParseQuery.getQuery(PlanShared.class);
		userSharedPlanQuery.whereEqualTo("user_id", ParseUser.getCurrentUser().getObjectId());
		
		try {
			plansShared = (ArrayList<PlanShared>) userSharedPlanQuery.find();
		} catch (ParseException parseEx) {
			LogMsg(parseEx, 1);
		}
		return plansShared;		
	}

	/**
	 * Log message for any parse message with log level
	 * @param ex exception
	 * @param errLvl error level
	 */
	private static void LogMsg(Exception ex, int errLvl) {
		StringWriter sw = new StringWriter();
		ex.printStackTrace(new PrintWriter(sw));
		String exceptionAsString = sw.toString();
		Log.d("ParseError", exceptionAsString);
	}

}
