package com.example.customer_management_service.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.customer_management_service.exceptions.CustomerNotFoundException;
import com.example.customer_management_service.model.Customer;
import com.example.customer_management_service.repository.CustomerRepository;

/**
 * Service class to handle customer related operations.
 */
@Service
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepo;
	
	@Autowired
	private AccountService accountService;

	/**
	 * Adds a new customer 
	 * @param customer the customer to add
	 * @return the added customer
	 */
	@Transactional
	public Customer addCustomer(Customer customer) {
		return customerRepo.save(customer);
		
	}

	/**
	 * Get all the customers
	 * @return the list of customers
	 */
	public List<Customer> getAllCustomer() {
		return customerRepo.findAll();
	}

	/**
	 * Finds the customer for the given customer id.
	 * @param id the id of the customer to find.
	 * @return the retrieved customer
	 * throws CustomerNotFoundException if no customer with the given ID is found.
	 */
	public Customer getCustomerById(Long id) {
		return customerRepo.findById(id).orElseThrow(() -> new CustomerNotFoundException("Customer with ID" + id + " not found."));
	}

	/**
	 * Update the customer details for the given customer Id.
	 * @param id the id of the customer.
	 * @param customerDetails the update customer details
	 * @return the updated customer
	 * @throws CustomerNotFoundException if no customer with the given ID is found.
	 */
	@Transactional
	public Customer updateCustomer(Long id, Customer customerDetails) {
		Customer customer = customerRepo.findById(id)
				.orElseThrow(() -> new CustomerNotFoundException("Customer with given id not present to update"));
		customer.setName(customerDetails.getName());
		customer.setPhoneNumber(customerDetails.getPhoneNumber());
		customer.setEmail(customerDetails.getEmail());
		return customerRepo.save(customer);
	}

	/**
	 * Delete the customer and his account
	 * @param id the id of the customer.
	 * @throws CustomerNotFoundException if no customer with the given ID is found to delete.
	 */
	@Transactional
	public void deleteCustomer(Long id) {
		Customer customer = customerRepo.findById(id)
				.orElseThrow(() -> new CustomerNotFoundException("Customer with given id not present to delete"));

		customerRepo.deleteById(customer.getId());
		accountService.deleteAccount(customer.getId());
	}

}
