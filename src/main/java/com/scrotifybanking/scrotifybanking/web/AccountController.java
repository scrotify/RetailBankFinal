package com.scrotifybanking.scrotifybanking.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scrotifybanking.scrotifybanking.dto.AccountResponseDto;
import com.scrotifybanking.scrotifybanking.exception.CustomerNotFoundException;
import com.scrotifybanking.scrotifybanking.service.AccountService;

@RestController
@RequestMapping("/accounts")
@CrossOrigin
public class AccountController {
	
	@Autowired
	AccountService accountService;
	
	@PostMapping("/{id}")
	public ResponseEntity<AccountResponseDto> createMortgageAccount(@PathVariable Long id) throws CustomerNotFoundException {
		return new ResponseEntity<>(accountService.createMortgageAccount(id), HttpStatus.CREATED);
	}

}
