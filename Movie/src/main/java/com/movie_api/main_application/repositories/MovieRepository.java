package com.movie_api.main_application.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.movie_api.main_application.entities.Movie;

@Repository
@Transactional
public class MovieRepository {

	@PersistenceContext
	private EntityManager movieEntityManager;
	
	private static final int BATCH_SIZE= 5;
	
	public List<Movie> getAllMovieList(){
		try {
			List<Movie> movieList= movieEntityManager.createQuery("select m from Movie m", Movie.class).getResultList();
			return movieList;
		}catch(Exception exception) {
			exception.printStackTrace();
			throw exception;
		}
	}
	
	public String addMovie(Movie movie) {
		try {
			movieEntityManager.persist(movie);
			return movie.getMovieId();
		}catch(Exception exception) {
			exception.printStackTrace();
			throw exception;
		}
	}
	
	public String updateMovie(Movie movie) {
		try {
			movieEntityManager.merge(movie);
			return movie.getMovieId();
		}catch(Exception exception) {
			exception.printStackTrace();
			throw exception;
		}
	}
	
	public boolean deleteMovie(String movieId) {
		try {
			Movie movie= movieEntityManager.find(Movie.class, movieId);
			movieEntityManager.remove(movie);
			return true;
		}catch(Exception exception) {
			exception.printStackTrace();
			throw exception;
		}
	}
	
	public boolean insertMovieList(List<Movie> movieList) {
		try {
			
			for(int i= 0; i< movieList.size(); i++) {
				
				movieEntityManager.persist(movieList.get(i));
				
				if((i+ 1)% BATCH_SIZE== 0) {
					movieEntityManager.flush();
					movieEntityManager.clear();
				}
			}
			return true;
			
		}catch(Exception exception) {
			exception.printStackTrace();
			throw exception;
		}
	}
	
	public List<Movie> getMovieByFilter(String field, String filter){
		
		try {
			String query= "SELECT m FROM Movie m WHERE "+ "m."+field+ "= '"+ filter+ "'";
			List<Movie> movieList= movieEntityManager.createQuery(query, Movie.class).getResultList();
			return movieList;
		}catch(Exception exception) {
			exception.printStackTrace();
			throw exception;
		}
	}
	
	public Movie getMovieById(String movieId) {
		
		try {
			return movieEntityManager.find(Movie.class, movieId);
		}catch(Exception exception) {
			exception.printStackTrace();
			throw exception;
		}
	}
	
	public List<Movie> getMoviesIn(String field, List<String> filtersList){
		try {
			Query query= movieEntityManager.createQuery("SELECT m FROM Movie m WHERE m."+ field+ " IN ?1", Movie.class);
			query.setParameter(1, filtersList);
			return query.getResultList();
		}catch(Exception exception) {
			exception.printStackTrace();
			throw exception;
		}
	}
	
}