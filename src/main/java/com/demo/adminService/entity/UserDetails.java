package com.demo.adminService.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

//@Data is equivalent to @Getter + @Setter + @RequiredArgsConstructor + @ToString + @EqualsAndHashCode
@Data
//@Entity
public class UserDetails {
	
	//@Id
	//@GeneratedValue (strategy = GenerationType.IDENTITY)
	private int id;
	private String firstName;
	private String lastName;

}
