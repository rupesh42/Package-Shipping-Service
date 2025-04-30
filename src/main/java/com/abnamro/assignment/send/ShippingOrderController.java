package com.abnamro.assignment.send;

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

	@GetMapping("/{orderId}")
	public ResponseEntity<ShippingOrder> getShippingOrderById(@PathVariable Long orderId) {
		ShippingOrder order = shippingOrderService.getShippingOrderById(orderId);
		return order != null ? ResponseEntity.ok(order) : ResponseEntity.notFound().build();
	}

	@PutMapping("/updateStatus")
	public ResponseEntity<String> updateShippingOrderStatus(@RequestParam Long orderID,
			@RequestParam String orderStatus) throws Exception {
		String message = shippingOrderService.updateShippingOrderStatus(orderID, orderStatus);
		System.err.println("MESSAGE: " + message);
		return ResponseEntity.ok(message);
	}

	@PostMapping("/receive")
	public ResponseEntity<String> receiveShippingOrder(@RequestParam Long orderID,
			@RequestParam String receiverEmployeeID) throws Exception {
		shippingOrderService.receiveShippingOrder(orderID, receiverEmployeeID);
		return ResponseEntity.ok("Order status has been updated to DELIVERED");
	}

}
