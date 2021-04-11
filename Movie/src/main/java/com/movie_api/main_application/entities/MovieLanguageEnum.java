package com.movie_api.main_application.entities;

public enum MovieLanguageEnum {
	ENGLISH("English"),
	HINDI("Hindi"),
	KANNADA("Kannada"),
	BANGLA("Bangla"),
	NA("NA");

	private final String label;
	
	private MovieLanguageEnum(String label) {
		this.label= label;
	}
	
	@Override
	public String toString() {
		return this.label;
	}
	
}
