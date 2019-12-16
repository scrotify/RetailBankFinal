package com.scrotifybanking.scrotifybanking.service;

import java.util.List;
import java.util.Optional;

import com.scrotifybanking.scrotifybanking.dto.ApiResponse;
import com.scrotifybanking.scrotifybanking.dto.FundRequestDto;
import com.scrotifybanking.scrotifybanking.dto.TransactionStatementDto;
import com.scrotifybanking.scrotifybanking.dto.TransactionStatementResponseDto;

/**
 * The interface Transaction service.
 */
public interface TransactionService {

	/**
	 * Transfer fund api response.
	 *
	 * @param custId        the cust id
	 * @param toAccountNo   the to account no
	 * @param amount        the amount
	 * @param accountStatus the account status
	 * @param accountType   the account type
	 * @return the api response
	 */
	public ApiResponse transferFund(FundRequestDto fundRequestDto, String accountStatus,
									String accountType);

	/**
	 * Gets transaction statement.
	 *
	 * @param transactionStatementDto the transaction statement dto
	 * @param accountStatus           the account status
	 * @param accountType             the account type
	 * @return the transaction statement
	 * @throws Exception the exception
	 */
	public List<TransactionStatementResponseDto> getTransactionStatement(
			TransactionStatementDto transactionStatementDto, String accountStatus, String accountType) throws Exception;
}
