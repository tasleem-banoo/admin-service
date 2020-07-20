package com.demo.adminService.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.demo.adminService.client.RatingsClient;
import com.demo.adminService.entity.AdminUser;
import com.demo.adminService.entity.Ratings;
import com.demo.adminService.entity.User;
import com.demo.adminService.entity.UserRatings;
import com.demo.adminService.service.UserInfoService;
import com.demo.adminService.service.UserRatingInfoService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
public class AdminController {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private WebClient.Builder webClientBuilder;
	
	@Autowired
	UserInfoService userInfo;
	
	@Autowired
	UserRatingInfoService userRatingInfo;
	
	//@Autowired
	//private DiscoveryClient discoveryClient; //get instances of services on eureka
	
	@Autowired
	private RatingsClient ratingClient;
	
	@GetMapping("/ratings")
	public UserRatings getUserRatings() {
		return ratingClient.getUserRatings(1);
	}

	@GetMapping("/admin/associatedUsers/{id}")
	//@HystrixCommand(fallbackMethod = "getFallBackUsers") //break this call when something goes wrong and wait for sometimes so that threads don't build up.
	public List<AdminUser> getAssociatedUsers(@PathVariable("id") int id){
		
		//return Collections.singletonList( new AdminUser(1,1,"Tasleem", "Banoo", (long) 5 ));
		
		UserRatings userRatings = userRatingInfo.getUserRatings(id);
		  
		return userRatings.getUserRatings().stream().map(userRating -> {
			/*
			 * User user = webClientBuilder.build().get()
			 * .uri("http://localhost:8810/user/"+userRating.getUserId()) .retrieve()
			 * .bodyToMono(User.class) //will give us object asynchronously. For now empty container , and object will get filled in 
			 * .block(); //blocking execution until we get user object
			 */
			
			return userInfo.getUsers(userRating);
		}).collect(Collectors.toList());
		 
	}

	/*
	 * @HystrixCommand(fallbackMethod = "getFallBackUsers") //hystrix doesn't work
	 * like this. it creates proxy class outside api class. this method is called
	 * inside api class so out of scope of hysterix. so we create another spring
	 * class for this and call this in that. 
	 * private AdminUser getUsers(Ratings
	 * userRating) { User user =
	 * restTemplate.getForObject("http://user-service/user/"+userRating.getUserId(),
	 * User.class); return new AdminUser(1,user.getUserId(), user.getFirstName(),
	 * user.getLastName(), userRating.getRating()); }
	 * 
	 * private AdminUser getFallBackUsers(Ratings userRating) { return new
	 * AdminUser(0,0,"NA", "NA", (long) 0 ); }
	 * 
	 * @HystrixCommand(fallbackMethod = "getFallBackUserRatings") private
	 * UserRatings getUserRatings(int id) { return
	 * restTemplate.getForObject("http://ratings-service/user/ratings/"+id,
	 * UserRatings.class); }
	 * 
	 * private UserRatings getFallBackUserRatings(int id) { UserRatings userRatings
	 * = new UserRatings(); userRatings.setUserRatings(Arrays.asList(new Ratings(0,
	 * 0))); return userRatings; }
	 */
	
	public List<AdminUser> getFallBackUsers(@PathVariable("id") int id){
		return Arrays.asList( new AdminUser(1,1,"Tasleem", "Banoo", (long) 5 ));
	}
}
