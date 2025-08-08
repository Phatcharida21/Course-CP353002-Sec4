package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages="com.example") //service, controller, model
//@ComponentScan(basePackecge={"com.example.service", "com.example.controller, "com.example.model"})
public class AutowiredApplication {

	public static void main(String[] args) {
		SpringApplication.run(AutowiredApplication.class, args);
	}

}
