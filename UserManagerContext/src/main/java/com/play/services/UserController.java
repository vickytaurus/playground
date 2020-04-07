package com.play.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.play.exception.UserNotLoggedInException;

//Handles incoming JSON requests that work on User resource/entity
public class UserController {
	// Store used by controller
	private Session store = new Session();

	// Create a new user
	public String createUser(String userJson) throws IOException {
		ObjectMapper mapper = new ObjectMapper();

		User user = mapper.readValue(userJson, User.class);

		if (!isValidUser(user)) {
			return "ERROR";
		}

		store.store(user);

		return "SUCCESS";
	}

	public List<Tour> getToursByUser(User user) throws UserNotLoggedInException {
		List<Tour> tourList = new ArrayList<Tour>();
		boolean isFriend = false;
		User loggedUser = store.getLoggedUser();
		if (loggedUser != null) {
			for (User friend : user.getFriends()) {
				if (friend.equals(loggedUser)) {
					isFriend = true;
					break;
				}
			}
			if (isFriend) {
				tourList = TourDao.findToursByUser(user);
			}
			return tourList;
		} else {
			throw new UserNotLoggedInException();
		}
	}

	// Validates the user object
	private boolean isValidUser(User user) {
		if (!isPresent(user.getName())) {
			return false;
		}
		user.setName(user.getName().trim());

		if (!isValidAlphaNumeric(user.getName())) {
			return false;
		}
		if (user.getEmail() == null || user.getEmail().trim().length() == 0) {
			return false;
		}
		user.setEmail(user.getEmail().trim());
		if (!isValidEmail(user.getEmail())) {
			return false;
		}
		return true;
	}

	// Simply checks if value is null or empty..
	private boolean isPresent(String value) {
		return value != null && value.trim().length() > 0;
	}

	// check string for special characters
	private boolean isValidAlphaNumeric(String value) {
		Pattern pattern = Pattern.compile("[^A-Za-z0-9]");
		Matcher matcher = pattern.matcher(value);
		return !matcher.find();
	}

	// check string for valid email address - this is not for prod.
	// Just for demo. This fails for lots of valid emails.
	private boolean isValidEmail(String value) {
		Pattern pattern = Pattern
				.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
		Matcher matcher = pattern.matcher(value);
		return matcher.find();
	}

}