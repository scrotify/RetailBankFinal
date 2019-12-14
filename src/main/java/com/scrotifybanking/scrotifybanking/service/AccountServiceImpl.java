package com.scrotifybanking.scrotifybanking.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scrotifybanking.scrotifybanking.dto.AccountDto;
import com.scrotifybanking.scrotifybanking.dto.AccountResponseDto;
import com.scrotifybanking.scrotifybanking.dto.MortgageTransferDto;
import com.scrotifybanking.scrotifybanking.entity.Account;
import com.scrotifybanking.scrotifybanking.entity.Customer;
import com.scrotifybanking.scrotifybanking.exception.CustomException;
import com.scrotifybanking.scrotifybanking.exception.CustomerNotFoundException;
import com.scrotifybanking.scrotifybanking.repository.AccountRepository;
import com.scrotifybanking.scrotifybanking.repository.CustomerRepository;
import com.scrotifybanking.scrotifybanking.util.ScrotifyConstant;

/**
 * The type Account service.
 */
@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private CustomerRepository customerRepository;

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

	@Override
	public AccountResponseDto createMortgageAccount(Long id) throws CustomerNotFoundException {
		Optional<Customer> customer = customerRepository.findByCustomerId(id);
		Account account = new Account();
		AccountResponseDto accountResponseDto = new AccountResponseDto();
		if (customer.isPresent()) {
			account.setAccountStatus(ScrotifyConstant.ACCOUNT_ACTIVE_STATUS);
			account.setAccountType(ScrotifyConstant.MORTGAGE_ACCOUNT_MESSAGE);
			account.setAvailableBalance(ScrotifyConstant.BALANCE_AMOUNT);
			account.setCustomer(customer.get());
			accountRepository.save(account);
			accountResponseDto.setMessage(ScrotifyConstant.ACCOUNT_CREATED_MESSAGE);
			accountResponseDto.setStatusCode(ScrotifyConstant.CREATED_CODE);
		} else {
			throw new CustomerNotFoundException(ScrotifyConstant.CUSTOMER_ID_NOT_FOUND);
		}
		return accountResponseDto;
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
