package com.abnamro.assignment.employees;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {

	private final EmployeeRepository employeeRepository;

	public EmployeeController(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}

	@GetMapping
	public List<Employee> getAllEmployees() {
		return employeeRepository.findAll();
	}

	@GetMapping("/{employeeID}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable String employeeID) {
		Optional<Employee> employee = employeeRepository.findByEmployeeID(employeeID);
		return employee.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

	}

}
