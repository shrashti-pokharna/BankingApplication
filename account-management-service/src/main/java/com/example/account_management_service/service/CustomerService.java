package com.example.account_management_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.example.account_management_service.model.CustomerDTO;

@Service
public class CustomerService {
	
	@Autowired
	private RestTemplate restTemplate;
	
	private static final String CUSTOMER_SERVICE_URL = "http://CUSTOMER-MANAGEMENT-SERVICE/customers";
	
	/**
	 * Get the customer details for the given customer ID by accessing the customer microservice.
	 * @param customerId the ID of the customer
	 * @return the customer details
	 */
	public CustomerDTO getCustomerById(Long customerId) {
		try {
			return restTemplate.getForObject(CUSTOMER_SERVICE_URL + "/" + customerId, CustomerDTO.class);
		}catch(HttpClientErrorException e) {
			return null;
		}
	}

}
