package com.cinema_movie_api.main.repositories;

import java.sql.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.cinema_movie_api.main.entities.CinemaMovieShows;

@Repository
@Transactional
public class CinemaMovieRepository {

	@PersistenceContext
	private EntityManager cinemaMovieEntityManager;
	
	private static final int BATCH_SIZE= 5;
	
	public CinemaMovieShows getShowById(int aEventId) {
		
		try {
			return cinemaMovieEntityManager.find(CinemaMovieShows.class, aEventId);
		}catch(Exception exception) {
			exception.printStackTrace();
			throw exception;
		}
		
	}

	public List<CinemaMovieShows> getFilteredShowList(Date aDate, String aCinemaId){
		try {
			String selectQuery= "select s from CinemaMvoie s ";
			Query query= null;
			
			if(aDate== null || aCinemaId== null) 
				query= cinemaMovieEntityManager.createQuery(selectQuery);
			else {
				
				selectQuery= selectQuery+ " where s.showDate"+ "= ?1";
				boolean secondFilter= false;
				
				if(aCinemaId!= null) {
					selectQuery= selectQuery+ " and s.cinemaHall.cinemaId"+ "= ?2";
					secondFilter= true;
				}
				
				query= cinemaMovieEntityManager.createQuery(selectQuery, CinemaMovieShows.class);
				query.setParameter(1, aDate);
				if(secondFilter)
					query.setParameter(2, aCinemaId);
				
			}
			
			return query.getResultList();
		}catch(Exception exception) {
			exception.printStackTrace();
			throw exception;
		}
	}

	public boolean addShowList(List<CinemaMovieShows> showList) {
		try {
			
			for(int i= 0; i< showList.size(); i++) {
				cinemaMovieEntityManager.persist(showList.get(i));

				if(i> 0 && (i+ 1)% BATCH_SIZE== 0) {
					cinemaMovieEntityManager.flush();
					cinemaMovieEntityManager.clear();
				}
			}
			return true;
		}catch(Exception exception) {
			exception.printStackTrace();
			throw exception;
		}
	}
	
	public int addShow(CinemaMovieShows aShow) {
		try {
			
			cinemaMovieEntityManager.persist(aShow);
			return aShow.getEventId();
		}catch(Exception exception) {
			exception.printStackTrace();
			throw exception;
		}
	}

	public int updateShow(CinemaMovieShows cinemaMovieShow) {
		try {
			
			CinemaMovieShows show= cinemaMovieEntityManager.find(CinemaMovieShows.class, cinemaMovieShow.getEventId());
			
			if(show!= null) {
				cinemaMovieEntityManager.merge(cinemaMovieShow);
				return cinemaMovieShow.getEventId();
			}
			
			return -1;
		}catch(Exception exception) {
			exception.printStackTrace();
			throw exception;
		}		
	}

	public boolean deleteShow(String eventId) {
		try {

			CinemaMovieShows show= cinemaMovieEntityManager.find(CinemaMovieShows.class, eventId);
			if(show!= null) {
				cinemaMovieEntityManager.remove(show);
				return true;
			}

			return false;
		}catch(Exception exception) {
			exception.printStackTrace();
			throw exception;
		}		
	}

}