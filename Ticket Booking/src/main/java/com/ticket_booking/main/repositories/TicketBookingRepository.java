package com.ticket_booking.main.repositories;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.ticket_booking.main.entities.TicketBooking;

@Repository
public class TicketBookingRepository {
	
	private EntityManager getEntityManager() {
		return EntityManagerHelper.getEntityManager();
	}
	
	private void closeEntityManager() {
		EntityManagerHelper.closeEntityManager();
	}
	
	public int bookTicket(TicketBooking ticket) throws Exception{
		
		EntityManager entityManager= null;
		try {
			
			entityManager= getEntityManager();
			
			entityManager.getTransaction().begin();
			entityManager.persist(ticket); 
			entityManager.getTransaction().commit();
			int id= ticket.getTicketId();

			closeEntityManager();
			
			return id;
		}catch(Exception exception) {
			exception.printStackTrace();
			
			if(entityManager!= null) {
				closeEntityManager();
			}
			
			throw exception;
		}
		
	}
	
}