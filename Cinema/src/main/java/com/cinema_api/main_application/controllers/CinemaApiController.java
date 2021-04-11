package com.cinema_api.main_application.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cinema_api.main_application.entities.CinemaHall;
import com.cinema_api.main_application.services.CinemaHallService;

@RequestMapping(value= "/cinemaAPI")
@RestController
public class CinemaApiController {
	
	@Autowired
	private CinemaHallService cinemaHallService;
	
	@RequestMapping(method= RequestMethod.GET, value= "/cinemaHalls")
	public List<CinemaHall> getAllCinemaHalls(){
		return cinemaHallService.getAllCinemaHall();
	}
	
	@RequestMapping(method= RequestMethod.GET, value= "/cinemaHall/{cinemaHallId}")
	public CinemaHall findCinemaHallById(@PathVariable(value= "cinemaHallId") String aCinemaHallId) {
		return cinemaHallService.findCinemaHallById(aCinemaHallId);
	}
	
	@RequestMapping(method= RequestMethod.POST, value= "/cinemaHall")
	public String addCinemaHall(@RequestBody CinemaHall aCinemaHall){
		return cinemaHallService.addCinemaHall(aCinemaHall);
	}
	
	@RequestMapping(method= RequestMethod.DELETE, value= "/cinemaHall/{cinemaHallId}")
	public boolean deleteCinemaHall(@PathVariable(value= "cinemaHallId") String aCinemaHallId) {
		return cinemaHallService.deleteCinemaHall(aCinemaHallId);
	}
	
	@RequestMapping(method= RequestMethod.PUT, value= "/cinemaHall")
	public CinemaHall updateCinemaHall(@RequestBody CinemaHall aCinemaHall) {
		return cinemaHallService.updateCinemaHall(aCinemaHall);
	}
	
	@RequestMapping(method= RequestMethod.POST, value= "/cinemaHalls")
	public String addCinemaHallList(@RequestBody List<CinemaHall> aCinemaHallList) {
		return cinemaHallService.addCinemaHallList(aCinemaHallList);
	}
	
	@RequestMapping(method= RequestMethod.GET, value= "/cinemaHallsByCity/{param}")
	public List<CinemaHall> getFilteredCinemaHallList(@PathVariable(value= "param") String param){
		return cinemaHallService.getCinemaHallListByCity(param);
	}
	
	@RequestMapping(method= RequestMethod.POST, value= "/cinemaHallsIn/{field}")
	public List<CinemaHall> getFilteredCinemaHallsIn(@PathVariable(value= "field") String field, @RequestBody List<String> filtersList){
		return cinemaHallService.getCinemaHallListIn(field, filtersList);
	}
	
	@RequestMapping(method= RequestMethod.GET, value= "/capacity/{cinemaHallId}")
	public int getCinemaHallCapacity(@PathVariable(value= "cinemaHallId") String aCimenaHallId) {
		return cinemaHallService.getCinemaHallCapacity(aCimenaHallId);
	}
	
}