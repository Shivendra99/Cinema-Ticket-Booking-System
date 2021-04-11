package com.cinema_api.main_application.entities;

import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity(name= "Movie")
@Table(name= "movie")
public class Movie {
	
	@Id
	@Column(name= "movie_id", length= 6, nullable= false)
	private String movieId;
	
	@Column(name= "movie_name", length= 50, nullable= false)
	private String movieName;
	
	//@Enumerated(EnumType.STRING)
	@Column(name= "movie_jonre")
	private String movieJonre;
	
	//@Enumerated(EnumType.STRING)
	@Column(name= "movie_language")
	private String movieLanguage;
	
	@Column(name= "release_date", nullable= false)
	private Date releaseDate;
	
	@OneToMany(fetch= FetchType.LAZY, mappedBy = "movie")
	private List<CinemaMovieShows> shows;

	public List<CinemaMovieShows> getShows() {
		return shows;
	}

	public void setShows(List<CinemaMovieShows> shows) {
		this.shows = shows;
	}

	public Movie() {
	}

	public String getMovieId() {
		return movieId;
	}

	public void setMovieId(String movieId) {
		this.movieId = movieId;
	}

	public String getMovieName() {
		return movieName;
	}

	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}

	public String getMovieJonre() {
		return movieJonre;
	}

	public void setMovieJonre(String movieJonre) {
		this.movieJonre = movieJonre;
	}

	public String getMovieLanguage() {
		return movieLanguage;
	}

	public void setMovieLanguage(String movieLanguage) {
		this.movieLanguage = movieLanguage;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}
}