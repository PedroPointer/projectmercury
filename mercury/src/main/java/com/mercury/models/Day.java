package com.mercury.models;

import java.util.List;

public class Day {

	private Long day; //": 1, # a day of a month
	private List <Flight> Flights;
	 
	public Long getDay() {
		return day;
	}
	public void setDay(Long day) {
		this.day = day;
	}
	public List<Flight> getFlights() {
		return Flights;
	}
	public void setFlights(List<Flight> flights) {
		Flights = flights;
	}
}
