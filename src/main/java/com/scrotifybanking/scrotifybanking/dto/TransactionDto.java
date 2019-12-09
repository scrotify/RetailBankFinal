package com.scrotifybanking.scrotifybanking.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * The type Transaction dto.
 */
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto {

	private Long payeeNo;
	private LocalDate transactionDate;
	private Double amount;
	private String transactionType;

	

	/**
	 * Gets payee no.
	 *
	 * @return the payee no
	 */
	public Long getPayeeNo() {
		return payeeNo;
	}

	/**
	 * Sets payee no.
	 *
	 * @param payeeNo the payee no
	 */
	public void setPayeeNo(Long payeeNo) {
		this.payeeNo = payeeNo;
	}

	/**
	 * Gets transaction date.
	 *
	 * @return the transaction date
	 */
	public LocalDate getTransactionDate() {
		return transactionDate;
	}

	/**
	 * Sets transaction date.
	 *
	 * @param transactionDate the transaction date
	 */
	public void setTransactionDate(LocalDate transactionDate) {
		this.transactionDate = transactionDate;
	}

	/**
	 * Gets amount.
	 *
	 * @return the amount
	 */
	public Double getAmount() {
		return amount;
	}

	/**
	 * Sets amount.
	 *
	 * @param amount the amount
	 */
	public void setAmount(Double amount) {
		this.amount = amount;
	}

	/**
	 * Gets transaction type.
	 *
	 * @return the transaction type
	 */
	public String getTransactionType() {
		return transactionType;
	}

	/**
	 * Sets transaction type.
	 *
	 * @param transactionType the transaction type
	 */
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
}
