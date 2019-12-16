package com.scrotifybanking.scrotifybanking.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SearchSavingsAccountResponseDto {
	
	private Long accountNo;
	private String accountType;
	private Long customerId;
	private String customerName;
	private Double customerSalary;
	private Integer customerAge;
	private Long customerMobileNo;
	private String customerCity;
	private Double availableBalance;
}
