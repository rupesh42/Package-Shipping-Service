package com.abnamro.assignment.ship;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.abnamro.assignment.employees.Employee;
import com.abnamro.assignment.employees.EmployeeRepository;
import com.abnamro.assignment.exceptions.InvalidStatusCode;
import com.abnamro.assignment.exceptions.OrderNotFoundException;
import com.abnamro.assignment.mappers.ResponseMappers;
import com.abnamro.assignment.utils.OrderStatus;
import com.abnamro.assignment.utils.PackageSize;

import jakarta.validation.Valid;

@Service
public class ShippingOrderServiceImpl implements ShippingOrderService {

	private final ShippingOrderRepository shippingOrderRepository;

	private final EmployeeRepository employeeRepository;

	private ResponseMappers mappers;

	public ShippingOrderServiceImpl(ShippingOrderRepository shippingOrderRepository,
			EmployeeRepository employeeRepository) {
		this.shippingOrderRepository = shippingOrderRepository;
		this.employeeRepository = employeeRepository;
	}

	@Override
	public ShippingOrder createShippingOrder(ShippingOrder shippingOrder) {
		shippingOrder.setOrderStatus(OrderStatus.IN_PROGRESS);
		shippingOrder.setOrderDate(LocalDate.now());

		shippingOrder.setExpectedDeliveryDate(LocalDate.now().plusDays(2));
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
	public Page<ShippingOrderProjection> getShippingOrders(Pageable pageable) {

		List<ShippingOrderProjection> projectionList = mappers.INSTANCE
				.getOrdersListToDto(shippingOrderRepository.findAll());
		final int start = (int) pageable.getOffset();
		final int end = Math.min((start + pageable.getPageSize()), projectionList.size());
		return new PageImpl<ShippingOrderProjection>(projectionList.subList(start, end), pageable,
				projectionList.size());

	}

	@Override
	public ShippingOrderProjection getShippingOrderById(Long orderId) {

		return mappers.INSTANCE.getOrdersToDto(shippingOrderRepository.findById(orderId)
				.orElseThrow(() -> new OrderNotFoundException("Order does not exist with id " + orderId)));
	}

	@Override
	public List<ShippingOrder> getShippingOrdersBySenderIDAndStatus(String senderID, String orderStatus) {
		if (!isValidOrderStatus(orderStatus)) {
			throw new InvalidStatusCode(
					"Order status is invalid. Correct status can be IN_PROGRESS, DELIVERED, or SENT");
		}
		List<ShippingOrder> orders = shippingOrderRepository.findBySenderIDAndOrderStatus(senderID,
				OrderStatus.valueOf(orderStatus));
		if (orders.isEmpty()) {
			throw new OrderNotFoundException(
					"No shipping orders found for sender ID: " + senderID + " with status: " + orderStatus);
		}
		return orders;
	}

	@Override
	public String updateShippingOrderStatus(Long orderId, @Valid String orderStatus) {

		ShippingOrder shippingOrder = shippingOrderRepository.findById(orderId)
				.orElseThrow(() -> new OrderNotFoundException("Order does not exist with id " + orderId));

		shippingOrder.setOrderStatus(OrderStatus.valueOf(orderStatus));

		if (OrderStatus.DELIVERED.name().toUpperCase().equals(orderStatus)) {
			shippingOrder.setActualDeliveryDate(LocalDate.now());
		}
		shippingOrderRepository.save(shippingOrder);
		return "Order status has been updated to " + orderStatus;
	}

	private boolean isValidOrderStatus(String orderStatus) {
		try {
			OrderStatus.valueOf(orderStatus);
			return true;
		} catch (IllegalArgumentException e) {
			return false;
		}
	}

}
