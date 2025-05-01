package com.abnamro.assignment.ship;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abnamro.assignment.utils.EnumValidator;
import com.abnamro.assignment.utils.OrderStatus;

@RestController
@RequestMapping("/api/v1/ship")
public class ShippingOrderController {

	private final ShippingOrderService shippingOrderService;

	public ShippingOrderController(ShippingOrderService shippingOrderService) {
		this.shippingOrderService = shippingOrderService;
	}

	@PostMapping("/create")
	public ResponseEntity<String> createShippingOrder(@RequestBody ShippingOrder shippingOrder) {
		ShippingOrder createdOrder = shippingOrderService.createShippingOrder(shippingOrder);
		String responseMessage = "Shipping has been created with order ID: " + createdOrder.getOrderId();
		return ResponseEntity.status(HttpStatus.CREATED).body(responseMessage);
	}

	@GetMapping("/orders")
	public ResponseEntity<Page<ShippingOrderProjection>> getShippingOrders(Pageable pageable) {
		return ResponseEntity.ok(shippingOrderService.getShippingOrders(pageable));
	}

	@GetMapping("/search")
	public ResponseEntity<List<ShippingOrder>> getShippingOrdersBySenderIDAndStatus(@RequestParam String senderID,
			@RequestParam String orderStatus) {
		List<ShippingOrder> orders = shippingOrderService.getShippingOrdersBySenderIDAndStatus(senderID, orderStatus);
		return orders.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(orders);
	}

	@PutMapping("/updateStatus")
	public ResponseEntity<String> updateShippingOrderStatus(@RequestParam Long orderID,
			@RequestParam @EnumValidator(enumClass = OrderStatus.class) String orderStatus) throws Exception {
		String message = shippingOrderService.updateShippingOrderStatus(orderID, orderStatus);
		return ResponseEntity.ok(message);
	}

}
