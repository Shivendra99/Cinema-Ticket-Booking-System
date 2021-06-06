package com.cinema_api.main_application.repositories;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import com.cinema_api.main_application.entities.User;

@Repository
@Transactional
public class UserAuthenticationRepository{

	@PersistenceContext
	private EntityManager entityManager;
	
	/**
	 * Finds user by it's userId, throws {@link UsernameNotFoundException} 
	 * if no user is found corresponding to given userId.
	 * 
	 */
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		
		try {
			User user= entityManager.find(User.class, userId);
			return user;
		}catch(UsernameNotFoundException usernameNotFoundException) {
			usernameNotFoundException.printStackTrace();
			throw usernameNotFoundException;
		}catch(Exception exception) {
			exception.printStackTrace();
			throw exception;
		}

	}
	
	public User createUser(User user) {
		try {
			entityManager.persist(user);
			return user;
		}catch(Exception exception) {
			exception.printStackTrace();
			throw exception;
		}
	}

}