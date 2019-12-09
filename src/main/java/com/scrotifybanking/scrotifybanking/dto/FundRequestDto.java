package com.scrotifybanking.scrotifybanking.dto;

import javax.validation.constraints.NotBlank;

/**
 * The type Fund request dto.
 */
public class FundRequestDto {

	@NotBlank(message = "Amount should not be blank")
	private String amount;

	/**
	 * Gets amount.
	 *
	 * @return the amount
	 */
	public String getAmount() {
		return amount;
	}

	/**
	 * Sets amount.
	 *
	 * @param amount the amount
	 */
	public void setAmount(String amount) {
		this.amount = amount;
	}
}
