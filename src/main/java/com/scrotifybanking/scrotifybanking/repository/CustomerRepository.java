package com.scrotifybanking.scrotifybanking.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scrotifybanking.scrotifybanking.entity.Customer;
import org.springframework.stereotype.Repository;

/**
 * Customer Repository has one method.
 *
 * @author anishaR
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

	/**
	 * Customer Repository has one method and it is finding by the mobile No.
	 *
	 * @param mobileNo the mobile no
	 * @return optional optional
	 */
	Customer findByCustomerMobileNo(Long customerMobileNo);

	/**
	 * Find by customer id optional.
	 *
	 * @param customerId the customer id
	 * @return the optional
	 */
	Optional<Customer> findByCustomerId(Long id);

}