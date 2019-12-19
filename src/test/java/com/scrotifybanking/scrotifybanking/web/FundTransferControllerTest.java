package com.scrotifybanking.scrotifybanking.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import com.scrotifybanking.scrotifybanking.dto.ApiResponse;
import com.scrotifybanking.scrotifybanking.dto.FundRequestDto;
import com.scrotifybanking.scrotifybanking.dto.MortgageTransferDto;
import com.scrotifybanking.scrotifybanking.dto.TransactionStatementDto;
import com.scrotifybanking.scrotifybanking.dto.TransactionStatementResponseDto;
import com.scrotifybanking.scrotifybanking.service.AccountService;
import com.scrotifybanking.scrotifybanking.service.TransactionService;

@RunWith(MockitoJUnitRunner.class)
public class FundTransferControllerTest {

    List<TransactionStatementResponseDto> transactionStatementResponseDtos = new ArrayList<>();
    /**
     * The Transaction statement dto.
     */
    TransactionStatementDto transactionStatementDto = null;
    /**
     * The Transaction statement response dto.
     */
    TransactionStatementResponseDto transactionStatementResponseDto = null;
    @InjectMocks
    private FundTransferController fundTransferController;
    @Mock
    private AccountService accountService;
    @Mock
    private TransactionService transactionService;
    /**
     * The Transaction statement response dtos.
     */


    /**
     * Test fund transfer.
     *
     * @throws Exception the custom exception
     */
    @Test
    public void testFundTransferCheckMinimumFalse() throws Exception {
        FundRequestDto fundRequestDto = new FundRequestDto();
        fundRequestDto.setAmount(1000.22);
        fundRequestDto.setAccountNo(2L);
        fundRequestDto.setCustId(123456L);
        ApiResponse response = new ApiResponse();
        response.setMessage("Success");
        response.setStatusCode(200);
        Mockito.when(transactionService.transferFund(any(), anyString(), anyString())).thenReturn(response);
        ResponseEntity<ApiResponse> responseOutput = fundTransferController.fundTransfer(fundRequestDto);
        Assert.assertNotNull(responseOutput);
    }

    /**
     * Test fund transfer check.
     *
     * @throws Exception the exception
     */
    @Test
    public void testFundTransferCheck()  {
        List<MortgageTransferDto> mortgageTransferDtos = new ArrayList<>();

        Mockito.when(accountService.findAllByCustomerNumber(anyLong()))
                .thenReturn(mortgageTransferDtos);
        List<MortgageTransferDto> mortgageTransferDtosOutput = fundTransferController.getMartgageAccounts(200L);
        Assert.assertNotNull(mortgageTransferDtosOutput);
    }
}
