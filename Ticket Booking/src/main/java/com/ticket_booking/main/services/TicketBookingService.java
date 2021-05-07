package com.ticket_booking.main.services;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.HystrixCommand;
import com.ticket_booking.main.entities.CinemaMovieShows;
import com.ticket_booking.main.entities.TicketBooking;
import com.ticket_booking.main.entities.User;
import com.ticket_booking.main.repositories.TicketBookingRepository;

@Service
public class TicketBookingService {

	@Autowired
	private TicketBookingRepository ticketBookingRepository;

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private HystrixCommand.Setter config;

	private static final String MY_LOG= "My Log: ";

	public TicketBooking bookTicket(String userId, int eventId, TicketBooking ticket) {

		final String METHOD_NAME= "bookTicket";

		try {
			Future<User> userFuture= makeGetCall(API_URL.HTTP.toString()+ API_URL.USER_API.toString()+ "/userAPI/user/"+ userId, User.class);
			Future<CinemaMovieShows> showFuture= makeGetCall(API_URL.HTTP.toString()+ API_URL.SHOWS_API.toString()+ "/showAPI/show/"+ eventId, CinemaMovieShows.class);

			User user= (User)userFuture.get();
			CinemaMovieShows show= showFuture.get();

			if(user== null || show== null) {
				System.out.print(MY_LOG+ "User/ show doesn't exists.");
				return null;
			}

			int seat= ticket.getSeat();
			if(show.getCinemaHall().getCapacity()< seat || seat<= 0) {
				System.out.print(MY_LOG+ "Seat doesn't exists");
				return null;
			}

			boolean isSeatAvaiable= checkSeatAvailability(eventId, seat);
			if(!isSeatAvaiable) {
				System.out.print(MY_LOG+ "Seat's not available");
				return null;
			}

			ticket.setUser(user);
			ticket.setShow(show);

			// Make synchronous call to make payment API, only on successful response proceed forward

			return ticketBookingRepository.bookTicket(ticket);

		}catch(Exception exception) {
			exception.printStackTrace();
			System.out.print(MY_LOG+ "Error in "+ METHOD_NAME+ ": "+ exception.toString());
			return null;
		}	
	}

	public boolean checkSeatAvailability(int aEventId, int seat) {

		final String METHOD_NAME= "checkSeatAvailability";

		try {
			TicketBooking ticket= ticketBookingRepository.getTicketBySeat(aEventId, seat);

			return (ticket== null)? true: false;	
		}catch(NoResultException noResultException) {
			System.out.print(MY_LOG+ "Error in "+ METHOD_NAME+ ": "+ noResultException.toString());
			return true;
		}catch(EmptyResultDataAccessException emptyResultDataAccessException) {
			System.out.print(MY_LOG+ "Error in "+ METHOD_NAME+ ": "+ emptyResultDataAccessException.toString());
			return true;
		}catch(Exception exception) {
			System.out.print(MY_LOG+ exception.toString());
			return false;
		}

	}

	public List<Integer> getAvailableSeats(int eventId){

		final String METHOD_NAME= "getAvailableSeats";

		try {
			Future<CinemaMovieShows> showFuture= makeGetCall(API_URL.HTTP.toString()+ API_URL.SHOWS_API.toString()+ "/showAPI/show/"+ eventId, CinemaMovieShows.class);

			List<TicketBooking> ticketList= getTicketListByEvent(eventId);
			List<Integer> occupiedSeatsList= ticketList.stream()
					.map(v -> v.getSeat())
					.collect(Collectors.toList());

			CinemaMovieShows show= showFuture.get();
			if(show== null) {
				System.out.print("No event exists with ID: "+ eventId);
				return null;
			}

			int capacity= show.getCinemaHall().getCapacity();
			List<Integer> availableSeatsList= new ArrayList<Integer>();

			for(int i= 1; i<= capacity; i++)
				if(!occupiedSeatsList.contains(i))
					availableSeatsList.add(i);

			return availableSeatsList;

		}catch(Exception exception) {
			System.out.print(MY_LOG+ METHOD_NAME+ ": "+ exception.toString());
			return null;
		}

	}

	public List<TicketBooking> getTicketListByEvent(int aEventId){
		final String METHOD_NAME= "getFilteredTicketList";

		try {
			return ticketBookingRepository.getTicketsByEvent(aEventId);
		}catch(Exception exception) {
			System.out.print(MY_LOG+ METHOD_NAME+ ": "+ exception.toString());
			return null;
		}
	}

	public List<TicketBooking> getTicketListByUser(String aUserId){
		final String METHOD_NAME= "getFilteredTicketList";

		try {
			return ticketBookingRepository.getTicketsByUser(aUserId);
		}catch(Exception exception) {
			System.out.print(MY_LOG+ METHOD_NAME+ ": "+ exception.toString());
			return null;
		}
	}

	public boolean deleteTicket(int aTicketId) {
		final String METHOD_NAME= "deleteTicket";

		try {
			return ticketBookingRepository.deleteTicket(aTicketId);
		}catch(Exception exception) {
			System.out.print(MY_LOG+ METHOD_NAME+ ": "+ exception.toString());
			return false;
		}
	}

	@Async
	private <T> Future<T> makeGetCall(String aUrl, Class<T> aResponseType, Object...aUriVariables){

		Future<T> response= (new TicketAPIHystrixCommand<T>(config, restTemplate, aUrl, aResponseType, aUriVariables)).queue();
		return response;
	}

}