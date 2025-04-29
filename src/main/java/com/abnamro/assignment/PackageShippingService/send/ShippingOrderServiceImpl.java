package com.abnamro.assignment.PackageShippingService.send;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abnamro.assignment.PackageShippingService.employees.Employee;
import com.abnamro.assignment.PackageShippingService.employees.EmployeeRepository;

@Service
public class ShippingOrderServiceImpl implements ShippingOrderService {

	@Autowired
	private ShippingOrderRepository shippingOrderRepository;

	@Autowired
	private EmployeeRepository employeeRepository;

	@Override
	public ShippingOrder createShippingOrder(ShippingOrder shippingOrder) {
		shippingOrder.setOrderStatus("InProgress");
		shippingOrder.setExpectedDeliveryDate(LocalDateTime.now().plusDays(2));
		Employee receiver = employeeRepository.findById(shippingOrder.getReceiverID().getEmployeeID())
				.orElseThrow(() -> new RuntimeException("Receiver not found"));
		shippingOrder.setStreetName(receiver.getStreetName());
		shippingOrder.setHouseNumber(receiver.getHouseNumber());
		shippingOrder.setExtention(receiver.getExtention());
		return shippingOrderRepository.save(shippingOrder);
	}

	@Override
	public List<ShippingOrder> getAllShippingOrders() {
		return shippingOrderRepository.findAll();
	}

	@Override
	public ShippingOrder getShippingOrderById(Long id) {
		return shippingOrderRepository.findById(id).orElse(null);
	}

	@Override
	public void updateShippingOrderStatus(Long id, String status) {
		ShippingOrder shippingOrder = getShippingOrderById(id);
		if (shippingOrder != null) {
			shippingOrder.setOrderStatus(status);
			if ("DELIVERED".equals(status)) {
				shippingOrder.setActualDeliveryDateTime(LocalDateTime.now());
			}
			shippingOrderRepository.save(shippingOrder);
		}
	}

	@Override
	public void receiveShippingOrder(Long id, String employeeID) {
		updateShippingOrderStatus(id, "DELIVERED");
	}

}
