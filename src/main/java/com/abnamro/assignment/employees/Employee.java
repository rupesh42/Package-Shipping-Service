package com.abnamro.assignment.employees;

import java.util.ArrayList;
import java.util.List;

import com.abnamro.assignment.ship.ShippingOrder;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@ToString
public class Employee {

	@Id
	private String employeeID;

	@Pattern(regexp = "\\d{4} [A-Z]{2}", message = "Invalid postal code format")
	private String postCode;

	@NotNull
	private String streetName;

	@NotNull
	private Integer houseNumber;

	private String extention;

	@JsonBackReference
	@OneToMany(mappedBy = "senderID", cascade = CascadeType.PERSIST, orphanRemoval = true)
	private List<ShippingOrder> shippingOrders = new ArrayList<>();

}
