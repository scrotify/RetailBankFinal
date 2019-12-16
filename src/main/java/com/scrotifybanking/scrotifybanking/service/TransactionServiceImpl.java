package com.scrotifybanking.scrotifybanking.service;

import com.scrotifybanking.scrotifybanking.dto.ApiResponse;
import com.scrotifybanking.scrotifybanking.dto.FundRequestDto;
import com.scrotifybanking.scrotifybanking.dto.TransactionStatementDto;
import com.scrotifybanking.scrotifybanking.dto.TransactionStatementResponseDto;
import com.scrotifybanking.scrotifybanking.entity.Account;
import com.scrotifybanking.scrotifybanking.entity.Transaction;
import com.scrotifybanking.scrotifybanking.exception.CustomException;
import com.scrotifybanking.scrotifybanking.repository.AccountRepository;
import com.scrotifybanking.scrotifybanking.repository.TransactionRepository;
import com.scrotifybanking.scrotifybanking.util.ScrotifyConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The type Transaction service.
 */
@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;


    private Optional<Double> checkMinimumBalance(Long custId, String accountStatus, String accountType, double amount) {
        Optional<Double> aDouble = accountRepository.findByAccountBalance(custId, accountStatus, accountType);
        if (aDouble.isPresent()) {
            return aDouble.filter(balance -> balance.doubleValue() > amount);
        }
        return Optional.empty();
    }

    private Optional<Double> checkManintenanceBalance(Long custId, String accountStatus, String accountType, double amount,
                                                      double maintainBalance) {
        Optional<Double> accountBalance = accountRepository.findByAccountBalance(custId, accountStatus, accountType);
        if (accountBalance.isPresent()) {
            return accountBalance.filter(value -> value.doubleValue() > (maintainBalance + amount));
        }
        return Optional.empty();
    }

	/**
	 * add fund transfer from saving to Account number or Mortgage account
	 * @param fundRequestDto - request
	 * @param accountStatus the account status
	 * @param accountType   the account type
	 * @return - api response
	 */
    @Transactional
    @Override
    public ApiResponse transferFund(FundRequestDto fundRequestDto, String accountStatus,
                                    String accountType) {
        ApiResponse response = new ApiResponse();
        Account payeeAccount = null;

        Optional<Double> checkMinimumBalance = checkMinimumBalance(fundRequestDto.getCustId(),
                ScrotifyConstant.ACCOUNT_ACTIVE_STATUS, ScrotifyConstant.ACCOUNT_TYPE, fundRequestDto.getAmount());
        if (checkMinimumBalance.isPresent()) {
            if (checkManintenanceBalance(fundRequestDto.getCustId(),
                    ScrotifyConstant.ACCOUNT_ACTIVE_STATUS, ScrotifyConstant.ACCOUNT_TYPE, fundRequestDto.getAmount(),
                    ScrotifyConstant.MINIMUM_BALANCE_MAINTAIN).isPresent()) {
                Optional<Account> accountOptional = accountRepository.findById(fundRequestDto.getAccountNo());
                Account customerAccount = accountRepository.findByCustomerByAccount(fundRequestDto.getCustId(), accountStatus, accountType);
                payeeAccount = accountOptional.orElseThrow(CustomException::new);
                double balanceAmount = customerAccount.getAvailableBalance() - fundRequestDto.getAmount();
                customerAccount.setAvailableBalance(balanceAmount);
                double payeeAccountBalance = payeeAccount.getAvailableBalance();
                if (payeeAccount.getAccountType().equalsIgnoreCase(ScrotifyConstant.MORTGAGE_ACCOUNT_TYPE)) {
                    payeeAccountBalance = payeeAccountBalance - fundRequestDto.getAmount();
                    if (payeeAccountBalance >= 0) {
                        payeeAccount.setAvailableBalance(payeeAccountBalance);
                    } else {
                        payeeAccount.setAvailableBalance(0D);
                    }
                } else {
                    payeeAccountBalance = payeeAccountBalance + fundRequestDto.getAmount();
                    payeeAccount.setAvailableBalance(payeeAccountBalance);
                }
                transaction(fundRequestDto.getAccountNo(), fundRequestDto.getAmount(), response, payeeAccount, customerAccount);
            } else {
                throw new CustomException(ScrotifyConstant.MINIMUM_BALANCE_FAILED);
            }
        } else {
            throw new CustomException(ScrotifyConstant.MAINTAIN_BALANCE_FAILED);
        }
        return response;
    }

    private void transaction(Long accountNo, double amount, ApiResponse response, Account payeeAccount, Account customerAccount) {
        Transaction customertransaction = new Transaction();
        customertransaction.setAccountNo(customerAccount);
        customertransaction.setAmount(amount);
        customertransaction.setTransactionDate(LocalDate.now());
        customertransaction.setTransactionType(ScrotifyConstant.DEBIT_TRANSACTION);
        customertransaction.setPayeeNo(accountNo);

        Transaction payeeTransaction = new Transaction();
        payeeTransaction.setAccountNo(payeeAccount);
        payeeTransaction.setAmount(amount);
        payeeTransaction.setTransactionDate(LocalDate.now());
        payeeTransaction.setTransactionType(ScrotifyConstant.CREDIT_TRANSACTION);
        payeeTransaction.setPayeeNo(customertransaction.getAccountNo().getAccountNo());

        transactionRepository.save(customertransaction);
        transactionRepository.save(payeeTransaction);
        accountRepository.save(payeeAccount);
        accountRepository.save(customerAccount);
        response.setStatusCode(ScrotifyConstant.SUCCESS_CODE);
    }

    @Override
    public List<TransactionStatementResponseDto> getTransactionStatement(
            TransactionStatementDto transactionStatementDto, String accountStatus, String accountType)
            throws Exception {
        TransactionStatementResponseDto transactionStatementResponseDto = null;
        Integer month = transactionStatementDto.getMonth();
        Integer year = transactionStatementDto.getYear();
        LocalDate startDate = LocalDate.parse(year + "-" + month + "-" + "01");
        LocalDate endDate = LocalDate.parse(year + "-" + month + "-" + "30");
        Account customerId = accountRepository.findByCustomerByAccount(transactionStatementDto.getCustomerId(),
                accountStatus, accountType);
        List<TransactionStatementResponseDto> monthlyTransaction = new ArrayList<>();
        if (customerId != null) {
            Account accountNo = new Account();
            accountNo.setAccountNo(customerId.getAccountNo());
            List<Transaction> transactions = transactionRepository.getAllByAccountNoAndTransactionDateBetween(accountNo,
                    startDate, endDate);
            for (Transaction transact : transactions) {
                transactionStatementResponseDto = new TransactionStatementResponseDto();
                transactionStatementResponseDto.setTransactionId(transact.getTransactionId());
                transactionStatementResponseDto.setAmount(transact.getAmount());
                transactionStatementResponseDto.setTransactionDate(transact.getTransactionDate());
                transactionStatementResponseDto.setTransactionType(transact.getTransactionType());
                monthlyTransaction.add(transactionStatementResponseDto);
                transactionStatementResponseDto.setMessage("transactions");
                transactionStatementResponseDto.setStatusCode(201);

            }
        }

        return monthlyTransaction;
    }

}

