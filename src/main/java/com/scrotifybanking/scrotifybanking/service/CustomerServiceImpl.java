package com.scrotifybanking.scrotifybanking.service;

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
import com.scrotifybanking.scrotifybanking.dto.LoginRequestDto;
import com.scrotifybanking.scrotifybanking.dto.LoginResponseDto;
import com.scrotifybanking.scrotifybanking.dto.TransactionDto;
import com.scrotifybanking.scrotifybanking.entity.Account;
import com.scrotifybanking.scrotifybanking.entity.Customer;
import com.scrotifybanking.scrotifybanking.entity.Transaction;
import com.scrotifybanking.scrotifybanking.exception.CustomException;
import com.scrotifybanking.scrotifybanking.exception.CustomerNotFoundException;
import com.scrotifybanking.scrotifybanking.repository.AccountRepository;
import com.scrotifybanking.scrotifybanking.repository.CustomerRepository;
import com.scrotifybanking.scrotifybanking.repository.TransactionRepository;
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
	 * This method is for customer and admin login.
	 * 
	 * @param loginDto
	 * @return
	 */

	@Override
	public CustomerResponseDto registerCustomer(CustomerRequestDto customerRequestDto) {
		CustomerResponseDto customerResponseDto = new CustomerResponseDto();
		LocalDate birthDate = customerRequestDto.getDob();
		LocalDate currentDate = LocalDate.now();
		Integer calculateAge = Period.between(birthDate, currentDate).getYears();
		Customer customers = customerRepository.findByCustomerMobileNo(customerRequestDto.getMobileNo());
		if (customers != null) {
			customerResponseDto.setMessage(ScrotifyConstant.FAILURE_REGISTRATION_MESSAGE);
			customerResponseDto.setStatusCode(ScrotifyConstant.FAILURE_REGISTRATION_CODE);
		} else if (calculateAge >= ScrotifyConstant.AGE_LIMIT) {
			Customer customer = new Customer();
			customer.setCustomerCity(customerRequestDto.getCity());
			customer.setCustomerDob(customerRequestDto.getDob());
			customer.setCustomerEmail(customerRequestDto.getEmailId());
			customer.setCustomerName(customerRequestDto.getName());
			customer.setCustomerPassword(customerRequestDto.getPassword());
			customer.setCustomerMobileNo(customerRequestDto.getMobileNo());
			customer.setCustomerAge(calculateAge);
			customer.setCustomerRole(customerRequestDto.getRole());
			customer.setCustomerSalary(customerRequestDto.getSalary());
			customerRepository.save(customer);
			Account account = new Account();
			account.setAccountStatus(ScrotifyConstant.ACCOUNT_ACTIVE_STATUS);
			account.getAccountNo();
			account.setCustomer(customer);
			account.setAccountType(ScrotifyConstant.SAVINGS_ACCOUNT_MESSAGE);
			account.setAvailableBalance(ScrotifyConstant.AMOUNT);
			accountRepository.save(account);
			customerResponseDto.setId(customer.getCustomerId());
			customerResponseDto.setMessage(ScrotifyConstant.SUCCESS_MESSAGE);
			customerResponseDto.setStatusCode(ScrotifyConstant.SUCCESS_CODE);
		} else {
			customerResponseDto.setMessage(ScrotifyConstant.FAILURE_MESSAGE);
			customerResponseDto.setStatusCode(ScrotifyConstant.FAILURE_CODE);
		}
		return customerResponseDto;
	}

	/**
	 * This method is used to login a customer and admin
	 */
	@Override
	public LoginResponseDto loginCustomer(LoginRequestDto loginRequestDto) throws CustomerNotFoundException {
		LoginResponseDto loginResponseDto = new LoginResponseDto();
		Long customerId = loginRequestDto.getId();
		String password = loginRequestDto.getPassword();
		Optional<Customer> customer = customerRepository.findByCustomerId(customerId);
		if (customer.isPresent()) {
			if (customer.get().getCustomerId().equals(customerId)
					&& customer.get().getCustomerPassword().equals(password)) {
				loginResponseDto.setId(customer.get().getCustomerId());
				loginResponseDto.setName(customer.get().getCustomerName());
				loginResponseDto.setRole(customer.get().getCustomerRole());
				loginResponseDto.setStatusCode(ScrotifyConstant.SUCCESS_CODE);
				loginResponseDto.setMessage(ScrotifyConstant.SUCCESS_MESSAGE);

			} else {
				loginResponseDto.setStatusCode(ScrotifyConstant.FAILURE_CODE);
				loginResponseDto.setMessage(ScrotifyConstant.INVALID_MESSAGE);
			}
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
	public AccountSummaryResponseDto savingsAccountSummary(Long id) {
		AccountSummaryResponseDto accountSummaryResponseDto = new AccountSummaryResponseDto();
		Optional<Customer> customer = customerRepository.findByCustomerId(id);
		Optional<Account> account = accountRepository.findByCustomerAndAccountType(customer,
				ScrotifyConstant.SAVINGS_ACCOUNT_MESSAGE);
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

	@Override
	public AccountSummaryResponseDto mortgageAccountSummary(Long id) {
		AccountSummaryResponseDto accountSummaryResponseDto = new AccountSummaryResponseDto();
		Optional<Customer> customer = customerRepository.findByCustomerId(id);
		Optional<Account> account = accountRepository.findByCustomerAndAccountType(customer,
				ScrotifyConstant.MORTGAGE_ACCOUNT_MESSAGE);
		
		if (account.isPresent()) {
			List<Transaction> transactions = transactionRepository.findTop5ByAccountNoOrderByTransactionIdDesc(account);
		List<TransactionDto> listTransaction = new ArrayList<>();
		if (customer.isPresent()) {
			accountSummaryResponseDto.setAccountNumber(account.get().getAccountNo());
			accountSummaryResponseDto.setBalance(account.get().getAvailableBalance());
			accountSummaryResponseDto.setAccountStatus(account.get().getAccountStatus());
			accountSummaryResponseDto.setAccountType(account.get().getAccountType());
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
		} else {
			throw new CustomException("Account is not avaialble in transactions");
		}
		return accountSummaryResponseDto;
	}

}
