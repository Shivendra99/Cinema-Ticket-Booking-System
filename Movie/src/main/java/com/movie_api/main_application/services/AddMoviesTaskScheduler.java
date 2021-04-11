package com.movie_api.main_application.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.movie_api.main_application.entities.Movie;

@Component
public class AddMoviesTaskScheduler {
	
	@Autowired
	private MovieService movieService;
	
	@Scheduled(initialDelayString = "${read_movie.task_scheduler.initial_delay}", fixedDelayString= "${read_movie.task_scheduler.fixed_delay}")
	public void addMoviesFromCSV() {
		
		List<Movie> movieList= null;
		
		try {
			movieList= parseMovieListFromCSV("C:\\Code Repo\\Cinema Booking System\\Movie\\src\\main\\resources\\MovieListCSV.csv");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.print("!! ALERT !! Unable to load movies from CSV: "+ e.toString());
			return;
		}
		
		movieService.insertMovieList(movieList);
	}
	
	private List<Movie> parseMovieListFromCSV(String path) throws FileNotFoundException{
		
		Scanner sc= null;
		
		try {
			sc= new Scanner(new File(path));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw e;
		}
		
		List<Movie> movieList= new ArrayList<Movie>();
		Movie movie= null;
		
		while(sc.hasNext()) {
			String[] arr= sc.nextLine().split(",");
			if(arr.length!= 5)
				continue;
			
			movie= new Movie();
			
			movie.setMovieId(arr[0]);
			movie.setMovieName(arr[1]);
			String dateArr[]= arr[2].split("-");
			Date date= new Date(Integer.parseInt(dateArr[0])- 1900, Integer.parseInt(dateArr[1])- 1, Integer.parseInt(dateArr[2]));
			movie.setReleaseDate(date);
			movie.setMovieLanguage(arr[3]);
			movie.setMovieJonre(arr[4]);
			
			movieList.add(movie);
		}
		
		System.out.println("Fetched movie list: ");
		movieList.stream().forEach(v -> System.out.println(v));
		
		return movieList;
	}

}