package com.mercury.models;

import java.util.List;

public class OutRest {
	
	private Integer stops ;
	private List<Legs> legs; 
	
	public Integer getStops() {
		return stops;
	}
	public void setStops(Integer stops) {
		this.stops = stops;
	}
	public List<Legs> getLegs() {
		return legs;
	}
	public void setLegs(List<Legs> legs) {
		this.legs = legs;
	}
}