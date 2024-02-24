package Entities;

import java.util.*;

/**
 * 
 */
public class UserActivity {

	/**
	 * Default constructor
	 */

	/**
	 * 
	 */
	private int UserId;
	private int id;
	/**
	 * 
	 */
	private int ActivityId;

	/**
	 * 
	 */
	private String Feedback;
	private int stars;


	public void LeaveFeedback(String feedback, int stars) {
	this.Feedback = feedback;
	this.stars=stars;
	}

	public UserActivity(int userId, int id, int activityId, String feedback, int stars) {
		UserId = userId;
		this.id = id;
		ActivityId = activityId;
		Feedback = feedback;
		this.stars = stars;
	}
	public UserActivity(int userId, int activityId, String feedback, int stars) {
		UserId = userId;
		ActivityId = activityId;
		Feedback = feedback;
		this.stars = stars;
	}

	public int getUserId() {
		return UserId;
	}

	public void setUserId(int userId) {
		UserId = userId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getActivityId() {
		return ActivityId;
	}

	public void setActivityId(int activityId) {
		ActivityId = activityId;
	}

	public String getFeedback() {
		return Feedback;
	}

	public void setFeedback(String feedback) {
		Feedback = feedback;
	}

	public int getStars() {
		return stars;
	}

	public void setStars(int stars) {
		this.stars = stars;
	}
}