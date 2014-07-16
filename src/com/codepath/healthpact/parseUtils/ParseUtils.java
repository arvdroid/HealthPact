package com.codepath.healthpact.parseUtils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
import com.parse.CountCallback;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class ParseUtils {
	protected static final String TAG = "HealthPact";
	public static ArrayList<UserPlan> userPlansList;
	public static String currentUserId = ParseUser.getCurrentUser().getObjectId();
	public static int followingCount;
	public static int followerCount;
	
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
	 * Get all HealthPact users including the user requesting the information
	 * @return ArrayList of ParseUsers
	 */
	public static ArrayList<ParseUser> getAllUsers(boolean isCurrentUser) {

		ParseQuery<ParseUser> query = ParseQuery.getQuery(ParseUser.class);
		
		if (!isCurrentUser) {
			query.whereNotEqualTo("objectId", ParseUser.getCurrentUser().getObjectId());
		}
		
		ArrayList<ParseUser> allUsers = null;
		
		try {
			allUsers = (ArrayList<ParseUser>) query.find();
		} catch (ParseException parseEx) {
			LogMsg(parseEx, 1);
		}
		
		return allUsers;
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
		final ArrayList<Plan> allPlans = new ArrayList<Plan>();
		
		try {

			ParseQuery<ParseUser> query = ParseUser.getQuery();
			query.whereEqualTo("Expertise", expertise);
			ArrayList<ParseUser> users = (ArrayList<ParseUser>) query.find();

			for (ParseUser user : users) {
				ParseQuery<UserPlan> innerUserPlanQuery = new ParseQuery<UserPlan>("UserPlan");
				innerUserPlanQuery.whereEqualTo("user_id", user.getObjectId());
				ArrayList<UserPlan> userplans = (ArrayList<UserPlan>) innerUserPlanQuery.find();

				for (UserPlan ups : userplans) {
					ParseQuery<Plan> innerquery = new ParseQuery<Plan>("Plan");
					innerquery.whereEqualTo("objectId", ups.getPlanId());
					ArrayList<Plan> plans = (ArrayList<Plan>) innerquery.find();
					allPlans.addAll(plans);
				}
			}
		} catch (ParseException parseEx) {
			LogMsg(parseEx, 1);
		}
		
		return allPlans;
	}
	
	/**
	 * Get userplan data for the specific user and plan
	 * @param user_plan_object_id objectId from userplan table
	 * @return userplan record
	 */
	public static UserPlan getUserPlan(String user_plan_object_id) {
		ParseQuery<UserPlan> query = new ParseQuery<UserPlan>("UserPlan");
		UserPlan userplan = null;
		
		query.whereEqualTo("objectId", user_plan_object_id);
		try {
			userplan = query.getFirst();
		} catch (ParseException e) {
			LogMsg(e,1);
		}
		
		return userplan;
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
	public static ArrayList<UserPlan> getUserFollowedPlans(View v) {
		ArrayList<UserPlan> userPlans = null;
		ParseQuery<UserPlan> userPlanQuery = ParseQuery.getQuery(UserPlan.class);
		userPlanQuery.whereEqualTo("user_id", ParseUser.getCurrentUser().getObjectId());
		userPlanQuery.whereEqualTo("plan_following", true);
		try {
			userPlans = (ArrayList<UserPlan>) userPlanQuery.find();
		} catch (ParseException parseEx) {
			LogMsg(parseEx, 1);
		}
		userPlansList = userPlans;
		return userPlans;
	}

	/**
	 * Get plan following by current user
	 * @return count
	 */
	public static int getUserFollowedPlansCount() {
		ParseQuery<UserPlan> userPlanQuery = ParseQuery.getQuery(UserPlan.class);
		userPlanQuery.whereEqualTo("user_id", ParseUser.getCurrentUser().getObjectId());
		userPlanQuery.whereEqualTo("plan_following", true);
		followingCount = 0;

		userPlanQuery.countInBackground(new CountCallback() {
			public void done(int count, ParseException e) {
				if (e == null) {
					followingCount = count;
				} else {
					// The request failed
				}
			}
		});
		
		return followingCount;
	}


	/**
	 * Get plan followers by current user
	 * @return count
	 */
	public static int getUserFollowingPlansCount() {
		ParseQuery<UserPlan> userPlanQuery = ParseQuery.getQuery(UserPlan.class);
		userPlanQuery.whereEqualTo("created_by", currentUserId);
		try {
			ArrayList<UserPlan> userPlans = (ArrayList<UserPlan>) userPlanQuery.find();
			followerCount = 0;
			for (UserPlan up : userPlans) {
				ParseQuery<UserPlan> query = ParseQuery.getQuery(UserPlan.class);
				query.whereEqualTo("plan_following", true);
				query.whereEqualTo("plan_id", up.getPlanId());

				query.countInBackground(new CountCallback() {
					public void done(int count, ParseException e) {
						if (e == null) {
							followerCount += count;
						} else {
							// The request failed
						}
					}
				});

			}
			
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
				
		userPlanQuery.countInBackground(new CountCallback() {
			public void done(int count, ParseException e) {
				if (e == null) {
					followingCount = count;
				} else {
					// The request failed
					followingCount = 0;
				}
			}
		});
		
		return followingCount;
	}

	/**
	 * Get plan for the current user from Plan table
	 * @param v View
	 * @return a list of user plans
	 */
	public static ArrayList<Plan> getPlansCreatedByCurrentUser() {
		ArrayList<Plan> allPlans = new ArrayList<Plan>();
		
		ParseQuery<UserPlan> userPlanQuery = ParseQuery.getQuery(UserPlan.class);
		userPlanQuery.whereEqualTo("created_by", currentUserId);
		//userPlanQuery.whereEqualTo("plan_following", false);
		ArrayList<UserPlan> userplans;
		try {
			userplans = (ArrayList<UserPlan>) userPlanQuery.find();
			for (UserPlan ups : userplans) {
				ParseQuery<Plan> innerquery = new ParseQuery<Plan>("Plan");
				innerquery.whereEqualTo("objectId", ups.getPlanId());
				ArrayList<Plan> plans = (ArrayList<Plan>) innerquery.find();
				allPlans.addAll(plans);
			}
		} catch (ParseException e) {
			LogMsg(e,1);
		}
		
		return allPlans;
	}

	/**
	 * Post data to shared plan
	 * @param shared_to_user_id other user to use the plan
	 * @param plan_id plan identifier
	 */
	public static void convertPlanToShared(String shared_to_user_id, String plan_id) {
		PlanShared planShared = new PlanShared();
		planShared.setUserId(ParseUser.getCurrentUser().getObjectId());
		planShared.set_shared_to_user_id(shared_to_user_id);
		planShared.setPlan_id(plan_id);
		planShared.saveInBackground();
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
		                	userplan.setUser_id(currentUserId);
		                	userplan.setCreatedBy(currentUserId);
		                	userplan.setPlan_following(false);
		                	
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
	
	public static void createPlanWithDuration(final Plan plan, final List<Action> actions, final int duration) {
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
		                	setupRelation(userplan, duration);
		                	
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

				private void setupRelation(UserPlan userplan, int duration) {
					userplan.setPlan_start_date(new Date());
					Calendar c = Calendar.getInstance();
					c.setTime(userplan.getPlan_start_date());
					c.add(Calendar.WEEK_OF_YEAR, duration);
					userplan.setPlan_end_date(c.getTime());
					
				}
		    });			
		}		
		
	}
	
	
	public static void updatePlanFollowedByUser(String plan_id, Date start_date, int duration) {
		
		ArrayList<UserPlan> userPlans = null;
		
		//Calendar c = Calendar.getInstance();
	    GregorianCalendar c = new GregorianCalendar();

		c.setTime(start_date);
		//c.add(Calendar.DATE, -1);
		start_date = c.getTime();
		
		c.add(Calendar.WEEK_OF_YEAR, duration);
		Date end_date = c.getTime();

		ParseQuery<UserPlan> userPlanQuery = ParseQuery.getQuery(UserPlan.class);
		userPlanQuery.whereEqualTo("user_id", currentUserId);
		userPlanQuery.whereEqualTo("plan_id", plan_id);
		try {
			userPlans = (ArrayList<UserPlan>) userPlanQuery.find();
			if ((userPlans != null) && (!userPlans.isEmpty())) {
				for (UserPlan up : userPlans) {
					up.setPlan_start_date(start_date);
					up.setPlan_end_date(end_date);
					up.setPlan_following(true);   
					up.saveEventually();  
					//ajfjla
				}
			}
			else {
				UserPlan userPlan = new UserPlan();
				userPlan.setUser_id(currentUserId);
				userPlan.setPlan_id(plan_id);
				userPlan.setPlan_following(true);
				userPlan.setPlan_start_date(start_date);
				userPlan.setPlan_end_date(end_date);
				userPlan.saveEventually();				
			}
		} catch (ParseException parseEx) {
			LogMsg(parseEx, 1);
		}
	}
	
	public static void updatePlanRelation(String user_plan_id_param, String action_id_param, Date start_date_param, int duration) {
		
	    GregorianCalendar gcal = new GregorianCalendar();
		gcal.setTime(start_date_param);
		
		gcal.add(Calendar.WEEK_OF_YEAR, duration);
		Date end_date = gcal.getTime();
		gcal.setTime(start_date_param);

		Date currentDate = start_date_param;
		while (true) {
			if (currentDate.getTime() >= end_date.getTime()) {
				break;
			}
			UserPlanRelation upr = new UserPlanRelation();
			upr.setCompletionDate(currentDate);
			upr.setUserPlanId(user_plan_id_param);
			upr.setActionId(action_id_param);
			upr.setUpdated(false);

			upr.saveEventually();

			gcal.add(Calendar.DATE, 1);
			currentDate = gcal.getTime();
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
