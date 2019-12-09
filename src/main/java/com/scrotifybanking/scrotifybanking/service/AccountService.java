package com.scrotifybanking.scrotifybanking.service;

import java.util.List;

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

}
