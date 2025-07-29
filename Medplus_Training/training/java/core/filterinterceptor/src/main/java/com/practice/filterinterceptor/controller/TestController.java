package com.practice.filterinterceptor.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
	
	@GetMapping("/hello")
	public String testMethod() {
		System.out.println("Hello from test controller");
		return "Hello from Test Controller";
	}
}
