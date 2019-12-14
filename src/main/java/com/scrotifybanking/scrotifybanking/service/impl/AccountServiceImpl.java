package com.scrotifybanking.scrotifybanking.service.impl;

import com.scrotifybanking.scrotifybanking.dto.response.AccountDto;
import com.scrotifybanking.scrotifybanking.dto.response.MortgageTransferDto;
import com.scrotifybanking.scrotifybanking.entity.Account;
import com.scrotifybanking.scrotifybanking.exception.CustomException;
import com.scrotifybanking.scrotifybanking.repository.AccountRepository;
import com.scrotifybanking.scrotifybanking.service.AccountService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * The type Account service.
 */
@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    /**
     * findAllByAccount except current cust
     *
     * @param custId        the cust id
     * @param accountStatus the account status
     * @param accountType   the account type
     * @return
     */
    @Override
    public List<Account> findAllByAccountNotCustomer(String custId, String accountStatus, String accountType) {
        return accountRepository.findAllByAccountNotCustomer(Long.parseLong(custId), accountStatus, accountType);
    }

    /**
     * search by account no
     *
     * @param accountNumber
     * @return
     */
    @Override
    public AccountDto findByAccountNumber(Long accountNumber) {
        Optional<Account> accountOptional = accountRepository.findById(accountNumber);
        AccountDto accountDto = new AccountDto();
        if (accountOptional.isPresent()) {
            Account account = accountOptional.get();
            BeanUtils.copyProperties(account, accountDto);
            accountDto.setCustomerId(account.getCustomer().getCustomerId());
        }
		return accountDto;
    }

    @Override
    public List<MortgageTransferDto> findAllByCustomerNumber(Long customerNumber) {
        Optional<List<Account>> accountOptional = accountRepository.findAllByCustomerCustomerId(customerNumber);
		List<MortgageTransferDto> mortgageTransferDtos = new ArrayList<>();
        if (accountOptional.isPresent()) {
            List<Account> account = accountOptional.get();
			 mortgageTransferDtos = account.stream().map(acc -> {
				MortgageTransferDto mortgageTransferDto = new MortgageTransferDto();
				mortgageTransferDto.setAccountNumber(acc.getAccountNo());
				mortgageTransferDto.setAccountType(acc.getAccountType());
				return mortgageTransferDto;
			}).collect(Collectors.toList());
        } else {
            throw new CustomException("Account is not available");
        }
		return mortgageTransferDtos;
    }

}
