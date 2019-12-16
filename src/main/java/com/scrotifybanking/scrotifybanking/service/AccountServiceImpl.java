package com.scrotifybanking.scrotifybanking.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scrotifybanking.scrotifybanking.dto.AccountDto;
import com.scrotifybanking.scrotifybanking.dto.AccountResponseDto;
import com.scrotifybanking.scrotifybanking.dto.MortgageTransferDto;
import com.scrotifybanking.scrotifybanking.dto.SearchSavingsAccountResponseDto;
import com.scrotifybanking.scrotifybanking.entity.Account;
import com.scrotifybanking.scrotifybanking.entity.Customer;
import com.scrotifybanking.scrotifybanking.exception.CustomException;
import com.scrotifybanking.scrotifybanking.exception.CustomerNotFoundException;
import com.scrotifybanking.scrotifybanking.exception.ErrorResponse;
import com.scrotifybanking.scrotifybanking.repository.AccountRepository;
import com.scrotifybanking.scrotifybanking.repository.CustomerRepository;
import com.scrotifybanking.scrotifybanking.util.ScrotifyConstant;

/**
 * The type Account service.
 */
@Service
public class AccountServiceImpl implements AccountService {

	private static Logger logger = LogManager.getLogger(AccountServiceImpl.class);

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private CustomerRepository customerRepository;

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
		logger.info("Entering into findAll by customer controller method");
		return accountRepository.findAllByAccountNotCustomer(Long.parseLong(custId), accountStatus, accountType);
	}

	/**
	 * This method is used for creating mortgage account
	 * 
	 * @param id
	 * @return
	 * 
	 */

	@Override
	public AccountResponseDto createMortgageAccount(Long id) throws CustomerNotFoundException {
		logger.info("Entering into create mortagage account");
		Optional<Customer> customer = customerRepository.findByCustomerId(id);
		Account account = new Account();
		AccountResponseDto accountResponseDto = new AccountResponseDto();
		if (customer.isPresent()) {
			account.setAccountStatus(ScrotifyConstant.ACCOUNT_ACTIVE_STATUS);
			account.setAccountType(ScrotifyConstant.MORTGAGE_ACCOUNT_MESSAGE);
			account.setAvailableBalance(ScrotifyConstant.BALANCE_AMOUNT);
			account.setCustomer(customer.get());
			accountRepository.save(account);
			accountResponseDto.setAccountNo(account.getAccountNo());
			accountResponseDto.setMessage(ScrotifyConstant.ACCOUNT_CREATED_MESSAGE);
			accountResponseDto.setStatusCode(ScrotifyConstant.CREATED_CODE);
		} else {
			throw new CustomerNotFoundException(ScrotifyConstant.CUSTOMER_ID_NOT_FOUND);
		}
		logger.info("Ending of create mortgage account method");
		return accountResponseDto;
	}

	/**
	 * search by account no
	 *
	 * @param accountNumber
	 * @return
	 */
	@Override
	public AccountDto findByAccountNumber(Long accountNumber) {

		Optional<Account> accountOptional = accountRepository.findById(accountNumber);
		AccountDto accountDto = new AccountDto();
		if (accountOptional.isPresent()) {
			Account account = accountOptional.get();
			BeanUtils.copyProperties(account, accountDto);
			accountDto.setCustomerId(account.getCustomer().getCustomerId());
		}
		return accountDto;
	}

	@Override
	public List<MortgageTransferDto> findAllByCustomerNumber(Long customerNumber) {
		Optional<List<Account>> accountOptional = accountRepository.findAllByCustomerCustomerId(customerNumber);
		List<MortgageTransferDto> mortgageTransferDtos = new ArrayList<>();
		if (accountOptional.isPresent()) {
			List<Account> account = accountOptional.get();
			mortgageTransferDtos = account.stream().map(acc -> {
				MortgageTransferDto mortgageTransferDto = new MortgageTransferDto();
				mortgageTransferDto.setAccountNumber(acc.getAccountNo());
				mortgageTransferDto.setAccountType(acc.getAccountType());
				return mortgageTransferDto;
			}).collect(Collectors.toList());
		} else {
			throw new CustomException("Account is not available");
		}
		return mortgageTransferDtos;
	}

	/**
	 * Description: this method is operate when the admin want to see list of
	 * savings accounts
	 * 
	 * @param accountNo
	 * @return 
	 * @return
	 * @throws Exception 
	 *
	 */
	@Override
	public List<SearchSavingsAccountResponseDto> searchSavingsAccounts(Long accountNo) throws Exception {
		List<SearchSavingsAccountResponseDto> accountsBySavings = new ArrayList<>();
			List<Account> accountList = accountRepository.getAccountsByPartialAccountNo("" + accountNo);
			if(accountList!=null) {
				if(accountList.get(0).getAccountNo()!=null) {
			accountList.forEach(accounts -> {
				Long customerId = accounts.getCustomer().getCustomerId();
				Optional<Customer> customerDetails = customerRepository.findByCustomerId(customerId);
				SearchSavingsAccountResponseDto	searchSavingsAccountResponseDto = new SearchSavingsAccountResponseDto();
				searchSavingsAccountResponseDto.setAccountNo(accounts.getAccountNo());
				searchSavingsAccountResponseDto.setAccountType(accounts.getAccountType());
				searchSavingsAccountResponseDto.setAvailableBalance(accounts.getAvailableBalance());
				searchSavingsAccountResponseDto.setCustomerId(customerDetails.get().getCustomerId());
				searchSavingsAccountResponseDto.setCustomerName(customerDetails.get().getCustomerName());
				searchSavingsAccountResponseDto.setCustomerSalary(customerDetails.get().getCustomerSalary());
				searchSavingsAccountResponseDto.setCustomerMobileNo(customerDetails.get().getCustomerMobileNo());
				searchSavingsAccountResponseDto.setCustomerAge(customerDetails.get().getCustomerAge());
				searchSavingsAccountResponseDto.setCustomerCity(customerDetails.get().getCustomerCity());
				accountsBySavings.add(searchSavingsAccountResponseDto);
			});
			}
			}
		return accountsBySavings;

	}
}
