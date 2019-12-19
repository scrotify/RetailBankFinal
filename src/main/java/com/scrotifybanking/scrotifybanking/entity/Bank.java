package com.scrotifybanking.scrotifybanking.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "banks")
@NoArgsConstructor
@AllArgsConstructor
@Data
@SequenceGenerator(name = "seq", initialValue = 2000)
public class Bank implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    @Column(name = "bank_id")
    private Long bankId;

    @Column(name = "bank_branch")
    private String bankBranch;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "bank_ifsccode")
    private String bankIfscCode;

    @Column(name = "bank_pincode")
    private Long bankPincode;

    @Column(name = "bank_address")
    private Long bankAddress;
}
