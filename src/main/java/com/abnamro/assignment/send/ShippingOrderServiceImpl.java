package com.abnamro.assignment.send;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Service;

import com.abnamro.assignment.employees.Employee;
import com.abnamro.assignment.employees.EmployeeRepository;
import com.abnamro.assignment.exceptions.ParcelExistsException;
import com.abnamro.assignment.utils.OrderStatus;
import com.abnamro.assignment.utils.PackageSize;

import jakarta.validation.Valid;

@Service
public class ShippingOrderServiceImpl implements ShippingOrderService {

	private final ShippingOrderRepository shippingOrderRepository;

	private final EmployeeRepository employeeRepository;

	private LocalDateTime currentDateTime = LocalDateTime.now();

	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	public ShippingOrderServiceImpl(ShippingOrderRepository shippingOrderRepository,
			EmployeeRepository employeeRepository) {
		this.shippingOrderRepository = shippingOrderRepository;
		this.employeeRepository = employeeRepository;
	}

	@Override
	public ShippingOrder createShippingOrder(ShippingOrder shippingOrder) {
		shippingOrder.setOrderStatus(OrderStatus.IN_PROGRESS);
		shippingOrder.setExpectedDeliveryDate(currentDateTime.plusDays(2).format(formatter));
		double packageWeight = shippingOrder.getPackageWeight();
		if (packageWeight < 200) {
			shippingOrder.setPackageSize(PackageSize.S);
		} else if (packageWeight >= 200 && packageWeight < 1000) {
			shippingOrder.setPackageSize(PackageSize.M);
		} else if (packageWeight >= 1000 && packageWeight < 10000) {
			shippingOrder.setPackageSize(PackageSize.L);
		} else {
			shippingOrder.setPackageSize(PackageSize.XL);
		}
		Employee sender = employeeRepository.findByEmployeeID(shippingOrder.getSenderID().getEmployeeID())
				.orElseThrow(() -> new RuntimeException("Sender not found"));
		Employee receiver = employeeRepository.findByEmployeeID(shippingOrder.getReceiverID().getEmployeeID())
				.orElseThrow(() -> new RuntimeException("Receiver not found"));
		shippingOrder.setSenderID(sender);
		shippingOrder.setReceiverID(receiver);

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
	public ShippingOrder getShippingOrderById(Long orderId) {
		return shippingOrderRepository.findById(orderId).orElse(null);
	}

	@Override
	public String updateShippingOrderStatus(Long orderId, @Valid String orderStatus) throws Exception {

		if (!OrderStatus.DELIVERED.toString().equals(orderStatus)
				&& !OrderStatus.IN_PROGRESS.toString().equals(orderStatus)
				&& OrderStatus.SENT.toString().equals(orderStatus)) {
			throw new IllegalArgumentException(
					"Order status is invalid. Correct status can be IN_PROGRESS, DELIVERED, or SENT");
		}

		ShippingOrder shippingOrder = getShippingOrderById(orderId);
		if (shippingOrder != null) {
			shippingOrder.setOrderStatus(OrderStatus.valueOf(orderStatus));
			if (OrderStatus.DELIVERED.equals(orderStatus)) {
				shippingOrder.setActualDeliveryDateTime(currentDateTime.format(formatter));
			}
			shippingOrderRepository.save(shippingOrder);
			return "Order status has been updated to " + orderStatus;
		} else {
			throw new Exception("Order not found");
		}
	}

	@Override
	public void receiveShippingOrder(Long orderId, String employeeID) throws Exception {
		ShippingOrder shippingOrder = getShippingOrderById(orderId);
		if (shippingOrder.getOrderStatus().equals(OrderStatus.DELIVERED)) {
			throw new ParcelExistsException(
					"The parcel is already delivered before on " + shippingOrder.getActualDeliveryDateTime());
		}
		if (shippingOrder != null) {
			if (shippingOrder.getReceiverID().getEmployeeID().equals(employeeID)) {
				updateShippingOrderStatus(orderId, OrderStatus.DELIVERED.toString());
			} else {
				throw new ParcelExistsException("Only the receiver can update the order status to DELIVERED.");
			}
		}
	}

}
