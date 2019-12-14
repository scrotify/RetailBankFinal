package com.scrotifybanking.scrotifybanking.web;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.scrotifybanking.scrotifybanking.dto.AccountSummaryResponseDto;
import com.scrotifybanking.scrotifybanking.dto.CustomerRequestDto;
import com.scrotifybanking.scrotifybanking.dto.CustomerResponseDto;
import com.scrotifybanking.scrotifybanking.dto.LoginRequestDto;
import com.scrotifybanking.scrotifybanking.dto.LoginResponseDto;
import com.scrotifybanking.scrotifybanking.dto.TransactionDto;
import com.scrotifybanking.scrotifybanking.entity.Account;
import com.scrotifybanking.scrotifybanking.entity.Customer;
import com.scrotifybanking.scrotifybanking.entity.Transaction;
import com.scrotifybanking.scrotifybanking.exception.CustomerNotFoundException;
import com.scrotifybanking.scrotifybanking.service.CustomerService;
import com.scrotifybanking.scrotifybanking.util.ScrotifyConstant;

@RunWith(MockitoJUnitRunner.Silent.class)
public class CustomerControllerLoginTest {

	@InjectMocks
	CustomerController customerController;

	@Mock
	CustomerService customerService;

	@Test
	public void testRegisterCustomerPositive() {

		CustomerRequestDto customerRequestDto = new CustomerRequestDto();
		customerRequestDto.setCity("Bangalore");
		customerRequestDto.setEmailId(("anisharavi101@gmail.com"));
		customerRequestDto.setName("Anisha");
		customerRequestDto.setPassword("Anu");
		customerRequestDto.setMobileNo(9894187107L);
		customerRequestDto.setDob(LocalDate.parse("1000-10-10"));

		CustomerResponseDto customerResponseDto = new CustomerResponseDto();
		customerResponseDto.setId(1000L);
		customerResponseDto.setMessage(ScrotifyConstant.SUCCESS_MESSAGE);
		customerResponseDto.setStatusCode(ScrotifyConstant.SUCCESS_CODE);

		Mockito.when(customerService.registerCustomer(customerRequestDto)).thenReturn(customerResponseDto);
		CustomerResponseDto result = customerController.registerCustomer(customerRequestDto);
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

		Mockito.when(customerService.loginCustomer(loginDto)).thenReturn(loginResponseDto);
		LoginResponseDto result = customerController.loginCustomer(loginDto).getBody();

		assertEquals("SUCCESS", result.getMessage());
	}

	@Test
	public void testLastTransactions() {

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

		Mockito.when(customerService.mortgageAccountSummary(1000L)).thenReturn(accountSummaryResponseDto);
		AccountSummaryResponseDto result = customerController.mortgageSummary(1000L).getBody();

		assertEquals(ScrotifyConstant.SUCCESS_MESSAGE, result.getMessage());
	}

}
