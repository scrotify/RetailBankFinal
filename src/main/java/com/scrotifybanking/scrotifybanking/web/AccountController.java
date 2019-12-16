package com.scrotifybanking.scrotifybanking.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scrotifybanking.scrotifybanking.dto.AccountResponseDto;
import com.scrotifybanking.scrotifybanking.dto.SearchSavingsAccountResponseDto;
import com.scrotifybanking.scrotifybanking.exception.CustomerNotFoundException;
import com.scrotifybanking.scrotifybanking.service.AccountService;

@RestController
@RequestMapping("/accounts")
@CrossOrigin
public class AccountController {

	@Autowired
	AccountService accountService;

	/**
	 * @Description this method is operate when the admin want to see list of
	 *              savings accounts
	 * @param accountNo
	 * @return
	 * @throws Exception 
	 */
	@GetMapping("{accountNumber}")
	public List<SearchSavingsAccountResponseDto> searchSavingsAccounts(Long accountNo) throws Exception {
		return accountService.searchSavingsAccounts(accountNo);
	}

	@PostMapping("/{id}")
	public ResponseEntity<AccountResponseDto> createMortgageAccount(@PathVariable Long id)
			throws CustomerNotFoundException {
		return new ResponseEntity<>(accountService.createMortgageAccount(id), HttpStatus.CREATED);
	}

}
