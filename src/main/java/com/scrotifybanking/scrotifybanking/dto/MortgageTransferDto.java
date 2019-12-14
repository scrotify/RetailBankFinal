package com.scrotifybanking.scrotifybanking.dto;

public class MortgageTransferDto {

    private Long accountNumber;
    private String accountType;

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
}
