package com.example.customer_management_service.model;

import lombok.Data;


@Data
public class Account {
	public enum AccountType{
		SAVINGS,
		LOAN,
		CURRENT
	}
	private Long customerId;
	private Double balance;
	private AccountType accountType;
	

}
