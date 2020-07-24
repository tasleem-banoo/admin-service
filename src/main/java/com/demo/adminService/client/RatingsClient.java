package com.demo.adminService.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.demo.adminService.entity.UserRatings;

@FeignClient(url="http://localhost:8830",name = "Ratings-Client", fallback = FeignClientFallbackClass.class )
public interface RatingsClient {
	
	@GetMapping("/user/ratings/{userId}")
	public UserRatings getUserRatings(@PathVariable int userId);

}

class FeignClientFallbackClass implements RatingsClient{

	@Override
	public UserRatings getUserRatings(int userId) {
		return new UserRatings();
	}
	
}
