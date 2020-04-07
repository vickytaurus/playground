package com.play.services;

import java.util.ArrayList;
import java.util.List;

public class TourDao {
	public static List<Tour> findToursByUser(User user) {
		List<Tour> tours = new ArrayList<>();
		tours.add(new Tour());
		tours.add(new Tour());
		return tours;
	}
}
