package com.example.account_management_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

/**
 * Entity representing a account of customer in the banking application. A
 * Account has customer id and it's account balance.
 */
@Entity
@Data
public class Account {
	
	public enum AccountType{
		SAVINGS,
		LOAN,
		CURRENT
	}
	/**
	 * The unique identifier for the account
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * id of the customer
	 */
	private Long customerId;
	
	/**
	 * AccountType
	 */
	@Enumerated(EnumType.STRING)
	private AccountType accountType;

	/**
	 * balance of the customer's account
	 */
	private Double balance;

}
