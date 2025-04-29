package com.abnamro.assignment.PackageShippingService.send;

import java.util.List;

public interface ShippingOrderService {

	public ShippingOrder createShippingOrder(ShippingOrder shippingOrder);

	public List<ShippingOrder> getAllShippingOrders();

	public ShippingOrder getShippingOrderById(Long id);

	public void updateShippingOrderStatus(Long id, String status);

	public void receiveShippingOrder(Long id, String employeeID);

}
