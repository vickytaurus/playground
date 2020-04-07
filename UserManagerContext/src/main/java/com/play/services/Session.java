package com.play.services;

import java.util.HashMap;
import java.util.Map;

//Stores data in memory
public class Session {

	private static final Map<String, User> STORAGE = new HashMap<>();
	private static User loggedInUser = null;

	// Adds user to in memory hash map
	public void store(User user) {
		synchronized (STORAGE) {
			STORAGE.put(user.getName(), user);
		}
	}

	// Returns used with given id if present in map else null
	public User getUser(String name) {
		synchronized (STORAGE) {
			return STORAGE.get(name);
		}
	}

	public void setLoggedInUser(User user) {
		synchronized (STORAGE) {
			loggedInUser = user;
		}
	}

	public User getLoggedUser() {
		return loggedInUser;
	}
}