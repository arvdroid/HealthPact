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
import com.codepath.healthpact.models.ActionPerPeriod;
import com.codepath.healthpact.models.Plan;
import com.codepath.healthpact.models.PlanAction;
import com.codepath.healthpact.models.PlanShared;
import com.codepath.healthpact.models.UserPlan;
import com.codepath.healthpact.models.UserPlanRelation;
import com.parse.CountCallback;
import com.parse.GetCallback;
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
	
	private static String user_plan_id;
	private static String action_id;
	
	public static ArrayList<UserPlanRelation> userPlanRelation = new ArrayList<UserPlanRelation>();

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

	public static ParseQuery<PlanAction> getActionForPlanQuery(String plan_id) {
		ParseQuery<PlanAction> query = ParseQuery.getQuery(PlanAction.class);
		query.whereEqualTo("plan_id", plan_id);
		return query;
	}

	public static  ArrayList<Action> getActionsForPlan(List<PlanAction> plan_actions){
		ArrayList<Action> actions = new ArrayList<Action>();
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
	public static ArrayList<Plan> getPlansBasedOnExpertise(List<ParseUser> expertise) {
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
	 * Get plan detail for specific expertise ...new code
	 * @param v View
	 * @return a list of user plans
	 */
	public static ArrayList<Plan> getPlansBasedOnUsers(List<ParseUser> users) {
		final ArrayList<Plan> allPlans = new ArrayList<Plan>();

		try {

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

	public static ParseQuery<ParseUser> getUserBasedOnExpertise(String expertise) {
		ParseQuery<ParseUser> query = ParseUser.getQuery();
		query.whereEqualTo("Expertise", expertise);

		return query;
	}

	public static ParseQuery<Plan> getPlansBasedOnExpertise1(String expertise) {


		try {
			ParseQuery<ParseUser> query = ParseUser.getQuery();
			query.whereEqualTo("Expertise", expertise);
			ArrayList<ParseUser> users = (ArrayList<ParseUser>) query.find();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ParseQuery<Plan> searchPlansQuery = ParseQuery.getQuery(Plan.class);
		searchPlansQuery.whereEqualTo("created_by", currentUserId);

		return searchPlansQuery;


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


	public static ParseQuery<UserPlan> getUserFollowedPlans() {
		ParseQuery<UserPlan> userPlanQuery = ParseQuery.getQuery(UserPlan.class);
		userPlanQuery.whereEqualTo("user_id", ParseUser.getCurrentUser().getObjectId());
		userPlanQuery.whereEqualTo("plan_following", true);
		return userPlanQuery;
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
	public static int getUserFollowerPlansCount() {
		ParseQuery<UserPlan> userPlanQuery = ParseQuery.getQuery(UserPlan.class);
		userPlanQuery.whereEqualTo("created_by", currentUserId);
		try {
			ArrayList<UserPlan> userPlans = (ArrayList<UserPlan>) userPlanQuery.find();
			followerCount = 0;
			for (UserPlan up : userPlans) {
				ParseQuery<UserPlan> query = ParseQuery.getQuery(UserPlan.class);
				query.whereEqualTo("plan_following", true);
				query.whereEqualTo("plan_id", up.getPlanId());
				followerCount += query.count();
				/*
				query.countInBackground(new CountCallback() {
					public void done(int count, ParseException e) {
						if (e == null) {
							followerCount += count;
						} else {
							// The request failed
						}
					}
				});
				*/
			}

		} catch (ParseException e1) {
			e1.printStackTrace();
		}

		return followerCount;
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
	 * Get plan for the current user from Plan table
	 * @param v View
	 * @return a list of user plans
	 */
	public static ParseQuery<UserPlan> getPlansCreatedByCurrentUser1() {

		ParseQuery<UserPlan> userPlanQuery = ParseQuery.getQuery(UserPlan.class);
		userPlanQuery.whereEqualTo("created_by", currentUserId);

		return userPlanQuery;
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
	 * asyn call
	 * @return a list of user shared plans
	 */
	public static ParseQuery<PlanShared> getUserSharedPlans() {
		ArrayList<PlanShared> plansShared = null;
		ParseQuery<PlanShared> userSharedPlanQuery = ParseQuery.getQuery(PlanShared.class);
		userSharedPlanQuery.whereEqualTo("user_id", ParseUser.getCurrentUser().getObjectId());

		/*try {
			plansShared = (ArrayList<PlanShared>) userSharedPlanQuery.find();
		} catch (ParseException parseEx) {
			LogMsg(parseEx, 1);
		}*/
		return userSharedPlanQuery;
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

			plan.setCreatedBy(ParseUser.getCurrentUser().getUsername());
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


	public static void updatePlanFollowedByUser(String plan_id, Date start_date, int duration, List<Action> actions) {

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
					for (Action action : actions) {
						updatePlanRelation(up.getObjectId(), action.getObjectId(), start_date, duration);
					}
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
				for (Action action : actions) {
					updatePlanRelation(userPlan.getObjectId(), action.getObjectId(), start_date, duration);
				}
			}
		} catch (ParseException parseEx) {
			LogMsg(parseEx, 1);
		}
	}

	public static boolean getIndividualActionPerPlan(String user_plan_id, String action_id) {
		UserPlanRelation userPlanRelation = null;
	    GregorianCalendar gcal = new GregorianCalendar();
	    Date date = gcal.getTime();
	    date = removeTimeFromDate(date);

		ParseQuery<UserPlanRelation> userPlanQuery = ParseQuery.getQuery(UserPlanRelation.class);
		userPlanQuery.whereEqualTo("user_plan_id", user_plan_id);
		userPlanQuery.whereEqualTo("action_id", action_id);
		userPlanQuery.whereGreaterThanOrEqualTo("completion_date", date);
		gcal.add(Calendar.DATE, 1);
	    date = gcal.getTime();
	    date = removeTimeFromDate(date);

		userPlanQuery.whereLessThan("completion_date", date);
		
		try {
			userPlanRelation = (UserPlanRelation) userPlanQuery.getFirst();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			return userPlanRelation.isUpdated();
	}

	public static void updateIndividualActionPerPlan(String user_plan_id, String action_id, boolean status) {
		ArrayList<UserPlanRelation> userPlanRelation = null;
	    GregorianCalendar gcal = new GregorianCalendar();
	    Date date = gcal.getTime();
	    date = removeTimeFromDate(date);

		ParseQuery<UserPlanRelation> userPlanQuery = ParseQuery.getQuery(UserPlanRelation.class);
		userPlanQuery.whereEqualTo("user_plan_id", user_plan_id);
		userPlanQuery.whereEqualTo("action_id", action_id);
		userPlanQuery.whereGreaterThanOrEqualTo("completion_date", date);
		gcal.add(Calendar.DATE, 1);
	    date = gcal.getTime();
	    date = removeTimeFromDate(date);

		userPlanQuery.whereLessThan("completion_date", date);


		try {
			userPlanRelation = (ArrayList<UserPlanRelation>) userPlanQuery.find();
			if ((userPlanRelation != null) && (!userPlanRelation.isEmpty())) {
				for (UserPlanRelation up : userPlanRelation) {
					up.setUpdated(status);
					up.saveEventually();
				}
			}

		} catch (ParseException parseEx) {
			LogMsg(parseEx, 1);
		}
	}

	public static ActionPerPeriod getPlanRelationPerDuration(String user_plan_id_param, String action_id_param, Date start_date_param, Date end_date_param) {

		ActionPerPeriod actionPerPeriod = new ActionPerPeriod();
		//dipankar not required ActionPerPeriod.WeekRange currentWeek = actionPerPeriod.getCurrentWeek();

		// set current week start and end date
		//not required dipankar actionPerPeriod.setCurrentWeek();

	    GregorianCalendar gcal = new GregorianCalendar();
	    // set first day of week Monday
	    gcal.setFirstDayOfWeek(Calendar.MONDAY);

	    // remove the time portion of the date to get data by Query
		gcal.setTime(start_date_param);
		gcal.set(Calendar.HOUR_OF_DAY, 0);
		gcal.clear(Calendar.MINUTE);
		gcal.clear(Calendar.SECOND);
		gcal.clear(Calendar.MILLISECOND);
		start_date_param = gcal.getTime();

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(end_date_param);
	    calendar.set(Calendar.HOUR_OF_DAY, 23);
	    calendar.set(Calendar.MINUTE, 59);
	    calendar.set(Calendar.SECOND, 59);
	    calendar.set(Calendar.MILLISECOND, 999);
	    end_date_param = calendar.getTime();


		// get the query based on calculated start and end date
		ParseQuery<UserPlanRelation> userPlanQuery = ParseQuery.getQuery(UserPlanRelation.class);
		userPlanQuery.whereEqualTo("user_plan_id", user_plan_id_param);
		userPlanQuery.whereEqualTo("action_id", action_id_param);
		userPlanQuery.whereGreaterThanOrEqualTo("completion_date", start_date_param);
		userPlanQuery.whereLessThanOrEqualTo("completion_date", end_date_param);
		userPlanQuery.addAscendingOrder("completion_date");

		try {
			ArrayList<UserPlanRelation> userPlanRelation = (ArrayList<UserPlanRelation>) userPlanQuery.find();

			for (UserPlanRelation upr : userPlanRelation) {
				actionPerPeriod.addToMap(upr.getCompletionDate(), upr.isUpdated());
			}
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return actionPerPeriod;

/*		userPlanQuery.findInBackground(new FindCallback<UserPlanRelation>() {
			@Override
			public void done(List<UserPlanRelation> retreivedResult, ParseException e) {
				if (e == null) {
					userPlanRelation.addAll(retreivedResult);
				}
			}

		});
*/
	}

	private static Date removeTimeFromDate(Date date) {
		GregorianCalendar gcal = new GregorianCalendar();

		// remove the time portion of the date to get data by Query
		gcal.setTime(date);
		gcal.set(Calendar.HOUR_OF_DAY, 0);
		gcal.clear(Calendar.MINUTE);
		gcal.clear(Calendar.SECOND);
		gcal.clear(Calendar.MILLISECOND);
		return (gcal.getTime());
	}
	
	public static int getDayOfWeek(Date date) {
		GregorianCalendar c = new GregorianCalendar();
		c.setTime(date);
		int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
		return dayOfWeek;
	}


	public static void setStartEndDateOfWeek(ActionPerPeriod.WeekRange weekRange, Date date) {
	    GregorianCalendar gcal = new GregorianCalendar();

	    // set provided date as parameter or currentdate as default
		gcal.setTime(date);

	    // set first day of week Monday
	    gcal.setFirstDayOfWeek(Calendar.MONDAY);

		while (gcal.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
			gcal.add(Calendar.DATE, -1);
		}

		// get start of this week in milliseconds
		gcal.set(Calendar.DAY_OF_WEEK, gcal.getFirstDayOfWeek());
		Date current_week_start_date = gcal.getTime();
		weekRange.setStartDate(current_week_start_date);

		gcal.add(Calendar.WEEK_OF_YEAR, 1);
		gcal.add(Calendar.DATE, -1);
		Date current_week_end_date = gcal.getTime();
		weekRange.setEndDate(current_week_end_date);
	}

	public static void setCurrentWeekStartEndDate(ActionPerPeriod.WeekRange weekRange) {
	    GregorianCalendar gcal = new GregorianCalendar();

	    // set first day of week Monday
	    gcal.setFirstDayOfWeek(Calendar.MONDAY);

		while (gcal.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
			gcal.add(Calendar.DATE, -1);
		}

		// get start of this week in milliseconds
		gcal.set(Calendar.DAY_OF_WEEK, gcal.getFirstDayOfWeek());
		Date current_week_start_date = gcal.getTime();
		weekRange.setStartDate(current_week_start_date);

		gcal.add(Calendar.WEEK_OF_YEAR, 1);
		gcal.add(Calendar.DATE, -1);
		Date current_week_end_date = gcal.getTime();
		weekRange.setEndDate(current_week_end_date);
	}

	public static void updatePlanRelation(String user_plan_id_param, String action_id_param, Date start_date_param, int duration) {

/*		if ((user_plan_id_param.equals(ParseUtils.user_plan_id)) && (action_id_param.equals(ParseUtils.action_id))) {
			return;
		}
		else {
			ParseUtils.user_plan_id = user_plan_id_param;
			ParseUtils.action_id = action_id_param;
		}
*/		
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
			currentDate = removeTimeFromDate(currentDate);
			upr.setCompletionDate(currentDate);
			upr.setUserPlanId(user_plan_id_param);
			upr.setActionId(action_id_param);
			upr.setUpdated(false);

/*			upr.pinInBackground( new SaveCallback( ) {

			    @Override
			    public void done( ParseException e ) {
			        if( e == null ) {
			            //success
			        } else {
			            //fail
			        }
			    }
			} );
*/			
			try {
				upr.save();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

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
