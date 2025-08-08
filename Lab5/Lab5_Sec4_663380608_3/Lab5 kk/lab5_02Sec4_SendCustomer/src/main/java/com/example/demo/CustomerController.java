package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.model.Customer;

@Controller
public class CustomerController {
	@GetMapping("/customer")
	public String getCustomer(Model model) {
		Customer customer = new Customer(6083L, "Phatcharida Fuesngarrom");
		model.addAttribute("customer", customer);
		return "customer"; //customer.html
	}
	

}
