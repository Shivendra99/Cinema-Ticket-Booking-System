package com.test.project;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@SpringBootApplication
@EnableCircuitBreaker
@RestController
@Configuration
@EnableEurekaClient
@EnableAsync
public class MainApplication {
	
	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
	
	@Autowired
	private RestTemplate restTemplate;
	
	public static void main(String args[]) {
		SpringApplication.run(MainApplication.class, args);
	}
	
	@Async
	@HystrixCommand(fallbackMethod = "getStringFallback")
	@RequestMapping(method= RequestMethod.GET, value= "/str")
	//public String getString(){
	public CompletableFuture<String> getString() {
		Object obj= restTemplate.getForObject("http://CINEMA-API/cinemaAPI/cinemaHall/dl_pvr", Object.class);
		return CompletableFuture.completedFuture(obj.toString());
		//return "norma return string";
	}
	
	//public String getStringFallback(){
	public String getStringFallback() {
		//return CompletableFuture.completedFuture("fall back string");
		return "Fall back string";
	}

}