package com.example.customer_management_service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.customer_management_service.exceptions.CustomerNotFoundException;
import com.example.customer_management_service.model.Customer;
import com.example.customer_management_service.repository.CustomerRepository;
import com.example.customer_management_service.service.AccountService;
import com.example.customer_management_service.service.CustomerService;

/**
 * Test the CustomerService class
 */
class CustomerServiceTest {
	
	@InjectMocks
	private CustomerService customerService;
	
	@Mock
	private CustomerRepository customerRepo;
	
	@Mock
	private AccountService accountService;

	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	void addCustomer_ShouldReturnSavedCustomer() {
		Customer customer = new Customer();
		customer.setId(1L);
		customer.setName("Test User");
		
		when(customerRepo.save(any(Customer.class))).thenReturn(customer);
		Customer savedCustomer = customerService.addCustomer(customer);
		
		assertNotNull(savedCustomer);
		assertEquals(1L, savedCustomer.getId());
		assertEquals("Test User", savedCustomer.getName());
	}
	
	@Test
	void getAllCustomer_ShouldReturnCustomerList() {
		Customer customer1 = new Customer();
		customer1.setId(1L);
		customer1.setName("Test User1");
		
		Customer customer2 = new Customer();
		customer2.setId(2L);
		customer2.setName("Test User2");
		
		List<Customer> customerList = new ArrayList<>();
		customerList.add(customer1);
		customerList.add(customer2);
		
		when(customerRepo.findAll()).thenReturn(customerList);
		
		List<Customer> savedCustomerList = customerService.getAllCustomer();
		
		assertNotNull(savedCustomerList);
		assertEquals(1L, savedCustomerList.get(0).getId());
		assertEquals("Test User1", savedCustomerList.get(0).getName());
	}
	
	@Test
	void getCustomerById_ShouldReturnCustomer() {
		Customer customer = new Customer();
		customer.setId(1L);
		customer.setName("Test User1");
			
		when(customerRepo.findById(1L)).thenReturn(Optional.of(customer));
		
		Customer result = customerService.getCustomerById(1L);
		
		assertNotNull(result);
		assertEquals(1L, result.getId());
		assertEquals("Test User1", result.getName());	
	}
	
	@Test
	void getCustomerById_ShouldThrowCustomerNotFoundException() {
			
		when(customerRepo.findById(1L)).thenReturn(Optional.empty());
		
		assertThrows(CustomerNotFoundException.class, ()-> customerService.getCustomerById(1L));
	}
	
	@Test
	void updateCustomer_ShouldReturnUpdatedCustomer() {
		Customer existingCustomer = new Customer();
		existingCustomer.setId(1L);
		existingCustomer.setName("Test User1");

		Customer updatedCustomer = new Customer();
		updatedCustomer.setId(1L);
		updatedCustomer.setName("Test User2");

		when(customerRepo.findById(1L)).thenReturn(Optional.of(existingCustomer));
		when(customerRepo.save(existingCustomer)).thenReturn(existingCustomer);

		Customer result = customerService.updateCustomer(1L, updatedCustomer);

		assertNotNull(result);
		assertEquals(1L, result.getId());
		assertEquals("Test User2", result.getName());
	}
	
	@Test
	void updateCustomer_ShouldThrowCustomerNotFoundException() {
		
		when(customerRepo.findById(1L)).thenReturn(Optional.empty());
		
		Customer updatedCustomer = new Customer();
		updatedCustomer.setId(1L);
		updatedCustomer.setName("Test User2");
		
		assertThrows(CustomerNotFoundException.class, ()-> customerService.updateCustomer(1L, updatedCustomer));
	}
	
	@Test
	void deleteCustomer_ShouldDeleteCustomerAndAccount() {
		Customer customer = new Customer();
		customer.setId(1L);
		customer.setName("Test User2");
		
		when(customerRepo.findById(1L)).thenReturn(Optional.of(customer));
		doNothing().when(accountService).deleteAccount(1L);
		doNothing().when(customerRepo).deleteById(1L);
		
		customerService.deleteCustomer(1L);
		
		verify(customerRepo, times(1)).findById(1L);
		verify(accountService, times(1)).deleteAccount(1L);
		verify(customerRepo, times(1)).deleteById(1L);
	}
	
	@Test
	void deleteCustomer_ShouldThrowCustomerNotFoundException() {
		when(customerRepo.findById(1L)).thenReturn(Optional.empty());
		
		assertThrows(CustomerNotFoundException.class, ()-> customerService.deleteCustomer(1L));
	}
	
	
	

}
