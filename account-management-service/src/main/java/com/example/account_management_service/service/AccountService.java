package com.example.account_management_service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.account_management_service.config.RestrictedEmailConfig;
import com.example.account_management_service.exceptions.ResourceNotFoundException;
import com.example.account_management_service.model.Account;
import com.example.account_management_service.model.AccountDetailsDTO;
import com.example.account_management_service.model.CustomerDTO;
import com.example.account_management_service.repository.AccountRepository;


/**
 * Service class to handle account related operations.
 */
@Service
public class AccountService {

	@Autowired
	private AccountRepository accountRepo;

	@Autowired
	private CustomerService customerService;

	@Autowired
	RestrictedEmailConfig restrictedEmailConfig;

	/**
	 * Create a new account for the given customer
	 * 
	 * @param account the account details
	 * @return the create account
	 * @throws IllegalArgumentException if account already exists for given customer.
	 *                                  id.
	 */
	@Transactional
	public Account createAccount(Account account) {
		if (accountRepo.findByCustomerId(account.getCustomerId()) != null) {
			throw new IllegalArgumentException("Account already exists for customerId:" + account.getCustomerId());
		}
		return accountRepo.save(account);
	}


	/**
	 * Adds money to the account of the given customer
	 * 
	 * @param customerId the ID of the customer
	 * @param amount     the amount of money to add
	 * @return the updated account
	 * @throws IllegalArgumentException if the customer ID is invalid or the account
	 *                                  is not found or the email is restricted
	 */
	@Transactional
	public Account addMoney(Long customerId, double amount) {
		CustomerDTO customer = customerService.getCustomerById(customerId);
		if (customer == null) {
			throw new IllegalArgumentException("Invalid customer ID");
		}
		if (restrictedEmailConfig.getEmails().contains(customer.getEmail())) {
			throw new IllegalArgumentException(
					"Customer with email " + customer.getEmail() + " is not allowed to add money");
		}
		Account account = accountRepo.findByCustomerId(customerId);
		if (account != null) {
			if(amount <= 0) {
				throw new IllegalArgumentException("Amount should be greater then zero");
			}
			account.setBalance(account.getBalance() + amount);
			return accountRepo.save(account);
		} else {
			throw new IllegalArgumentException("Account not found for customer ID");
		}

	}

	/**
	 * Withdraws money from the account of the given customer.
	 * 
	 * @param customerId the ID of the customer
	 * @param amount     the amount of money to withdraw
	 * @return the updated account
	 * @throws IllegalArgumentException if the customer ID is invalid or the account
	 *                                  is not found or the balance is insufficient.
	 */
	@Transactional
	public Account withdrawMoney(Long customerId, double amount) {
		CustomerDTO customer = customerService.getCustomerById(customerId);
		if (customer == null) {
			throw new IllegalArgumentException("Invalid customer ID");
		}
		
		if (restrictedEmailConfig.getEmails().contains(customer.getEmail())) {
			throw new IllegalArgumentException(
					"Customer with email " + customer.getEmail() + " is not allowed to withdraw money");
		}
		Account account = accountRepo.findByCustomerId(customerId);
		if (account != null ) {
			if(account.getBalance() < amount) {
				throw new IllegalArgumentException("Insufficent balance");
			}
			account.setBalance(account.getBalance() - amount);
			return accountRepo.save(account);
		}else {
			throw new IllegalArgumentException("Account not found");
		}

	}

	/**
	 * Retrieves the account details along with the customer details for the given
	 * customer ID.
	 * 
	 * @param customerId the ID of the customer
	 * @return the account details and customer details
	 * @throws ResourceNotFoundException if the customer or the account is not found
	 */
	public AccountDetailsDTO getAccountDetails(Long customerId) {
		Account account = accountRepo.findByCustomerId(customerId);
		if (account != null) {
			CustomerDTO customer = customerService.getCustomerById(customerId);
			if (customer != null) {
				AccountDetailsDTO accountDetailsDTO = new AccountDetailsDTO();
				accountDetailsDTO.setAccount(account);
				accountDetailsDTO.setCustomer(customer);
				return accountDetailsDTO;
			}
			throw new ResourceNotFoundException("Customer not found for ID:" + customerId);
		}
		throw new ResourceNotFoundException("Account not found for customer ID:" + customerId);
	}

	/**
	 * Delete the account of the customer for given customer id.
	 * 
	 * @param customerId the id of the customer
	 * @throws ResourceNotFoundException when account not found.
	 */
	@Transactional
	public void deleteAccount(Long customerId) {
		Account account = accountRepo.findByCustomerId(customerId);
		if (account != null) {
			accountRepo.delete(account);
		} else {
			throw new ResourceNotFoundException("Account not found for customer ID:" + customerId);
		}
	}

}
