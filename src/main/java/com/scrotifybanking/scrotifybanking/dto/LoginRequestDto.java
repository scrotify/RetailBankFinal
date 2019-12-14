package com.scrotifybanking.scrotifybanking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The type LoginRequestDto.
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDto {

	private Long id;
	private String password;

}
