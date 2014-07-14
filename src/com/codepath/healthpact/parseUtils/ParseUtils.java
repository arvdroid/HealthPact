package com.codepath.healthpact.parseUtils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;

import com.activeandroid.util.Log;
import com.codepath.healthpact.models.Action;
import com.codepath.healthpact.models.Plan;
import com.codepath.healthpact.models.PlanAction;
import com.codepath.healthpact.models.PlanShared;
import com.codepath.healthpact.models.UserPlan;
import com.codepath.healthpact.models.UserPlanRelation;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class ParseUtils {
	protected static final String TAG = "HealthPact";
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

	public static UserPlanRelation getActionWeeksStatus(String user_plan_id, String action_id) {
		UserPlanRelation user_plan_relation = null;
		ParseQuery<UserPlanRelation> query = ParseQuery.getQuery(UserPlanRelation.class);
		query.whereEqualTo("action_id", action_id);
		query.whereEqualTo("user_plan_id", action_id);
		try {
			user_plan_relation = (UserPlanRelation) query.find();
		} catch (ParseException parseEx) {
			parseEx.printStackTrace();
			Log.d("getUserPlanRelation failed");
		}
		return user_plan_relation;
	}
	
	public static Action getAction(String name) {
		Action action = null;
		ParseQuery<Action> query = ParseQuery.getQuery(Action.class);
		query.whereEqualTo("action_name", name);
		try {
			action = query.getFirst();
		} catch (ParseException parseEx) {
			parseEx.printStackTrace();
			Log.d("getAction failed");
		}
		return action;
	}
	
	/**
	 * Get action for the provided plan
	 * @param plan_id plan identifier
	 * @return a list of actions specific to the provided plan
	 */
	public static ArrayList<Action> getActionForPlan(String plan_id) {
		ArrayList<Action> actions = new ArrayList<Action>();
		ParseQuery<PlanAction> query = ParseQuery.getQuery(PlanAction.class);
		query.whereEqualTo("plan_id", plan_id);
		
		try {
			ArrayList<PlanAction> plan_actions = (ArrayList<PlanAction>) query.find();
			for (PlanAction plan_action : plan_actions) {
				ParseQuery<Action> actionQuery = ParseQuery.getQuery(Action.class);
				actionQuery.whereEqualTo("objectId", plan_action.getActionId());
				try {
					Action action = actionQuery.getFirst();
					if (action != null) {
						actions.add(action);
					}
				}
				catch (ParseException parseEx) {
					LogMsg(parseEx, 1);
				}
			}

		} catch (ParseException ex) {
			LogMsg(ex, 1);
		}

		return actions;
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
	 * Get plan detail for specific expertise
	 * @param v View
	 * @return a list of user plans
	 */
	public static ArrayList<Plan> getPlansBasedOnExpertise(String expertise) {
/*		ParseUser parseUser = new ParseUser();
		parseUser.put("Expertise", expertise);
*/		ArrayList<ParseUser> users;
		ArrayList<Plan> plans = null;
		ParseQuery<ParseUser> query = new ParseQuery<ParseUser>("ParseUser");
		query.whereEqualTo("Expertise", expertise);
		try {
			users = (ArrayList<ParseUser>) query.find();
		
			for (ParseUser user : users) {
				ParseQuery<UserPlan> innerUserPlanQuery = new ParseQuery<UserPlan>("UserPlan");
				innerUserPlanQuery.whereEqualTo("user_id", user.getObjectId());
				ArrayList<UserPlan> userplans = (ArrayList<UserPlan>) innerUserPlanQuery.find();
				
				for (UserPlan ups : userplans) {
					ParseQuery<Plan> innerquery = new ParseQuery<Plan>("Plan");
					innerquery.whereEqualTo("user_id", user.getObjectId());
					plans = (ArrayList<Plan>) innerquery.find();
					
				}
			}
		} catch (ParseException parseEx) {
				LogMsg(parseEx, 1);
		}
		
		return plans;
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

	public static void createPlan(final Plan plan, final List<Action> actions) {
		if ((plan != null) && (actions != null)) {
			if ((plan.getPlanDesc() == null) || (plan.getPlanDesc().isEmpty())) {
				return;
			}
			
			plan.saveInBackground(new SaveCallback() {
		        public void done(ParseException e) {
		            if (e == null) {
		                // Saved successfully.
		                Log.d(TAG, "Plan created and saved in database!");
		                final String plan_id = plan.getObjectId();
		                Log.d(TAG, "The object id is: " + plan_id);
		                if (plan_id != null) {
		                	
		                	final UserPlan userplan = new UserPlan();
		                	userplan.setPlan_id(plan_id);
		                	userplan.setUser_id(ParseUser.getCurrentUser().getObjectId());
		                	
							userplan.saveInBackground(new SaveCallback() {
								public void done(ParseException e) {
									if (e == null) {
										
										//get Actions
										if (actions != null) {
											for (final Action action : actions) {
												action.saveInBackground(new SaveCallback() {
													public void done(ParseException e) {
														if (e == null) {
															PlanAction pa = new PlanAction();

															String action_id = action.getObjectId();
															//String user_plan_id = userplan.getObjectId();

															pa.setPlanId(plan_id);
															pa.setActionId(action_id);
															pa.saveEventually();

														} else {
											                // The save failed for user plan
											                Log.d(TAG, "User Plan save error: " + e);
														}
													}
												});									
											}
										}

									} else {
						                // The save failed.
						                Log.d(TAG, "User Plan save error: " + e);
									}
								}
							});
		                }
		            } else {
		                // The save failed.
		                Log.d(TAG, "Plan save error: " + e);
		            }
		        }
		    });			
		}		
		
	}
	
	public static void updateProfile(String expertise,String location,String description) {
		ParseUser user = ParseUser.getCurrentUser();
		user.getUsername();
		user.put("Expertise", expertise);
		user.put("location", location);
		user.put("desc", description);
		user.get("Expertise");
		user.saveEventually();
	}
	
	public static String getUserName() {
		ParseUser user = ParseUser.getCurrentUser();
		String userName = user.getUsername();
		return userName;
	}
	
	public static String getExpertise() {
		ParseUser user = ParseUser.getCurrentUser();
		String expertise = (String) user.get("Expertise");		
		return expertise;
	}
	
	public static String getLocation() {
		ParseUser user = ParseUser.getCurrentUser();
		String location = (String) user.get("location");		
		return location;
	}
	
	public static String getDescription() {
		ParseUser user = ParseUser.getCurrentUser();
		String description = (String) user.get("desc");		
		return description;
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
