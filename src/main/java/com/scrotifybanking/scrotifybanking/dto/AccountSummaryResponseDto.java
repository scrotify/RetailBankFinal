package com.scrotifybanking.scrotifybanking.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The type Account summary response dto.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountSummaryResponseDto {

	private String message;
	private Integer statusCode;
	private Long accountNumber;
	private Double balance;
	private String name;
	private String accountType;
	private String accountStatus;
	private List<TransactionDto> transactions;

}
