package com.abnamro.assignment.PackageShippingService.send;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/ship")
public class ShippingOrderController {

	@Autowired
	private ShippingOrderService shippingOrderService;

	@PostMapping("/create")
	public ResponseEntity<ShippingOrder> createShippingOrder(@RequestBody ShippingOrder shippingOrder) {
		ShippingOrder createdOrder = shippingOrderService.createShippingOrder(shippingOrder);
		return ResponseEntity.status(201).body(createdOrder);
	}

	@GetMapping
	public List<ShippingOrder> getAllShippingOrders() {
		return shippingOrderService.getAllShippingOrders();
	}

	@GetMapping("/{id}")
	public ResponseEntity<ShippingOrder> getShippingOrderById(@PathVariable Long id) {
		ShippingOrder order = shippingOrderService.getShippingOrderById(id);
		return order != null ? ResponseEntity.ok(order) : ResponseEntity.notFound().build();
	}

	@PutMapping("/updateStatus")
	public ResponseEntity<Void> updateShippingOrderStatus(@RequestParam Long id, @RequestParam String status) {
		shippingOrderService.updateShippingOrderStatus(id, status);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/receive")
	public ResponseEntity<Void> receiveShippingOrder(@RequestParam Long id, @RequestParam String employeeID) {
		shippingOrderService.receiveShippingOrder(id, employeeID);
		return ResponseEntity.ok().build();
	}
}
