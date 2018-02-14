package com.mercury.models;

import java.util.List;

public class Schedules {

	private Long year; 
	private Long month; //": 6, # a month of a year
	private Integer departureOffset ;
	private Integer arrivalOffset ;
	private List <Day> Days;
	
	
	public Integer getDepartureOffset() {
		return departureOffset;
	}
	public void setDepartureOffset(Integer departureOffset) {
		this.departureOffset = departureOffset;
	}
	public Integer getArrivalOffset() {
		return arrivalOffset;
	}
	public void setArrivalOffset(Integer arrivalOffset) {
		this.arrivalOffset = arrivalOffset;
	}
	public Long getMonth() {
		return month;
	}
	public void setMonth(Long month) {
		this.month = month;
	}
	public List<Day> getDays() {
		return Days;
	}
	public void setDays(List<Day> days) {
		Days = days;
	}
	public Long getYear() {
		return year;
	}
	public void setYear(Long year) {
		this.year = year;
	}
	
}
