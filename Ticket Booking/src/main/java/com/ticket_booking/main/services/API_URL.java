package com.ticket_booking.main.services;

public enum API_URL {
	
	CINEMA_API("CINEMA-API"), 
	MOVIE_API("MOVIE-API"), 
	SHOWS_API("SHOWS-API"), 
	USER_API("USER-API"),
	HTTP("http://");
	
	private String label;
	
	private API_URL(String label) {
		this.label= label;
	}
	
	@Override
	public String toString() {
		return label;
	}

}