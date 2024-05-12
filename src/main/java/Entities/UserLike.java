package Entities;

import java.util.*;

/**
 * 
 */
public class UserLike {


	private int id;
	private int userId;
	private int PostId;

	public UserLike(int id, int userId, int postId) {
		this.id = id;
		this.userId = userId;
		PostId = postId;
	}
	public UserLike(int userId, int postId) {
		this.userId = userId;
		PostId = postId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getPostId() {
		return PostId;
	}

	public void setPostId(int postId) {
		PostId = postId;
	}
}