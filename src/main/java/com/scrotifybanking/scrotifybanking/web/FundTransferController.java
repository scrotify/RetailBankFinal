package com.scrotifybanking.scrotifybanking.web;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.scrotifybanking.scrotifybanking.dto.ApiResponse;
import com.scrotifybanking.scrotifybanking.dto.FundRequestDto;
import com.scrotifybanking.scrotifybanking.dto.ListOfPayeeResponseDto;
import com.scrotifybanking.scrotifybanking.dto.MortgageTransferDto;
import com.scrotifybanking.scrotifybanking.entity.Customer;
import com.scrotifybanking.scrotifybanking.service.AccountService;
import com.scrotifybanking.scrotifybanking.service.TransactionService;
import com.scrotifybanking.scrotifybanking.util.ScrotifyConstant;

/**
 * The type Fund transfer controller.
 */
@RestController
@RequestMapping("/fund-transfer")
@CrossOrigin
public class FundTransferController {

	private static final Logger logger = LogManager.getLogger(FundTransferController.class);

	@Autowired
	private TransactionService transactionService;

	@Autowired
	private AccountService accountService;

	@Autowired
	private RestTemplate restTemplate;

	@Bean
	private RestTemplate restTemplate() {
		return new RestTemplate();
	}

	/**
	 * Fund transfer response entity.
	 *
	 * @param fundRequestDto the fund request dto
	 * @return the response entity
	 */
	@PostMapping
	public ResponseEntity<ApiResponse> fundTransfer(@RequestBody @Valid FundRequestDto fundRequestDto) {
		logger.info("Entering into fund transfer");
		ApiResponse response = new ApiResponse();
		response.setStatusCode(ScrotifyConstant.TRANSACTION_FAILED);
		response.setMessage(ScrotifyConstant.FUND_TRANSFER_FAILED);
		response = transactionService.transferFund(fundRequestDto, ScrotifyConstant.ACCOUNT_ACTIVE_STATUS,
				ScrotifyConstant.ACCOUNT_TYPE);
		response.setMessage(ScrotifyConstant.FUND_TRANSFER_SUCCESS);
		logger.debug(" Fund transfer Status Code : " + response.getStatusCode());
		logger.info("Existing from fund transfer");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * Gets martgage accounts.
	 *
	 * @param custId the cust id
	 * @return the martgage accounts
	 */
	@GetMapping("/{custId}")
	public List<MortgageTransferDto> getMartgageAccounts(@PathVariable Long custId) {
		logger.info("Getting martgage account details of current logged in user");
		return accountService.findAllByCustomerNumber(custId);
	}

	/** This method is used to get all the beneficiaries from payee service, internally we used rest template for that.
	 * @param customerId
	 * @return
	 */
	@GetMapping("/customers/beneficiaries/{customerId}")
	public ResponseEntity<List<ListOfPayeeResponseDto>> getAllBeneficiaries(Long customerId) {
		final String uri = "http://localhost:8082/payee/beneficiaries/" + customerId;
	
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.add("Accept", MediaType.APPLICATION_JSON_VALUE);
		Customer customer = new Customer();
		customer.setCustomerId(customerId);
		HttpEntity<Customer> requestEntity = new HttpEntity<>(customer, requestHeaders);

		ArrayList<ListOfPayeeResponseDto> list = restTemplate
				.exchange(uri, HttpMethod.GET, requestEntity, ArrayList.class).getBody();
		List<ListOfPayeeResponseDto> response = list;
		if (response != null)
			return new ResponseEntity<List<ListOfPayeeResponseDto>>(response, HttpStatus.OK);
		return new ResponseEntity<List<ListOfPayeeResponseDto>>(HttpStatus.NO_CONTENT);
	}

}
