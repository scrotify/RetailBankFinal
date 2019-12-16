package com.scrotifybanking.scrotifybanking.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

/**
 * The type Customer request dto.
 */
@Getter
@Setter
public class CustomerRequestDto {

	private String name;
	private String password;
	private String emailId;
	private LocalDate dob;
	private Long mobileNo;
	private String city;
	private String role;
	private Double salary;
}
