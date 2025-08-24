package com.lab7sec4.web;

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

import com.lab7sec4.model.*;
import com.lab7sec4.service.CustomerService;
@RestController
@RequestMapping("/api/customers")
public class CustomerRestController {

	@Autowired
	private CustomerService custService;
	
	@GetMapping
	public ResponseEntity<List<Customer>> getAllCustomer(){
		List <Customer> customers = custService.getCustomerList();
		return new ResponseEntity<>(customers,HttpStatus.OK);
	}
	@GetMapping("/{id}")
	public ResponseEntity<Customer> getCustomerById(@PathVariable Long id){
		Customer cust = custService.getCustomerById(id);
		return new ResponseEntity<>(cust,HttpStatus.OK);
	}
	@PostMapping
	public Customer addCustomer(@RequestBody Customer cust) {
		 Customer custNew = custService.addCustomer(cust);
		 return custNew;
	}
	@PutMapping("/{id}")
	public ResponseEntity<Customer> upadateCustomer(@PathVariable Long id, @RequestBody Customer custReq){
		Customer cust = custService.getCustomerById(id);
		if(cust != null) {
			cust.setName(custReq.getName());
			cust.setEmail(custReq.getEmail());
			custService.save(cust);
			return new ResponseEntity<>(cust, HttpStatus.CREATED);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteCustomerById(@PathVariable Long id) {
	  Customer custDel = custService.getCustomerById(id);
	  if(custDel != null) {
		  custService.deleteCustomerById(id);
		  return new ResponseEntity<>("deleted", HttpStatus.OK);
	  }
	  return new ResponseEntity<>(" Customer doesnot Exist",HttpStatus.NOT_FOUND);
  }
}
