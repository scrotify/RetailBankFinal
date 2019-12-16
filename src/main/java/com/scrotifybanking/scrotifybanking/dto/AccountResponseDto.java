package com.scrotifybanking.scrotifybanking.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AccountResponseDto {
	
	private Long accountNo;
	private String message;
	private Integer statusCode;

}
