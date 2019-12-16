package com.scrotifybanking.scrotifybanking.web;

import com.scrotifybanking.scrotifybanking.dto.ApiResponse;
import com.scrotifybanking.scrotifybanking.dto.FundRequestDto;
import com.scrotifybanking.scrotifybanking.dto.TransactionStatementDto;
import com.scrotifybanking.scrotifybanking.dto.TransactionStatementResponseDto;
import com.scrotifybanking.scrotifybanking.exception.CustomException;
import com.scrotifybanking.scrotifybanking.service.AccountService;
import com.scrotifybanking.scrotifybanking.service.TransactionService;
import com.scrotifybanking.scrotifybanking.util.ScrotifyConstant;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;

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
     * Sets up.
     */
    @Before
    public void setUp() {


    }

    /**
     * Test fund transfer.
     *
     * @throws Exception the custom exception
     */
    @Test (expected = CustomException.class)
    public void testFundTransferCheckMinimumFalse() throws Exception {
        FundRequestDto fundRequestDto = new FundRequestDto();
        fundRequestDto.setAmount(1000);
        fundRequestDto.setAccountNo(2L);
        fundRequestDto.setCustId(123456L);
        Mockito.when(transactionService.checkMinimumBalance(anyLong(), anyString(), anyString(),
                anyDouble())).thenReturn(new Boolean(true));
//        Mockito.when(transactionService.checkMinimumBalance(anyLong(), anyString(), anyString(),
//                anyDouble())).thenReturn(new Boolean(true));
        Mockito.when(transactionService.checkManintenanceBalance(anyLong(), anyString(), anyString(),
                anyDouble(), anyDouble())).thenReturn(true);
        ResponseEntity<ApiResponse> response = fundTransferController.fundTransfer(fundRequestDto);
        Assert.assertNotNull(response);
    }

    /**
     * Test fund transfer check.
     *
     * @throws Exception the exception
     */
    @Test
    public void testFundTransferCheck() throws Exception {

        FundRequestDto fundRequestDto = new FundRequestDto();
        fundRequestDto.setAmount(1000);
        fundRequestDto.setAccountNo(2L);
        fundRequestDto.setCustId(123456L);
        Mockito.when(
                transactionService.checkMinimumBalance(fundRequestDto.getCustId(), ScrotifyConstant.ACCOUNT_ACTIVE_STATUS,
                        ScrotifyConstant.ACCOUNT_TYPE, fundRequestDto.getAmount()))
                .thenReturn(true);
        Mockito.when(transactionService.checkManintenanceBalance(fundRequestDto.getCustId(),
                ScrotifyConstant.ACCOUNT_ACTIVE_STATUS, ScrotifyConstant.ACCOUNT_TYPE,
                fundRequestDto.getAmount(), ScrotifyConstant.MINIMUM_BALANCE_MAINTAIN))
                .thenReturn(true);
        ApiResponse response = new ApiResponse();
        response.setMessage("success");
        response.setStatusCode(200);
        Mockito.when(transactionService.transferFund(anyLong(), anyString(), anyDouble(), any(), any()))
                .thenReturn(response);
        ResponseEntity<ApiResponse> responseRes = fundTransferController.fundTransfer(fundRequestDto);
        Assert.assertNotNull(responseRes);
        Assert.assertNotNull(responseRes.getBody().getMessage());
        Assert.assertNotNull(responseRes.getStatusCode());

    }
}
