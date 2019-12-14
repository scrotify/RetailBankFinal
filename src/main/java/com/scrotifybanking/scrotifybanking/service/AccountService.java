package com.scrotifybanking.scrotifybanking.service;

import java.util.List;

import com.scrotifybanking.scrotifybanking.dto.response.AccountDto;
import com.scrotifybanking.scrotifybanking.dto.response.MortgageTransferDto;
import com.scrotifybanking.scrotifybanking.entity.Account;

/**
 * The interface Account service.
 */
public interface AccountService {

	/**
	 * Find all by account not customer list.
	 *
	 * @param custId        the cust id
	 * @param accountStatus the account status
	 * @param accountType   the account type
	 * @return the list
	 */
	List<Account> findAllByAccountNotCustomer(String custId, String accountStatus, String accountType);

	public AccountDto findByAccountNumber(Long accountNumber);

	public List<MortgageTransferDto> findAllByCustomerNumber(Long customerNumber);

}
