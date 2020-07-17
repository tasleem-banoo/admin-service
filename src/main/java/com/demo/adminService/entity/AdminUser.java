package com.demo.adminService.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AdminUser {
	
	private int adminId;
	private int userId;
	private String userFirstName;
	private String userLastName;
	private double userRating;

}
