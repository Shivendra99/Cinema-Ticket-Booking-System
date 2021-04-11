package com.cinema_api.main_application.entities;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity(name= "CinemaMvoie")
@Table(name= "cinema_movie")
@JsonIgnoreProperties({"movie", "cinemaHall", "hibernateLazyInitializer", "handler"})
public class CinemaMovieShows {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name= "event_id")
	private int eventId;
	
	@Column(name= "shows", nullable= false, length= 1)
	private String shows;
	
	@Column(name= "show_date", nullable= false)
	private Date showDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name= "cinema_id")
	private CinemaHall cinemaHall;

	@ManyToOne(fetch= FetchType.LAZY)
	@JoinColumn(name= "movie_id")
	private Movie movie;

	public CinemaHall getCinemaHall() {
		return cinemaHall;
	}

	public void setCinemaHall(CinemaHall cinemaHall) {
		this.cinemaHall = cinemaHall;
	}

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}	
	
	public int getEventId() {
		return eventId;
	}

	public void setEventId(int eventId) {
		this.eventId = eventId;
	}

	public String getShows() {
		return shows;
	}

	public void setShows(String shows) {
		this.shows = shows;
	}

	public Date getShowDate() {
		return showDate;
	}

	public void setShowDate(Date showDate) {
		this.showDate = showDate;
	}
	
	public CinemaMovieShows() {
		
	}
	
} 