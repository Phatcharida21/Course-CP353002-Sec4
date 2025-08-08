package com.example.demo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.model.Customer;

@Controller
public class CustomerController {

    //private final AppConfig appConfig;
	
	AnnotationConfigApplicationContext context;
	public CustomerController(AppConfig appConfig) {
		context = new AnnotationConfigApplicationContext(AppConfig.class);
		//this.appConfig = appConfig;
	}
	
	@GetMapping("/customer")
	@ResponseBody
	public String getCustomerInfo() {
		String text = "Hello!";
		Customer cust;
		cust = (Customer) context.getBean("customeragrs",Long.valueOf(15),"JJ");
		text = text + "<br>" + cust.getId() + " " + cust.getName();
		
		Customer cust2;
		cust2 = (Customer) context.getBean("customer");
		cust2.setId(Long.valueOf(15));
		cust2.setName("Phatcharida");
		text = text + "<br>" + cust2.getId() + " " + cust2.getName();
		
		return text; //customer.html
	}
	@GetMapping("/customers")
	public String getCustomerList(Model model) {
		List<Customer> listcust = new ArrayList<>();
		Customer temp;
		temp = (Customer) context.getBean("customeragrs",Long.valueOf(11),"JA");
		listcust.add(temp);
		
		temp = (Customer) context.getBean("customeragrs",Long.valueOf(12),"JB");
		listcust.add(temp);
		
		temp = (Customer) context.getBean("customeragrs",Long.valueOf(13),"JC");
		listcust.add(temp);
		
		model.addAttribute("customers",listcust); 
		//th:ecth="customer:${customers}"
		//th:text="${customers.id}"
		//th:text="${customers.name}"
		
		
		return "customers"; // customers.html
	}
	
}
