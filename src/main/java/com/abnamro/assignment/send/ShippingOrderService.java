package com.abnamro.assignment.send;

import java.util.List;

public interface ShippingOrderService {

	public ShippingOrder createShippingOrder(ShippingOrder shippingOrder);

	public List<ShippingOrder> getAllShippingOrders();

	public ShippingOrder getShippingOrderById(Long id);

	public String updateShippingOrderStatus(Long id, String status) throws Exception;

	public void receiveShippingOrder(Long id, String employeeID) throws Exception;

}
