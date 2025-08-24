package com.lab7sec4.repository;

import org.springframework.data.repository.CrudRepository;

import com.lab7sec4.model.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

}
