package com.scrotifybanking.scrotifybanking.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scrotifybanking.scrotifybanking.entity.Customer;

/**
 * Customer Repository has one method.
 *
 * @author anishaR
 */
public interface CustomerRepository extends JpaRepository<Customer, Long> {

	/**
	 * Customer Repository has one method and it is finding by the mobile No.
	 *
	 * @param mobileNo the mobile no
	 * @return optional optional
	 */
	Customer findByCustomerMobileNo(Long customerMobileNo);

	/**
	 * Find by account type customer.
	 *
	 * @param accountType the account type
	 * @return the customer
	 */
	Customer findByAccountType(String accountType);

	/**
	 * Find by customer id optional.
	 *
	 * @param customerId the customer id
	 * @return the optional
	 */
	Optional<Customer> findByCustomerId(Long customerId);

}