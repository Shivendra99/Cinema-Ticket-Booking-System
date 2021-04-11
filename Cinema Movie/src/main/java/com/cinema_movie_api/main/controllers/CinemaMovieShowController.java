package com.cinema_movie_api.main.controllers;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cinema_movie_api.main.entities.CinemaMovieShows;
import com.cinema_movie_api.main.services.CinemaMovieShowService;

@RestController
@RequestMapping("/showAPI")
public class CinemaMovieShowController {
	
	@Autowired
	private CinemaMovieShowService cinemaMovieShowService;
	
	@RequestMapping(method= RequestMethod.GET, value= "/show/{showId}")
	public CinemaMovieShows getShowById(@PathVariable("showId") int aShowId) {
		return cinemaMovieShowService.getShowById(aShowId);
	}
	
	@RequestMapping(method= RequestMethod.GET, value= {"/shows/{date}/{cinemaId}", "/shows" })
	public List<CinemaMovieShows> getFilteredMovieShows(@PathVariable(value= "date", required= false) Date date, @PathVariable(value= "cinemaId", required= false) String cinemaId){
	
		return cinemaMovieShowService.getFilteredShowList(date, cinemaId);
	}
	
	/**
	 * Would be needing another request object/ DTO
	 * 
	 * @param showList
	 * @return
	 */
	@RequestMapping(method= RequestMethod.POST, value= "/shows")
	public boolean addShowList(@RequestBody List<CinemaMovieShows> showList, @RequestBody List<String> aCinemaHallIdList, @RequestBody List<String> aMovieIdList ) {
		return cinemaMovieShowService.addShowList(showList, aCinemaHallIdList, aMovieIdList);
	}
	
	@RequestMapping(method= RequestMethod.POST, value= "/show/{cinemaId}/{movieId}")
	public String addShow(@PathVariable(value= "cinemaId") String cinemaId, @PathVariable(value= "movieId") String movieId, @RequestBody CinemaMovieShows cinemaMovieShow) {
		return cinemaMovieShowService.addUpdateShow(cinemaId, movieId, cinemaMovieShow, true);
	}
	
	@RequestMapping(method= RequestMethod.PUT, value= "/show/{cinemaId}/{movieId}")
	public String updateMovieShow(@PathVariable(value= "cinemaId") String cinemaId, @PathVariable(value= "movieId") String movieId, @RequestBody CinemaMovieShows aShow) {
		return cinemaMovieShowService.addUpdateShow(cinemaId, movieId, aShow, false);
	}
	
	@RequestMapping(method= RequestMethod.DELETE, value= "/show/{showId}")
	public boolean deleteMovieShow(@PathVariable(value= "showId") String showId) {
		return cinemaMovieShowService.deleteShow(showId);
	} 

}