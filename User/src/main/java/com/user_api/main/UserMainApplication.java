package com.user_api.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class UserMainApplication {

	public static void main(String args[]) {
		SpringApplication.run(UserMainApplication.class, args);
	}
	
}
