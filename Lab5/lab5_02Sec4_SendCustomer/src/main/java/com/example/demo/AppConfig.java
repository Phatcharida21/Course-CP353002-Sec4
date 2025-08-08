package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.example.model.Customer;

@Configuration
@ComponentScan(basePackages="com.example.model")
public class AppConfig {
	@Bean(name = "customeragrs") //context.getBean("customerargs",Long.valueof(15),"JJ");
	@Scope("prototype")
	public Customer createCustomerargs(Long id, String name) {
		return new Customer(id,name); //IOC container to create
	}
	@Bean(name = "customer") //context.getBean("customer");
	@Scope("prototype")
	public Customer createCustomer() {
		return new Customer();
	}

}
