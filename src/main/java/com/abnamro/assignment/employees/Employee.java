package com.abnamro.assignment.employees;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@ToString
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String employeeID;

	@Pattern(regexp = "\\d{4} [A-Z]{2}", message = "Invalid postal code format")
	private String postCode;

	@NotNull
	private String streetName;

	@NotNull
	private Integer houseNumber;

	private String extention;

}
