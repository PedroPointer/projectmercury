package com.mercury.models;

public class Routes {

	private String airportFrom ;   // "LUZ", # a departure airport IATA code
	private String airportTo;  //": "STN", # an arrival airport IATA code
	private String connectingAirport;  //": null, # a connecting airport IATA code
	private Boolean newRoute;  //": false,
	private Boolean seasonalRoute;  //": false,
	private String group;  // ": "GENERIC"
	
	public String getAirportFrom() {
		return airportFrom;
	}
	public void setAirportFrom(String airportFrom) {
		this.airportFrom = airportFrom;
	}
	public String getAirportTo() {
		return airportTo;
	}
	public void setAirportTo(String airportTo) {
		this.airportTo = airportTo;
	}
	public String getConnectingAirport() {
		return connectingAirport;
	}
	public void setConnectingAirport(String connectingAirport) {
		this.connectingAirport = connectingAirport;
	}
	public Boolean getNewRoute() {
		return newRoute;
	}
	public void setNewRoute(Boolean newRoute) {
		this.newRoute = newRoute;
	}
	public Boolean getSeasonalRoute() {
		return seasonalRoute;
	}
	public void setSeasonalRoute(Boolean seasonalRoute) {
		this.seasonalRoute = seasonalRoute;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}

}
