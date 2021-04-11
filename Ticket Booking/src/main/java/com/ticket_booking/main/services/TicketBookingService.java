package com.ticket_booking.main.services;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
	
	private static final String MY_LOG= "My Log: ";
	
	public String bookTicket(String userId, int eventId, TicketBooking ticket) {
		
		try {
			CompletableFuture<User> userCompletableFuture= makeGetCall(API_URL.HTTP.toString()+ API_URL.USER_API.toString()+ "/userAPI/user/"+ userId, User.class);
			CompletableFuture<CinemaMovieShows> showCompletableFuture= makeGetCall(API_URL.HTTP.toString()+ API_URL.SHOWS_API.toString()+ "/showAPI/show/"+ eventId, CinemaMovieShows.class);

			User user= (User)userCompletableFuture.get();
			CinemaMovieShows show= showCompletableFuture.get();

			if(user== null || show== null)
				return "User/ show doesn't exists.";
			
			ticket.setUser(user);
			ticket.setShow(show);
			
			// Make synchronous call to make payment API, only on successful response proceed forward
			
			int ticketId= ticketBookingRepository.bookTicket(ticket);
			
			return "Ticket with ID "+ ticketId+ " booked successfully."; 
		}catch(Exception exception) {
			exception.printStackTrace();
			return "Error in booking the ticket: "+ exception.toString();
		}	
	}
	
	@Async
	private <T> CompletableFuture<T> makeGetCall(String aUrl, Class<T> aResponseType, Object...aUriVariables){
		
		T response= null;
		
		if(aUriVariables.length== 0)
			response= restTemplate.getForObject(aUrl, aResponseType);
		else
			response= restTemplate.getForObject(aUrl, aResponseType, aUriVariables);
		
		return CompletableFuture.completedFuture(response);
		
	}

}