package com.scrotifybanking.scrotifybanking.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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

@RunWith(SpringJUnit4ClassRunner.class)
public class CustomerServiceTest {

	@InjectMocks
	CustomerServiceImpl customerServiceImpl;

	@Mock
	CustomerRepository customerRepository;

	@Mock
	AccountRepository accountRepository;

	@Mock
	TransactionRepository transactionRepository;

	@Test
	public void testRegisterNegativeCase() {
		Customer customer1 = null;
		CustomerRequestDto customerRequestDto = new CustomerRequestDto();
		customerRequestDto.setCity("Bangalore");
		customerRequestDto.setEmailId(("anisharavi101@gmail.com"));
		customerRequestDto.setName("Anisha");
		customerRequestDto.setPassword("Anu");
		customerRequestDto.setMobileNo(9894187107L);
		customerRequestDto.setDob(LocalDate.parse("2019-10-10"));

		Customer customer = new Customer();
		customer.setCustomerCity(customerRequestDto.getCity());
		customer.setCustomerDob(customerRequestDto.getDob());
		customer.setCustomerEmail(customerRequestDto.getEmailId());
		customer.setCustomerId(100L);
		customer.setCustomerMobileNo(customerRequestDto.getMobileNo());
		customer.setCustomerName(customerRequestDto.getName());
		customer.setCustomerPassword(customerRequestDto.getPassword());

		Account account = new Account();
		account.setAccountNo(10000L);
		account.setAccountStatus(ScrotifyConstant.ACCOUNT_ACTIVE_STATUS);
		account.setAccountType(ScrotifyConstant.ACCOUNT_TYPE);
		account.setAvailableBalance(ScrotifyConstant.AMOUNT);
		account.setCustomer(customer);

		Mockito.when(customerRepository.findByCustomerMobileNo(customerRequestDto.getMobileNo())).thenReturn(customer1);
		CustomerResponseDto customerResponseDto = new CustomerResponseDto();
		customerResponseDto.setId(1000L);
		customerResponseDto.setMessage(ScrotifyConstant.FAILURE_MESSAGE);
		customerResponseDto.setStatusCode(ScrotifyConstant.FAILURE_CODE);
		CustomerResponseDto result = customerServiceImpl.registerCustomer(customerRequestDto);
		assertEquals("Registration is not appicable", result.getMessage());
	}

	@Test
	public void testRegisterFailureCase() {
		Customer customer1 = null;
		CustomerRequestDto customerRequestDto = new CustomerRequestDto();
		customerRequestDto.setCity("Bangalore");
		customerRequestDto.setEmailId(("anisharavi101@gmail.com"));
		customerRequestDto.setName("Anisha");
		customerRequestDto.setPassword("Anu");
		customerRequestDto.setMobileNo(9894187107L);
		customerRequestDto.setDob(LocalDate.parse("2019-10-10"));

		Customer customer = new Customer();
		customer.setCustomerCity(customerRequestDto.getCity());
		customer.setCustomerDob(customerRequestDto.getDob());
		customer.setCustomerEmail(customerRequestDto.getEmailId());
		customer.setCustomerId(100L);
		customer.setCustomerMobileNo(customerRequestDto.getMobileNo());
		customer.setCustomerName(customerRequestDto.getName());
		customer.setCustomerPassword(customerRequestDto.getPassword());

		Account account = new Account();
		account.setAccountNo(10000L);
		account.setAccountStatus(ScrotifyConstant.ACCOUNT_ACTIVE_STATUS);
		account.setAccountType(ScrotifyConstant.ACCOUNT_TYPE);
		account.setAvailableBalance(ScrotifyConstant.AMOUNT);
		account.setCustomer(customer);

		Mockito.when(customerRepository.findByCustomerMobileNo(customerRequestDto.getMobileNo())).thenReturn(customer1);
		CustomerResponseDto customerResponseDto = new CustomerResponseDto();
		customerResponseDto.setId(1000L);
		customerResponseDto.setMessage(ScrotifyConstant.FAILURE_MESSAGE);
		customerResponseDto.setStatusCode(ScrotifyConstant.FAILURE_CODE);
		CustomerResponseDto result = customerServiceImpl.registerCustomer(customerRequestDto);
		assertEquals("Registration is not appicable", result.getMessage());
	}

	@Test
	public void testRegistersFailureCase() {
		Customer customer1 = new Customer();
		CustomerRequestDto customerRequestDto = new CustomerRequestDto();
		customerRequestDto.setCity("Bangalore");
		customerRequestDto.setEmailId(("anisharavi101@gmail.com"));
		customerRequestDto.setName("Anisha");
		customerRequestDto.setPassword("Anu");
		customerRequestDto.setMobileNo(9894187107L);
		customerRequestDto.setDob(LocalDate.parse("2019-10-10"));

		Mockito.when(customerRepository.findByCustomerMobileNo(customerRequestDto.getMobileNo())).thenReturn(customer1);
		CustomerResponseDto customerResponseDto = new CustomerResponseDto();
		customerResponseDto.setId(1000L);
		customerResponseDto.setMessage(ScrotifyConstant.FAILURE_REGISTRATION_MESSAGE);
		customerResponseDto.setStatusCode(ScrotifyConstant.FAILURE_REGISTRATION_CODE);
		CustomerResponseDto result = customerServiceImpl.registerCustomer(customerRequestDto);
		assertEquals("Already Registered", result.getMessage());
	}

	@Test
	public void testRegisterPositiveCase() {

		Customer customer1 = null;

		CustomerRequestDto customerRequestDto = new CustomerRequestDto();
		customerRequestDto.setCity("Bangalore");
		customerRequestDto.setEmailId(("anisharavi101@gmail.com"));
		customerRequestDto.setName("Anisha");
		customerRequestDto.setPassword("Anu");
		customerRequestDto.setMobileNo(9894187107L);
		customerRequestDto.setDob(LocalDate.parse("2000-10-10"));

		Customer customer = new Customer();
		customer.setCustomerCity(customerRequestDto.getCity());
		customer.setCustomerDob(customerRequestDto.getDob());
		customer.setCustomerEmail(customerRequestDto.getEmailId());
		customer.setCustomerId(100L);
		customer.setCustomerMobileNo(customerRequestDto.getMobileNo());
		customer.setCustomerName(customerRequestDto.getName());
		customer.setCustomerPassword(customerRequestDto.getPassword());

		Account account = new Account();
		account.setAccountNo(10000L);
		account.setAccountStatus(ScrotifyConstant.ACCOUNT_ACTIVE_STATUS);
		account.setAccountType(ScrotifyConstant.ACCOUNT_TYPE);
		account.setAvailableBalance(ScrotifyConstant.AMOUNT);
		account.setCustomer(customer);

		Mockito.when(customerRepository.findByCustomerMobileNo(customerRequestDto.getMobileNo())).thenReturn(customer1);
		CustomerResponseDto customerResponseDto = new CustomerResponseDto();
		customerResponseDto.setId(1000L);
		customerResponseDto.setMessage(ScrotifyConstant.SUCCESS_MESSAGE);
		customerResponseDto.setStatusCode(ScrotifyConstant.SUCCESS_CODE);
		Mockito.when(customerRepository.save(customer)).thenReturn(customer);
		Mockito.when(accountRepository.save(account)).thenReturn(account);
		CustomerResponseDto result = customerServiceImpl.registerCustomer(customerRequestDto);
		assertEquals("SUCCESS", result.getMessage());

	}

	@Test
	public void testLoginCustomer() throws CustomerNotFoundException {

		LoginResponseDto loginResponseDto = new LoginResponseDto();
		loginResponseDto.setId(1000L);
		loginResponseDto.setName("visha");
		loginResponseDto.setStatusCode(ScrotifyConstant.SUCCESS_CODE);
		loginResponseDto.setMessage(ScrotifyConstant.SUCCESS_MESSAGE);

		LoginRequestDto loginDto = new LoginRequestDto();
		loginDto.setId(1001L);
		loginDto.setPassword("bai123");

		Customer customer = new Customer();
		customer.setCustomerId(loginDto.getId());
		customer.setCustomerPassword(loginDto.getPassword());

		Mockito.when(customerRepository.findByCustomerId(loginDto.getId())).thenReturn(Optional.of(customer));
		LoginResponseDto result = customerServiceImpl.loginCustomer(loginDto);

		assertEquals("SUCCESS", result.getMessage());
	}

	@Test
	public void testLoginCustomerForFailure() throws CustomerNotFoundException {

		LoginResponseDto loginResponseDto = new LoginResponseDto();
		loginResponseDto.setStatusCode(ScrotifyConstant.FAILURE_CODE);
		loginResponseDto.setMessage(ScrotifyConstant.FAILURE_MESSAGE);

		LoginRequestDto loginDto = new LoginRequestDto();
		loginDto.setId(1001L);
		loginDto.setPassword("bai3");

		Customer customer = new Customer();
		customer.setCustomerId(loginDto.getId());
		customer.setCustomerPassword("bai12");

		Mockito.when(customerRepository.findByCustomerId(loginDto.getId())).thenReturn(Optional.of(customer));
		LoginResponseDto result = customerServiceImpl.loginCustomer(loginDto);

		assertEquals("Invalid Username or Password", result.getMessage());
	}

	@Test
	public void testLoginCustomerForNotFound() throws CustomerNotFoundException {

		LoginResponseDto loginResponseDto = new LoginResponseDto();
		loginResponseDto.setStatusCode(ScrotifyConstant.NOT_FOUND_CODE);
		loginResponseDto.setMessage(ScrotifyConstant.INVALID_MESSAGE);

		LoginRequestDto loginDto = new LoginRequestDto();
		loginDto.setId(1008L);
		loginDto.setPassword("bai123");

		Customer customer = new Customer();
		customer.setCustomerId(1001L);
		customer.setCustomerPassword(loginDto.getPassword());

		Mockito.when(customerRepository.findByCustomerId(1001L)).thenReturn(Optional.of(customer));
		LoginResponseDto result = customerServiceImpl.loginCustomer(loginDto);

		assertEquals("Invalid Username or Password", result.getMessage());
	}

	@Test
	public void testLastTransaction() {

		AccountSummaryResponseDto accountSummaryResponseDto = new AccountSummaryResponseDto();
		accountSummaryResponseDto.setStatusCode(ScrotifyConstant.SUCCESS_CODE);
		accountSummaryResponseDto.setMessage(ScrotifyConstant.SUCCESS_MESSAGE);
		accountSummaryResponseDto.setAccountNumber(10001L);
		accountSummaryResponseDto.setBalance(3754.0);
		accountSummaryResponseDto.setName("Sam");

		Customer customer = new Customer();
		customer.setCustomerId(1000L);

		Account account = new Account();
		account.setCustomer(customer);

		TransactionDto transactionDto = new TransactionDto();
		transactionDto.setAmount(45334.0);
		transactionDto.setPayeeNo(10001L);
		transactionDto.setTransactionDate(LocalDate.parse("2000-12-19"));
		transactionDto.setTransactionType("Debit");

		Transaction transaction = new Transaction();
		transaction.setAmount(transactionDto.getAmount());
		transaction.setPayeeNo(transactionDto.getPayeeNo());
		transaction.setTransactionDate(transactionDto.getTransactionDate());
		transaction.setTransactionType(transactionDto.getTransactionType());

		List<Transaction> transactionList = new ArrayList<>();
		transactionList.add(transaction);

		Mockito.when(customerRepository.findByCustomerId(1000L)).thenReturn(Optional.of(customer));
		Mockito.when(accountRepository.findByCustomerAndAccountType(Optional.of(customer), "Mortgage")).thenReturn(Optional.of(account));
		Mockito.when(transactionRepository.findTop5ByAccountNoOrderByTransactionIdDesc(Optional.of(account)))
				.thenReturn(transactionList);
		AccountSummaryResponseDto result = customerServiceImpl.mortgageAccountSummary(1000L);

		assertEquals(ScrotifyConstant.SUCCESS_MESSAGE, result.getMessage());
	}
	
	@Test
	public void testSavingSummary() {

		AccountSummaryResponseDto accountSummaryResponseDto = new AccountSummaryResponseDto();
		accountSummaryResponseDto.setStatusCode(ScrotifyConstant.SUCCESS_CODE);
		accountSummaryResponseDto.setMessage(ScrotifyConstant.SUCCESS_MESSAGE);
		accountSummaryResponseDto.setAccountNumber(10001L);
		accountSummaryResponseDto.setBalance(3754.0);
		accountSummaryResponseDto.setName("Sam");

		Customer customer = new Customer();
		customer.setCustomerId(1000L);

		Account account = new Account();
		account.setCustomer(customer);

		TransactionDto transactionDto = new TransactionDto();
		transactionDto.setAmount(45334.0);
		transactionDto.setPayeeNo(10001L);
		transactionDto.setTransactionDate(LocalDate.parse("2000-12-19"));
		transactionDto.setTransactionType("Debit");

		Transaction transaction = new Transaction();
		transaction.setAmount(transactionDto.getAmount());
		transaction.setPayeeNo(transactionDto.getPayeeNo());
		transaction.setTransactionDate(transactionDto.getTransactionDate());
		transaction.setTransactionType(transactionDto.getTransactionType());

		List<Transaction> transactionList = new ArrayList<>();
		transactionList.add(transaction);

		Mockito.when(customerRepository.findByCustomerId(1000L)).thenReturn(Optional.of(customer));
		Mockito.when(accountRepository.findByCustomerAndAccountType(Optional.of(customer), "Savings")).thenReturn(Optional.of(account));
		Mockito.when(transactionRepository.findTop5ByAccountNoOrderByTransactionIdDesc(Optional.of(account)))
				.thenReturn(transactionList);
		AccountSummaryResponseDto result = customerServiceImpl.savingsAccountSummary(1000L);

		assertEquals(ScrotifyConstant.SUCCESS_MESSAGE, result.getMessage());
	}

	@Test(expected = CustomException.class)
	public void testLastTransactionForNegative() {

		AccountSummaryResponseDto accountSummaryResponseDto = new AccountSummaryResponseDto();
		accountSummaryResponseDto.setStatusCode(ScrotifyConstant.NOT_FOUND_CODE);
		accountSummaryResponseDto.setMessage(ScrotifyConstant.INVALID_MESSAGE);
		accountSummaryResponseDto.setAccountNumber(10001L);
		accountSummaryResponseDto.setBalance(3754.0);
		accountSummaryResponseDto.setName("Sam");

		Customer customer = new Customer();
		customer.setCustomerId(1000L);

		Account account = new Account();
		account.setCustomer(customer);

		TransactionDto transactionDto = new TransactionDto();
		transactionDto.setAmount(45334.0);
		transactionDto.setPayeeNo(10001L);
		transactionDto.setTransactionDate(LocalDate.parse("2000-12-19"));
		transactionDto.setTransactionType("Debit");

		Transaction transaction = new Transaction();
		transaction.setAmount(transactionDto.getAmount());
		transaction.setPayeeNo(transactionDto.getPayeeNo());
		transaction.setTransactionDate(transactionDto.getTransactionDate());
		transaction.setTransactionType(transactionDto.getTransactionType());

		List<Transaction> transactionList = new ArrayList<>();
		transactionList.add(transaction);

		Mockito.when(customerRepository.findByCustomerId(1008L)).thenReturn(Optional.of(customer));
		Mockito.when(accountRepository.findByCustomer(Optional.of(customer))).thenReturn(Optional.of(account));
		Mockito.when(transactionRepository.findTop5ByAccountNoOrderByTransactionIdDesc(Optional.of(account)))
				.thenReturn(transactionList);
		AccountSummaryResponseDto result = customerServiceImpl.mortgageAccountSummary(1000L);

		assertEquals("Account is not avaialble in transactions", result.getMessage());
	}

}
