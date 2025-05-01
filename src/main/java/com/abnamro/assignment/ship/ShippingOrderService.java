package com.abnamro.assignment.ship;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ShippingOrderService {

	public ShippingOrder createShippingOrder(ShippingOrder shippingOrder);

	public ShippingOrderProjection getShippingOrderById(Long orderId);

	public Page<ShippingOrderProjection> getShippingOrders(Pageable pageable);

	public List<ShippingOrder> getShippingOrdersBySenderIDAndStatus(String senderID, String orderStatus);

	public String updateShippingOrderStatus(Long id, String status);

}
