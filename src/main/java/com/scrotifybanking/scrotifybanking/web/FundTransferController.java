package com.scrotifybanking.scrotifybanking.web;

import com.scrotifybanking.scrotifybanking.dto.FundRequestDto;
import com.scrotifybanking.scrotifybanking.dto.response.ApiResponse;
import com.scrotifybanking.scrotifybanking.dto.response.MortgageTransferDto;
import com.scrotifybanking.scrotifybanking.exception.CustomException;
import com.scrotifybanking.scrotifybanking.repository.AccountRepository;
import com.scrotifybanking.scrotifybanking.service.AccountService;
import com.scrotifybanking.scrotifybanking.service.CustomerService;
import com.scrotifybanking.scrotifybanking.service.TransactionService;
import com.scrotifybanking.scrotifybanking.service.impl.AccountServiceImpl;
import com.scrotifybanking.scrotifybanking.util.ScrotifyConstant;
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
public class FundTransferController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private AccountService accountService;

    /**
     * Fund transfer response entity.
     *
     * @param custId         the cust id
     * @param toAccountNo    the to account no
     * @param fundRequestDto the fund request dto
     * @return the response entity
     */
    @PostMapping
    public ResponseEntity<ApiResponse> fundTransfer(@RequestBody @Valid FundRequestDto fundRequestDto) {

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


    @GetMapping("/{custId}")
    public List<MortgageTransferDto> getMartgageAccounts(@PathVariable Long custId) {
        return accountService.findAllByCustomerNumber(custId);
    }
}
