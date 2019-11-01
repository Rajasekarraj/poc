package com.example.reactivecouchbase.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import com.example.reactivecouchbase.model.Employee;
import com.example.reactivecouchbase.model.User;
import com.example.reactivecouchbase.repository.EmployeeRepository;
import com.example.reactivecouchbase.repository.UserRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class ReactiveWebController {
	
	@Autowired
	EmployeeRepository employeeRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@PostMapping("/add-employee")
	public Mono<Employee> addEmployee(@RequestBody Employee employee) {
		return employeeRepository.save(employee);
	}
	
	@GetMapping("/get-employee/{id}")
	public Mono<Employee> getEmployee(@PathVariable int id) {
		return employeeRepository.findById(id);
	}
	
	@GetMapping("/get-all-employee")
	public Flux<Employee> getAllEmployee() {
		return employeeRepository.findAll();
	}
	
	@PostMapping("/add-user")
	public Mono<User> addUser(@RequestBody User user) {
		return userRepository.save(user);
	}
}
