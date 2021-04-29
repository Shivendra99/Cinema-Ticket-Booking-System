package com.cinema_movie_api.main.services;

import java.sql.Date;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.cinema_movie_api.main.entities.CinemaHall;
import com.cinema_movie_api.main.entities.Movie;
import com.netflix.hystrix.HystrixCommand;

@Component
@Scope(value = "prototype")
public class ShowAPICallHystrixCommand<T> extends HystrixCommand<T>{

	private RestTemplate restTemplate;
	private String url;
	private Class<T> responseType;
	private Object uriVariables;

	public ShowAPICallHystrixCommand(Setter config, RestTemplate restTemplate, String aUrl, Class<T> aResponseType, Object...aUriVariables) {
		super(config);
		this.restTemplate= restTemplate;
		this.url= aUrl;
		this.responseType= aResponseType;
		this.uriVariables= aUriVariables;
	}

	@Override
	public T run() throws Exception {

		T response= restTemplate.getForObject(url, responseType, uriVariables);
		return response;
	}

	@Override
	public T getFallback() {

		if(responseType == CinemaHall.class) {
			CinemaHall cinemaHall= new CinemaHall();
			cinemaHall.setCapacity(1);
			cinemaHall.setCinemaId("NA");
			cinemaHall.setCinemaName("NA");
			cinemaHall.setCity("NA");

			return (T)cinemaHall;
		}
		else if(responseType== Movie.class) {
			Movie movie= new Movie();
			movie.setMovieId("NA");
			movie.setMovieJonre("Others");
			movie.setMovieLanguage("NA");
			movie.setMovieName("NA");
			movie.setReleaseDate(new Date(0));

			return (T)movie;
		}
		return null;
	}

}