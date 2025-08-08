package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;




@Controller
public class Lab5_01controller {
	
	
	@GetMapping("/hello")
	public String greeting(Model model) {
		String yourfirstName = "Phatcharida";
		model.addAttribute("fname", yourfirstName); //fname = youfirstName
		
		String yourlastName = "Fueangarrom";
		model.addAttribute("lname", yourlastName); //lname = yourlastName
		
		return "hello"; //hello.html
	}

}
