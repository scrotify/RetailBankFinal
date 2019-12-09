package com.scrotifybanking.scrotifybanking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The type Login dto.
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {

	private Long custId;
	private String password;

	/**
	 * Gets cust id.
	 *
	 * @return the cust id
	 */
	public Long getCustId() {
		return custId;
	}

	/**
	 * Sets cust id.
	 *
	 * @param custId the cust id
	 */
	public void setCustId(Long custId) {
		this.custId = custId;
	}

	/**
	 * Gets password.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets password.
	 *
	 * @param password the password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
}
