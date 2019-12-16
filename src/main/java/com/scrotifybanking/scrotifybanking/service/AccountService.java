package com.scrotifybanking.scrotifybanking.service;

import java.util.List;

import com.scrotifybanking.scrotifybanking.dto.AccountDto;
import com.scrotifybanking.scrotifybanking.dto.AccountResponseDto;
import com.scrotifybanking.scrotifybanking.dto.MortgageTransferDto;
import com.scrotifybanking.scrotifybanking.dto.SearchSavingsAccountResponseDto;
import com.scrotifybanking.scrotifybanking.entity.Account;
import com.scrotifybanking.scrotifybanking.exception.CustomerNotFoundException;

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
	
	AccountResponseDto createMortgageAccount(Long id) throws CustomerNotFoundException;

	public AccountDto findByAccountNumber(Long accountNumber);

	public List<MortgageTransferDto> findAllByCustomerNumber(Long customerNumber);
	
	public List<SearchSavingsAccountResponseDto> searchSavingsAccounts(Long accountNo) throws Exception;
}
