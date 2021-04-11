package com.cinema_movie_api.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class CinemaMovieMainApplication {

	public static void main(String args[]) {
		SpringApplication.run(CinemaMovieMainApplication.class, args);
	}
	
}