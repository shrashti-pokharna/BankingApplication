package com.example.account_management_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.account_management_service.model.Account;
import com.example.account_management_service.model.AccountDetailsDTO;
import com.example.account_management_service.service.AccountService;

/**
 * REST controller for handling account related requests.
 */
@RestController
@RequestMapping("/accounts")
public class AccountController {

	@Autowired
	AccountService accountService;

	/**
	 * Create a new account for the given customer
	 * 
	 * @param account the account details containing customer ID.
	 * @return the created account.
	 */
	@PostMapping
	public ResponseEntity<Account> createAccount(@RequestBody Account account) {
		Account createdAccount = accountService.createAccount(account);

		return ResponseEntity.ok(createdAccount);
	}


	/**
	 * Adds money to the specific account
	 * 
	 * @param customerId the id of the customer
	 * @param amount     the amount to be added
	 * @return the updated account
	 */
	@PostMapping("/add-money")
	public ResponseEntity<Account> addMoneyToAccount(@RequestParam Long customerId, @RequestParam double amount) {
		Account updatedAccount = accountService.addMoney(customerId, amount);
		return ResponseEntity.ok(updatedAccount);

	}

	/**
	 * Withdraws money to the specific account
	 * 
	 * @param customerId the id of the customer
	 * @param amount     the amount to be withdraw
	 * @return the updated account
	 */
	@PostMapping("/withdraw-money")
	public ResponseEntity<Account> withdrawMoneyFromAccount(@RequestParam Long customerId,
			@RequestParam double amount) {

		Account updatedAccount = accountService.withdrawMoney(customerId, amount);
		return ResponseEntity.ok(updatedAccount);

	}

	/**
	 * Retrieves the account details along with the customer details for the given
	 * customer ID.
	 * 
	 * @param customerId the ID of the customer
	 * @return the account details and customer details
	 */
	@GetMapping("/{customerId}")
	public ResponseEntity<AccountDetailsDTO> getAccountDetails(@PathVariable Long customerId) {

		AccountDetailsDTO account = accountService.getAccountDetails(customerId);
		return ResponseEntity.ok(account);

	}

	/**
	 * Delete the account of the customer for given customer id.
	 * 
	 * @param customerId the id of the customer
	 */
	@DeleteMapping("/{customerId}")
	public ResponseEntity<Void> deleteAccount(@PathVariable Long customerId) {
		accountService.deleteAccount(customerId);
		return ResponseEntity.ok().build();
	}

}
