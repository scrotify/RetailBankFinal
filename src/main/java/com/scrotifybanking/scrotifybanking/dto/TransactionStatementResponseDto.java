package com.scrotifybanking.scrotifybanking.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The type Transaction statement response dto.
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionStatementResponseDto {
	private Long transactionId;
	private LocalDate transactionDate;
	private double amount;
	private String transactionType;
	private String message;
	private Integer statusCode;

	/**
	 * Gets transaction id.
	 *
	 * @return the transaction id
	 */
	public Long getTransactionId() {
		return transactionId;
	}

	/**
	 * Sets transaction id.
	 *
	 * @param transactionId the transaction id
	 */
	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
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
	public double getAmount() {
		return amount;
	}

	/**
	 * Sets amount.
	 *
	 * @param amount the amount
	 */
	public void setAmount(double amount) {
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

	/**
	 * Gets message.
	 *
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Sets message.
	 *
	 * @param message the message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Gets status code.
	 *
	 * @return the status code
	 */
	public Integer getStatusCode() {
		return statusCode;
	}

	/**
	 * Sets status code.
	 *
	 * @param statusCode the status code
	 */
	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}
}
