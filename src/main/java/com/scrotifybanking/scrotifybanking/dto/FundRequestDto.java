package com.scrotifybanking.scrotifybanking.dto;

/**
 * The type Fund request dto.
 */
public class FundRequestDto {

	private double amount;
	private Long custId;
	private Long accountNo;

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Long getCustId() {
		return custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	public Long getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(Long accountNo) {
		this.accountNo = accountNo;
	}
}
