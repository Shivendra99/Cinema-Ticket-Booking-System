package com.movie_api.main_application.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.movie_api.main_application.entities.Movie;
import com.movie_api.main_application.services.MovieService;

@RestController
@RequestMapping(value= "/movieAPI")
public class MovieAPIController {
	
	@Autowired
	MovieService movieService;
	
	@RequestMapping(method= RequestMethod.GET, value= "/movies")
	public List<Movie> getAllMovies(){
		return movieService.getAllMovies();
	}
	
	@RequestMapping(method= RequestMethod.GET, value= "/movie/{movieId}")
	public Movie findCinemaHallById(@PathVariable(value= "movieId") String aMovieId) {
		return null;
		//return movieService.findCinemaHallById(aMovieId);
	}
	
	@RequestMapping(method= RequestMethod.POST, value= "/movie")
	public String addCinemaHall(@RequestBody Movie aMovie){
		return movieService.addMovie(aMovie);
	}
	
	@RequestMapping(method= RequestMethod.DELETE, value= "/movie/{movieId}")
	public String deleteMoive(@PathVariable(value= "movieId") String aMovieId) {
		return movieService.deleteMovie(aMovieId);
	}
	
	@RequestMapping(method= RequestMethod.PUT, value= "/movie")
	public String updateCinemaHall(@RequestBody Movie aMovie) {
		return movieService.updateMovie(aMovie);
	}
	
	@RequestMapping(method= RequestMethod.POST, value= "/movies")
	public boolean addMovieList(@RequestBody List<Movie> aMovieList) {
		return movieService.insertMovieList(aMovieList);
	}
	
	@RequestMapping(method= RequestMethod.GET, value= "/movies/{field}/{filter}")
	public List<Movie> getFilteredCinemaHallList(@PathVariable(value= "field") String field, @PathVariable(value= "filter") String filter){
		return movieService.getMovieListByFilter(field, filter);
	}
	
	@RequestMapping(method= RequestMethod.GET, value= "/movieById/{movieId}")
	public Movie getMovieById(@PathVariable("movieId") String movieId) {
		return movieService.getMovieById(movieId);
	}
	
	@RequestMapping(method= RequestMethod.POST, value= "/moviesIn/{field}")
	public List<Movie> getFilteredMoviesIn(@PathVariable(value= "field") String field, @RequestBody List<String> filtersList){
		return movieService.getMoviesListIn(field, filtersList);
	}

}