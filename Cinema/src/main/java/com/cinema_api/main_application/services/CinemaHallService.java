package com.cinema_api.main_application.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cinema_api.main_application.entities.CinemaHall;
import com.cinema_api.main_application.repositories.CinemaRepository;

@Service
public class CinemaHallService {
	
	private final static String MY_LOG= "My logger: ";

	@Autowired
	private CinemaRepository cinemaRepository;
	
	public List<CinemaHall> getAllCinemaHall(){
		try {
			return cinemaRepository.getAllCinemaHalls();
		}catch(Exception exception) {
			exception.printStackTrace();
			return null;
		}
	}
	
	public String addCinemaHall(CinemaHall aCinemaHall) {
		try {
			if(cinemaRepository.addCinemaHall(aCinemaHall))
				return "Cinema Hall with ID: "+ aCinemaHall.getCinemaId()+ " inserted";
		}catch(Exception exception) {
			exception.printStackTrace();
			return "Error in inserting Cinema Hall: "+ exception.toString();
		}
		
		return "Cinema Hall record not inserted";
	}
	
	public CinemaHall findCinemaHallById(String aCinemaHallId) {
		try {
			return cinemaRepository.getCinemaHallById(aCinemaHallId);
		}catch(Exception exception) {
			exception.printStackTrace();
			return null;
		}
	}
	
	public boolean deleteCinemaHall(String aCinemaHallId) {
		try {
			return cinemaRepository.deleteCinemaHall(aCinemaHallId);
		}catch(Exception exception) {
			exception.printStackTrace();
			return false;
		}
	}
	
	public CinemaHall updateCinemaHall(CinemaHall aCinemaHall) {
		
		CinemaHall myCinemaHall= findCinemaHallById(aCinemaHall.getCinemaId());
		if(myCinemaHall!= null) {
			try {
				return cinemaRepository.updateCinemaHall(aCinemaHall);
			}catch(Exception exception) {
				exception.printStackTrace();
				return null;
			}
		}
		
		System.out.print(MY_LOG+ "No such record found to update");
		return null;
	}
	
	public String addCinemaHallList(List<CinemaHall> aCinemaHallList) {
		try {
			if(cinemaRepository.addCinemaHallList(aCinemaHallList))
				return "All Cinema Hall records added";
		}catch(Exception exception) {
			exception.printStackTrace();
			return "Error in inserting records: "+ exception;
		}
		
		return "Records not inserted";
	}
	
	public List<CinemaHall> getCinemaHallListByCity(String param) {
		try {
			return cinemaRepository.getCinamaHallsByCity(param);
		}catch(Exception exception) {
			System.out.print(MY_LOG+ " Error in retrieving filtered list: "+ exception.toString());
			return null;
		}
	}
	
	public List<CinemaHall> getCinemaHallListIn(String field, List<String> filtersList) {
		try {
			List<CinemaHall> cinemaHallList= cinemaRepository.getCinemaHallsIn(field, filtersList);
			return cinemaHallList;
		}catch(Exception exception) {
			System.out.print(MY_LOG+ " Error in retrieving filtered list: "+ exception.toString());
			return null;
		}
	}
	
	public int getCinemaHallCapacity(String aCinemaHallId) {
		try {
			return cinemaRepository.getCinemaHallCapacity(aCinemaHallId);
		}catch(Exception exception) {
			System.out.print(MY_LOG+ " Error in fetching capacity of Cinema Hall with Id: "+ exception.toString());
			return -1;
		}
	}
	
}