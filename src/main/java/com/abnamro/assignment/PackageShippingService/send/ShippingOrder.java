package com.abnamro.assignment.PackageShippingService.send;

import java.time.LocalDateTime;

import com.abnamro.assignment.PackageShippingService.employees.Employee;

import jakarta.persistence.Entity;
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
	private Long id;

	@NotNull
	private String packageName;

	@NotNull
	private String orderId;

	@NotNull
	private Double packageWeight;

	@NotNull
	private String packageSize;

	@NotNull
	private String orderStatus;

	@NotNull
	private LocalDateTime expectedDeliveryDate;

	private LocalDateTime actualDeliveryDateTime;

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
