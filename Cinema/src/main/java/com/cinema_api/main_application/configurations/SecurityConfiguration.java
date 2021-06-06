package com.cinema_api.main_application.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.cinema_api.main_applciation.constants.USER_ROLES;
import com.cinema_api.main_application.services.UserDetailsServiceImpl;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		
//		auth.inMemoryAuthentication()
//		    .withUser("user")
//		    .password("user")
//		    .roles(USER_ROLES.USER.toString())
//		    .and()
//		    .withUser("admin")
//		    .password("admin")
//		    .roles(USER_ROLES.ADMIN.toString());
	
		auth.userDetailsService(userDetailsServiceImpl);
		
	}
	
	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http.authorizeRequests()
		    .antMatchers("/cinemaAPI/**", "/actuator**").hasAnyRole(USER_ROLES.ADMIN.toString(), USER_ROLES.DBA.toString(), USER_ROLES.DEV.toString(), USER_ROLES.TEST.toString())
		    .antMatchers("/**").permitAll()
		    .and().formLogin();
	}

}