package com.scrotifybanking.scrotifybanking.web;

import java.util.List;
import java.util.stream.Collectors;

import com.scrotifybanking.scrotifybanking.exception.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scrotifybanking.scrotifybanking.dto.AccountSummaryResponseDto;
import com.scrotifybanking.scrotifybanking.dto.CustomerRequestDto;
import com.scrotifybanking.scrotifybanking.dto.CustomerResponseDto;
import com.scrotifybanking.scrotifybanking.dto.LoginDto;
import com.scrotifybanking.scrotifybanking.dto.LoginResponseDto;
import com.scrotifybanking.scrotifybanking.dto.TransactionStatementDto;
import com.scrotifybanking.scrotifybanking.dto.TransactionStatementResponseDto;
import com.scrotifybanking.scrotifybanking.dto.response.AccountNumbersDto;
import com.scrotifybanking.scrotifybanking.entity.Account;
import com.scrotifybanking.scrotifybanking.repository.AccountRepository;
import com.scrotifybanking.scrotifybanking.repository.CustomerRepository;
import com.scrotifybanking.scrotifybanking.service.CustomerService;
import com.scrotifybanking.scrotifybanking.service.TransactionService;
import com.scrotifybanking.scrotifybanking.util.ScrotifyConstant;

@RestController
@RequestMapping("/customers")
@CrossOrigin(allowedHeaders = "*", origins = "*")
public class CustomerController {

	@Autowired
	CustomerService customerService;
	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private TransactionService transactionService;


	@GetMapping("/{custId}/accounts")
	public ResponseEntity<AccountNumbersDto> getAllAccountNos(@PathVariable String custId) {

		AccountNumbersDto accountNumbersDtos = new AccountNumbersDto();
		List<Account> accounts = accountRepository.findAllByAccountNotCustomer(Long.parseLong(custId),
				ScrotifyConstant.ACCOUNT_ACTIVE_STATUS, ScrotifyConstant.ACCOUNT_TYPE);
		if (!accounts.isEmpty()) {
			List<Long> accountNos = accounts.stream().map(Account::getAccountNo).collect(Collectors.toList());
			accountNumbersDtos.setAccountNos(accountNos);
		} else {
			throw new CustomException(ScrotifyConstant.CUSTOMER_ID_NOT_FOUND);
		}
		return new ResponseEntity<>(accountNumbersDtos, HttpStatus.OK);
	}

	@PostMapping("/transactions")
	public List<TransactionStatementResponseDto> getTransactionStatement(
			@RequestBody TransactionStatementDto transactionStatementDto) throws Exception {
		return transactionService.getTransactionStatement(transactionStatementDto,
				ScrotifyConstant.ACCOUNT_ACTIVE_STATUS, ScrotifyConstant.ACCOUNT_TYPE);

	}

	@PostMapping
	public CustomerResponseDto registerCustomer(@RequestBody CustomerRequestDto customerRequestDto) {
		return customerService.registerCustomer(customerRequestDto);
	}

	@PostMapping("/{customerId}/{password}")
	public ResponseEntity<LoginResponseDto> loginCustomer(@RequestBody LoginDto loginDto) {
		return new ResponseEntity<>(customerService.loginCustomer(loginDto), HttpStatus.OK);
	}

	@GetMapping("/{customerId}")
	public ResponseEntity<AccountSummaryResponseDto> lastTransaction(@PathVariable Long customerId) {
		return new ResponseEntity<>(customerService.accountSummary(customerId), HttpStatus.OK);
	}

}
