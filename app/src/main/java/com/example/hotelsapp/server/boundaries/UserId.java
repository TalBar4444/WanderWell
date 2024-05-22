package com.example.hotelsapp.server.boundaries;

public class UserId {
	private String email;

// Constructors
	public UserId() {
	}

	public UserId(String email) {
		this.email = email;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	@Override
	public String toString() {
		return "[email=" + email + "]";
	}
	}