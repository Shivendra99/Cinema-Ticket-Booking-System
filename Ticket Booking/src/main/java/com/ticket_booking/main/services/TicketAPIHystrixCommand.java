package com.ticket_booking.main.services;

import java.sql.Date;

import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.HystrixCommand;
import com.ticket_booking.main.entities.CinemaMovieShows;
import com.ticket_booking.main.entities.User;

public class TicketAPIHystrixCommand<T> extends HystrixCommand<T> {

	private RestTemplate restTemplate;
	private String url;
	private Class<T> responseType;
	private Object uriVariables;

	public TicketAPIHystrixCommand(Setter config, RestTemplate restTemplate, String aUrl, Class<T> aResponseType, Object...aUriVariables) {
		super(config);
		this.restTemplate= restTemplate;
		this.url= aUrl;
		this.responseType= aResponseType;
		this.uriVariables= aUriVariables;
	}

	@Override
	public T run() throws Exception {
		return restTemplate.getForObject(url, responseType, uriVariables);
	}

	@Override
	public T getFallback() {
		if(responseType == User.class) {
			User user= new User();
			user.setPhoneNo("0123456789");
			user.setUserId("NA");
			user.setUserName("NA");
			return (T)user;
		}
		if(responseType == CinemaMovieShows.class) {
			CinemaMovieShows show= new CinemaMovieShows();
			show.setEventId(-1);
			show.setMovie(null);
			show.setShowDate(new Date(0));
			show.setShows("1");
			
			return (T)show;
		}
		return null;
	}
	
}
