package com.ticket_booking.main.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ticket_booking.main.entities.TicketBooking;
import com.ticket_booking.main.services.TicketBookingService;

@RestController
@RequestMapping(value= "/ticket-booking-API")
@CrossOrigin("*")
public class TicketBookingController {

	@Autowired
	private TicketBookingService ticketBookingService;
	
	@RequestMapping(method= RequestMethod.POST, value= "/ticket/{userId}/{eventId}")
	public TicketBooking bookTicket(@PathVariable(value= "userId") String aUserId, @PathVariable(value= "eventId") int aEventId, @RequestBody TicketBooking aTicket) {
		return ticketBookingService.bookTicket(aUserId, aEventId, aTicket);
	}
	
	@RequestMapping(method= RequestMethod.GET, value= "/seat/{eventId}/{seat}")
	public boolean checkSeatAvailability(@PathVariable(value= "eventId") int aEventId, @PathVariable("seat") int aSeat) {
		return ticketBookingService.checkSeatAvailability(aEventId, aSeat);
	}
	
	@RequestMapping(method= RequestMethod.GET, value= "/availableSeatList/{eventId}")
	public List<Integer> getAvailableSeats(@PathVariable(value= "eventId") int eventId) {
		return ticketBookingService.getAvailableSeats(eventId);
	}
	
	@RequestMapping(method= RequestMethod.GET, value= {"/tickets/event/{eventId}", "/tickets/user/{userId}"})
	public List<TicketBooking> getTicketListByEventOrUser(@PathVariable(value= "eventId", required = false) Integer aEventId, 
			                                        @PathVariable(value= "userId", required= false) String aUserId){
		return (aUserId== null)?  ticketBookingService.getTicketListByEvent(aEventId): ticketBookingService.getTicketListByUser(aUserId);
	}
	
	@RequestMapping(method= RequestMethod.DELETE, value= "/ticket/{ticketId}")
	public boolean deleteTicket(@PathVariable(value= "ticketId") int aTicketId) {
		return ticketBookingService.deleteTicket(aTicketId);
	}
	
}