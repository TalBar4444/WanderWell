package com.example.hotelsapp.server.boundaries;


public class UserBoundary {
	private UserId userId;
	private String username;

// Constructors
	public UserBoundary() {
		userId = new UserId();

	}

	public UserBoundary(String superApp, String email, String userName) {
	//	userId = new UserId(superApp, email);
		userId = new UserId(email);
		this.username = userName;
	}

// Gets
	public UserId getUserId() {
		return userId;
	}

	public String getUsername() {
		return username;
	}


// Sets
	public void setUserId(UserId userId) {
		this.userId = userId;
	}

	public void setUsername(String username) {
		this.username = username;
	}


// To String
	@Override
	public String toString() {
		return "[userId=" + userId + ", username=" + username + "]";
	}
}