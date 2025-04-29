package com.abnamro.assignment.PackageShippingService.employees;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class EmployeeDataLoader implements CommandLineRunner {

	@Autowired
	private EmployeeService employeeService;

	@Override
	public void run(String... args) throws Exception {
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

		employeeService.saveEmployee(employee1);
		employeeService.saveEmployee(employee2);
	}
}
