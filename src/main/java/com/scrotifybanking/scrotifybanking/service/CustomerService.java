package com.scrotifybanking.scrotifybanking.service;

import com.scrotifybanking.scrotifybanking.dto.AccountSummaryResponseDto;
import com.scrotifybanking.scrotifybanking.dto.CustomerRequestDto;
import com.scrotifybanking.scrotifybanking.dto.CustomerResponseDto;
import com.scrotifybanking.scrotifybanking.dto.LoginRequestDto;
import com.scrotifybanking.scrotifybanking.dto.LoginResponseDto;
import com.scrotifybanking.scrotifybanking.exception.CustomerNotFoundException;

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
	 * @throws CustomerNotFoundException 
	 */
	public LoginResponseDto loginCustomer(LoginRequestDto loginDto) throws CustomerNotFoundException;

	/**
	 * Account summary account summary response dto.
	 *
	 * @param customerId the customer id
	 * @return the account summary response dto
	 */
	public AccountSummaryResponseDto mortgageAccountSummary(Long id);

	public AccountSummaryResponseDto savingsAccountSummary(Long id);

}
