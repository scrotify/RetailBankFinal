package com.scrotifybanking.scrotifybanking.web;

import com.scrotifybanking.scrotifybanking.dto.ApiResponse;
import com.scrotifybanking.scrotifybanking.dto.FundRequestDto;
import com.scrotifybanking.scrotifybanking.dto.MortgageTransferDto;
import com.scrotifybanking.scrotifybanking.service.AccountService;
import com.scrotifybanking.scrotifybanking.service.TransactionService;
import com.scrotifybanking.scrotifybanking.util.ScrotifyConstant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
        response = transactionService.transferFund(fundRequestDto,
                ScrotifyConstant.ACCOUNT_ACTIVE_STATUS, ScrotifyConstant.ACCOUNT_TYPE);
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

}
