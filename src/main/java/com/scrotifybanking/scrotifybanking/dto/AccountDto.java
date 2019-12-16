package com.scrotifybanking.scrotifybanking.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AccountDto {

    private Long accountNo;

    private String accountType;

    private String accountStatus;

    private Double availableBalance;

    private Long  customerId;




}
