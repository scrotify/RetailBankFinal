package com.scrotifybanking.scrotifybanking.web;

import java.util.List;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scrotifybanking.scrotifybanking.dto.ApiResponse;
import com.scrotifybanking.scrotifybanking.dto.FundRequestDto;
import com.scrotifybanking.scrotifybanking.dto.MortgageTransferDto;
import com.scrotifybanking.scrotifybanking.exception.CustomException;
import com.scrotifybanking.scrotifybanking.service.AccountService;
import com.scrotifybanking.scrotifybanking.service.TransactionService;
import com.scrotifybanking.scrotifybanking.util.ScrotifyConstant;

/**
 * The type Fund transfer controller.
 */
@RestController
@RequestMapping("/fund-transfer")
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
    public ResponseEntity<ApiResponse> fundTransfer(@RequestBody FundRequestDto fundRequestDto) {
        boolean checkMinimumBalance = transactionService.checkMinimumBalance(fundRequestDto.getCustId(),
                ScrotifyConstant.ACCOUNT_ACTIVE_STATUS, ScrotifyConstant.ACCOUNT_TYPE,
                fundRequestDto.getAmount());
        ApiResponse response = new ApiResponse();
        response.setStatusCode(ScrotifyConstant.TRANSACTION_FAILED);
        response.setMessage(ScrotifyConstant.FUND_TRANSFER_FAILED);
        if (checkMinimumBalance) {
            if (transactionService.checkManintenanceBalance(fundRequestDto.getCustId(),
                    ScrotifyConstant.ACCOUNT_ACTIVE_STATUS, ScrotifyConstant.ACCOUNT_TYPE,
                    fundRequestDto.getAmount(), ScrotifyConstant.MINIMUM_BALANCE_MAINTAIN)) {
                response = transactionService.transferFund(fundRequestDto.getCustId(), String.valueOf(fundRequestDto.getAccountNo()),
                        fundRequestDto.getAmount(), ScrotifyConstant.ACCOUNT_ACTIVE_STATUS,
                        ScrotifyConstant.ACCOUNT_TYPE);
                response.setMessage(ScrotifyConstant.FUND_TRANSFER_SUCCESS);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                throw new CustomException(ScrotifyConstant.MINIMUM_BALANCE_FAILED);
            }
        } else {
            throw new CustomException(ScrotifyConstant.MAINTAIN_BALANCE_FAILED);
        }
    }


    /**
     * Gets martgage accounts.
     *
     * @param custId the cust id
     * @return the martgage accounts
     */
    @GetMapping("/{custId}")
    public List<MortgageTransferDto> getMartgageAccounts(@PathVariable Long custId) {
        return accountService.findAllByCustomerNumber(custId);
    }
}

