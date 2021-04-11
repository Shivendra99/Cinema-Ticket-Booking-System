package com.movie_api.main_application.entities;

public enum MovieJonreEnum {
	ACTION("Action"), 
	ROMANCE("Romance"), 
	SCIENCE_FICTION("Science Fiction"), 
	THRILLER("Thriller"), 
	SUSPENSE("Suspense"), 
	HORROR("Horror"), 
	OTHERS("Others");
	
	private final String label;
	
	private MovieJonreEnum(String label) {
		this.label= label;
	}
	
	@Override
	public String toString() {
		return this.label;
	}
}
