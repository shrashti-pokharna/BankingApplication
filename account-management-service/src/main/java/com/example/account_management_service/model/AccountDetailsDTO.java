package com.example.account_management_service.model;

import lombok.Data;

/**
 * DTO for fetching account detail including related customer information.
 */
@Data
public class AccountDetailsDTO {

	/**
	 * customer account details
	 */
	private Account account;

	/**
	 * customer details
	 */
	private CustomerDTO customer;

}
