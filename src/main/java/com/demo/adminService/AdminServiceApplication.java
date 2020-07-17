package com.demo.adminService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@EnableDiscoveryClient
@SpringBootApplication
@EnableCircuitBreaker
@EnableHystrixDashboard
public class AdminServiceApplication {

	//inbuilt template to call micro services, but this is going to depricated
	@Bean
	@LoadBalanced //does service discovery. It will look for hint that which service to call based on url
	public RestTemplate getRestTemplate() {
		//HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
		//clientHttpRequestFactory.setConnectTimeout(3000); //if microservice is slow set timeout is one option to solve this issue partly as incoming request are faster then outgoing
		//return new RestTemplate(clientHttpRequestFactory);
		
		return new RestTemplate();
	}
	
	//use instead of rest template
	@Bean
	public WebClient.Builder getWebClientBuilder(){
		return WebClient.builder();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(AdminServiceApplication.class, args);
	}

}
