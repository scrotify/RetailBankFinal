package com.scrotifybanking.scrotifybanking.web;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

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
import com.scrotifybanking.scrotifybanking.dto.FundRequestDto;
import com.scrotifybanking.scrotifybanking.dto.LoginDto;
import com.scrotifybanking.scrotifybanking.dto.LoginResponseDto;
import com.scrotifybanking.scrotifybanking.dto.TransactionStatementDto;
import com.scrotifybanking.scrotifybanking.dto.TransactionStatementResponseDto;
import com.scrotifybanking.scrotifybanking.dto.response.AccountNosDto;
import com.scrotifybanking.scrotifybanking.dto.response.ApiResponse;
import com.scrotifybanking.scrotifybanking.entity.Account;
import com.scrotifybanking.scrotifybanking.exception.CustomException;
import com.scrotifybanking.scrotifybanking.exception.MaintainBalanceException;
import com.scrotifybanking.scrotifybanking.exception.MinimumBalanceNotFoundException;
import com.scrotifybanking.scrotifybanking.repository.AccountRepository;
import com.scrotifybanking.scrotifybanking.repository.CustomerRepository;
import com.scrotifybanking.scrotifybanking.service.CustomerService;
import com.scrotifybanking.scrotifybanking.service.TransactionService;
import com.scrotifybanking.scrotifybanking.util.ScrotifyConstant;

/**
 * The type Customer controller.
 */
@RestController
@RequestMapping("/customers")
@CrossOrigin(allowedHeaders = "*", origins = "*")
public class CustomerController {

	/**
	 * The Customer service.
	 */
	@Autowired
	CustomerService customerService;
	/**
	 * The Customer repository.
	 */
	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private TransactionService transactionService;

	/**
	 * Fund transfer response entity.
	 *
	 * @param custId         the cust id
	 * @param toAccountNo    the to account no
	 * @param fundRequestDto the fund request dto
	 * @return the response entity
	 * @throws MinimumBalanceNotFoundException the minimum balance not found
	 *                                         exception
	 * @throws MaintainBalanceException        the maintain balance exception
	 */
	@PostMapping("/{custId}/accounts/{toAccountNo}")
	public ResponseEntity<ApiResponse> fundTransfer(@PathVariable String custId, @PathVariable String toAccountNo,
			@RequestBody @Valid FundRequestDto fundRequestDto) {

		boolean checkMinimumBalance = transactionService.checkMinimumBalance(Long.parseLong(custId),
				ScrotifyConstant.ACCOUNT_ACTIVE_STATUS, ScrotifyConstant.ACCOUNT_TYPE,
				Double.parseDouble(fundRequestDto.getAmount()));
		ApiResponse response = new ApiResponse();
		response.setStatusCode(ScrotifyConstant.TRANSACTION_FAILED);
		response.setMessage(ScrotifyConstant.FUND_TRANSFER_FAILED);
		if (checkMinimumBalance) {
			if (transactionService.checkManintenanceBalance(Long.parseLong(custId),
					ScrotifyConstant.ACCOUNT_ACTIVE_STATUS, ScrotifyConstant.ACCOUNT_TYPE,
					Double.parseDouble(fundRequestDto.getAmount()), ScrotifyConstant.MINIMUM_BALANCE_MAINTAIN)) {
				response = transactionService.transferFund(Long.parseLong(custId), toAccountNo,
						Double.parseDouble(fundRequestDto.getAmount()), ScrotifyConstant.ACCOUNT_ACTIVE_STATUS,
						ScrotifyConstant.ACCOUNT_TYPE);
				response.setMessage(ScrotifyConstant.FUND_TRANSFER_SUCCESS);
				return new ResponseEntity<>(response, HttpStatus.OK);
			} else {
				throw new MinimumBalanceNotFoundException(ScrotifyConstant.MINIMUM_BALANCE_FAILED);
			}
		} else {
			throw new MaintainBalanceException(ScrotifyConstant.MAINTAIN_BALANCE_FAILED);
		}
	}

	/**
	 * get list of accounts except current customer
	 *
	 * @param custId the cust id
	 * @return all account nos
	 * @throws CustomException the custom exception
	 */
	@GetMapping("/{custId}/accounts")
	public ResponseEntity<AccountNosDto> getAllAccountNos(@PathVariable String custId) {

		AccountNosDto accountNosDtos = new AccountNosDto();
		List<Account> accounts = accountRepository.findAllByAccountNotCustomer(Long.parseLong(custId),
				ScrotifyConstant.ACCOUNT_ACTIVE_STATUS, ScrotifyConstant.ACCOUNT_TYPE);
		if (!accounts.isEmpty()) {
			List<Long> accountNos = accounts.stream().map(Account::getAccountNo).collect(Collectors.toList());
			accountNosDtos.setAccountNos(accountNos);
		} else {
			throw new CustomException(ScrotifyConstant.CUSTOMER_ID_NOT_FOUND);
		}
		return new ResponseEntity<>(accountNosDtos, HttpStatus.OK);
	}

	/**
	 * Gets transaction statement.
	 *
	 * @param transactionStatementDto the transaction statement dto
	 * @return transaction statement
	 * @throws Exception the exception
	 */
	@PostMapping("/transactions")
	public List<TransactionStatementResponseDto> getTransactionStatement(
			@RequestBody TransactionStatementDto transactionStatementDto) throws Exception {
		return transactionService.getTransactionStatement(transactionStatementDto,
				ScrotifyConstant.ACCOUNT_ACTIVE_STATUS, ScrotifyConstant.ACCOUNT_TYPE);

	}

	/**
	 * Register customer customer response dto.
	 *
	 * @param customerRequestDto the customer request dto
	 * @return the customer response dto
	 */

	@PostMapping
	public CustomerResponseDto registerCustomer(@RequestBody CustomerRequestDto customerRequestDto) {
		return customerService.registerCustomer(customerRequestDto);
	}

	/**
	 * Login customer response entity.
	 *
	 * @param loginDto the login dto
	 * @return the response entity
	 */

	@PostMapping("/{customerId}/{password}")
	public ResponseEntity<LoginResponseDto> loginCustomer(@RequestBody LoginDto loginDto) {
		return new ResponseEntity<>(customerService.loginCustomer(loginDto), HttpStatus.OK);
	}

	/**
	 * Last transaction response entity.
	 *
	 * @param customerId the customer id
	 * @return the response entity
	 */

	@GetMapping("/{customerId}")
	public ResponseEntity<AccountSummaryResponseDto> lastTransaction(@PathVariable Long customerId) {
		return new ResponseEntity<>(customerService.accountSummary(customerId), HttpStatus.OK);
	}

}
