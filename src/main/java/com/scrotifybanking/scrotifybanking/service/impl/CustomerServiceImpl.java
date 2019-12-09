package com.scrotifybanking.scrotifybanking.service.impl;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scrotifybanking.scrotifybanking.dto.AccountSummaryResponseDto;
import com.scrotifybanking.scrotifybanking.dto.CustomerRequestDto;
import com.scrotifybanking.scrotifybanking.dto.CustomerResponseDto;
import com.scrotifybanking.scrotifybanking.dto.LoginDto;
import com.scrotifybanking.scrotifybanking.dto.LoginResponseDto;
import com.scrotifybanking.scrotifybanking.dto.TransactionDto;
import com.scrotifybanking.scrotifybanking.entity.Account;
import com.scrotifybanking.scrotifybanking.entity.Customer;
import com.scrotifybanking.scrotifybanking.entity.Transaction;
import com.scrotifybanking.scrotifybanking.repository.AccountRepository;
import com.scrotifybanking.scrotifybanking.repository.CustomerRepository;
import com.scrotifybanking.scrotifybanking.repository.TransactionRepository;
import com.scrotifybanking.scrotifybanking.service.CustomerService;
import com.scrotifybanking.scrotifybanking.util.ScrotifyConstant;

/**
 * The type Customer service.
 */
@Service
public class CustomerServiceImpl implements CustomerService {

	/**
	 * The Customer repository.
	 */
	@Autowired
	CustomerRepository customerRepository;

	/**
	 * The Account repository.
	 */
	@Autowired
	AccountRepository accountRepository;

	/**
	 * The Transaction repository.
	 */
	@Autowired
	TransactionRepository transactionRepository;

	/**
	 * This is the method to register new customer.
	 * 
	 * @param customerRequestDto
	 * @return
	 */
	@Override
	public CustomerResponseDto registerCustomer(CustomerRequestDto customerRequestDto) {
		CustomerResponseDto customerResponseDto = new CustomerResponseDto();
		LocalDate birthDate = customerRequestDto.getDob();
		LocalDate currentDate = LocalDate.now();
		Integer calculateAge = Period.between(birthDate, currentDate).getYears();
		Customer customers = customerRepository.findByCustomerMobileNo(customerRequestDto.getPhone());
		if (customers != null) {
			customerResponseDto.setMessage(ScrotifyConstant.FAILURE_REGISTRATION_MESSAGE);
			customerResponseDto.setStatusCode(ScrotifyConstant.FAILURE_REGISTRATION_CODE);
		} else if (customerRequestDto.getAccountType().equalsIgnoreCase(ScrotifyConstant.ACCOUNT_TYPE)
				&& calculateAge >= ScrotifyConstant.AGE_LIMIT) {
			Customer customer = new Customer();
			customer.setAccountType(customerRequestDto.getAccountType());
			customer.setCustomerCity(customerRequestDto.getCity());
			customer.setCustomerDob(customerRequestDto.getDob());
			customer.setCustomerEmail(customerRequestDto.getEmailId());
			customer.setCustomerName(customerRequestDto.getName());
			customer.setCustomerPassword(customerRequestDto.getPassword());
			customer.setCustomerMobileNo(customerRequestDto.getPhone());
			customerRepository.save(customer);
			Account account = new Account();
			account.setAccountStatus(ScrotifyConstant.ACCOUNT_ACTIVE_STATUS);
			account.getAccountNo();
			account.setCustomer(customer);
			account.setAccountType(customer.getAccountType());
			account.setAvailableBalance(ScrotifyConstant.AMOUNT);
			accountRepository.save(account);
			customerResponseDto.setCustomerId(customer.getCustomerId());
			customerResponseDto.setMessage(ScrotifyConstant.SUCCESS_MESSAGE);
			customerResponseDto.setStatusCode(ScrotifyConstant.SUCCESS_CODE);
		} else {
			customerResponseDto.setMessage(ScrotifyConstant.FAILURE_MESSAGE);
			customerResponseDto.setStatusCode(ScrotifyConstant.FAILURE_CODE);
		}
		return customerResponseDto;
	}

	/**
	 * This method is for customer login.
	 * 
	 * @param loginDto
	 * @return
	 */
	@Override
	public LoginResponseDto loginCustomer(LoginDto loginDto) {
		LoginResponseDto loginResponseDto = new LoginResponseDto();
		Long customerId = loginDto.getCustId();
		String password = loginDto.getPassword();
		Optional<Customer> customer = customerRepository.findByCustomerId(customerId);
		if (customer.isPresent() && customer.get().getCustomerPassword().equals(password)) {
			loginResponseDto.setCustomerId(customer.get().getCustomerId());
			loginResponseDto.setName(customer.get().getCustomerName());
			loginResponseDto.setStatusCode(ScrotifyConstant.SUCCESS_CODE);
			loginResponseDto.setMessage(ScrotifyConstant.SUCCESS_MESSAGE);

		} else if (!customer.isPresent()) {
			loginResponseDto.setStatusCode(ScrotifyConstant.NOT_FOUND_CODE);
			loginResponseDto.setMessage(ScrotifyConstant.INVALID_MESSAGE);
		} else {
			loginResponseDto.setStatusCode(ScrotifyConstant.FAILURE_CODE);
			loginResponseDto.setMessage(ScrotifyConstant.INVALID_MESSAGE);
		}
		return loginResponseDto;
	}

	/**
	 * This method is used to see the transaction details of customer.
	 * 
	 * @param customerId
	 * @return
	 */
	@Override
	public AccountSummaryResponseDto accountSummary(Long customerId) {
		AccountSummaryResponseDto accountSummaryResponseDto = new AccountSummaryResponseDto();
		Optional<Customer> customer = customerRepository.findByCustomerId(customerId);
		Optional<Account> account = accountRepository.findByCustomer(customer);
		List<Transaction> transactions = transactionRepository.findTop5ByAccountNoOrderByTransactionIdDesc(account);
		List<TransactionDto> listTransaction = new ArrayList<>();
		if (account.isPresent() && customer.isPresent()) {
			accountSummaryResponseDto.setAccountNumber(account.get().getAccountNo());
			accountSummaryResponseDto.setBalance(account.get().getAvailableBalance());
			accountSummaryResponseDto.setName(customer.get().getCustomerName());
			accountSummaryResponseDto.setMessage(ScrotifyConstant.SUCCESS_MESSAGE);
			accountSummaryResponseDto.setStatusCode(ScrotifyConstant.SUCCESS_CODE);
			for (Transaction transaction : transactions) {
				TransactionDto transactionDto = new TransactionDto();
				transactionDto.setAmount(transaction.getAmount());
				transactionDto.setTransactionDate(transaction.getTransactionDate());
				transactionDto.setTransactionType(transaction.getTransactionType());
				transactionDto.setPayeeNo(transaction.getPayeeNo());
				listTransaction.add(transactionDto);
			}
			accountSummaryResponseDto.setTransactions(listTransaction);
		} else {
			accountSummaryResponseDto.setStatusCode(ScrotifyConstant.NOT_FOUND_CODE);
			accountSummaryResponseDto.setMessage(ScrotifyConstant.INVALID_MESSAGE);
		}
		return accountSummaryResponseDto;
	}
}
