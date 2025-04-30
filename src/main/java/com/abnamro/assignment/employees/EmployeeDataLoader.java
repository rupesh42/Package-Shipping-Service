package com.abnamro.assignment.employees;

import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class EmployeeDataLoader {

	private final EmployeeRepository employeeRepository;

	public EmployeeDataLoader(EmployeeRepository employeeService) {
		this.employeeRepository = employeeService;
	}

	@PostConstruct
	public void init() throws Exception {
		Employee employee1 = new Employee();
		employee1.setEmployeeID("C47240");
		employee1.setPostCode("1183 KE");
		employee1.setStreetName("Camera Obsuralaan");
		employee1.setHouseNumber(234);

		Employee employee2 = new Employee();
		employee2.setEmployeeID("C48546");
		employee2.setPostCode("1184 AB");
		employee2.setStreetName("Krongenberg");
		employee2.setHouseNumber(20);

		employeeRepository.save(employee1);
		employeeRepository.save(employee2);
	}
}
