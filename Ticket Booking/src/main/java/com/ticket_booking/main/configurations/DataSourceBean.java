package com.ticket_booking.main.configurations;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Prototype for creating bean which can be feeded dyncamic values
 * from external property files. Later on, could be autowired anywhere.
 * 
 * @author Shiv
 *
 */
@Configuration
@ConfigurationProperties("spring.datasource")
public class DataSourceBean {

	private String url;
	private String userName;
	private String password;
	
	public DataSourceBean(String url, String userName, String password) {
		super();
		this.url = url;
		this.userName = userName;
		this.password = password;
	}
	
	public DataSourceBean() {
		super();
	}

	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
