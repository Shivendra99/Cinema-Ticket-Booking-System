package com.ticket_booking.main.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity(name= "CinemaHall")
@Table(name= "cinema_hall")
@JsonIgnoreProperties({"shows", "hibernateLazyInitializer", "handler"})
public class CinemaHall {
	
	@Id
	@Column(name= "cinema_id", length= 6, nullable= false)
	private String cinemaId;
	
	@Column(name= "city", length= 50, nullable= false)
	private String city;
	
	@Column(name= "cinema_name", length= 50, nullable= false)
	private String cinemaName;
	
	@Column(name= "seats", nullable= false)
	private int capacity;
	
	@OneToMany(fetch= FetchType.LAZY, mappedBy= "cinemaHall")
	private List<CinemaMovieShows> shows;
	
	public List<CinemaMovieShows> getShows() {
		return shows;
	}

	public void setShows(List<CinemaMovieShows> shows) {
		this.shows = shows;
	}

	public CinemaHall() {
	}

	public String getCinemaId() {
		return cinemaId;
	}

	public void setCinemaId(String cinemaId) {
		this.cinemaId = cinemaId;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCinemaName() {
		return cinemaName;
	}

	public void setCinemaName(String cinemaName) {
		this.cinemaName = cinemaName;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
}