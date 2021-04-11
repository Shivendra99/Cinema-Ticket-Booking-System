package com.movie_api.main_application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableEurekaClient
@EnableScheduling
public class MovieAPIMainApplication {

	public static void main(String args[]) {
		SpringApplication.run(MovieAPIMainApplication.class, args);
	}
	
}
