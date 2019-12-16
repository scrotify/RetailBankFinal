package com.scrotifybanking.scrotifybanking.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

/**
 * The type Customer request dto.
 */
@Getter
@Setter
public class CustomerRequestDto {

	
	private String name;
	private String password;
	private String emailId;
	private LocalDate dob;
	private Long mobileNo;
	private String city;
	private String role;
	private Double Salary;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public LocalDate getDob() {
		return dob;
	}

	public void setDob(LocalDate dob) {
		this.dob = dob;
	}

	public Long getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(Long mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Double getSalary() {
		return Salary;
	}

	public void setSalary(Double salary) {
		Salary = salary;
	}
}
