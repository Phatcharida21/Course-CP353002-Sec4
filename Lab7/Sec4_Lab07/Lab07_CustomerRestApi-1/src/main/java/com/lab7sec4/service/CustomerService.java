package com.lab7sec4.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lab7sec4.model.*;
import com.lab7sec4.repository.CustomerRepository;
@Service
public class CustomerService {
   @Autowired
  private CustomerRepository repoCust;
  
   public List<Customer> getCustomerList(){
	   List<Customer> customers= (List<Customer>) repoCust.findAll();
	   return customers;
   }
   public Customer getCustomerById(long id) {
	   Customer customer = repoCust.findById(id).get();
	   return customer;
   }
   public void save(Customer cust) {
	   repoCust.save(cust);
   }
   public Customer addCustomer(Customer cust) {
	   Customer custNew = repoCust.save(cust);
	   return custNew;
   }
   public Customer updateCustomer(long id, Customer custNew) {
	   Customer custExist = repoCust.findById(id).orElseThrow( () ->new CustomerNotFoundException(id));
	  custExist.setName(custNew.getName());
	  custExist.setEmail(custNew.getEmail());
	  repoCust.save(custExist);
	  return custExist; 
   }
   public void deleteCustomerById(long id) {
	   Customer cust = repoCust.findById(id).orElseThrow( () ->new CustomerNotFoundException(id));
	   repoCust.delete(cust);
   }
}
