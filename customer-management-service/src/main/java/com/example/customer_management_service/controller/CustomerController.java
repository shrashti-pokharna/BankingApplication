package com.example.customer_management_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.customer_management_service.model.Customer;
import com.example.customer_management_service.service.CustomerService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
@RequestMapping("/customers")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	/**
	 * Creates a new customer
	 * 
	 * @param customer the customer details
	 * @return the created customer
	 */
	@PostMapping
	@CircuitBreaker(name = "newAccountBreaker", fallbackMethod = "newAccountFallback")
	public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer) {
		Customer customer1 = customerService.addCustomer(customer);
		return ResponseEntity.status(HttpStatus.CREATED).body(customer1);
	}

	/**
	 * Fallback method if customer was not created
	 * 
	 * @param customer the customer details
	 * @param ex       the exception that has occured.
	 * @return the dummy customer
	 */
	public ResponseEntity<Customer> newAccountFallback(@RequestBody Customer customer, Exception ex) {
		Customer dummyCustomer = Customer.builder().email("dummyEmail@gmail.com").name("Dummy").build();
		return new ResponseEntity<>(dummyCustomer, HttpStatus.OK);

	}

	/**
	 * Get all customers present in the database.
	 * 
	 * @return the list of the cutsomers.
	 */
	@GetMapping
	public ResponseEntity<List<Customer>> getAllCustomers() {
		List<Customer> allCustomers = customerService.getAllCustomer();
		return ResponseEntity.ok().body(allCustomers);
	}

	/**
	 * Retrieves the customer details with the given customer id
	 * 
	 * @param id the unique id of the customer
	 * @return the customer details
	 */
	@GetMapping("/{id}")
	public ResponseEntity<Customer> getCustomerByid(@PathVariable Long id) {
		Customer customer = customerService.getCustomerById(id);
		return ResponseEntity.ok().body(customer);
	}

	/**
	 * Updates the customer with the updated details
	 * 
	 * @param id              the unique id of the customer.
	 * @param customerDetails the updated customer details.
	 * @return the updated customer.
	 */
	@PutMapping("/{id}")
	public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @RequestBody Customer customerDetails) {
		Customer updatedCustomer = customerService.updateCustomer(id, customerDetails);
		return ResponseEntity.ok(updatedCustomer);
	}

	/**
	 * Delete the customer and attached account
	 * 
	 * @param id the unique id of the customer
	 * @return
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
		customerService.deleteCustomer(id);
		return ResponseEntity.noContent().build();
	}

}
