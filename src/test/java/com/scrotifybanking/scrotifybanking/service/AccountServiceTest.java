package com.scrotifybanking.scrotifybanking.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.scrotifybanking.scrotifybanking.dto.AccountResponseDto;
import com.scrotifybanking.scrotifybanking.entity.Account;
import com.scrotifybanking.scrotifybanking.entity.Customer;
import com.scrotifybanking.scrotifybanking.exception.CustomerNotFoundException;
import com.scrotifybanking.scrotifybanking.repository.AccountRepository;
import com.scrotifybanking.scrotifybanking.repository.CustomerRepository;
import com.scrotifybanking.scrotifybanking.util.ScrotifyConstant;

@RunWith(MockitoJUnitRunner.Silent.class)
public class AccountServiceTest {
	
	@InjectMocks
	AccountServiceImpl accountServiceImpl;
	
	@Mock
	CustomerRepository customerRepository;
	
	@Mock
	AccountRepository accountRepository;
	
	@Test
	public void testCreateMortgageAccount() throws CustomerNotFoundException {
		Customer customer = new Customer();
		customer.setCustomerId(1000L);
		
		Account account = new Account();
		account.setAccountNo(1000L);
		account.setAccountStatus(ScrotifyConstant.ACCOUNT_ACTIVE_STATUS);
		account.setAccountType(ScrotifyConstant.MORTGAGE_ACCOUNT_MESSAGE);
		account.setAvailableBalance(10000.0);
		account.setCustomer(customer);
		
		AccountResponseDto accountResponseDto = new AccountResponseDto();
		accountResponseDto.setMessage(ScrotifyConstant.ACCOUNT_CREATED_MESSAGE);
		accountResponseDto.setStatusCode(ScrotifyConstant.CREATED_CODE);
		
		Mockito.when(customerRepository.findByCustomerId(1003L)).thenReturn(Optional.of(customer));
		Mockito.when(accountRepository.save(account)).thenReturn(null);
		AccountResponseDto result = accountServiceImpl.createMortgageAccount(1003L);
		
		assertEquals(ScrotifyConstant.ACCOUNT_CREATED_MESSAGE, result.getMessage());
	}
	
	@Test(expected = CustomerNotFoundException.class)
	public void testCreateMortgageAccountForNegative() throws CustomerNotFoundException {
		Account account = new Account();
		account.setAccountNo(1000L);
		account.setAccountStatus(ScrotifyConstant.ACCOUNT_ACTIVE_STATUS);
		account.setAccountType(ScrotifyConstant.MORTGAGE_ACCOUNT_MESSAGE);
		account.setAvailableBalance(10000.0);
		
		Customer customer = new Customer();
		
		AccountResponseDto accountResponseDto = new AccountResponseDto();
		accountResponseDto.setMessage(ScrotifyConstant.ACCOUNT_CREATED_MESSAGE);
		accountResponseDto.setStatusCode(ScrotifyConstant.CREATED_CODE);
		
		Mockito.when(customerRepository.findByCustomerId(1003L)).thenReturn(Optional.of(customer));
		Mockito.when(accountRepository.save(account)).thenReturn(null);
		AccountResponseDto result = accountServiceImpl.createMortgageAccount(1001L);
		
		assertEquals(ScrotifyConstant.CUSTOMER_ID_NOT_FOUND, result.getMessage());
	}


}
