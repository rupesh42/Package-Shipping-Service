package com.abnamro.assignment.ship;

import java.time.LocalDate;

import com.abnamro.assignment.employees.Employee;
import com.abnamro.assignment.utils.OrderStatus;
import com.abnamro.assignment.utils.PackageSize;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@ToString
public class ShippingOrder {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long orderId;

	@NotNull
	private String packageName;

	private String message;

	@NotNull
	private Double packageWeight;

	@Enumerated(EnumType.STRING)
	private PackageSize packageSize;

	@NotNull
	@Enumerated(EnumType.STRING)
	private OrderStatus orderStatus;

	@NotNull
	private LocalDate orderDate;

	@NotNull
	private LocalDate expectedDeliveryDate;

	private LocalDate actualDeliveryDate;

	@ManyToOne
	@NotNull
	@JoinColumn(name = "sender_id", referencedColumnName = "employeeID")
	private Employee senderID;

	@ManyToOne
	@NotNull
	@JoinColumn(name = "receiver_id", referencedColumnName = "employeeID")
	private Employee receiverID;

	@NotNull
	private String StreetName;

	@NotNull
	private Integer houseNumber;

	private String extention;

}
