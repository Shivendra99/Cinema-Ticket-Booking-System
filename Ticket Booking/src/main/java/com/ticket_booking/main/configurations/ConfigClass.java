package com.ticket_booking.main.configurations;

import java.util.concurrent.Executor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;

@Configuration
@EnableAsync
public class ConfigClass {
	
	@Value("${spring.datasource.url}")
	private String url;
	
	@Value("${spring.datasource.username}")
	private String userName;
	
	@Value("${spring.datasource.password}")
	private String password;

	@Bean
	@LoadBalanced
	public RestTemplate getRestTemplate() {
		HttpComponentsClientHttpRequestFactory clientRequestFactory= new HttpComponentsClientHttpRequestFactory();
		clientRequestFactory.setConnectTimeout(5000);
		return new RestTemplate(clientRequestFactory);
	}
	
	@Bean(name= "taskExecutor")
	public Executor getExecutor() {
		ThreadPoolTaskExecutor executor= new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(2);
		executor.setMaxPoolSize(3);
		executor.setQueueCapacity(10);
		executor.setThreadNamePrefix("Ticket Booking thread");
		executor.initialize();
		return executor;
	}
	
	@Bean(name= "hystrixCommandSetter")
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
	
//	/**
//	 * Declaring data source without need of application.properties
//	 * @return
//	 */
//	@Bean(name= "cinemaBookingSystemDataSource")
//	public DataSource dataSource() {
//		DriverManagerDataSource dataSource= new DriverManagerDataSource();
//		
//		dataSource.setUrl(url);
//		dataSource.setUsername(userName);
//		dataSource.setPassword(password);
//		
//		return dataSource;
//	}
	
//	@Primary
//	@Bean(name= "cinemaBookingSystemDataSource")
//	@ConfigurationProperties(value= "spring.cinema.datasource")
//	public DataSource dataSource() {
//		
//		return DataSourceBuilder.create().build();
//	}
	
}