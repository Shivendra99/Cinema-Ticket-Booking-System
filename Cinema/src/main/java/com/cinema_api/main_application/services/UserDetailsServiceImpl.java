package com.cinema_api.main_application.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cinema_api.main_application.repositories.UserAuthenticationRepository;


@Service
public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	private UserAuthenticationRepository userAuthenticationRepository;
	
	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		try {
			return userAuthenticationRepository.loadUserByUsername(userId);
		}catch(UsernameNotFoundException usernameNotFoundException) {
			usernameNotFoundException.printStackTrace();
			throw usernameNotFoundException;
		}
		catch(Exception exception) {
			exception.printStackTrace();
			return null;
		}
	}

}