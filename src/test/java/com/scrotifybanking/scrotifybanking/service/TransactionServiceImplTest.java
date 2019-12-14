package com.scrotifybanking.scrotifybanking.service;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.scrotifybanking.scrotifybanking.dto.TransactionStatementDto;
import com.scrotifybanking.scrotifybanking.dto.TransactionStatementResponseDto;
import com.scrotifybanking.scrotifybanking.entity.Account;
import com.scrotifybanking.scrotifybanking.entity.Transaction;
import com.scrotifybanking.scrotifybanking.repository.AccountRepository;
import com.scrotifybanking.scrotifybanking.repository.TransactionRepository;

@RunWith(MockitoJUnitRunner.Silent.class)
public class TransactionServiceImplTest {
	@InjectMocks
	private TransactionServiceImpl transactionServiceImpl;

	@Mock
	private TransactionRepository transactionRepository;

	@Mock
	private AccountRepository accountRepository;

	TransactionStatementResponseDto transactionStatementResponseDto = null;
	TransactionStatementDto transactionStatementDto = null;
	List<TransactionStatementResponseDto> transactionsList = new ArrayList<>();

	Account account = null;

	List<Transaction> transactionList = new ArrayList<>();

	Transaction transaction = null;

	@Before

	public void setup() {

		transactionStatementDto = new TransactionStatementDto();

		transactionStatementDto.setCustomerId(1451L);

		transactionStatementDto.setMonth(11);

		transactionStatementDto.setYear(2019);

		transactionStatementResponseDto = new TransactionStatementResponseDto();

		transactionStatementResponseDto.setTransactionId(1L);

		transactionStatementResponseDto.setAmount(2779.33);

		transactionStatementResponseDto.setTransactionDate(LocalDate.parse("2019-11-03"));

		transactionStatementResponseDto.setTransactionType("credit");

		transactionsList.add(transactionStatementResponseDto);

		transactionStatementResponseDto.setMessage("sucess");

		transactionStatementResponseDto.setStatusCode(201);

		account = new Account();

		account.setAccountNo(1521L);
		/*
		 * account.setAccountStatus("open"); account.setAccountType("saving");
		 */
		transaction = new Transaction();

		transaction.setAccountNo(account);

		transaction.setAmount(2779.33);

		transaction.setTransactionDate(LocalDate.parse("2019-11-03"));

		transaction.setTransactionId(1L);

		transaction.setTransactionType("credit");

		transactionList.add(transaction);

	}

	@Test

	public void monthlyTransactionsTest() throws Exception {

		Mockito.when(transactionRepository.getAllByAccountNoAndTransactionDateBetween(account,

				LocalDate.parse("2019-11-01"), LocalDate.parse("2019-11-30"))).thenReturn(transactionList);

		Mockito.when(accountRepository.findByCustomerByAccount(anyLong(), anyString(), anyString()))
				.thenReturn(account);

		List<TransactionStatementResponseDto> transactionStatementResponseDtos = transactionServiceImpl
				.getTransactionStatement(transactionStatementDto, "active", "Saving");

		Assert.assertNotNull(transactionStatementResponseDtos);

	}

}
