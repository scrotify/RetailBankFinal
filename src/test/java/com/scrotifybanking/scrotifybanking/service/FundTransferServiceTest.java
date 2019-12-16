package com.scrotifybanking.scrotifybanking.service;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.scrotifybanking.scrotifybanking.dto.FundRequestDto;
import com.scrotifybanking.scrotifybanking.exception.CustomException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.scrotifybanking.scrotifybanking.dto.ApiResponse;
import com.scrotifybanking.scrotifybanking.entity.Account;
import com.scrotifybanking.scrotifybanking.entity.Customer;
import com.scrotifybanking.scrotifybanking.entity.Transaction;
import com.scrotifybanking.scrotifybanking.repository.AccountRepository;
import com.scrotifybanking.scrotifybanking.repository.TransactionRepository;

@RunWith(MockitoJUnitRunner.class)
public class FundTransferServiceTest {

	@InjectMocks
	private TransactionServiceImpl transactionService;

	@Mock
	private TransactionRepository transactionRepository;

	@Mock
	private AccountRepository accountRepository;

	@Test(expected = CustomException.class)
	public void testFundTransfer() {
		Account account = new Account();
		Customer customer = new Customer();
		customer.setCustomerMobileNo(1111111L);
		customer.setCustomerCity("Hosur");
		customer.setCustomerDob(LocalDate.of(1983, 10, 10));
		customer.setCustomerName("xxy");
		customer.setCustomerEmail("xxy@gmail.com");
		customer.setCustomerPassword("xxy");
		customer.setCustomerId(111L);
		account.setAccountNo(1L);
		account.setAvailableBalance(10000.00);
		account.setAccountStatus("Active");
		account.setAccountType("Saving");
		account.setCustomer(customer);
		Transaction transaction = new Transaction();
		transaction.setAccountNo(account);
		transaction.setPayeeNo(222L);
		transaction.setTransactionType("CR");
		transaction.setTransactionDate(LocalDate.now());
		transaction.setAmount(2000.00);
		transaction.setTransactionId(12L);
		List<Transaction> transactions = new ArrayList<>();
		transactions.add(transaction);
		account.setTransactionList(transactions);
		Optional<Account> accountOptional = Optional.of(account);
		Mockito.when(accountRepository.findById(anyLong())).thenReturn(accountOptional);
		Mockito.when(accountRepository.findByCustomerByAccount(anyLong(), anyString(), anyString()))
				.thenReturn(account);
		FundRequestDto fundRequestDto = new FundRequestDto();
		fundRequestDto.setCustId(2000L);
		fundRequestDto.setAccountNo(3000L);
		fundRequestDto.setAmount(5000);
		ApiResponse apiResponse = transactionService.transferFund(fundRequestDto, "Active", "Saving");
		Assert.assertNotNull(apiResponse);
		Assert.assertNotNull(apiResponse.getStatusCode());
	}

	@Test
	public void testFundTransfer2() {
		Account account = new Account();
		Customer customer = new Customer();
		customer.setCustomerMobileNo(1111111L);
		customer.setCustomerCity("Hosur");
		customer.setCustomerDob(LocalDate.of(1983, 10, 10));
		customer.setCustomerName("xxy");
		customer.setCustomerEmail("xxy@gmail.com");
		customer.setCustomerPassword("xxy");
		customer.setCustomerId(111L);
		account.setAccountNo(1L);
		account.setAvailableBalance(10000.00);
		account.setAccountStatus("Active");
		account.setAccountType("Saving");
		account.setCustomer(customer);
		Transaction transaction = new Transaction();
		transaction.setAccountNo(account);
		transaction.setPayeeNo(222L);
		transaction.setTransactionType("CR");
		transaction.setTransactionDate(LocalDate.now());
		transaction.setAmount(2000.00);
		transaction.setTransactionId(12L);
		List<Transaction> transactions = new ArrayList<>();
		transactions.add(transaction);
		account.setTransactionList(transactions);
		Optional<Account> accountOptional = Optional.of(account);
		Mockito.when(accountRepository.findById(anyLong())).thenReturn(accountOptional);
		Mockito.when(accountRepository.findByCustomerByAccount(anyLong(), anyString(), anyString()))
				.thenReturn(account);
		FundRequestDto fundRequestDto = new FundRequestDto();
		fundRequestDto.setCustId(2000L);
		fundRequestDto.setAccountNo(3000L);
		fundRequestDto.setAmount(100);
		ApiResponse apiResponse = transactionService.transferFund(fundRequestDto, "Active", "Saving");
		Assert.assertNotNull(apiResponse);
		Assert.assertNotNull(apiResponse.getStatusCode());
	}
}
