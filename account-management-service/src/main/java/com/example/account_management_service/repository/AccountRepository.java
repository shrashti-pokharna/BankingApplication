package com.example.account_management_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.account_management_service.model.Account;

/**
 * Repository interface for performing CRUD operations on Account entities.
 */
public interface AccountRepository extends JpaRepository<Account, Long> {

	/**
	 * Finds an account by the customer ID.
	 * 
	 * @param customerId the unique id of the customer.
	 * @return the account if found or empty if not found.
	 */
	Account findByCustomerId(Long customerId);

}
