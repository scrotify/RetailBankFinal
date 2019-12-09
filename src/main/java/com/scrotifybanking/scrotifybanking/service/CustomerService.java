package com.scrotifybanking.scrotifybanking.service;

import com.scrotifybanking.scrotifybanking.dto.AccountSummaryResponseDto;
import com.scrotifybanking.scrotifybanking.dto.CustomerRequestDto;
import com.scrotifybanking.scrotifybanking.dto.CustomerResponseDto;
import com.scrotifybanking.scrotifybanking.dto.LoginDto;
import com.scrotifybanking.scrotifybanking.dto.LoginResponseDto;

/**
 * The interface Customer service.
 *
 * @author AnishaR
 */
public interface CustomerService {
	/**
	 * In this Service we have one method.This method is used to register the
	 * method.
	 *
	 * @param customerRequestDto the customer request dto
	 * @return customer response dto
	 */
	CustomerResponseDto registerCustomer(CustomerRequestDto customerRequestDto);

	/**
	 * Login customer login response dto.
	 *
	 * @param loginDto the login dto
	 * @return the login response dto
	 */
	public LoginResponseDto loginCustomer(LoginDto loginDto);

	/**
	 * Account summary account summary response dto.
	 *
	 * @param customerId the customer id
	 * @return the account summary response dto
	 */
	public AccountSummaryResponseDto accountSummary(Long customerId);

}
