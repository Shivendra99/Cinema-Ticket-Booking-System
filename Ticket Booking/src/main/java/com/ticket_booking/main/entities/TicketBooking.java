package com.ticket_booking.main.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity(name= "TicketBooking")
@Table(name= "user_booking")
public class TicketBooking {

	@Id
	@Column(name= "ticket_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int ticketId;

	@ManyToOne(fetch= FetchType.LAZY)
	@JoinColumn(name= "user_id")
	private User user;
	
	@ManyToOne(fetch= FetchType.LAZY)
	@JoinColumn(name= "event_id")
	private CinemaMovieShows show;
	
	@Column(name= "person_name", length= 50, nullable= false)
	private String personName;
	
	@Column(name= "seats", nullable= false)
	private int seat;
	
	public TicketBooking() {
		super();
	}
	
	public TicketBooking(int ticketId, User user, CinemaMovieShows show, String personName, int seat) {
		super();
		this.ticketId= ticketId;
		this.user = user;
		this.show = show;
		this.personName = personName;
		this.seat = seat;
	}

	public User getUser() {
		return user;
	}
	
	public int getTicketId() {
		return ticketId;
	}

	public void setTicketId(int ticketId) {
		this.ticketId = ticketId;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public CinemaMovieShows getShow() {
		return show;
	}

	public void setShow(CinemaMovieShows show) {
		this.show = show;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public int getSeat() {
		return seat;
	}

	public void setSeat(int seat) {
		this.seat = seat;
	}

}