package com.scrotifybanking.scrotifybanking.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ListOfPayeeResponseDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nickName;
	private String name;
	private Long accountNo;
	private String ifscCode;
	private String bankName;
	private Double limit;
	private Long id;

}