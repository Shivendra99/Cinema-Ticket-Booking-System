package com.user_api.main.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name= "User")
@Table(name= "User")
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
	
	@Column(name= "password", nullable= false)
	private String password;
	
	@Column(name= "enabled", length= 1, nullable= false)
	private String enabled;
	
	public void setEnabled(boolean isEnabled) {
		this.enabled= isEnabled ? "y": "n";
	}
	
	public boolean isEnabled() {
		return ("y".equalsIgnoreCase(enabled))? true: false;
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

	public User(String userId, String userName, String phoneNo, String userType, String password) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.phoneNo = phoneNo;
		this.userType= userType;
		this.password= password;
	}
	
	public User() {
		
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}