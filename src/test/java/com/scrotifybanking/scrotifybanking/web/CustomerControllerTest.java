package com.scrotifybanking.scrotifybanking.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;

import java.util.ArrayList;
import java.util.List;

import com.scrotifybanking.scrotifybanking.exception.CustomException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import com.scrotifybanking.scrotifybanking.dto.FundRequestDto;
import com.scrotifybanking.scrotifybanking.dto.TransactionStatementDto;
import com.scrotifybanking.scrotifybanking.dto.TransactionStatementResponseDto;
import com.scrotifybanking.scrotifybanking.dto.response.AccountNumbersDto;
import com.scrotifybanking.scrotifybanking.dto.response.ApiResponse;
import com.scrotifybanking.scrotifybanking.entity.Account;
import com.scrotifybanking.scrotifybanking.repository.AccountRepository;
import com.scrotifybanking.scrotifybanking.service.TransactionService;
import com.scrotifybanking.scrotifybanking.util.ScrotifyConstant;

/**
 * The type Customer controller test.
 */
@RunWith(MockitoJUnitRunner.class)
public class CustomerControllerTest {

	@InjectMocks
	private CustomerController customerController;

	@Mock
	private AccountRepository accountRepository;

	@Mock
	private TransactionService transactionService;

	/**
	 * The Transaction statement dto.
	 */
	TransactionStatementDto transactionStatementDto = null;
	/**
	 * The Transaction statement response dto.
	 */
	TransactionStatementResponseDto transactionStatementResponseDto = null;
	/**
	 * The Transaction statement response dtos.
	 */
	List<TransactionStatementResponseDto> transactionStatementResponseDtos = new ArrayList<>();

	/**
	 * Sets up.
	 */
	@Before
	public void setUp() {

		transactionStatementResponseDto = new TransactionStatementResponseDto();
		transactionStatementResponseDto.setMessage("transactions");

		transactionStatementResponseDto.setStatusCode(201);

		transactionStatementDto = new TransactionStatementDto();

		transactionStatementDto.setCustomerId(1451L);

		transactionStatementDto.setMonth(11);

		transactionStatementDto.setYear(2019);
		transactionStatementResponseDtos.add(transactionStatementResponseDto);

	}

	/**
	 * Monthly transactions test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void monthlyTransactionsTest() throws Exception {

		Mockito.when(transactionService.getTransactionStatement(transactionStatementDto,
				ScrotifyConstant.ACCOUNT_ACTIVE_STATUS, ScrotifyConstant.ACCOUNT_TYPE))
				.thenReturn(transactionStatementResponseDtos);
		List<TransactionStatementResponseDto> transactions = customerController
				.getTransactionStatement(transactionStatementDto);

		Assert.assertNotNull(transactions);

	}

	/**
	 * Test get all accounts.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testGetAllAccounts() throws Exception {
		String custId = "123456";
		List<Account> accounts = new ArrayList<>();
		Account account = new Account();
		account.setAccountNo(10L);
		accounts.add(account);

		Account account2 = new Account();
		account2.setAccountNo(20L);
		accounts.add(account2);

		Mockito.when(accountRepository.findAllByAccountNotCustomer(any(), any(), any())).thenReturn(accounts);

		ResponseEntity<AccountNumbersDto> accountNosDtos = customerController.getAllAccountNos(custId);
		Assert.assertNotNull(accountNosDtos);
		Assert.assertEquals(200, accountNosDtos.getStatusCode().value());
		Assert.assertEquals(10L, accountNosDtos.getBody().getAccountNos().get(0).longValue());
	}

	/**
	 * Test get in valid cust.
	 *
	 * @throws Exception the exception
	 */
	@Test(expected = CustomException.class)
	public void testGetInValidCust() throws Exception {
		String custId = "12345689";
		List<Account> accounts = new ArrayList<>();
		Mockito.when(accountRepository.findAllByAccountNotCustomer(any(), any(), any()))
				.thenThrow(Exception.class);
		customerController.getAllAccountNos(custId);
	}

	/**
	 * Test fund transfer.
	 *
	 * @throws Exception the custom exception
	 */
	@Test(expected = CustomException.class)
	public void testFundTransferCheckMinimumFalse() throws Exception {
		String custId = "123456";
		String toAccountNo = "2";
		FundRequestDto fundRequestDto = new FundRequestDto();
		fundRequestDto.setAmount(1000);
		// Mockito.when(transactionService.checkMinimumBalance(any(), any(), any(),
		// any())).thenReturn(new Boolean(true));
		// Mockito.when(transactionService.checkManintenanceBalance(any(), any(), any(),
		// any(), any())).thenReturn(true);
		//ResponseEntity<ApiResponse> response = customerController.fundTransfer(custId, toAccountNo, fundRequestDto);
		//Assert.assertNotNull(response);
	}

	/**
	 * Test fund transfer check maintain balance.
	 *
	 * @throws Exception the exception
	 */
	@Test(expected = CustomException.class)
	public void testFundTransferCheckMaintainBalance() throws Exception {
		String custId = "123456";
		String toAccountNo = "2";
		FundRequestDto fundRequestDto = new FundRequestDto();
		fundRequestDto.setAmount(1000);
		Mockito.when(
				transactionService.checkMinimumBalance(Long.parseLong(custId), ScrotifyConstant.ACCOUNT_ACTIVE_STATUS,
						ScrotifyConstant.ACCOUNT_TYPE, fundRequestDto.getAmount()))
				.thenReturn(true);
		// Mockito.when(transactionService.checkManintenanceBalance(any(), any(), any(),
		// any(), any())).thenReturn(true);
		//ResponseEntity<ApiResponse> response = customerController.fundTransfer(custId, toAccountNo, fundRequestDto);
		//Assert.assertNotNull(response);
	}

	/**
	 * Test fund transfer check.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testFundTransferCheck() throws Exception {
		String custId = "123456";
		String toAccountNo = "2";
		FundRequestDto fundRequestDto = new FundRequestDto();
		fundRequestDto.setAmount(1000);
		Mockito.when(
				transactionService.checkMinimumBalance(Long.parseLong(custId), ScrotifyConstant.ACCOUNT_ACTIVE_STATUS,
						ScrotifyConstant.ACCOUNT_TYPE, fundRequestDto.getAmount()))
				.thenReturn(true);
		Mockito.when(transactionService.checkManintenanceBalance(Long.parseLong(custId),
				ScrotifyConstant.ACCOUNT_ACTIVE_STATUS, ScrotifyConstant.ACCOUNT_TYPE,
				fundRequestDto.getAmount(), ScrotifyConstant.MINIMUM_BALANCE_MAINTAIN))
				.thenReturn(true);
		ApiResponse response = new ApiResponse();
		response.setMessage("success");
		response.setStatusCode(200);
		Mockito.when(transactionService.transferFund(anyLong(), anyString(), anyDouble(), any(), any()))
				.thenReturn(response);
		//ResponseEntity<ApiResponse> responseRes = customerController.fundTransfer(custId, toAccountNo, fundRequestDto);
		//Assert.assertNotNull(response);
		//Assert.assertNotNull(response.getMessage());
		//Assert.assertNotNull(response.getStatusCode());

	}

}
