package com.cinema_api.main_application.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.cinema_api.main_application.entities.CinemaHall;

@Repository
@Transactional
public class CinemaRepository {

	private static final int BATCH_SIZE= 5;
	
	//private Class<T> daoClass;
	
	@PersistenceContext
	private EntityManager cinemaEntityManager;
	
	
//	public void setDaoClass(Class<T> daoClass) {
//		this.daoClass= daoClass;
//	}
	
	public List<CinemaHall> getAllCinemaHalls(){
		try {
		List<CinemaHall> cinemaHallList= cinemaEntityManager.createQuery("Select C from CinemaHall C", CinemaHall.class).getResultList();
		return cinemaHallList;
		}catch(Exception exception) {
			exception.printStackTrace();
			throw exception;
		}
	}
	
	/*public List<T> getAllCinemaHalls(){
		try {
		List<T> itemList= cinemaEntityManager.createQuery("Select t from "+ daoClass.getClass().toString()+ " t", daoClass).getResultList();
		return itemList;
		}catch(Exception exception) {
			exception.printStackTrace();
			throw exception;
		}
	}*/
	
	public boolean addCinemaHall(CinemaHall aCinemaHall) {
		try {
			cinemaEntityManager.persist(aCinemaHall);
			if(null!= getCinemaHallById(aCinemaHall.getCinemaId()))
				return true;
			return false;
		}catch(Exception exception) {
			exception.printStackTrace();
			throw exception;
		}
	}
	
	public CinemaHall getCinemaHallById(String aCinemaId) {
		try {
			CinemaHall cinemaHall= cinemaEntityManager.find(CinemaHall.class, aCinemaId);
			return cinemaHall;
		}catch(Exception exception) {
			exception.printStackTrace();
			throw exception;
		}
	}
	
	public CinemaHall updateCinemaHall(CinemaHall aCinemaHall) {
		try {
			return cinemaEntityManager.merge(aCinemaHall);
		}catch(Exception exception) {
			exception.printStackTrace();
			throw exception;
		}
	}
	
	public boolean deleteCinemaHall(String aCinemaHallId) {
		try {
			CinemaHall cinemaHall= getCinemaHallById(aCinemaHallId);
			cinemaEntityManager.remove(cinemaHall);
			//cinemaEntityManager.detach(cinemaHall);
			return true;
		}catch(Exception exception) {
			exception.printStackTrace();
			throw exception;
		}
	}
	
	/**
	 * Won't be able to check with Hibernate log if batch processing is 
	 * happening or not. ??? .. Try with EntityManager.createNativeQuery()
	 * 
	 * @param aCinemaHallList
	 * @return
	 */
	public boolean addCinemaHallList(List<CinemaHall> aCinemaHallList) {

		try {
		for(int i= 0; i< aCinemaHallList.size(); i++) {
			
			cinemaEntityManager.persist(aCinemaHallList.get(i));
			
			if(i>0 && (i+1)% BATCH_SIZE== 0) {
				cinemaEntityManager.flush();
				cinemaEntityManager.clear();
			}	
		}
		
		//cinemaEntityManager.flush();
		//cinemaEntityManager.clear();
		
		 return true;
		}catch(Exception exception) {
			exception.printStackTrace();
			throw exception;
		}
	}
	
	public List<CinemaHall> getCinamaHallsByCity(String param){
		try {
			Query query= cinemaEntityManager.createQuery("SELECT c FROM CinemaHall c WHERE c.city like ?1", CinemaHall.class);
			query.setParameter(1, param);
			List<CinemaHall> filteredList= query.getResultList();
			return filteredList;
			
		}catch(Exception exception) {
			exception.printStackTrace();
			throw exception;
		}
	}
	
	public List<CinemaHall> getCinemaHallsIn(String field, List<String> filtersList){
		try {
			Query query= cinemaEntityManager.createQuery("SELECT c FROM CinemaHall c WHERE c."+ field+ " IN ?1", CinemaHall.class);
			query.setParameter(1, filtersList);
			return query.getResultList();
		}catch(Exception exception) {
			exception.printStackTrace();
			throw exception;
		}
	}
	
	public int getCinemaHallCapacity(String aCinemaHallId) {
		try {
			Query query= cinemaEntityManager.createQuery("select c.capacity from CinemaHall c where c.cinemaId like ?1", Integer.class);
			query.setParameter(1, aCinemaHallId);
			return (Integer)(query.getSingleResult());
		}catch(Exception exception) {
			exception.printStackTrace();
			throw exception;
		}
	}
	
}