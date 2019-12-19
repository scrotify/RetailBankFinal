package com.scrotifybanking.scrotifybanking.dto;

/**
 * The type Fund request dto.
 */
public class FundRequestDto {

	
	private Long custId;
	private Long accountNo;
	private Double amount;

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
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
