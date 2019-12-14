package com.scrotifybanking.scrotifybanking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The type Login response dto.
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDto {

	private Long id;
	private String name;
	private String role;
	private String message;
	private Integer statusCode;

	
}
