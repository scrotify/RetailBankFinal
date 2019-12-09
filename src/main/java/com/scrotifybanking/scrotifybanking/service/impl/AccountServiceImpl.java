package com.scrotifybanking.scrotifybanking.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.scrotifybanking.scrotifybanking.entity.Account;
import com.scrotifybanking.scrotifybanking.repository.AccountRepository;
import com.scrotifybanking.scrotifybanking.service.AccountService;

/**
 * The type Account service.
 */
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountRepository accountRepository;

	/**
	 * findAllByAccount except current cust
	 *
	 * @param custId        the cust id
	 * @param accountStatus the account status
	 * @param accountType   the account type
	 * @return
	 */
	@Override
	public List<Account> findAllByAccountNotCustomer(String custId, String accountStatus, String accountType) {
		return accountRepository.findAllByAccountNotCustomer(Long.parseLong(custId), accountStatus, accountType);
	}
}
