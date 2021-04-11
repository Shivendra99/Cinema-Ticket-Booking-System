package com.ticket_booking.main.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity(name= "User")
@Table(name= "User")
@JsonIgnoreProperties({"bookedTicketList", "hibernateLazyInitializer", "handler"})
public class User {
	
	@Id
	@Column(name= "user_id", length= 20, nullable= false)
	private String userId;
	
	@Column(name= "user_name", length= 50, nullable= false)
	private String userName;
	
	@Column(name= "phone_no", length= 10)
	private String phoneNo;
	
	@Column(name= "user_type", nullable= false)
	private String userType;
	
	@OneToMany(fetch= FetchType.LAZY, mappedBy= "user")
	private List<TicketBooking> bookedTicketList;

	public List<TicketBooking> getBookedTicketList() {
		return bookedTicketList;
	}

	public void setBookedTicketList(List<TicketBooking> bookedTicketList) {
		this.bookedTicketList = bookedTicketList;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public User(String userId, String userName, String phoneNo, String userType) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.phoneNo = phoneNo;
		this.userType= userType;
	}
	
	public User() {
		
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}
}