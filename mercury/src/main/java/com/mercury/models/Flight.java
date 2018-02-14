package com.mercury.models;

public class Flight {
	private String departure;
	private String arrival;
	private Integer number ; //": "1926", # a flight number
	private String departureTime; //": "18:00", # a departure time in the departure	airport timezone
	private String arrivalTime; //": "21:35" # an arrival time in the arrival airport timezone
	 
	 public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public String getDepartureTime() {
		return departureTime;
	}
	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}
	public String getArrivalTime() {
		return arrivalTime;
	}
	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	 public String getDeparture() {
		return departure;
	}
	public void setDeparture(String departure) {
		this.departure = departure;
	}
	public String getArrival() {
		return arrival;
	}
	public void setArrival(String arrival) {
		this.arrival = arrival;
	}	
}
