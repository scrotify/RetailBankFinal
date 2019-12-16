package com.scrotifybanking.scrotifybanking.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.scrotifybanking.scrotifybanking.entity.Account;
import com.scrotifybanking.scrotifybanking.entity.Customer;

/**
 * The interface Account repository.
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

	/**
	 * Find by account balance double.
	 *
	 * @param custId        the cust id
	 * @param accountStatus the account status
	 * @param accountType   the account type
	 * @return the double
	 */
	@Query("select acc.availableBalance from Account acc where acc.customer.customerId = :custId and acc.accountStatus = :accountStatus and acc.accountType = :accountType")
	Optional<Double> findByAccountBalance(@Param("custId") Long custId, @Param("accountStatus") String accountStatus,
			@Param("accountType") String accountType);

	/**
	 * Find by customer by account account.
	 *
	 * @param custId        the cust id
	 * @param accountStatus the account status
	 * @param accountType   the account type
	 * @return the account
	 */
	@Query("select acc from Account acc where acc.customer.customerId = :custId and acc.accountStatus = :accountStatus and acc.accountType = :accountType")
	public Account findByCustomerByAccount(@Param("custId") Long custId, @Param("accountStatus") String accountStatus,
			@Param("accountType") String accountType);

	/**
	 * Find all by account not customer list.
	 *
	 * @param custId        the cust id
	 * @param accountStatus the account status
	 * @param accountType   the account type
	 * @return the list
	 */
	@Query("select acc from Account acc where acc.customer.customerId != :custId and acc.accountStatus = :accountStatus and acc.accountType = :accountType")
	List<Account> findAllByAccountNotCustomer(@Param("custId") Long custId,
			@Param("accountStatus") String accountStatus, @Param("accountType") String accountType);

	/**
	 * Account Repository has one method.It is used to find the method by
	 * accountType.
	 *
	 * @param accountType the account type
	 * @return account account
	 */
	Account findByAccountType(String accountType);

	/**
	 * Find account by customer account.
	 *
	 * @param customerId the customer id
	 * @return the account
	 */
	@Query("select acc from Account acc where acc.customer.customerId = :customerId")
	Account findAccountByCustomer(@Param("customerId") Long customerId);

	/**
	 * Find by customer optional.
	 *
	 * @param customer the customer
	 * @return the optional
	 */
	Optional<Account> findByCustomer(Optional<Customer> customer);

	Optional<Account> findByCustomerAndAccountType(Optional<Customer> customer, String string);

	Optional<List<Account>> findAllByCustomerCustomerId(Long customerId);
	
	
	@Query(nativeQuery = true, value = "select * from account ac where ac.customer_id not in (select a.customer_id from account a where a.account_type='mortgage') and CONCAT(ac.account_no, '') Like %:accountNo%")
	List<Account> getAccountsByPartialAccountNo(@Param("accountNo")  String accountNo);

}