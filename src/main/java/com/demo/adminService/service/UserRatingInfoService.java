package com.demo.adminService.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.demo.adminService.entity.Ratings;
import com.demo.adminService.entity.UserRatings;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@Service
public class UserRatingInfoService {

	@Autowired
	private RestTemplate restTemplate;
	
	//@HystrixCommand(fallbackMethod = "getFallBackUserRatings")
	
	//Another way to handle fallback is bulkhead which is assigning separated threadpools to different methods
	@HystrixCommand(fallbackMethod = "getFallBackUserRatings",
					threadPoolKey= "ratingInfoPool", //creating separate bulkhead(threadpool)
					threadPoolProperties = {
							@HystrixProperty(name="coreSize",value="20"), //only 20 threads waiting at a time
							@HystrixProperty(name="maxQueueSize",value="10") // how many request can be queued other then thread waiting. They are not consuming threads
					}
			)
	public UserRatings getUserRatings(int id) {
		return restTemplate.getForObject("http://ratings-service/user/ratings/"+id, UserRatings.class);
	}
	
	public UserRatings getFallBackUserRatings(int id) {
		UserRatings userRatings = new UserRatings();
		userRatings.setUserRatings(Arrays.asList(new Ratings(0, 0)));
		return userRatings;
	}
}
