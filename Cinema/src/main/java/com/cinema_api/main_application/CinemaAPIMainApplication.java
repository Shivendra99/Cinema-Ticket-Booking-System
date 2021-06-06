package com.cinema_api.main_application;

import org.springframework.boot.SpringApplication; 
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class CinemaAPIMainApplication {

	public static void main(String args[]) {
		SpringApplication.run(CinemaAPIMainApplication.class, args);
	}
	
}
