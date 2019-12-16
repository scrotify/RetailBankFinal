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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
