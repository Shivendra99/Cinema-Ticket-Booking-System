package com.ticket_booking.main.repositories;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.springframework.stereotype.Component;

/**
 * Provides thread safe application managed entityManagers
 * 
 * @author Shiv
 *
 */
@Component
public class EntityManagerHelper {
	
	private static EntityManagerFactory entityManagerFactory;
	private static ThreadLocal<EntityManager> threadLocal;
	
	static {
		entityManagerFactory= Persistence.createEntityManagerFactory("com.cinema.booking.persistence");
		threadLocal= new ThreadLocal<EntityManager>();
	}
	
	public static EntityManager getEntityManager() {
		
		EntityManager entityManager= threadLocal.get();
		
		if(entityManager== null) {
			entityManager= entityManagerFactory.createEntityManager();
			threadLocal.set(entityManager);
		}
		
		return threadLocal.get();
	}
	
	public static void closeEntityManager() {

		EntityManager entityManager= threadLocal.get();
		
		if(entityManager== null)
			return;
		
		entityManager.close();
		threadLocal.set(null);
	}
	
	public static void closeEntityManagerFactory() {
		entityManagerFactory.close();
	}

}