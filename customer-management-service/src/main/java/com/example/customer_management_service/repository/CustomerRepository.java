package com.example.customer_management_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.customer_management_service.model.Customer;


public interface CustomerRepository extends JpaRepository<Customer, Long> {
	Optional<Customer> findById(Long id);

}
