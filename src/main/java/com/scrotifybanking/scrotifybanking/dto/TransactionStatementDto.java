package com.scrotifybanking.scrotifybanking.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * The type Transaction statement dto.
 */
@NoArgsConstructor
@AllArgsConstructor
public class TransactionStatementDto implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long customerId;
	private Integer month;
	private Integer year;

	/**
	 * Gets customer id.
	 *
	 * @return the customer id
	 */
	public Long getCustomerId() {
		return customerId;
	}

	/**
	 * Sets customer id.
	 *
	 * @param customerId the customer id
	 */
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	/**
	 * Gets month.
	 *
	 * @return the month
	 */
	public Integer getMonth() {
		return month;
	}

	/**
	 * Sets month.
	 *
	 * @param month the month
	 */
	public void setMonth(Integer month) {
		this.month = month;
	}

	/**
	 * @return the year
	 */
	public Integer getYear() {
		return year;
	}

	/**
	 * @param year the year to set
	 */
	public void setYear(Integer year) {
		this.year = year;
	}
}
