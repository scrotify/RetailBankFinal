package com.scrotifybanking.scrotifybanking.web;

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

import com.scrotifybanking.scrotifybanking.dto.SearchSavingsAccountResponseDto;
import com.scrotifybanking.scrotifybanking.service.AccountServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class AccountControllerTest {
	
	@InjectMocks
	AccountController accountController;
	
	@Mock
	AccountServiceImpl accountServiceImpl;

	SearchSavingsAccountResponseDto searchSavingsAccountResponseDto = null;

	@Before
	public void setUp() {

		searchSavingsAccountResponseDto = new SearchSavingsAccountResponseDto();
		searchSavingsAccountResponseDto.setAccountNo(10001234L);
		searchSavingsAccountResponseDto.setAccountType("savings");
		searchSavingsAccountResponseDto.setAvailableBalance(458899.22);
		searchSavingsAccountResponseDto.setCustomerAge(25);
		searchSavingsAccountResponseDto.setCustomerCity("Bangalore");
		searchSavingsAccountResponseDto.setCustomerId(100L);
		searchSavingsAccountResponseDto.setCustomerMobileNo(9916438755L);
		searchSavingsAccountResponseDto.setCustomerName("Naresh");
		searchSavingsAccountResponseDto.setCustomerSalary(700000.33);

	}

	@Test
	public void testSearchSavingsAccounts() throws Exception {
		List<SearchSavingsAccountResponseDto> search = new ArrayList<>();
		search.add(searchSavingsAccountResponseDto);
		Mockito.when(accountServiceImpl.searchSavingsAccounts(10001234L)).thenReturn(search);

		List<SearchSavingsAccountResponseDto> response = accountController.searchSavingsAccounts(10001234L);
		Assert.assertNotNull(response);
	}
}
