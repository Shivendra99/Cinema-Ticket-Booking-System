package com.ticket_booking.main.controllers;

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
@RequestMapping(value= "/ticket-booking")
@CrossOrigin("*")
public class TicketBookingController {

	@Autowired
	private TicketBookingService ticketBookingService;
	
	@RequestMapping(method= RequestMethod.POST, value= "/ticket/{userId}/{eventId}")
	public String bookTicket(@PathVariable(value= "userId") String aUserId, @PathVariable(value= "eventId") int aEventId, @RequestBody TicketBooking aTicket) {
		return ticketBookingService.bookTicket(aUserId, aEventId, aTicket);
	}
	
}