package com.demo.adminService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.demo.adminService.entity.AdminUser;
import com.demo.adminService.entity.Ratings;
import com.demo.adminService.entity.UserDetails;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@Service
public class UserInfoService {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@HystrixCommand(fallbackMethod = "getFallBackUsers"
			//,commandProperties = {
			//@HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value="2000"), //timeout after this seconds
			//@HystrixProperty(name="circuitBreaker.requestVolumeThreshold",value="5"), //look at last 5 calls
			//@HystrixProperty(name="circuitBreaker.errorThresholdPercentage",value="50"), //fallback after how many percentage. so 50% out of last 5 should fail
			//@HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds",value="5000") //will sleep for 5 sec and then try after 5 seconds.
			//}
			) 
	public AdminUser getUsers(Ratings userRating) {
		UserDetails user = restTemplate.getForObject("http://user-service/user/"+userRating.getUserId(), UserDetails.class);
		return new AdminUser(1,user.getId(), user.getFirstName(), user.getLastName(), userRating.getRating());
	}
	
	public AdminUser getFallBackUsers(Ratings userRating) {
		return new AdminUser(0,0,"NA", "NA", userRating.getRating() );
	}
}
