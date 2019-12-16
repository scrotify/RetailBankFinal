package com.scrotifybanking.scrotifybanking.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scrotifybanking.scrotifybanking.entity.Account;
import com.scrotifybanking.scrotifybanking.entity.Transaction;
import org.springframework.stereotype.Repository;

/**
 * The interface Transaction repository.
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
	/**
	 * Find by account no list.
	 *
	 * @param accountNo the account no
	 * @return the list
	 */
	List<Transaction> findAccountByAccountNo(Long accountNo);

	
	/**
	 * Find top 5 by account order by transaction id desc list.
	 *
	 * @param accountDetails the account details
	 * @return the list
	 */
	List<Transaction> findTop5ByAccountNoOrderByTransactionIdDesc(Optional<Account> accountDetails);

	/**
	 * GetAll By AccountNo And TransactionDate
	 *
	 * @param
	 * @return the list
	 */
	List<Transaction> getAllByAccountNoAndTransactionDateBetween(Account accountNo, LocalDate startDate,
			LocalDate endDate);

}