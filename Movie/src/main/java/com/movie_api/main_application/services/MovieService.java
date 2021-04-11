package com.movie_api.main_application.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.movie_api.main_application.entities.Movie;
import com.movie_api.main_application.repositories.MovieRepository;

@Service
public class MovieService {

	@Autowired
	private MovieRepository movieRepository;
	
	private final static String MY_LOG= "My logger: ";
	
	public List<Movie> getAllMovies() {
		
		try {
			return movieRepository.getAllMovieList();
		}catch(Exception exception) {
			System.out.println(MY_LOG+ exception.toString());
		}
		return null;
	}
	
	public String addMovie(Movie movie) {
		
		try {
			return "Movie with ID: "+ movieRepository.addMovie(movie)+ " inserted";
		}catch(Exception exception) {
			System.out.println(MY_LOG+ exception.toString());
			return "Error in inserting movie: "+ exception.toString();
		}
	}
	
	public String updateMovie(Movie movie) {
		
		try {
			return movieRepository.updateMovie(movie);
		}catch(Exception exception) {
			System.out.println(MY_LOG+ exception.toString());
			return "Error in updating movie: "+ exception.toString();
		}
	}
	
	public String deleteMovie(String movieId) {
		
		try {
			return (movieRepository.deleteMovie(movieId))? "Movie with id: "+ movieId+ " deleted": "Unable to delete the movie";
		}catch(Exception exception) {
			System.out.println(MY_LOG+ exception.toString());
			return "Error in deleting movie: "+ exception.toString();
		}
	}
	
	public boolean insertMovieList(List<Movie> movieList) {
		try {
			return movieRepository.insertMovieList(movieList);
		}catch(Exception exception) {
			System.out.println(MY_LOG+ exception.toString());
			return false;
		}
	}
	
	public Movie getMovieById(String movieId) {
		try {
			return movieRepository.getMovieById(movieId);
		}catch(Exception exception) {
			System.out.println(MY_LOG+ exception.toString());
			return null;
		}
	}
	
	public List<Movie> getMovieListByFilter(String field, String filter) {
		try {
			return movieRepository.getMovieByFilter(field, filter);
		}catch(Exception exception) {
			System.out.println(MY_LOG+ exception.toString());
			return null;
		}
	}
	
	public List<Movie> getMoviesListIn(String field, List<String> filtersList) {
		try {
			List<Movie> movieList= movieRepository.getMoviesIn(field, filtersList);
			return movieList;
		}catch(Exception exception) {
			System.out.print(MY_LOG+ " Error in retrieving filtered list: "+ exception.toString());
			return null;
		}
	}
	
}