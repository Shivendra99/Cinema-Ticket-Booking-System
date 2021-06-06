package com.cinema_api.main_application.entities;

import java.util.Arrays;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cinema_api.main_applciation.constants.USER_ROLES;

@Entity(name= "User")
@Table(name= "User")
public class User implements UserDetails{
	
	@Id
	@Column(name= "user_id", length= 20, nullable= false)
	private String userId;
	
	@Column(name= "user_type", nullable= false)
	private String authority;
	
	@Column(name= "password", nullable= false)
	private String password;
	
	@Column(name= "enabled", length= 1, nullable= false)
	private String enabled;
	
	public void setEnabled(boolean isEnabled) {
		this.enabled= isEnabled ? "y": "n";
	}
	
	//private boolean accountNonLocked;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public User(String userId, String authority) {
		super();
		this.userId = userId;
		this.authority= authority;
		//this.accountNonLocked= accountNonLocked;
	}
	
	public User() {
		
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Arrays.asList(new SimpleGrantedAuthority("ROLE_"+ authority));
	}

	public void setPassword(String password) {
		this.password= password;
	}
	
	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return userId;  
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return ("y".equalsIgnoreCase(enabled))? true: false;
	}

//	public void setAccountNonLocked(boolean accountNonLocked) {
//		this.accountNonLocked = accountNonLocked;
//	}
}