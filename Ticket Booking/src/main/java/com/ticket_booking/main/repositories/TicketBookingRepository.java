package com.ticket_booking.main.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TransactionRequiredException;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.ticket_booking.main.entities.TicketBooking;

@Repository
@Transactional
public class TicketBookingRepository {

	@PersistenceContext(name = "com.cinema.booking.persistence")
	private EntityManager entityManager;

	private EntityManager getEntityManager() {
		return EntityManagerHelper.getEntityManager();
	}

	private void closeEntityManager() {
		EntityManagerHelper.closeEntityManager();
	}

	public TicketBooking bookTicket(TicketBooking ticket) throws Exception{

		EntityManager entityManager= null;
		try {

			entityManager= getEntityManager();

			entityManager.getTransaction().begin();
			entityManager.persist(ticket); 
			entityManager.getTransaction().commit();

			return ticket;
		}catch(Exception exception) {
			exception.printStackTrace();
			throw exception;
		}finally {
			if(entityManager!= null)
				closeEntityManager();
		}

	}

	public TicketBooking getTicketBySeat(int aEventId, int aSeat) {

		EntityManager entityManager= null;
		try {
			entityManager= getEntityManager();
			entityManager.getTransaction().begin();

			Query query= entityManager.createQuery("select t from TicketBooking t where t.show.eventId = ?1 and t.seats = ?2", TicketBooking.class);
			query.setParameter(1, aEventId);
			query.setParameter(2, aSeat);
			TicketBooking ticket= (TicketBooking) query.getSingleResult();

			entityManager.getTransaction().commit();

			return ticket;

		}catch(NoResultException noResultException) {
			noResultException.printStackTrace();
			throw noResultException;
		}catch(Exception exception) {
			exception.printStackTrace();
			throw exception;
		}finally {
			if(entityManager!= null) {
				closeEntityManager();
			}
		}
	}

//	/**
//	 * Issue coming while handling entity relationships in application managed EntityManager
//	 * 
//	 * @param aEventId
//	 * @return
//	 */
//	public List<TicketBooking> getTicketsByEvent(int aEventId){
//
//		EntityManager entityManager= null;
//		try {
//			entityManager= getEntityManager();
//			entityManager.getTransaction().begin();
//
//			Query query= entityManager.createQuery("select t from TicketBooking t where t.show.eventId = ?1", TicketBooking.class);
//			query.setParameter(1, aEventId);
//			List<TicketBooking> ticketList= query.getResultList();
//
//			entityManager.getTransaction().commit();
//
//			return ticketList;
//
//		}catch(NoResultException noResultException) {
//			noResultException.printStackTrace();
//			throw noResultException;
//		}catch(Exception exception) {
//			exception.printStackTrace();
//			throw exception;
//		}finally {
//			if(entityManager!= null) {
//				closeEntityManager();
//			}
//		}
//
//	}

	public List<TicketBooking> getTicketsByEvent(int aEventId){

		try {
			Query query= entityManager.createQuery("select t from TicketBooking t where t.show.eventId = ?1", TicketBooking.class);
			query.setParameter(1, aEventId);
			List<TicketBooking> ticketList= query.getResultList();
			return ticketList;
		}catch(NoResultException noResultException) {
			noResultException.printStackTrace();
			throw noResultException;
		}catch(Exception exception) {
			exception.printStackTrace();
			throw exception;
		}
	}
	
	public List<TicketBooking> getTicketsByUser(String aUserId){

		try {
			Query query= entityManager.createQuery("select t from TicketBooking t where t.user.userId = ?1", TicketBooking.class);
			query.setParameter(1, aUserId);
			List<TicketBooking> ticketList= query.getResultList();
			return ticketList;
		}catch(NoResultException noResultException) {
			noResultException.printStackTrace();
			throw noResultException;
		}catch(Exception exception) {
			exception.printStackTrace();
			throw exception;
		}
	}
	
	public boolean deleteTicket(int aTicketId) {
		
		EntityManager entityManager= null;
		try {
			entityManager= getEntityManager();
			entityManager.getTransaction().begin();
			TicketBooking ticket= entityManager.find(TicketBooking.class, aTicketId);
			entityManager.remove(ticket);
			entityManager.getTransaction().commit();

			return true;
		}catch(IllegalArgumentException illegalArgumentException) {
			illegalArgumentException.printStackTrace();
			throw illegalArgumentException;
		}catch(TransactionRequiredException transactionRequiredException) {
			transactionRequiredException.printStackTrace();
			throw transactionRequiredException;
		}catch(Exception exception) {
			exception.printStackTrace();
			throw exception;
		}finally {
			if(entityManager!= null) {
				closeEntityManager();
			}
		}
	}

}