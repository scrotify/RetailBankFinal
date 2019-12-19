package com.scrotifybanking.scrotifybanking.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The type Customer.
 */
@Table(name = "customer")
@Entity
@NoArgsConstructor
@Data
@SequenceGenerator(name = "seq", initialValue = 1000)
public class Customer implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
	@Column(name = "customer_id")
	private Long customerId;

	@Column(name = "customer_name", nullable = false)
	private String customerName;

	@Column(name = "customer_email")
	private String customerEmail;

	@Column(name = "customer_password")
	private String customerPassword;

	@Column(name = "customer_dob")
	private LocalDate customerDob;
	
	@Column(name = "customer_salary")
	private Double customerSalary;
	
	@Column(name = "customer_age")
	private Integer customerAge;
	
	@Column(name = "customer_role")
	private String customerRole;

	@Column(name = "customer_mobileno")
	private Long customerMobileNo;

	@Column(name = "customer_city")
	private String customerCity;
	 

	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Account> accountList;

	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Beneficiary> beneficiaries;

}