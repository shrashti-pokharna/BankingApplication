package com.example.account_management_service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.account_management_service.config.RestrictedEmailConfig;
import com.example.account_management_service.exceptions.ResourceNotFoundException;
import com.example.account_management_service.model.Account;
import com.example.account_management_service.model.AccountDetailsDTO;
import com.example.account_management_service.model.CustomerDTO;
import com.example.account_management_service.repository.AccountRepository;
import com.example.account_management_service.service.AccountService;
import com.example.account_management_service.service.CustomerService;

public class AccountServiceTest {

	@InjectMocks
	private AccountService accountService;

	@Mock
	private AccountRepository accountRepo;

	@Mock
	private CustomerService customerService;

	@Mock
	private RestrictedEmailConfig restrictedEmailConfig;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void createAccountForCustomer_ShouldCreateAndReturnAccount() {
		Account account = new Account();
		account.setId(1L);
		account.setCustomerId(1L);

		when(accountRepo.findByCustomerId(1L)).thenReturn(null);
		when(accountRepo.save(any(Account.class))).thenReturn(account);

		Account createdAccount = accountService.createAccount(account);

		assertNotNull(createdAccount);
		assertEquals(1L, createdAccount.getId());
		assertEquals(1L, createdAccount.getCustomerId());
		verify(accountRepo, times(1)).save(any(Account.class));
	}

	@Test
	void addMoneyToAccount_WithRestrictedEmail_ShouldThrowException() {
		CustomerDTO customer = new CustomerDTO();
		customer.setId(1L);
		customer.setName("test User");
		customer.setEmail("test1@gmail.com");

		when(customerService.getCustomerById(1L)).thenReturn(customer);
		when(restrictedEmailConfig.getEmails()).thenReturn(Collections.singletonList("test1@gmail.com"));

		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			accountService.addMoney(1L, Double.valueOf(20000));
		});

		String expectedMessage = "Customer with email test1@gmail.com is not allowed to add money";
		String actualMessage = exception.getMessage();

		assertEquals(actualMessage, expectedMessage);

	}

	@Test
	void addMoneyToAccount_ShouldAddMoneyToAccount() {
		CustomerDTO customer = new CustomerDTO();
		customer.setId(1L);
		customer.setName("test User");
		customer.setEmail("test2@gmail.com");

		Account account = new Account();
		account.setId(1L);
		account.setCustomerId(1L);
		account.setBalance(Double.valueOf(20000));

		when(customerService.getCustomerById(1L)).thenReturn(customer);
		when(accountRepo.findByCustomerId(1L)).thenReturn(account);
		when(accountRepo.save(any(Account.class))).thenReturn(account);
		when(restrictedEmailConfig.getEmails()).thenReturn(Collections.singletonList("test1@gmail.com"));

		Account updatedAccount = accountService.addMoney(1L, Double.valueOf(20000));

		assertNotNull(updatedAccount);
		assertEquals(Double.valueOf(40000), updatedAccount.getBalance());
		verify(accountRepo, times(1)).save(any(Account.class));

	}

	@Test
	void withdrawMoneyToAccount_WithRestrictedEmail_ShouldThrowException() {
		CustomerDTO customer = new CustomerDTO();
		customer.setId(1L);
		customer.setName("test User");
		customer.setEmail("test1@gmail.com");

		when(customerService.getCustomerById(1L)).thenReturn(customer);
		when(restrictedEmailConfig.getEmails()).thenReturn(Collections.singletonList("test1@gmail.com"));

		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			accountService.withdrawMoney(1L, Double.valueOf(20000));
		});

		String expectedMessage = "Customer with email test1@gmail.com is not allowed to withdraw money";
		String actualMessage = exception.getMessage();

		assertEquals(actualMessage, expectedMessage);

	}

	@Test
	void withdrawMoneyToAccount_ShouldWithdrawMoneyFromAccount() {
		CustomerDTO customer = new CustomerDTO();
		customer.setId(1L);
		customer.setName("test User");
		customer.setEmail("test2@gmail.com");

		Account account = new Account();
		account.setId(1L);
		account.setCustomerId(1L);
		account.setBalance(Double.valueOf(30000));

		when(customerService.getCustomerById(1L)).thenReturn(customer);
		when(accountRepo.findByCustomerId(1L)).thenReturn(account);
		when(accountRepo.save(any(Account.class))).thenReturn(account);
		when(restrictedEmailConfig.getEmails()).thenReturn(Collections.singletonList("test1@gmail.com"));

		Account updatedAccount = accountService.withdrawMoney(1L, Double.valueOf(20000));

		assertNotNull(updatedAccount);
		assertEquals(Double.valueOf(10000), updatedAccount.getBalance());
		verify(accountRepo, times(1)).save(any(Account.class));

	}

	@Test
	void getAccountDetails_ShouldReturnAccountDetailsWithCustomerDetails() {

		CustomerDTO customer = new CustomerDTO();
		customer.setId(1L);
		customer.setName("test User");
		customer.setEmail("test2@gmail.com");

		Account account = new Account();
		account.setId(1L);
		account.setCustomerId(1L);
		account.setBalance(Double.valueOf(30000));

		AccountDetailsDTO accountDetails = new AccountDetailsDTO();
		accountDetails.setAccount(account);
		accountDetails.setCustomer(customer);

		when(customerService.getCustomerById(1L)).thenReturn(customer);
		when(accountRepo.findByCustomerId(1L)).thenReturn(account);

		AccountDetailsDTO accountDetailsWithCustomer = accountService.getAccountDetails(1L);

		assertNotNull(accountDetailsWithCustomer);
		assertEquals(1L, accountDetailsWithCustomer.getCustomer().getId());
		assertEquals(Double.valueOf(30000), accountDetailsWithCustomer.getAccount().getBalance());

	}

	@Test
	void getAccountDetails_ShouldThrowResourceNotFoundException() {
			
		when(accountRepo.findByCustomerId(1L)).thenReturn(null);
		
		assertThrows(ResourceNotFoundException.class, ()-> accountService.getAccountDetails(1L));
	}

	@Test
	void deleteAccount_ShouldDeleteAccount() {
		Account account = new Account();
		account.setId(1L);
		account.setCustomerId(1L);
		account.setBalance(Double.valueOf(30000));

		when(accountRepo.findByCustomerId(1L)).thenReturn(account);
		doNothing().when(accountRepo).delete(account);

		accountService.deleteAccount(1L);

		verify(accountRepo, times(1)).delete(account);

	}

	@Test
	void deleteAccount_ShouldThrowResourceNotFoundException_whenAccountNotFound() {
        when(accountRepo.findByCustomerId(1L)).thenReturn(null);
		
		assertThrows(ResourceNotFoundException.class, ()-> accountService.getAccountDetails(1L));
		
	}

}
