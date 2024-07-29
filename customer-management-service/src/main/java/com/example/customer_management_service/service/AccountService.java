package com.example.customer_management_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.customer_management_service.model.Account;
import com.example.customer_management_service.model.Account.AccountType;

/**
 * Service class for account microservice connection
 */
@Service
public class AccountService {
	
	@Autowired
	private RestTemplate restTemplate;
	
	private static final String ACCOUNT_SERVICE_URL = "http://ACCOUNT-MANAGEMENT-SERVICE/accounts";	
	
	/**
	 * Delete the account of the deleted customer
	 * @param customerId the customer id of the deleted customer
	 */
	public void deleteAccount(Long customerId) {
		String url = ACCOUNT_SERVICE_URL + "/" + customerId;
		restTemplate.delete(url);
	}

}
