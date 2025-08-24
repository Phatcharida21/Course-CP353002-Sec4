package com.example.services;


import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.model.Customer;

@Service
public class CustomerServices {
	public Customer getCustomerById(Long id) {
		return new Customer(id,"Lisa");
	}
	
	public Customer getCustomerByName(Long id, String name) {
		return new Customer(id, name);
	}
	
	public List<Customer> getCustomerList() {
		List<Customer> customers = new ArrayList<>();
		customers.add(getCustomerByName(Long.valueOf(1),"Anna"));
		customers.add(getCustomerByName(Long.valueOf(2),"Giant"));
		customers.add(getCustomerByName(Long.valueOf(3),"Sira"));
		customers.add(getCustomerByName(Long.valueOf(4),"Nobita"));
		return customers;
	}

	

}
