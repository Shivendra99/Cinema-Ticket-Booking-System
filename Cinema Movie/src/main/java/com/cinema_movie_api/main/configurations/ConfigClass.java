package com.cinema_movie_api.main.configurations;

import java.util.concurrent.Executor;

import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;
//import org.springframework.web.reactive.function.client.WebClient;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;

@Configuration
@EnableAsync
public class ConfigClass {

	@Bean
	@LoadBalanced
	public RestTemplate getRestTemplate() {
		HttpComponentsClientHttpRequestFactory clientHttpRequestFactory= new HttpComponentsClientHttpRequestFactory();
		clientHttpRequestFactory.setConnectTimeout(3000);
		return new RestTemplate(clientHttpRequestFactory);
	}

//	@Bean
//	public WebClient.Builder getWebClientBuilder(){
//		return WebClient.builder();
//	}
	
	@Bean(name= "taskExecutor")
	public Executor getExecutor() {
		final ThreadPoolTaskExecutor executor= new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(2);
		executor.setMaxPoolSize(2);
		executor.setQueueCapacity(10);
		executor.setThreadNamePrefix("Show Thread");
		executor.initialize();
		return executor;
	}
	
	@Bean(name= "remoteAPICallGroup")
	public HystrixCommand.Setter getHystrixCommandSetter(){
		HystrixCommand.Setter config= HystrixCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("RemoteAPICallGroup"));
		
		HystrixCommandProperties.Setter configProperties= HystrixCommandProperties.Setter();
		configProperties.withExecutionTimeoutInMilliseconds(3000);
		configProperties.withCircuitBreakerRequestVolumeThreshold(5);
		configProperties.withCircuitBreakerErrorThresholdPercentage(50);
		configProperties.withCircuitBreakerSleepWindowInMilliseconds(2000);
		
		config.andCommandPropertiesDefaults(configProperties);
		
		return config;
	}
	
}